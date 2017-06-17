package org.luaj.vm2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Varargs;

public class Print extends Lua {

   private static final String STRING_FOR_NULL = "null";
   public static PrintStream ps = System.out;
   public static final String[] OPNAMES = new String[]{"MOVE", "LOADK", "LOADBOOL", "LOADNIL", "GETUPVAL", "GETGLOBAL", "GETTABLE", "SETGLOBAL", "SETUPVAL", "SETTABLE", "NEWTABLE", "SELF", "ADD", "SUB", "MUL", "DIV", "MOD", "POW", "UNM", "NOT", "LEN", "CONCAT", "JMP", "EQ", "LT", "LE", "TEST", "TESTSET", "CALL", "TAILCALL", "RETURN", "FORLOOP", "FORPREP", "TFORLOOP", "SETLIST", "CLOSE", "CLOSURE", "VARARG", null};


   static void printString(PrintStream var0, LuaString var1) {
      var0.print('\"');
      int var2 = 0;

      for(int var3 = var1.m_length; var2 < var3; ++var2) {
         byte var4 = var1.m_bytes[var1.m_offset + var2];
         if(var4 >= 32 && var4 <= 126 && var4 != 34 && var4 != 92) {
            var0.print((char)var4);
         } else {
            switch(var4) {
            case 7:
               var0.print("\\a");
               break;
            case 8:
               var0.print("\\b");
               break;
            case 9:
               var0.print("\\t");
               break;
            case 10:
               var0.print("\\n");
               break;
            case 11:
               var0.print("\\v");
               break;
            case 12:
               var0.print("\\f");
               break;
            case 13:
               var0.print("\\r");
               break;
            case 34:
               var0.print("\\\"");
               break;
            case 92:
               var0.print("\\\\");
               break;
            default:
               var0.print('\\');
               var0.print(Integer.toString(1255 & var4).substring(1));
            }
         }
      }

      var0.print('\"');
   }

   static void printValue(PrintStream var0, LuaValue var1) {
      switch(var1.type()) {
      case 4:
         printString(var0, (LuaString)var1);
         break;
      default:
         var0.print(var1.tojstring());
      }

   }

   static void printConstant(PrintStream var0, Prototype var1, int var2) {
      printValue(var0, var1.k[var2]);
   }

