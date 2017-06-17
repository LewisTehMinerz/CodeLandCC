package org.luaj.vm2.luajc;

import java.util.Vector;
import org.luaj.vm2.Lua;
import org.luaj.vm2.Prototype;

public class BasicBlock {

   int pc0;
   int pc1;
   BasicBlock[] prev;
   BasicBlock[] next;
   boolean islive;


   public BasicBlock(Prototype var1, int var2) {
      this.pc0 = this.pc1 = var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.pc0 + 1 + "-" + (this.pc1 + 1) + (this.prev != null?"  prv: " + this.str(this.prev, 1):"") + (this.next != null?"  nxt: " + this.str(this.next, 0):"") + "\n");
      return var1.toString();
   }

   private String str(BasicBlock[] var1, int var2) {
      if(var1 == null) {
         return "";
      } else {
         StringBuffer var3 = new StringBuffer();
         var3.append("(");
         int var4 = 0;

         for(int var5 = var1.length; var4 < var5; ++var4) {
            if(var4 > 0) {
               var3.append(",");
            }

            var3.append(String.valueOf(var2 == 1?var1[var4].pc1 + 1:var1[var4].pc0 + 1));
         }

         var3.append(")");
         return var3.toString();
      }
   }

   public static BasicBlock[] findBasicBlocks(Prototype var0) {
      int var1 = var0.code.length;
      final boolean[] var2 = new boolean[var1];
      final boolean[] var3 = new boolean[var1];
      var2[0] = true;
      BasicBlock.BranchVisitor var4 = new BasicBlock.BranchVisitor(var2) {
         public void visitBranch(int var1, int var2) {
            var3[var1] = true;
            this.isbeg[var2] = true;
         }
         public void visitReturn(int var1) {
            var3[var1] = true;
         }
      };
      visitBranches(var0, var4);
      visitBranches(var0, var4);
      final BasicBlock[] var5 = new BasicBlock[var1];

      for(int var6 = 0; var6 < var1; ++var6) {
         var2[var6] = true;
         BasicBlock var7 = new BasicBlock(var0, var6);

         for(var5[var6] = var7; !var3[var6] && var6 + 1 < var1 && !var2[var6 + 1]; var5[var7.pc1 = var6] = var7) {
            ++var6;
         }
      }

      final int[] var8 = new int[var1];
      final int[] var9 = new int[var1];
      visitBranches(var0, new BasicBlock.BranchVisitor(var2) {
         public void visitBranch(int var1, int var2) {
            ++var8[var1];
            ++var9[var2];
         }
      });
      visitBranches(var0, new BasicBlock.BranchVisitor(var2) {
         public void visitBranch(int var1, int var2) {
            if(var5[var1].next == null) {
               var5[var1].next = new BasicBlock[var8[var1]];
            }

            if(var5[var2].prev == null) {
               var5[var2].prev = new BasicBlock[var9[var2]];
            }

            var5[var1].next[--var8[var1]] = var5[var2];
            var5[var2].prev[--var9[var2]] = var5[var1];
         }
      });
      return var5;
   }

   public static void visitBranches(Prototype var0, BasicBlock.BranchVisitor var1) {
      int[] var5 = var0.code;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         int var8 = var5[var7];
         int var2;
         int var3;
         switch(Lua.GET_OPCODE(var8)) {
         case 2:
            if(0 != Lua.GETARG_C(var8)) {
               if(Lua.GET_OPCODE(var5[var7 + 1]) == 22) {
                  throw new IllegalArgumentException("OP_LOADBOOL followed by jump at " + var7);
               }

               var1.visitBranch(var7, var7 + 2);
               break;
            }
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 28:
         default:
            if(var7 + 1 < var6 && var1.isbeg[var7 + 1]) {
               var1.visitBranch(var7, var7 + 1);
            }
            break;
         case 22:
         case 32:
            var2 = Lua.GETARG_sBx(var8);
            var3 = var7 + var2 + 1;
            var1.visitBranch(var7, var3);
            break;
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 33:
            if(Lua.GET_OPCODE(var5[var7 + 1]) != 22) {
               throw new IllegalArgumentException("test not followed by jump at " + var7);
            }

            var2 = Lua.GETARG_sBx(var5[var7 + 1]);
            ++var7;
            var3 = var7 + var2 + 1;
            var1.visitBranch(var7, var3);
            var1.visitBranch(var7, var7 + 1);
            break;
         case 29:
         case 30:
            var1.visitReturn(var7);
            break;
         case 31:
            var2 = Lua.GETARG_sBx(var8);
            var3 = var7 + var2 + 1;
            var1.visitBranch(var7, var3);
            var1.visitBranch(var7, var7 + 1);
         }
      }

   }

   public static BasicBlock[] findLiveBlocks(BasicBlock[] var0) {
      Vector var1 = new Vector();
      var1.addElement(var0[0]);

      int var3;
      while(!var1.isEmpty()) {
         BasicBlock var2 = (BasicBlock)var1.elementAt(0);
         var1.removeElementAt(0);
         if(!var2.islive) {
            var2.islive = true;
            var3 = 0;

            for(int var4 = var2.next != null?var2.next.length:0; var3 < var4; ++var3) {
               if(!var2.next[var3].islive) {
                  var1.addElement(var2.next[var3]);
               }
            }
         }
      }

      Vector var5 = new Vector();

      for(var3 = 0; var3 < var0.length; var3 = var0[var3].pc1 + 1) {
         if(var0[var3].islive) {
            var5.addElement(var0[var3]);
         }
      }

      BasicBlock[] var6 = new BasicBlock[var5.size()];
      var5.copyInto(var6);
      return var6;
   }

   public abstract static class BranchVisitor {

      final boolean[] isbeg;


      public BranchVisitor(boolean[] var1) {
         this.isbeg = var1;
      }

      public void visitBranch(int var1, int var2) {}

      public void visitReturn(int var1) {}
   }
}
