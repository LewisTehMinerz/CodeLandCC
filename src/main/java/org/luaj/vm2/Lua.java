package org.luaj.vm2;


public class Lua {

   public static final String _VERSION = "Luaj-jse 2.0.3";
   public static final int LUA_MULTRET = -1;
   public static final int VARARG_HASARG = 1;
   public static final int VARARG_ISVARARG = 2;
   public static final int VARARG_NEEDSARG = 4;
   public static final int iABC = 0;
   public static final int iABx = 1;
   public static final int iAsBx = 2;
   public static final int SIZE_C = 9;
   public static final int SIZE_B = 9;
   public static final int SIZE_Bx = 18;
   public static final int SIZE_A = 8;
   public static final int SIZE_OP = 6;
   public static final int POS_OP = 0;
   public static final int POS_A = 6;
   public static final int POS_C = 14;
   public static final int POS_B = 23;
   public static final int POS_Bx = 14;
   public static final int MAX_OP = 63;
   public static final int MAXARG_A = 255;
   public static final int MAXARG_B = 511;
   public static final int MAXARG_C = 511;
   public static final int MAXARG_Bx = 262143;
   public static final int MAXARG_sBx = 131071;
   public static final int MASK_OP = 63;
   public static final int MASK_A = 16320;
   public static final int MASK_B = -8388608;
   public static final int MASK_C = 8372224;
   public static final int MASK_Bx = -16384;
   public static final int MASK_NOT_OP = -64;
   public static final int MASK_NOT_A = -16321;
   public static final int MASK_NOT_B = 8388607;
   public static final int MASK_NOT_C = -8372225;
   public static final int MASK_NOT_Bx = 16383;
   public static final int BITRK = 256;
   public static final int MAXINDEXRK = 255;
   public static final int NO_REG = 255;
   public static final int OP_MOVE = 0;
   public static final int OP_LOADK = 1;
   public static final int OP_LOADBOOL = 2;
   public static final int OP_LOADNIL = 3;
   public static final int OP_GETUPVAL = 4;
   public static final int OP_GETGLOBAL = 5;
   public static final int OP_GETTABLE = 6;
   public static final int OP_SETGLOBAL = 7;
   public static final int OP_SETUPVAL = 8;
   public static final int OP_SETTABLE = 9;
   public static final int OP_NEWTABLE = 10;
   public static final int OP_SELF = 11;
   public static final int OP_ADD = 12;
   public static final int OP_SUB = 13;
   public static final int OP_MUL = 14;
   public static final int OP_DIV = 15;
   public static final int OP_MOD = 16;
   public static final int OP_POW = 17;
   public static final int OP_UNM = 18;
   public static final int OP_NOT = 19;
   public static final int OP_LEN = 20;
   public static final int OP_CONCAT = 21;
   public static final int OP_JMP = 22;
   public static final int OP_EQ = 23;
   public static final int OP_LT = 24;
   public static final int OP_LE = 25;
   public static final int OP_TEST = 26;
   public static final int OP_TESTSET = 27;
   public static final int OP_CALL = 28;
   public static final int OP_TAILCALL = 29;
   public static final int OP_RETURN = 30;
   public static final int OP_FORLOOP = 31;
   public static final int OP_FORPREP = 32;
   public static final int OP_TFORLOOP = 33;
   public static final int OP_SETLIST = 34;
   public static final int OP_CLOSE = 35;
   public static final int OP_CLOSURE = 36;
   public static final int OP_VARARG = 37;
   public static final int NUM_OPCODES = 38;
   public static final int OP_GT = 63;
   public static final int OP_GE = 62;
   public static final int OP_NEQ = 61;
   public static final int OP_AND = 60;
   public static final int OP_OR = 59;
   public static final int OpArgN = 0;
   public static final int OpArgU = 1;
   public static final int OpArgR = 2;
   public static final int OpArgK = 3;
   public static final int[] luaP_opmodes = new int[]{96, 113, 84, 96, 80, 113, 108, 49, 16, 60, 84, 108, 124, 124, 124, 124, 124, 124, 96, 96, 96, 104, 34, 188, 188, 188, 228, 228, 84, 84, 16, 98, 98, 132, 20, 0, 81, 80};
   public static final int LFIELDS_PER_FLUSH = 50;


   public static int GET_OPCODE(int var0) {
      return var0 >> 0 & 63;
   }

   public static int GETARG_A(int var0) {
      return var0 >> 6 & 255;
   }

   public static int GETARG_B(int var0) {
      return var0 >> 23 & 511;
   }

   public static int GETARG_C(int var0) {
      return var0 >> 14 & 511;
   }

   public static int GETARG_Bx(int var0) {
      return var0 >> 14 & 262143;
   }

   public static int GETARG_sBx(int var0) {
      return (var0 >> 14 & 262143) - 131071;
   }

   public static boolean ISK(int var0) {
      return 0 != (var0 & 256);
   }

   public static int INDEXK(int var0) {
      return var0 & -257;
   }

   public static int RKASK(int var0) {
      return var0 | 256;
   }

   public static int getOpMode(int var0) {
      return luaP_opmodes[var0] & 3;
   }

   public static int getBMode(int var0) {
      return luaP_opmodes[var0] >> 4 & 3;
   }

   public static int getCMode(int var0) {
      return luaP_opmodes[var0] >> 2 & 3;
   }

   public static boolean testAMode(int var0) {
      return 0 != (luaP_opmodes[var0] & 64);
   }

   public static boolean testTMode(int var0) {
      return 0 != (luaP_opmodes[var0] & 128);
   }

}