   public static void printCode(Prototype var0) {
      int[] var1 = var0.code;
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         printOpCode(var0, var2);
         ps.println();
      }

   }

   public static void printOpCode(Prototype var0, int var1) {
      printOpCode(ps, var0, var1);
   }

   public static void printOpCode(PrintStream var0, Prototype var1, int var2) {
      int[] var3 = var1.code;
      int var4 = var3[var2];
      int var5 = GET_OPCODE(var4);
      int var6 = GETARG_A(var4);
      int var7 = GETARG_B(var4);
      int var8 = GETARG_C(var4);
      int var9 = GETARG_Bx(var4);
      int var10 = GETARG_sBx(var4);
      int var11 = getline(var1, var2);
      var0.print("  " + (var2 + 1) + "  ");
      if(var11 > 0) {
         var0.print("[" + var11 + "]  ");
      } else {
         var0.print("[-]  ");
      }

      var0.print(OPNAMES[var5] + "  ");
      switch(getOpMode(var5)) {
      case 0:
         var0.print(var6);
         if(getBMode(var5) != 0) {
            var0.print(" " + (ISK(var7)?-1 - INDEXK(var7):var7));
         }

         if(getCMode(var5) != 0) {
            var0.print(" " + (ISK(var8)?-1 - INDEXK(var8):var8));
         }
         break;
      case 1:
         if(getBMode(var5) == 3) {
            var0.print(var6 + " " + (-1 - var9));
         } else {
            var0.print(var6 + " " + var9);
         }
         break;
      case 2:
         if(var5 == 22) {
            var0.print(var10);
         } else {
            var0.print(var6 + " " + var10);
         }
      }

      switch(var5) {
      case 1:
         var0.print("  ; ");
         printConstant(var0, var1, var9);
      case 2:
      case 3:
      case 10:
      case 16:
      case 18:
      case 19:
      case 20:
      case 21:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 33:
      case 35:
      default:
         break;
      case 4:
      case 8:
         var0.print("  ; ");
         if(var1.upvalues.length > var7) {
            printValue(var0, var1.upvalues[var7]);
         } else {
            var0.print("-");
         }
         break;
      case 5:
      case 7:
         var0.print("  ; ");
         printConstant(var0, var1, var9);
         break;
      case 6:
      case 11:
         if(ISK(var8)) {
            var0.print("  ; ");
            printConstant(var0, var1, INDEXK(var8));
         }
         break;
      case 9:
      case 12:
      case 13:
      case 14:
      case 15:
      case 17:
      case 23:
      case 24:
      case 25:
         if(ISK(var7) || ISK(var8)) {
            var0.print("  ; ");
            if(ISK(var7)) {
               printConstant(var0, var1, INDEXK(var7));
            } else {
               var0.print("-");
            }

            var0.print(" ");
            if(ISK(var8)) {
               printConstant(var0, var1, INDEXK(var8));
            } else {
               var0.print("-");
            }
         }
         break;
      case 22:
      case 31:
      case 32:
         var0.print("  ; to " + (var10 + var2 + 2));
         break;
      case 34:
         if(var8 == 0) {
            StringBuffer var10001 = (new StringBuffer()).append("  ; ");
            ++var2;
            var0.print(var10001.append(var3[var2]).toString());
         } else {
            var0.print("  ; " + var8);
         }
         break;
      case 36:
         var0.print("  ; " + var1.p[var9].getClass().getName());
         break;
      case 37:
         var0.print("  ; is_vararg=" + var1.is_vararg);
      }

   }

   private static int getline(Prototype var0, int var1) {
      return var1 > 0 && var0.lineinfo != null && var1 < var0.lineinfo.length?var0.lineinfo[var1]:-1;
   }

   static void printHeader(Prototype var0) {
      String var1 = String.valueOf(var0.source);
      if(!var1.startsWith("@") && !var1.startsWith("=")) {
         if("Lua".equals(var1)) {
            var1 = "(bstring)";
         } else {
            var1 = "(string)";
         }
      } else {
         var1 = var1.substring(1);
      }

      String var2 = var0.linedefined == 0?"main":"function";
      ps.print("\n%" + var2 + " <" + var1 + ":" + var0.linedefined + "," + var0.lastlinedefined + "> (" + var0.code.length + " instructions, " + var0.code.length * 4 + " bytes at " + id(var0) + ")\n");
      ps.print(var0.numparams + " param, " + var0.maxstacksize + " slot, " + var0.upvalues.length + " upvalue, ");
      ps.print(var0.locvars.length + " local, " + var0.k.length + " constant, " + var0.p.length + " function\n");
   }

   static void printConstants(Prototype var0) {
      int var2 = var0.k.length;
      ps.print("constants (" + var2 + ") for " + id(var0) + ":\n");

      for(int var1 = 0; var1 < var2; ++var1) {
         ps.print("  " + (var1 + 1) + "  ");
         printValue(ps, var0.k[var1]);
         ps.print("\n");
      }

   }

   static void printLocals(Prototype var0) {
      int var2 = var0.locvars.length;
      ps.print("locals (" + var2 + ") for " + id(var0) + ":\n");

      for(int var1 = 0; var1 < var2; ++var1) {
         ps.println("  " + var1 + "  " + var0.locvars[var1].varname + " " + (var0.locvars[var1].startpc + 1) + " " + (var0.locvars[var1].endpc + 1));
      }

   }

   static void printUpValues(Prototype var0) {
      int var2 = var0.upvalues.length;
      ps.print("upvalues (" + var2 + ") for " + id(var0) + ":\n");

      for(int var1 = 0; var1 < var2; ++var1) {
         ps.print("  " + var1 + "  " + var0.upvalues[var1] + "\n");
      }

   }

   public static void print(Prototype var0) {
      printFunction(var0, true);
   }

   public static void printFunction(Prototype var0, boolean var1) {
      int var3 = var0.p.length;
      printHeader(var0);
      printCode(var0);
      if(var1) {
         printConstants(var0);
         printLocals(var0);
         printUpValues(var0);
      }

      for(int var2 = 0; var2 < var3; ++var2) {
         printFunction(var0.p[var2], var1);
      }

   }

   private static void format(String var0, int var1) {
      int var2 = var0.length();
      if(var2 > var1) {
         ps.print(var0.substring(0, var1));
      } else {
         ps.print(var0);
         int var3 = var1 - var2;

         while(true) {
            --var3;
            if(var3 < 0) {
               break;
            }

            ps.print(' ');
         }
      }

   }

   private static String id(Prototype var0) {
      return "Proto";
   }

   private void _assert(boolean var1) {
      if(!var1) {
         throw new NullPointerException("_assert failed");
      }
   }

   public static void printState(LuaClosure var0, int var1, LuaValue[] var2, int var3, Varargs var4) {
      PrintStream var5 = ps;
      ByteArrayOutputStream var6 = new ByteArrayOutputStream();
      ps = new PrintStream(var6);
      printOpCode(var0.p, var1);
      ps.flush();
      ps.close();
      ps = var5;
      format(var6.toString(), 50);
      ps.print('[');

      for(int var7 = 0; var7 < var2.length; ++var7) {
         LuaValue var8 = var2[var7];
         if(var8 == null) {
            ps.print("null");
         } else {
            switch(var8.type()) {
            case 4:
               LuaString var9 = var8.checkstring();
               ps.print(var9.length() < 48?var9.tojstring():var9.substring(0, 32).tojstring() + "...+" + (var9.length() - 32) + "b");
               break;
            case 5:
            default:
               ps.print(var8.tojstring());
               break;
            case 6:
               ps.print(var8 instanceof LuaClosure?((LuaClosure)var8).p.toString():var8.tojstring());
               break;
            case 7:
               Object var10 = var8.touserdata();
               if(var10 != null) {
                  String var11 = var10.getClass().getName();
                  var11 = var11.substring(var11.lastIndexOf(46) + 1);
                  ps.print(var11 + ": " + Integer.toHexString(var10.hashCode()));
               } else {
                  ps.print(var8.toString());
               }
            }
         }

         if(var7 + 1 == var3) {
            ps.print(']');
         }

         ps.print(" | ");
      }

      ps.print(var4);
      ps.println();
   }

}
