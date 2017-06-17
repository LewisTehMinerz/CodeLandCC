package org.luaj.vm2.luajc;

import org.luaj.vm2.Lua;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.luajc.BasicBlock;
import org.luaj.vm2.luajc.JavaBuilder;
import org.luaj.vm2.luajc.ProtoInfo;

public class JavaGen {

   public final String classname;
   public final byte[] bytecode;
   public final JavaGen[] inners;


   public JavaGen(Prototype var1, String var2, String var3) {
      this(new ProtoInfo(var1, var2), var2, var3);
   }

   private JavaGen(ProtoInfo var1, String var2, String var3) {
      this.classname = var2;
      JavaBuilder var4 = new JavaBuilder(var1, var2, var3);
      this.scanInstructions(var1, var2, var4);
      this.bytecode = var4.completeClass();
      if(var1.subprotos != null) {
         int var5 = var1.subprotos.length;
         this.inners = new JavaGen[var5];

         for(int var6 = 0; var6 < var5; ++var6) {
            this.inners[var6] = new JavaGen(var1.subprotos[var6], this.closureName(var2, var6), var3);
         }
      } else {
         this.inners = null;
      }

   }

   private String closureName(String var1, int var2) {
      return var1 + "$" + var2;
   }

   private void scanInstructions(ProtoInfo var1, String var2, JavaBuilder var3) {
      Prototype var4 = var1.prototype;
      int var5 = -1;

      for(int var6 = 0; var6 < var1.blocklist.length; ++var6) {
         BasicBlock var7 = var1.blocklist[var6];

         int var8;
         for(var8 = 0; var8 < var4.maxstacksize; ++var8) {
            int var9 = var7.pc0;
            boolean var10 = var1.isUpvalueCreate(var9, var8);
            if(var10 && var1.vars[var8][var9].isPhiVar()) {
               var3.convertToUpvalue(var9, var8);
            }
         }

         for(var8 = var7.pc0; var8 <= var7.pc1; ++var8) {
            int var24 = var4.code[var8];
            int var11 = Lua.GET_OPCODE(var24);
            int var12 = Lua.GETARG_A(var24);
            int var13 = Lua.GETARG_B(var24);
            int var14 = Lua.GETARG_Bx(var24);
            int var15 = Lua.GETARG_sBx(var24);
            int var16 = Lua.GETARG_C(var24);
            int var17;
            int var19;
            int var20;
            label239:
            switch(var11) {
            case 0:
               var3.loadLocal(var8, var13);
               var3.storeLocal(var8, var12);
               break;
            case 1:
               var3.loadConstant(var4.k[var14]);
               var3.storeLocal(var8, var12);
               break;
            case 2:
               var3.loadBoolean(var13 != 0);
               var3.storeLocal(var8, var12);
               if(var16 != 0) {
                  var3.addBranch(var8, 1, var8 + 2);
               }
               break;
            case 3:
               var3.loadNil();

               while(true) {
                  if(var12 > var13) {
                     break label239;
                  }

                  if(var12 < var13) {
                     var3.dup();
                  }

                  var3.storeLocal(var8, var12);
                  ++var12;
               }
            case 4:
               var3.loadUpvalue(var13);
               var3.storeLocal(var8, var12);
               break;
            case 5:
               var3.loadEnv();
               var3.loadConstant(var4.k[var14]);
               var3.getTable();
               var3.storeLocal(var8, var12);
               break;
            case 6:
               var3.loadLocal(var8, var13);
               this.loadLocalOrConstant(var4, var3, var8, var16);
               var3.getTable();
               var3.storeLocal(var8, var12);
               break;
            case 7:
               var3.loadEnv();
               var3.loadConstant(var4.k[var14]);
               var3.loadLocal(var8, var12);
               var3.setTable();
               break;
            case 8:
               var3.storeUpvalue(var8, var13, var12);
               break;
            case 9:
               var3.loadLocal(var8, var12);
               this.loadLocalOrConstant(var4, var3, var8, var13);
               this.loadLocalOrConstant(var4, var3, var8, var16);
               var3.setTable();
               break;
            case 10:
               var3.newTable(var13, var16);
               var3.storeLocal(var8, var12);
               break;
            case 11:
               var3.loadLocal(var8, var13);
               var3.dup();
               var3.storeLocal(var8, var12 + 1);
               this.loadLocalOrConstant(var4, var3, var8, var16);
               var3.getTable();
               var3.storeLocal(var8, var12);
               break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
               this.loadLocalOrConstant(var4, var3, var8, var13);
               this.loadLocalOrConstant(var4, var3, var8, var16);
               var3.binaryop(var11);
               var3.storeLocal(var8, var12);
               break;
            case 18:
            case 19:
            case 20:
               var3.loadLocal(var8, var13);
               var3.unaryop(var11);
               var3.storeLocal(var8, var12);
               break;
            case 21:
               for(var17 = var13; var17 <= var16; ++var17) {
                  var3.loadLocal(var8, var17);
               }

               if(var16 > var13 + 1) {
                  var3.tobuffer();
                  var17 = var16;

                  while(true) {
                     --var17;
                     if(var17 < var13) {
                        var3.tovalue();
                        break;
                     }

                     var3.concatbuffer();
                  }
               } else {
                  var3.concatvalue();
               }

               var3.storeLocal(var8, var12);
               break;
            case 22:
               var3.addBranch(var8, 1, var8 + 1 + var15);
               break;
            case 23:
            case 24:
            case 25:
               this.loadLocalOrConstant(var4, var3, var8, var13);
               this.loadLocalOrConstant(var4, var3, var8, var16);
               var3.compareop(var11);
               var3.addBranch(var8, var12 != 0?3:2, var8 + 2);
               break;
            case 26:
               var3.loadLocal(var8, var12);
               var3.toBoolean();
               var3.addBranch(var8, var16 != 0?3:2, var8 + 2);
               break;
            case 27:
               var3.loadLocal(var8, var13);
               var3.toBoolean();
               var3.addBranch(var8, var16 != 0?3:2, var8 + 2);
               var3.loadLocal(var8, var13);
               var3.storeLocal(var8, var12);
               break;
            case 28:
               var3.loadLocal(var8, var12);
               var17 = var13 - 1;
               label218:
               switch(var17) {
               case -1:
                  this.loadVarargResults(var3, var8, var12 + 1, var5);
                  var17 = -1;
                  break;
               case 0:
               case 1:
               case 2:
               case 3:
                  int var18 = 1;

                  while(true) {
                     if(var18 >= var13) {
                        break label218;
                     }

                     var3.loadLocal(var8, var12 + var18);
                     ++var18;
                  }
               default:
                  var3.newVarargs(var8, var12 + 1, var13 - 1);
                  var17 = -1;
               }

               boolean var25 = var17 < 0 || var16 < 1 || var16 > 2;
               if(var25) {
                  var3.invoke(var17);
               } else {
                  var3.call(var17);
               }

               switch(var16) {
               case 0:
                  var5 = var12;
                  var3.storeVarresult();
                  break label239;
               case 1:
                  var3.pop();
                  break label239;
               case 2:
                  if(var25) {
                     var3.arg(1);
                  }

                  var3.storeLocal(var8, var12);
                  break label239;
               default:
                  var19 = 1;

                  while(true) {
                     if(var19 >= var16) {
                        break label239;
                     }

                     if(var19 + 1 < var16) {
                        var3.dup();
                     }

                     var3.arg(var19);
                     var3.storeLocal(var8, var12 + var19 - 1);
                     ++var19;
                  }
               }
            case 29:
               var3.loadLocal(var8, var12);
               switch(var13) {
               case 0:
                  this.loadVarargResults(var3, var8, var12 + 1, var5);
                  break;
               case 1:
                  var3.loadNone();
                  break;
               case 2:
                  var3.loadLocal(var8, var12 + 1);
                  break;
               default:
                  var3.newVarargs(var8, var12 + 1, var13 - 1);
               }

               var3.newTailcallVarargs();
               var3.areturn();
               break;
            case 30:
               if(var16 == 1) {
                  var3.loadNone();
               } else {
                  switch(var13) {
                  case 0:
                     this.loadVarargResults(var3, var8, var12, var5);
                     break;
                  case 1:
                     var3.loadNone();
                     break;
                  case 2:
                     var3.loadLocal(var8, var12);
                     break;
                  default:
                     var3.newVarargs(var8, var12, var13 - 1);
                  }
               }

               var3.areturn();
               break;
            case 31:
               var3.loadLocal(var8, var12);
               var3.loadLocal(var8, var12 + 2);
               var3.binaryop(12);
               var3.dup();
               var3.dup();
               var3.storeLocal(var8, var12);
               var3.storeLocal(var8, var12 + 3);
               var3.loadLocal(var8, var12 + 1);
               var3.loadLocal(var8, var12 + 2);
               var3.testForLoop();
               var3.addBranch(var8, 2, var8 + 1 + var15);
               break;
            case 32:
               var3.loadLocal(var8, var12);
               var3.loadLocal(var8, var12 + 2);
               var3.binaryop(13);
               var3.storeLocal(var8, var12);
               var3.addBranch(var8, 1, var8 + 1 + var15);
               break;
            case 33:
               var3.loadLocal(var8, var12);
               var3.loadLocal(var8, var12 + 1);
               var3.loadLocal(var8, var12 + 2);
               var3.invoke(2);
               var3.dup();
               var3.storeVarresult();
               var3.arg(1);
               var3.isNil();
               var3.addBranch(var8, 2, var8 + 2);
               var3.createUpvalues(var8, var12 + 3, var16);
               var3.loadVarresult();
               if(var16 >= 2) {
                  var3.dup();
               }

               var3.arg(1);
               var3.dup();
               var3.storeLocal(var8, var12 + 2);
               var3.storeLocal(var8, var12 + 3);
               var19 = 2;

               while(true) {
                  if(var19 > var16) {
                     break label239;
                  }

                  if(var19 < var16) {
                     var3.dup();
                  }

                  var3.arg(var19);
                  var3.storeLocal(var8, var12 + 2 + var19);
                  ++var19;
               }
            case 34:
               var19 = (var16 - 1) * 50 + 1;
               var3.loadLocal(var8, var12);
               if(var13 == 0) {
                  var20 = var5 - (var12 + 1);
                  if(var20 > 0) {
                     var3.setlistStack(var8, var12 + 1, var19, var20);
                     var19 += var20;
                  }

                  var3.setlistVarargs(var19, var5);
               } else {
                  var3.setlistStack(var8, var12 + 1, var19, var13);
                  var3.pop();
               }
            case 35:
            default:
               break;
            case 36:
               Prototype var26 = var4.p[var14];
               String var21 = this.closureName(var2, var14);
               int var22 = var26.nups;
               var3.closureCreate(var21);
               if(var22 > 0) {
                  var3.dup();
               }

               var3.storeLocal(var8, var12);
               if(var22 > 0) {
                  for(int var23 = 0; var23 < var22; ++var23) {
                     if(var23 + 1 < var22) {
                        var3.dup();
                     }

                     var24 = var4.code[var8 + var23 + 1];
                     var13 = Lua.GETARG_B(var24);
                     if((var24 & 4) != 0) {
                        var3.closureInitUpvalueFromUpvalue(var21, var23, var13);
                     } else {
                        var3.closureInitUpvalueFromLocal(var21, var23, var8, var13);
                     }
                  }

                  var8 += var22;
               }
               break;
            case 37:
               if(var13 == 0) {
                  var3.loadVarargs();
                  var3.storeVarresult();
                  var5 = var12;
               } else {
                  for(var20 = 1; var20 < var13; ++var20) {
                     var3.loadVarargs(var20);
                     var3.storeLocal(var8, var12);
                     ++var12;
                  }
               }
            }

            var3.onEndOfLuaInstruction(var8);
         }
      }

   }

   private void loadVarargResults(JavaBuilder var1, int var2, int var3, int var4) {
      if(var4 <= var3) {
         var1.loadVarresult();
         var1.subargs(var3 + 1 - var4);
      } else if(var4 == var3) {
         var1.loadVarresult();
      } else {
         var1.newVarargsVarresult(var2, var3, var4 - var3);
      }

   }

   private void loadLocalOrConstant(Prototype var1, JavaBuilder var2, int var3, int var4) {
      if(var4 <= 255) {
         var2.loadLocal(var3, var4);
      } else {
         var2.loadConstant(var1.k[var4 & 255]);
      }

   }
}
