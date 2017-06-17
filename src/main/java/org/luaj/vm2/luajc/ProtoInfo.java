package org.luaj.vm2.luajc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.luaj.vm2.Lua;
import org.luaj.vm2.Print;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.luajc.BasicBlock;
import org.luaj.vm2.luajc.UpvalInfo;
import org.luaj.vm2.luajc.VarInfo;

public class ProtoInfo {

   public final String name;
   public final Prototype prototype;
   public final ProtoInfo[] subprotos;
   public final BasicBlock[] blocks;
   public final BasicBlock[] blocklist;
   public final VarInfo[] params;
   public final VarInfo[][] vars;
   public final UpvalInfo[] upvals;
   public final UpvalInfo[][] openups;


   public ProtoInfo(Prototype var1, String var2) {
      this(var1, var2, (UpvalInfo[])null);
   }

   private ProtoInfo(Prototype var1, String var2, UpvalInfo[] var3) {
      this.name = var2;
      this.prototype = var1;
      this.upvals = var3;
      this.subprotos = var1.p != null && var1.p.length > 0?new ProtoInfo[var1.p.length]:null;
      this.blocks = BasicBlock.findBasicBlocks(var1);
      this.blocklist = BasicBlock.findLiveBlocks(this.blocks);
      this.params = new VarInfo[var1.maxstacksize];

      for(int var4 = 0; var4 < var1.maxstacksize; ++var4) {
         VarInfo var5 = VarInfo.PARAM(var4);
         this.params[var4] = var5;
      }

      this.vars = this.findVariables();
      this.replaceTrivialPhiVariables();
      this.openups = new UpvalInfo[var1.maxstacksize][];
      this.findUpvalues();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("proto \'" + this.name + "\'\n");
      int var2 = 0;

      int var3;
      for(var3 = this.upvals != null?this.upvals.length:0; var2 < var3; ++var2) {
         var1.append(" up[" + var2 + "]: " + this.upvals[var2] + "\n");
      }

      for(var2 = 0; var2 < this.blocklist.length; ++var2) {
         BasicBlock var13 = this.blocklist[var2];
         int var4 = var13.pc0;
         var1.append("  block " + var13.toString());
         this.appendOpenUps(var1, -1);

         for(int var5 = var4; var5 <= var13.pc1; ++var5) {
            this.appendOpenUps(var1, var5);
            var1.append("     ");

            for(int var6 = 0; var6 < this.prototype.maxstacksize; ++var6) {
               VarInfo var7 = this.vars[var6][var5];
               String var8 = var7 == null?"":(var7.upvalue != null?(!var7.upvalue.rw?"[C] ":(var7.allocupvalue && var7.pc == var5?"[*] ":"[]  ")):"    ");
               String var9 = var7 == null?"null   ":String.valueOf(var7);
               var1.append(var9 + var8);
            }

            var1.append("  ");
            ByteArrayOutputStream var14 = new ByteArrayOutputStream();
            PrintStream var15 = Print.ps;
            Print.ps = new PrintStream(var14);

            try {
               Print.printOpCode(this.prototype, var5);
            } finally {
               Print.ps.close();
               Print.ps = var15;
            }

            var1.append(var14.toString());
            var1.append("\n");
         }
      }

      var2 = 0;

      for(var3 = this.subprotos != null?this.subprotos.length:0; var2 < var3; ++var2) {
         var1.append(this.subprotos[var2].toString());
      }

      return var1.toString();
   }

   private void appendOpenUps(StringBuffer var1, int var2) {
      for(int var3 = 0; var3 < this.prototype.maxstacksize; ++var3) {
         VarInfo var4 = var2 < 0?this.params[var3]:this.vars[var3][var2];
         if(var4 != null && var4.pc == var2 && var4.allocupvalue) {
            var1.append("    open: " + var4.upvalue + "\n");
         }
      }

   }

   private VarInfo[][] findVariables() {
      int var1 = this.prototype.code.length;
      int var2 = this.prototype.maxstacksize;
      VarInfo[][] var3 = new VarInfo[var2][];

      int var4;
      for(var4 = 0; var4 < var3.length; ++var4) {
         var3[var4] = new VarInfo[var1];
      }

      for(var4 = 0; var4 < this.blocklist.length; ++var4) {
         BasicBlock var5 = this.blocklist[var4];
         int var6 = var5.prev != null?var5.prev.length:0;

         int var7;
         int var9;
         for(var7 = 0; var7 < var2; ++var7) {
            VarInfo var8 = null;
            if(var6 == 0) {
               var8 = this.params[var7];
            } else if(var6 == 1) {
               var8 = var3[var7][var5.prev[0].pc1];
            } else {
               for(var9 = 0; var9 < var6; ++var9) {
                  BasicBlock var10 = var5.prev[var9];
                  if(var3[var7][var10.pc1] == VarInfo.INVALID) {
                     var8 = VarInfo.INVALID;
                  }
               }
            }

            if(var8 == null) {
               var8 = VarInfo.PHI(this, var7, var5.pc0);
            }

            var3[var7][var5.pc0] = var8;
         }

         label235:
         for(var7 = var5.pc0; var7 <= var5.pc1; ++var7) {
            if(var7 > var5.pc0) {
               propogateVars(var3, var7 - 1, var7);
            }

            int var12 = this.prototype.code[var7];
            int var13 = Lua.GET_OPCODE(var12);
            int var14;
            int var16;
            int var17;
            switch(var13) {
            case 0:
            case 18:
            case 19:
            case 20:
            case 27:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var3[var9][var7].isreferenced = true;
               var3[var17][var7] = new VarInfo(var17, var7);
               break;
            case 1:
            case 2:
            case 4:
            case 5:
            case 10:
               var17 = Lua.GETARG_A(var12);
               var3[var17][var7] = new VarInfo(var17, var7);
               break;
            case 3:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);

               while(true) {
                  if(var17 > var9) {
                     continue label235;
                  }

                  var3[var17][var7] = new VarInfo(var17, var7);
                  ++var17;
               }
            case 6:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var16 = Lua.GETARG_C(var12);
               var3[var9][var7].isreferenced = true;
               if(!Lua.ISK(var16)) {
                  var3[var16][var7].isreferenced = true;
               }

               var3[var17][var7] = new VarInfo(var17, var7);
               break;
            case 7:
            case 8:
            case 26:
               var17 = Lua.GETARG_A(var12);
               var3[var17][var7].isreferenced = true;
               break;
            case 9:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var16 = Lua.GETARG_C(var12);
               var3[var17][var7].isreferenced = true;
               if(!Lua.ISK(var9)) {
                  var3[var9][var7].isreferenced = true;
               }

               if(!Lua.ISK(var16)) {
                  var3[var16][var7].isreferenced = true;
               }
               break;
            case 11:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var16 = Lua.GETARG_C(var12);
               var3[var9][var7].isreferenced = true;
               if(!Lua.ISK(var16)) {
                  var3[var16][var7].isreferenced = true;
               }

               var3[var17][var7] = new VarInfo(var17, var7);
               var3[var17 + 1][var7] = new VarInfo(var17 + 1, var7);
               break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var16 = Lua.GETARG_C(var12);
               if(!Lua.ISK(var9)) {
                  var3[var9][var7].isreferenced = true;
               }

               if(!Lua.ISK(var16)) {
                  var3[var16][var7].isreferenced = true;
               }

               var3[var17][var7] = new VarInfo(var17, var7);
               break;
            case 21:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);

               for(var16 = Lua.GETARG_C(var12); var9 <= var16; ++var9) {
                  var3[var9][var7].isreferenced = true;
               }

               var3[var17][var7] = new VarInfo(var17, var7);
            case 22:
               break;
            case 23:
            case 24:
            case 25:
               var9 = Lua.GETARG_B(var12);
               var16 = Lua.GETARG_C(var12);
               if(!Lua.ISK(var9)) {
                  var3[var9][var7].isreferenced = true;
               }

               if(!Lua.ISK(var16)) {
                  var3[var16][var7].isreferenced = true;
               }
               break;
            case 28:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var16 = Lua.GETARG_C(var12);
               var3[var17][var7].isreferenced = true;
               var3[var17][var7].isreferenced = true;

               for(var14 = 1; var14 <= var9 - 1; ++var14) {
                  var3[var17 + var14][var7].isreferenced = true;
               }

               for(var14 = 0; var14 <= var16 - 2; ++var17) {
                  var3[var17][var7] = new VarInfo(var17, var7);
                  ++var14;
               }

               while(true) {
                  if(var17 >= var2) {
                     continue label235;
                  }

                  var3[var17][var7] = VarInfo.INVALID;
                  ++var17;
               }
            case 29:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var3[var17][var7].isreferenced = true;
               var14 = 1;

               while(true) {
                  if(var14 > var9 - 1) {
                     continue label235;
                  }

                  var3[var17 + var14][var7].isreferenced = true;
                  ++var14;
               }
            case 30:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var14 = 0;

               while(true) {
                  if(var14 > var9 - 2) {
                     continue label235;
                  }

                  var3[var17 + var14][var7].isreferenced = true;
                  ++var14;
               }
            case 31:
               var17 = Lua.GETARG_A(var12);
               var3[var17][var7].isreferenced = true;
               var3[var17 + 2][var7].isreferenced = true;
               var3[var17][var7] = new VarInfo(var17, var7);
               var3[var17][var7].isreferenced = true;
               var3[var17 + 1][var7].isreferenced = true;
               var3[var17 + 3][var7] = new VarInfo(var17 + 3, var7);
               break;
            case 32:
               var17 = Lua.GETARG_A(var12);
               var3[var17 + 2][var7].isreferenced = true;
               var3[var17][var7] = new VarInfo(var17, var7);
               break;
            case 33:
               var17 = Lua.GETARG_A(var12);
               var16 = Lua.GETARG_C(var12);
               var3[var17++][var7].isreferenced = true;
               var3[var17++][var7].isreferenced = true;
               var3[var17++][var7].isreferenced = true;

               for(var14 = 0; var14 < var16; ++var17) {
                  var3[var17][var7] = new VarInfo(var17, var7);
                  ++var14;
               }

               while(true) {
                  if(var17 >= var2) {
                     continue label235;
                  }

                  var3[var17][var7] = VarInfo.INVALID;
                  ++var17;
               }
            case 34:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);
               var3[var17][var7].isreferenced = true;
               var14 = 1;

               while(true) {
                  if(var14 > var9) {
                     continue label235;
                  }

                  var3[var17 + var14][var7].isreferenced = true;
                  ++var14;
               }
            case 35:
               var17 = Lua.GETARG_A(var12);

               while(true) {
                  if(var17 >= var2) {
                     continue label235;
                  }

                  var3[var17][var7] = VarInfo.INVALID;
                  ++var17;
               }
            case 36:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_Bx(var12);
               int var11 = this.prototype.p[var9].nups;

               for(var14 = 1; var14 <= var11; ++var14) {
                  int var15 = this.prototype.code[var7 + var14];
                  if((var15 & 4) == 0) {
                     var9 = Lua.GETARG_B(var15);
                     var3[var9][var7].isreferenced = true;
                  }
               }

               var3[var17][var7] = new VarInfo(var17, var7);

               for(var14 = 1; var14 <= var11; ++var14) {
                  propogateVars(var3, var7, var7 + var14);
               }

               var7 += var11;
               break;
            case 37:
               var17 = Lua.GETARG_A(var12);
               var9 = Lua.GETARG_B(var12);

               for(var14 = 1; var14 < var9; ++var17) {
                  var3[var17][var7] = new VarInfo(var17, var7);
                  ++var14;
               }

               if(var9 == 0) {
                  while(var17 < var2) {
                     var3[var17][var7] = VarInfo.INVALID;
                     ++var17;
                  }
               }
               break;
            default:
               throw new IllegalStateException("unhandled opcode: " + var12);
            }
         }
      }

      return var3;
   }

   private static void propogateVars(VarInfo[][] var0, int var1, int var2) {
      int var3 = 0;

      for(int var4 = var0.length; var3 < var4; ++var3) {
         var0[var3][var2] = var0[var3][var1];
      }

   }

   private void replaceTrivialPhiVariables() {
      for(int var1 = 0; var1 < this.blocklist.length; ++var1) {
         BasicBlock var2 = this.blocklist[var1];

         for(int var3 = 0; var3 < this.prototype.maxstacksize; ++var3) {
            VarInfo var4 = this.vars[var3][var2.pc0];
            VarInfo var5 = var4.resolvePhiVariableValues();
            if(var5 != null) {
               this.substituteVariable(var3, var4, var5);
            }
         }
      }

   }

   private void substituteVariable(int var1, VarInfo var2, VarInfo var3) {
      int var4 = 0;

      for(int var5 = this.prototype.code.length; var4 < var5; ++var4) {
         this.replaceAll(this.vars[var1], this.vars[var1].length, var2, var3);
      }

   }

   private void replaceAll(VarInfo[] var1, int var2, VarInfo var3, VarInfo var4) {
      for(int var5 = 0; var5 < var2; ++var5) {
         if(var1[var5] == var3) {
            var1[var5] = var4;
         }
      }

   }

   private void findUpvalues() {
      int[] var1 = this.prototype.code;
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         if(Lua.GET_OPCODE(var1[var3]) == 36) {
            int var4 = Lua.GETARG_Bx(var1[var3]);
            Prototype var5 = this.prototype.p[var4];
            UpvalInfo[] var6 = var5.nups > 0?new UpvalInfo[var5.nups]:null;
            String var7 = this.name + "$" + var4;

            for(int var8 = 0; var8 < var5.nups; ++var8) {
               ++var3;
               int var9 = var1[var3];
               int var10 = Lua.GETARG_B(var9);
               var6[var8] = (var9 & 4) != 0?this.upvals[var10]:this.findOpenUp(var3, var10);
            }

            this.subprotos[var4] = new ProtoInfo(var5, var7, var6);
         }
      }

      for(var3 = 0; var3 < var2; ++var3) {
         if(Lua.GET_OPCODE(var1[var3]) == 8) {
            this.upvals[Lua.GETARG_B(var1[var3])].rw = true;
         }
      }

   }

   private UpvalInfo findOpenUp(int var1, int var2) {
      if(this.openups[var2] == null) {
         this.openups[var2] = new UpvalInfo[this.prototype.code.length];
      }

      if(this.openups[var2][var1] != null) {
         return this.openups[var2][var1];
      } else {
         UpvalInfo var3 = new UpvalInfo(this, var1, var2);
         int var4 = 0;

         for(int var5 = this.prototype.code.length; var4 < var5; ++var4) {
            if(this.vars[var2][var4] != null && this.vars[var2][var4].upvalue == var3) {
               this.openups[var2][var4] = var3;
            }
         }

         return var3;
      }
   }

   public boolean isUpvalueAssign(int var1, int var2) {
      VarInfo var3 = var1 < 0?this.params[var2]:this.vars[var2][var1];
      return var3 != null && var3.upvalue != null && var3.upvalue.rw;
   }

   public boolean isUpvalueCreate(int var1, int var2) {
      VarInfo var3 = var1 < 0?this.params[var2]:this.vars[var2][var1];
      return var3 != null && var3.upvalue != null && var3.upvalue.rw && var3.allocupvalue && var1 == var3.pc;
   }

   public boolean isUpvalueRefer(int var1, int var2) {
      if(var1 > 0 && this.vars[var2][var1] != null && this.vars[var2][var1].pc == var1 && this.vars[var2][var1 - 1] != null) {
         --var1;
      }

      VarInfo var3 = var1 < 0?this.params[var2]:this.vars[var2][var1];
      return var3 != null && var3.upvalue != null && var3.upvalue.rw;
   }

   public boolean isInitialValueUsed(int var1) {
      VarInfo var2 = this.params[var1];
      return var2.isreferenced;
   }

   public boolean isReadWriteUpvalue(UpvalInfo var1) {
      return var1.rw;
   }
}
