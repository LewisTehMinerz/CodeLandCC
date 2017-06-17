package org.luaj.vm2.parser;

import java.io.IOException;
import java.io.PrintStream;
import org.luaj.vm2.parser.LuaParserConstants;
import org.luaj.vm2.parser.SimpleCharStream;
import org.luaj.vm2.parser.Token;
import org.luaj.vm2.parser.TokenMgrError;

public class LuaParserTokenManager implements LuaParserConstants {

   public PrintStream debugStream;
   static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
   static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
   static final int[] jjnextStates = new int[]{53, 54, 57, 58, 40, 41, 42, 27, 28, 29, 27, 28, 29, 37, 27, 38, 28, 29, 40, 41, 42, 50, 40, 51, 41, 42, 24, 25, 30, 31, 36, 43, 44, 49, 55, 56, 61, 62, 0, 1, 3};
   public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "and", "break", "do", "else", "elseif", "end", "false", "for", "function", "if", "in", "local", "nil", "not", "or", "return", "repeat", "then", "true", "until", "while", null, null, null, null, null, null, null, null, null, null, null, null, null, null, ";", "=", ",", ".", ":", "(", ")", "[", "]", "...", "{", "}", "+", "-", "*", "/", "^", "%", "..", "<", "<=", ">", ">=", "==", "~=", "#"};
   public static final String[] lexStateNames = new String[]{"DEFAULT", "IN_COMMENT", "IN_LC0", "IN_LC1", "IN_LC2", "IN_LC3", "IN_LCN", "IN_LS0", "IN_LS1", "IN_LS2", "IN_LS3", "IN_LSN"};
   public static final int[] jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
   static final long[] jjtoToken = new long[]{436849163578114049L, 67108863L};
   static final long[] jjtoSkip = new long[]{8257598L, 0L};
   static final long[] jjtoSpecial = new long[]{8257536L, 0L};
   static final long[] jjtoMore = new long[]{268566464L, 0L};
   protected SimpleCharStream input_stream;
   private final int[] jjrounds;
   private final int[] jjstateSet;
   private final StringBuffer jjimage;
   private StringBuffer image;
   private int jjimageLen;
   private int lengthOfMatch;
   protected char curChar;
   int curLexState;
   int defaultLexState;
   int jjnewStateCnt;
   int jjround;
   int jjmatchedPos;
   int jjmatchedKind;


   public void setDebugStream(PrintStream var1) {
      this.debugStream = var1;
   }

   private int jjStopAtPos(int var1, int var2) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;
      return var1 + 1;
   }

   private int jjMoveStringLiteralDfa0_2() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_2(262144L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_2(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 93:
         if((var1 & 262144L) != 0L) {
            return this.jjStopAtPos(1, 18);
         }

         return 2;
      default:
         return 2;
      }
   }

   private int jjMoveStringLiteralDfa0_11() {
      return this.jjMoveNfa_11(6, 0);
   }

   private int jjMoveNfa_11(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 7;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         if(++this.jjround == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long var6;
         if(this.curChar < 64) {
            var6 = 1L << this.curChar;

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
               case 1:
                  if(this.curChar == 61) {
                     this.jjCheckNAddTwoStates(1, 2);
                  }
               case 2:
               default:
                  break;
               case 3:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 0;
                  }
                  break;
               case 4:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 3;
                  }
                  break;
               case 5:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 4;
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            var6 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 2:
                  if(this.curChar == 93 && var5 > 27) {
                     var5 = 27;
                  }
                  break;
               case 6:
                  if(this.curChar == 93) {
                     this.jjstateSet[this.jjnewStateCnt++] = 5;
                  }
               }
            } while(var4 != var3);
         } else {
            int var14 = this.curChar >> 8;
            int var7 = var14 >> 6;
            long var8 = 1L << (var14 & 63);
            int var10 = (this.curChar & 255) >> 6;
            long var11 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         if((var4 = this.jjnewStateCnt) == (var3 = 7 - (this.jjnewStateCnt = var3))) {
            return var2;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var13) {
            return var2;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_10() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_10(67108864L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_10(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 61:
         return this.jjMoveStringLiteralDfa2_10(var1, 67108864L);
      default:
         return 2;
      }
   }

   private int jjMoveStringLiteralDfa2_10(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 2;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 2;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa3_10(var3, 67108864L);
         default:
            return 3;
         }
      }
   }

   private int jjMoveStringLiteralDfa3_10(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 3;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 3;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa4_10(var3, 67108864L);
         default:
            return 4;
         }
      }
   }

   private int jjMoveStringLiteralDfa4_10(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 4;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 4;
         }

         switch(this.curChar) {
         case 93:
            if((var3 & 67108864L) != 0L) {
               return this.jjStopAtPos(4, 26);
            }

            return 5;
         default:
            return 5;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_9() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_9(33554432L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_9(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 61:
         return this.jjMoveStringLiteralDfa2_9(var1, 33554432L);
      default:
         return 2;
      }
   }

   private int jjMoveStringLiteralDfa2_9(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 2;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 2;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa3_9(var3, 33554432L);
         default:
            return 3;
         }
      }
   }

   private int jjMoveStringLiteralDfa3_9(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 3;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 3;
         }

         switch(this.curChar) {
         case 93:
            if((var3 & 33554432L) != 0L) {
               return this.jjStopAtPos(3, 25);
            }

            return 4;
         default:
            return 4;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_8() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_8(16777216L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_8(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 61:
         return this.jjMoveStringLiteralDfa2_8(var1, 16777216L);
      default:
         return 2;
      }
   }

   private int jjMoveStringLiteralDfa2_8(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 2;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 2;
         }

         switch(this.curChar) {
         case 93:
            if((var3 & 16777216L) != 0L) {
               return this.jjStopAtPos(2, 24);
            }

            return 3;
         default:
            return 3;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_7() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_7(8388608L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_7(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 93:
         if((var1 & 8388608L) != 0L) {
            return this.jjStopAtPos(1, 23);
         }

         return 2;
      default:
         return 2;
      }
   }

   private final int jjStopStringLiteralDfa_0(int var1, long var2, long var4) {
      switch(var1) {
      case 0:
         if((var4 & 262664L) != 0L) {
            return 22;
         } else {
            if((var2 & 30720L) == 0L && (var4 & 128L) == 0L) {
               if((var2 & 1125899369971712L) != 0L) {
                  this.jjmatchedKind = 50;
                  return 17;
               }

               if((var2 & 66496L) == 0L && (var4 & 8192L) == 0L) {
                  return -1;
               }

               return 7;
            }

            return 14;
         }
      case 1:
         if((var2 & 66496L) != 0L) {
            return 6;
         } else if((var2 & 28672L) != 0L) {
            return 13;
         } else if((var2 & 9622874226688L) != 0L) {
            return 17;
         } else {
            if((var2 & 1116276495745024L) != 0L) {
               if(this.jjmatchedPos != 1) {
                  this.jjmatchedKind = 50;
                  this.jjmatchedPos = 1;
               }

               return 17;
            }

            return -1;
         }
      case 2:
         if((var2 & 1109592989761536L) != 0L) {
            this.jjmatchedKind = 50;
            this.jjmatchedPos = 2;
            return 17;
         } else if((var2 & 24576L) != 0L) {
            return 12;
         } else if((var2 & 960L) != 0L) {
            return 5;
         } else {
            if((var2 & 6683505983488L) != 0L) {
               return 17;
            }

            return -1;
         }
      case 3:
         if((var2 & 896L) != 0L) {
            return 4;
         } else if((var2 & 898473872326656L) != 0L) {
            if(this.jjmatchedPos != 3) {
               this.jjmatchedKind = 50;
               this.jjmatchedPos = 3;
            }

            return 17;
         } else if((var2 & 211119117434880L) != 0L) {
            return 17;
         } else {
            if((var2 & 16384L) != 0L) {
               return 9;
            }

            return -1;
         }
      case 4:
         if((var2 & 768L) != 0L) {
            return 3;
         } else if((var2 & 52922587021312L) != 0L) {
            this.jjmatchedKind = 50;
            this.jjmatchedPos = 4;
            return 17;
         } else {
            if((var2 & 845559875239936L) != 0L) {
               return 17;
            }

            return -1;
         }
      case 5:
         if((var2 & 512L) != 0L) {
            return 0;
         } else if((var2 & 137438953472L) != 0L) {
            this.jjmatchedKind = 50;
            this.jjmatchedPos = 5;
            return 17;
         } else {
            if((var2 & 52785148067840L) != 0L) {
               return 17;
            }

            return -1;
         }
      case 6:
         if((var2 & 137438953472L) != 0L) {
            this.jjmatchedKind = 50;
            this.jjmatchedPos = 6;
            return 17;
         }

         return -1;
      default:
         return -1;
      }
   }

   private final int jjStartNfa_0(int var1, long var2, long var4) {
      return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(var1, var2, var4), var1 + 1);
   }

   private int jjMoveStringLiteralDfa0_0() {
      switch(this.curChar) {
      case 35:
         return this.jjStopAtPos(0, 89);
      case 36:
      case 38:
      case 39:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      case 90:
      case 92:
      case 95:
      case 96:
      case 99:
      case 103:
      case 104:
      case 106:
      case 107:
      case 109:
      case 112:
      case 113:
      case 115:
      case 118:
      case 120:
      case 121:
      case 122:
      case 124:
      default:
         return this.jjMoveNfa_0(8, 0);
      case 37:
         return this.jjStopAtPos(0, 81);
      case 40:
         return this.jjStopAtPos(0, 69);
      case 41:
         return this.jjStopAtPos(0, 70);
      case 42:
         return this.jjStopAtPos(0, 78);
      case 43:
         return this.jjStopAtPos(0, 76);
      case 44:
         return this.jjStopAtPos(0, 66);
      case 45:
         this.jjmatchedKind = 77;
         return this.jjMoveStringLiteralDfa1_0(66496L, 0L);
      case 46:
         this.jjmatchedKind = 67;
         return this.jjMoveStringLiteralDfa1_0(0L, 262656L);
      case 47:
         return this.jjStopAtPos(0, 79);
      case 58:
         return this.jjStopAtPos(0, 68);
      case 59:
         return this.jjStopAtPos(0, 64);
      case 60:
         this.jjmatchedKind = 83;
         return this.jjMoveStringLiteralDfa1_0(0L, 1048576L);
      case 61:
         this.jjmatchedKind = 65;
         return this.jjMoveStringLiteralDfa1_0(0L, 8388608L);
      case 62:
         this.jjmatchedKind = 85;
         return this.jjMoveStringLiteralDfa1_0(0L, 4194304L);
      case 91:
         this.jjmatchedKind = 71;
         return this.jjMoveStringLiteralDfa1_0(30720L, 0L);
      case 93:
         return this.jjStopAtPos(0, 72);
      case 94:
         return this.jjStopAtPos(0, 80);
      case 97:
         return this.jjMoveStringLiteralDfa1_0(536870912L, 0L);
      case 98:
         return this.jjMoveStringLiteralDfa1_0(1073741824L, 0L);
      case 100:
         return this.jjMoveStringLiteralDfa1_0(2147483648L, 0L);
      case 101:
         return this.jjMoveStringLiteralDfa1_0(30064771072L, 0L);
      case 102:
         return this.jjMoveStringLiteralDfa1_0(240518168576L, 0L);
      case 105:
         return this.jjMoveStringLiteralDfa1_0(824633720832L, 0L);
      case 108:
         return this.jjMoveStringLiteralDfa1_0(1099511627776L, 0L);
      case 110:
         return this.jjMoveStringLiteralDfa1_0(6597069766656L, 0L);
      case 111:
         return this.jjMoveStringLiteralDfa1_0(8796093022208L, 0L);
      case 114:
         return this.jjMoveStringLiteralDfa1_0(52776558133248L, 0L);
      case 116:
         return this.jjMoveStringLiteralDfa1_0(211106232532992L, 0L);
      case 117:
         return this.jjMoveStringLiteralDfa1_0(281474976710656L, 0L);
      case 119:
         return this.jjMoveStringLiteralDfa1_0(562949953421312L, 0L);
      case 123:
         return this.jjStopAtPos(0, 74);
      case 125:
         return this.jjStopAtPos(0, 75);
      case 126:
         return this.jjMoveStringLiteralDfa1_0(0L, 16777216L);
      }
   }

   private int jjMoveStringLiteralDfa1_0(long var1, long var3) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var6) {
         this.jjStopStringLiteralDfa_0(0, var1, var3);
         return 1;
      }

      switch(this.curChar) {
      case 45:
         if((var1 & 65536L) != 0L) {
            this.jjmatchedKind = 16;
            this.jjmatchedPos = 1;
         }

         return this.jjMoveStringLiteralDfa2_0(var1, 960L, var3, 0L);
      case 46:
         if((var3 & 262144L) != 0L) {
            this.jjmatchedKind = 82;
            this.jjmatchedPos = 1;
         }

         return this.jjMoveStringLiteralDfa2_0(var1, 0L, var3, 512L);
      case 61:
         if((var3 & 1048576L) != 0L) {
            return this.jjStopAtPos(1, 84);
         }

         if((var3 & 4194304L) != 0L) {
            return this.jjStopAtPos(1, 86);
         }

         if((var3 & 8388608L) != 0L) {
            return this.jjStopAtPos(1, 87);
         }

         if((var3 & 16777216L) != 0L) {
            return this.jjStopAtPos(1, 88);
         }

         return this.jjMoveStringLiteralDfa2_0(var1, 28672L, var3, 0L);
      case 91:
         if((var1 & 2048L) != 0L) {
            return this.jjStopAtPos(1, 11);
         }
         break;
      case 97:
         return this.jjMoveStringLiteralDfa2_0(var1, 34359738368L, var3, 0L);
      case 101:
         return this.jjMoveStringLiteralDfa2_0(var1, 52776558133248L, var3, 0L);
      case 102:
         if((var1 & 274877906944L) != 0L) {
            return this.jjStartNfaWithStates_0(1, 38, 17);
         }
         break;
      case 104:
         return this.jjMoveStringLiteralDfa2_0(var1, 633318697598976L, var3, 0L);
      case 105:
         return this.jjMoveStringLiteralDfa2_0(var1, 2199023255552L, var3, 0L);
      case 108:
         return this.jjMoveStringLiteralDfa2_0(var1, 12884901888L, var3, 0L);
      case 110:
         if((var1 & 549755813888L) != 0L) {
            return this.jjStartNfaWithStates_0(1, 39, 17);
         }

         return this.jjMoveStringLiteralDfa2_0(var1, 281492693450752L, var3, 0L);
      case 111:
         if((var1 & 2147483648L) != 0L) {
            return this.jjStartNfaWithStates_0(1, 31, 17);
         }

         return this.jjMoveStringLiteralDfa2_0(var1, 5566277615616L, var3, 0L);
      case 114:
         if((var1 & 8796093022208L) != 0L) {
            return this.jjStartNfaWithStates_0(1, 43, 17);
         }

         return this.jjMoveStringLiteralDfa2_0(var1, 140738562097152L, var3, 0L);
      case 117:
         return this.jjMoveStringLiteralDfa2_0(var1, 137438953472L, var3, 0L);
      }

      return this.jjStartNfa_0(0, var1, var3);
   }

   private int jjMoveStringLiteralDfa2_0(long var1, long var3, long var5, long var7) {
      if(((var3 &= var1) | (var7 &= var5)) == 0L) {
         return this.jjStartNfa_0(0, var1, var5);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var10) {
            this.jjStopStringLiteralDfa_0(1, var3, var7);
            return 2;
         }

         switch(this.curChar) {
         case 46:
            if((var7 & 512L) != 0L) {
               return this.jjStopAtPos(2, 73);
            }
            break;
         case 61:
            return this.jjMoveStringLiteralDfa3_0(var3, 24576L, var7, 0L);
         case 91:
            if((var3 & 4096L) != 0L) {
               return this.jjStopAtPos(2, 12);
            }

            return this.jjMoveStringLiteralDfa3_0(var3, 960L, var7, 0L);
         case 99:
            return this.jjMoveStringLiteralDfa3_0(var3, 1099511627776L, var7, 0L);
         case 100:
            if((var3 & 536870912L) != 0L) {
               return this.jjStartNfaWithStates_0(2, 29, 17);
            }

            if((var3 & 17179869184L) != 0L) {
               return this.jjStartNfaWithStates_0(2, 34, 17);
            }
            break;
         case 101:
            return this.jjMoveStringLiteralDfa3_0(var3, 70369817919488L, var7, 0L);
         case 105:
            return this.jjMoveStringLiteralDfa3_0(var3, 562949953421312L, var7, 0L);
         case 108:
            if((var3 & 2199023255552L) != 0L) {
               return this.jjStartNfaWithStates_0(2, 41, 17);
            }

            return this.jjMoveStringLiteralDfa3_0(var3, 34359738368L, var7, 0L);
         case 110:
            return this.jjMoveStringLiteralDfa3_0(var3, 137438953472L, var7, 0L);
         case 112:
            return this.jjMoveStringLiteralDfa3_0(var3, 35184372088832L, var7, 0L);
         case 114:
            if((var3 & 68719476736L) != 0L) {
               return this.jjStartNfaWithStates_0(2, 36, 17);
            }
            break;
         case 115:
            return this.jjMoveStringLiteralDfa3_0(var3, 12884901888L, var7, 0L);
         case 116:
            if((var3 & 4398046511104L) != 0L) {
               return this.jjStartNfaWithStates_0(2, 42, 17);
            }

            return this.jjMoveStringLiteralDfa3_0(var3, 299067162755072L, var7, 0L);
         case 117:
            return this.jjMoveStringLiteralDfa3_0(var3, 140737488355328L, var7, 0L);
         }

         return this.jjStartNfa_0(1, var3, var7);
      }
   }

   private int jjMoveStringLiteralDfa3_0(long var1, long var3, long var5, long var7) {
      if(((var3 &= var1) | var7 & var5) == 0L) {
         return this.jjStartNfa_0(1, var1, var5);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var10) {
            this.jjStopStringLiteralDfa_0(2, var3, 0L);
            return 3;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa4_0(var3, 17280L);
         case 91:
            if((var3 & 64L) != 0L) {
               return this.jjStopAtPos(3, 6);
            }

            if((var3 & 8192L) != 0L) {
               return this.jjStopAtPos(3, 13);
            }
            break;
         case 97:
            return this.jjMoveStringLiteralDfa4_0(var3, 1100585369600L);
         case 99:
            return this.jjMoveStringLiteralDfa4_0(var3, 137438953472L);
         case 101:
            if((var3 & 4294967296L) != 0L) {
               this.jjmatchedKind = 32;
               this.jjmatchedPos = 3;
            } else if((var3 & 140737488355328L) != 0L) {
               return this.jjStartNfaWithStates_0(3, 47, 17);
            }

            return this.jjMoveStringLiteralDfa4_0(var3, 35192962023424L);
         case 105:
            return this.jjMoveStringLiteralDfa4_0(var3, 281474976710656L);
         case 108:
            return this.jjMoveStringLiteralDfa4_0(var3, 562949953421312L);
         case 110:
            if((var3 & 70368744177664L) != 0L) {
               return this.jjStartNfaWithStates_0(3, 46, 17);
            }
            break;
         case 115:
            return this.jjMoveStringLiteralDfa4_0(var3, 34359738368L);
         case 117:
            return this.jjMoveStringLiteralDfa4_0(var3, 17592186044416L);
         }

         return this.jjStartNfa_0(2, var3, 0L);
      }
   }

   private int jjMoveStringLiteralDfa4_0(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return this.jjStartNfa_0(2, var1, 0L);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(3, var3, 0L);
            return 4;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa5_0(var3, 768L);
         case 91:
            if((var3 & 128L) != 0L) {
               return this.jjStopAtPos(4, 7);
            }

            if((var3 & 16384L) != 0L) {
               return this.jjStopAtPos(4, 14);
            }
            break;
         case 97:
            return this.jjMoveStringLiteralDfa5_0(var3, 35184372088832L);
         case 101:
            if((var3 & 34359738368L) != 0L) {
               return this.jjStartNfaWithStates_0(4, 35, 17);
            }

            if((var3 & 562949953421312L) != 0L) {
               return this.jjStartNfaWithStates_0(4, 49, 17);
            }
            break;
         case 105:
            return this.jjMoveStringLiteralDfa5_0(var3, 8589934592L);
         case 107:
            if((var3 & 1073741824L) != 0L) {
               return this.jjStartNfaWithStates_0(4, 30, 17);
            }
            break;
         case 108:
            if((var3 & 1099511627776L) != 0L) {
               return this.jjStartNfaWithStates_0(4, 40, 17);
            }

            if((var3 & 281474976710656L) != 0L) {
               return this.jjStartNfaWithStates_0(4, 48, 17);
            }
            break;
         case 114:
            return this.jjMoveStringLiteralDfa5_0(var3, 17592186044416L);
         case 116:
            return this.jjMoveStringLiteralDfa5_0(var3, 137438953472L);
         }

         return this.jjStartNfa_0(3, var3, 0L);
      }
   }

   private int jjMoveStringLiteralDfa5_0(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return this.jjStartNfa_0(3, var1, 0L);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(4, var3, 0L);
            return 5;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa6_0(var3, 512L);
         case 91:
            if((var3 & 256L) != 0L) {
               return this.jjStopAtPos(5, 8);
            }
            break;
         case 102:
            if((var3 & 8589934592L) != 0L) {
               return this.jjStartNfaWithStates_0(5, 33, 17);
            }
            break;
         case 105:
            return this.jjMoveStringLiteralDfa6_0(var3, 137438953472L);
         case 110:
            if((var3 & 17592186044416L) != 0L) {
               return this.jjStartNfaWithStates_0(5, 44, 17);
            }
            break;
         case 116:
            if((var3 & 35184372088832L) != 0L) {
               return this.jjStartNfaWithStates_0(5, 45, 17);
            }
         }

         return this.jjStartNfa_0(4, var3, 0L);
      }
   }

   private int jjMoveStringLiteralDfa6_0(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return this.jjStartNfa_0(4, var1, 0L);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(5, var3, 0L);
            return 6;
         }

         switch(this.curChar) {
         case 91:
            if((var3 & 512L) != 0L) {
               return this.jjStopAtPos(6, 9);
            }
         case 111:
            return this.jjMoveStringLiteralDfa7_0(var3, 137438953472L);
         default:
            return this.jjStartNfa_0(5, var3, 0L);
         }
      }
   }

   private int jjMoveStringLiteralDfa7_0(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return this.jjStartNfa_0(5, var1, 0L);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(6, var3, 0L);
            return 7;
         }

         switch(this.curChar) {
         case 110:
            if((var3 & 137438953472L) != 0L) {
               return this.jjStartNfaWithStates_0(7, 37, 17);
            }
         default:
            return this.jjStartNfa_0(6, var3, 0L);
         }
      }
   }

   private int jjStartNfaWithStates_0(int var1, int var2, int var3) {
      this.jjmatchedKind = var2;
      this.jjmatchedPos = var1;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return var1 + 1;
      }

      return this.jjMoveNfa_0(var3, var1 + 1);
   }

   private int jjMoveNfa_0(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 63;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         if(++this.jjround == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long var6;
         if(this.curChar < 64) {
            var6 = 1L << this.curChar;

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
               case 1:
                  if(this.curChar == 61) {
                     this.jjCheckNAddTwoStates(1, 2);
                  }
               case 2:
               case 6:
               case 11:
               case 15:
               case 16:
               case 19:
               case 23:
               case 29:
               case 31:
               case 42:
               case 44:
               case 54:
               case 60:
               default:
                  break;
               case 3:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 0;
                  }
                  break;
               case 4:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 3;
                  }
                  break;
               case 5:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 4;
                  }
                  break;
               case 7:
                  if(this.curChar == 45) {
                     this.jjstateSet[this.jjnewStateCnt++] = 6;
                  }
                  break;
               case 8:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAddStates(0, 3);
                  } else if(this.curChar == 39) {
                     this.jjCheckNAddStates(4, 6);
                  } else if(this.curChar == 34) {
                     this.jjCheckNAddStates(7, 9);
                  } else if(this.curChar == 46) {
                     this.jjCheckNAdd(22);
                  } else if(this.curChar == 45) {
                     this.jjstateSet[this.jjnewStateCnt++] = 7;
                  }

                  if(this.curChar == 48) {
                     this.jjstateSet[this.jjnewStateCnt++] = 19;
                  }
                  break;
               case 9:
               case 10:
                  if(this.curChar == 61) {
                     this.jjCheckNAddTwoStates(10, 11);
                  }
                  break;
               case 12:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 9;
                  }
                  break;
               case 13:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 12;
                  }
                  break;
               case 14:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 13;
                  }
                  break;
               case 17:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 50) {
                        var5 = 50;
                     }

                     this.jjstateSet[this.jjnewStateCnt++] = 17;
                  }
                  break;
               case 18:
                  if(this.curChar == 48) {
                     this.jjstateSet[this.jjnewStateCnt++] = 19;
                  }
                  break;
               case 20:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjstateSet[this.jjnewStateCnt++] = 20;
                  }
                  break;
               case 21:
                  if(this.curChar == 46) {
                     this.jjCheckNAdd(22);
                  }
                  break;
               case 22:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAddTwoStates(22, 23);
                  }
                  break;
               case 24:
                  if((43980465111040L & var6) != 0L) {
                     this.jjCheckNAdd(25);
                  }
                  break;
               case 25:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAdd(25);
                  }
                  break;
               case 26:
                  if(this.curChar == 34) {
                     this.jjCheckNAddStates(7, 9);
                  }
                  break;
               case 27:
                  if((-17179869185L & var6) != 0L) {
                     this.jjCheckNAddStates(7, 9);
                  }
                  break;
               case 28:
                  if(this.curChar == 34 && var5 > 57) {
                     var5 = 57;
                  }
                  break;
               case 30:
                  this.jjCheckNAddStates(7, 9);
                  break;
               case 32:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 33;
                  }
                  break;
               case 33:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 34;
                  }
                  break;
               case 34:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 35;
                  }
                  break;
               case 35:
               case 38:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjCheckNAddStates(7, 9);
                  }
                  break;
               case 36:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjCheckNAddStates(10, 13);
                  }
                  break;
               case 37:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjCheckNAddStates(14, 17);
                  }
                  break;
               case 39:
                  if(this.curChar == 39) {
                     this.jjCheckNAddStates(4, 6);
                  }
                  break;
               case 40:
                  if((-549755813889L & var6) != 0L) {
                     this.jjCheckNAddStates(4, 6);
                  }
                  break;
               case 41:
                  if(this.curChar == 39 && var5 > 58) {
                     var5 = 58;
                  }
                  break;
               case 43:
                  this.jjCheckNAddStates(4, 6);
                  break;
               case 45:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 46;
                  }
                  break;
               case 46:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 47;
                  }
                  break;
               case 47:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 48;
                  }
                  break;
               case 48:
               case 51:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjCheckNAddStates(4, 6);
                  }
                  break;
               case 49:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjCheckNAddStates(18, 21);
                  }
                  break;
               case 50:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjCheckNAddStates(22, 25);
                  }
                  break;
               case 52:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAddStates(0, 3);
                  }
                  break;
               case 53:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAddTwoStates(53, 54);
                  }
                  break;
               case 55:
                  if((43980465111040L & var6) != 0L) {
                     this.jjCheckNAdd(56);
                  }
                  break;
               case 56:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAdd(56);
                  }
                  break;
               case 57:
                  if((287948901175001088L & var6) != 0L) {
                     this.jjCheckNAddTwoStates(57, 58);
                  }
                  break;
               case 58:
                  if(this.curChar == 46) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAddTwoStates(59, 60);
                  }
                  break;
               case 59:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAddTwoStates(59, 60);
                  }
                  break;
               case 61:
                  if((43980465111040L & var6) != 0L) {
                     this.jjCheckNAdd(62);
                  }
                  break;
               case 62:
                  if((287948901175001088L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAdd(62);
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            var6 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 2:
                  if(this.curChar == 91 && var5 > 10) {
                     var5 = 10;
                  }
               case 3:
               case 4:
               case 5:
               case 7:
               case 9:
               case 10:
               case 12:
               case 13:
               case 14:
               case 18:
               case 21:
               case 22:
               case 24:
               case 25:
               case 26:
               case 28:
               case 36:
               case 37:
               case 38:
               case 39:
               case 41:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 55:
               case 56:
               case 57:
               case 58:
               case 59:
               default:
                  break;
               case 6:
                  if(this.curChar == 91) {
                     this.jjstateSet[this.jjnewStateCnt++] = 5;
                  }
                  break;
               case 8:
                  if((576460745995190270L & var6) != 0L) {
                     if(var5 > 50) {
                        var5 = 50;
                     }

                     this.jjCheckNAdd(17);
                  } else if(this.curChar == 91) {
                     this.jjstateSet[this.jjnewStateCnt++] = 14;
                  }
                  break;
               case 11:
                  if(this.curChar == 91 && var5 > 15) {
                     var5 = 15;
                  }
                  break;
               case 15:
                  if(this.curChar == 91) {
                     this.jjstateSet[this.jjnewStateCnt++] = 14;
                  }
                  break;
               case 16:
               case 17:
                  if((576460745995190270L & var6) != 0L) {
                     if(var5 > 50) {
                        var5 = 50;
                     }

                     this.jjCheckNAdd(17);
                  }
                  break;
               case 19:
                  if((72057594054705152L & var6) != 0L) {
                     this.jjCheckNAdd(20);
                  }
                  break;
               case 20:
                  if((541165879422L & var6) != 0L) {
                     if(var5 > 51) {
                        var5 = 51;
                     }

                     this.jjCheckNAdd(20);
                  }
                  break;
               case 23:
                  if((137438953504L & var6) != 0L) {
                     this.jjAddStates(26, 27);
                  }
                  break;
               case 27:
                  if((-268435457L & var6) != 0L) {
                     this.jjCheckNAddStates(7, 9);
                  }
                  break;
               case 29:
                  if(this.curChar == 92) {
                     this.jjAddStates(28, 30);
                  }
                  break;
               case 30:
                  this.jjCheckNAddStates(7, 9);
                  break;
               case 31:
                  if(this.curChar == 117) {
                     this.jjstateSet[this.jjnewStateCnt++] = 32;
                  }
                  break;
               case 32:
                  if((541165879422L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 33;
                  }
                  break;
               case 33:
                  if((541165879422L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 34;
                  }
                  break;
               case 34:
                  if((541165879422L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 35;
                  }
                  break;
               case 35:
                  if((541165879422L & var6) != 0L) {
                     this.jjCheckNAddStates(7, 9);
                  }
                  break;
               case 40:
                  if((-268435457L & var6) != 0L) {
                     this.jjCheckNAddStates(4, 6);
                  }
                  break;
               case 42:
                  if(this.curChar == 92) {
                     this.jjAddStates(31, 33);
                  }
                  break;
               case 43:
                  this.jjCheckNAddStates(4, 6);
                  break;
               case 44:
                  if(this.curChar == 117) {
                     this.jjstateSet[this.jjnewStateCnt++] = 45;
                  }
                  break;
               case 45:
                  if((541165879422L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 46;
                  }
                  break;
               case 46:
                  if((541165879422L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 47;
                  }
                  break;
               case 47:
                  if((541165879422L & var6) != 0L) {
                     this.jjstateSet[this.jjnewStateCnt++] = 48;
                  }
                  break;
               case 48:
                  if((541165879422L & var6) != 0L) {
                     this.jjCheckNAddStates(4, 6);
                  }
                  break;
               case 54:
                  if((137438953504L & var6) != 0L) {
                     this.jjAddStates(34, 35);
                  }
                  break;
               case 60:
                  if((137438953504L & var6) != 0L) {
                     this.jjAddStates(36, 37);
                  }
               }
            } while(var4 != var3);
         } else {
            int var14 = this.curChar >> 8;
            int var7 = var14 >> 6;
            long var8 = 1L << (var14 & 63);
            int var10 = (this.curChar & 255) >> 6;
            long var11 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 27:
               case 30:
                  if(jjCanMove_0(var14, var7, var10, var8, var11)) {
                     this.jjCheckNAddStates(7, 9);
                  }
                  break;
               case 40:
               case 43:
                  if(jjCanMove_0(var14, var7, var10, var8, var11)) {
                     this.jjCheckNAddStates(4, 6);
                  }
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         if((var4 = this.jjnewStateCnt) == (var3 = 63 - (this.jjnewStateCnt = var3))) {
            return var2;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var13) {
            return var2;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_1() {
      return this.jjMoveNfa_1(4, 0);
   }

   private int jjMoveNfa_1(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 4;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         if(++this.jjround == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long var6;
         if(this.curChar < 64) {
            var6 = 1L << this.curChar;

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
                  if((-9217L & var6) != 0L) {
                     var5 = 17;
                     this.jjCheckNAddStates(38, 40);
                  }
                  break;
               case 1:
                  if((9216L & var6) != 0L && var5 > 17) {
                     var5 = 17;
                  }
                  break;
               case 2:
                  if(this.curChar == 10 && var5 > 17) {
                     var5 = 17;
                  }
                  break;
               case 3:
                  if(this.curChar == 13) {
                     this.jjstateSet[this.jjnewStateCnt++] = 2;
                  }
                  break;
               case 4:
                  if((-9217L & var6) != 0L) {
                     if(var5 > 17) {
                        var5 = 17;
                     }

                     this.jjCheckNAddStates(38, 40);
                  } else if((9216L & var6) != 0L && var5 > 17) {
                     var5 = 17;
                  }

                  if(this.curChar == 13) {
                     this.jjstateSet[this.jjnewStateCnt++] = 2;
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            var6 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
               case 4:
                  var5 = 17;
                  this.jjCheckNAddStates(38, 40);
               }
            } while(var4 != var3);
         } else {
            int var14 = this.curChar >> 8;
            int var7 = var14 >> 6;
            long var8 = 1L << (var14 & 63);
            int var10 = (this.curChar & 255) >> 6;
            long var11 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
               case 4:
                  if(jjCanMove_0(var14, var7, var10, var8, var11)) {
                     if(var5 > 17) {
                        var5 = 17;
                     }

                     this.jjCheckNAddStates(38, 40);
                  }
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         if((var4 = this.jjnewStateCnt) == (var3 = 4 - (this.jjnewStateCnt = var3))) {
            return var2;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var13) {
            return var2;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_6() {
      return this.jjMoveNfa_6(6, 0);
   }

   private int jjMoveNfa_6(int var1, int var2) {
      int var3 = 0;
      this.jjnewStateCnt = 7;
      int var4 = 1;
      this.jjstateSet[0] = var1;
      int var5 = Integer.MAX_VALUE;

      while(true) {
         if(++this.jjround == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long var6;
         if(this.curChar < 64) {
            var6 = 1L << this.curChar;

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 0:
               case 1:
                  if(this.curChar == 61) {
                     this.jjCheckNAddTwoStates(1, 2);
                  }
               case 2:
               default:
                  break;
               case 3:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 0;
                  }
                  break;
               case 4:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 3;
                  }
                  break;
               case 5:
                  if(this.curChar == 61) {
                     this.jjstateSet[this.jjnewStateCnt++] = 4;
                  }
               }
            } while(var4 != var3);
         } else if(this.curChar < 128) {
            var6 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               case 2:
                  if(this.curChar == 93 && var5 > 22) {
                     var5 = 22;
                  }
                  break;
               case 6:
                  if(this.curChar == 93) {
                     this.jjstateSet[this.jjnewStateCnt++] = 5;
                  }
               }
            } while(var4 != var3);
         } else {
            int var14 = this.curChar >> 8;
            int var7 = var14 >> 6;
            long var8 = 1L << (var14 & 63);
            int var10 = (this.curChar & 255) >> 6;
            long var11 = 1L << (this.curChar & 63);

            do {
               --var4;
               switch(this.jjstateSet[var4]) {
               }
            } while(var4 != var3);
         }

         if(var5 != Integer.MAX_VALUE) {
            this.jjmatchedKind = var5;
            this.jjmatchedPos = var2;
            var5 = Integer.MAX_VALUE;
         }

         ++var2;
         if((var4 = this.jjnewStateCnt) == (var3 = 7 - (this.jjnewStateCnt = var3))) {
            return var2;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var13) {
            return var2;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_5() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_5(2097152L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_5(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 61:
         return this.jjMoveStringLiteralDfa2_5(var1, 2097152L);
      default:
         return 2;
      }
   }

   private int jjMoveStringLiteralDfa2_5(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 2;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 2;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa3_5(var3, 2097152L);
         default:
            return 3;
         }
      }
   }

   private int jjMoveStringLiteralDfa3_5(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 3;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 3;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa4_5(var3, 2097152L);
         default:
            return 4;
         }
      }
   }

   private int jjMoveStringLiteralDfa4_5(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 4;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 4;
         }

         switch(this.curChar) {
         case 93:
            if((var3 & 2097152L) != 0L) {
               return this.jjStopAtPos(4, 21);
            }

            return 5;
         default:
            return 5;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_4() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_4(1048576L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_4(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 61:
         return this.jjMoveStringLiteralDfa2_4(var1, 1048576L);
      default:
         return 2;
      }
   }

   private int jjMoveStringLiteralDfa2_4(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 2;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 2;
         }

         switch(this.curChar) {
         case 61:
            return this.jjMoveStringLiteralDfa3_4(var3, 1048576L);
         default:
            return 3;
         }
      }
   }

   private int jjMoveStringLiteralDfa3_4(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 3;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 3;
         }

         switch(this.curChar) {
         case 93:
            if((var3 & 1048576L) != 0L) {
               return this.jjStopAtPos(3, 20);
            }

            return 4;
         default:
            return 4;
         }
      }
   }

   private int jjMoveStringLiteralDfa0_3() {
      switch(this.curChar) {
      case 93:
         return this.jjMoveStringLiteralDfa1_3(524288L);
      default:
         return 1;
      }
   }

   private int jjMoveStringLiteralDfa1_3(long var1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         return 1;
      }

      switch(this.curChar) {
      case 61:
         return this.jjMoveStringLiteralDfa2_3(var1, 524288L);
      default:
         return 2;
      }
   }

   private int jjMoveStringLiteralDfa2_3(long var1, long var3) {
      if((var3 &= var1) == 0L) {
         return 2;
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return 2;
         }

         switch(this.curChar) {
         case 93:
            if((var3 & 524288L) != 0L) {
               return this.jjStopAtPos(2, 19);
            }

            return 3;
         default:
            return 3;
         }
      }
   }

   private static final boolean jjCanMove_0(int var0, int var1, int var2, long var3, long var5) {
      switch(var0) {
      case 0:
         return (jjbitVec2[var2] & var5) != 0L;
      default:
         return (jjbitVec0[var1] & var3) != 0L;
      }
   }

   public LuaParserTokenManager(SimpleCharStream var1) {
      this.debugStream = System.out;
      this.jjrounds = new int[63];
      this.jjstateSet = new int[126];
      this.jjimage = new StringBuffer();
      this.image = this.jjimage;
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = var1;
   }

   public LuaParserTokenManager(SimpleCharStream var1, int var2) {
      this(var1);
      this.SwitchTo(var2);
   }

   public void ReInit(SimpleCharStream var1) {
      this.jjmatchedPos = this.jjnewStateCnt = 0;
      this.curLexState = this.defaultLexState;
      this.input_stream = var1;
      this.ReInitRounds();
   }

   private void ReInitRounds() {
      this.jjround = -2147483647;

      for(int var1 = 63; var1-- > 0; this.jjrounds[var1] = Integer.MIN_VALUE) {
         ;
      }

   }

   public void ReInit(SimpleCharStream var1, int var2) {
      this.ReInit(var1);
      this.SwitchTo(var2);
   }

   public void SwitchTo(int var1) {
      if(var1 < 12 && var1 >= 0) {
         this.curLexState = var1;
      } else {
         throw new TokenMgrError("Error: Ignoring invalid lexical state : " + var1 + ". State unchanged.", 2);
      }
   }

   protected Token jjFillToken() {
      String var2;
      int var3;
      int var4;
      int var5;
      int var6;
      if(this.jjmatchedPos < 0) {
         if(this.image == null) {
            var2 = "";
         } else {
            var2 = this.image.toString();
         }

         var3 = var4 = this.input_stream.getBeginLine();
         var5 = var6 = this.input_stream.getBeginColumn();
      } else {
         String var7 = jjstrLiteralImages[this.jjmatchedKind];
         var2 = var7 == null?this.input_stream.GetImage():var7;
         var3 = this.input_stream.getBeginLine();
         var5 = this.input_stream.getBeginColumn();
         var4 = this.input_stream.getEndLine();
         var6 = this.input_stream.getEndColumn();
      }

      Token var1 = Token.newToken(this.jjmatchedKind, var2);
      var1.beginLine = var3;
      var1.endLine = var4;
      var1.beginColumn = var5;
      var1.endColumn = var6;
      return var1;
   }

   public Token getNextToken() {
      Token var1 = null;
      int var3 = 0;

      label164:
      while(true) {
         Token var2;
         try {
            this.curChar = this.input_stream.BeginToken();
         } catch (IOException var9) {
            this.jjmatchedKind = 0;
            var2 = this.jjFillToken();
            var2.specialToken = var1;
            return var2;
         }

         this.image = this.jjimage;
         this.image.setLength(0);
         this.jjimageLen = 0;

         while(true) {
            switch(this.curLexState) {
            case 0:
               try {
                  this.input_stream.backup(0);

                  while(this.curChar <= 32 && (4294981120L & 1L << this.curChar) != 0L) {
                     this.curChar = this.input_stream.BeginToken();
                  }
               } catch (IOException var12) {
                  continue label164;
               }

               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_0();
               break;
            case 1:
               this.jjmatchedKind = 17;
               this.jjmatchedPos = -1;
               boolean var13 = false;
               var3 = this.jjMoveStringLiteralDfa0_1();
               break;
            case 2:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_2();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 3:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_3();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 4:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_4();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 5:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_5();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 6:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_6();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 7:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_7();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 8:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_8();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 9:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_9();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 10:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_10();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
               break;
            case 11:
               this.jjmatchedKind = Integer.MAX_VALUE;
               this.jjmatchedPos = 0;
               var3 = this.jjMoveStringLiteralDfa0_11();
               if(this.jjmatchedPos == 0 && this.jjmatchedKind > 28) {
                  this.jjmatchedKind = 28;
               }
            }

            if(this.jjmatchedKind == Integer.MAX_VALUE) {
               break label164;
            }

            if(this.jjmatchedPos + 1 < var3) {
               this.input_stream.backup(var3 - this.jjmatchedPos - 1);
            }

            if((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
               var2 = this.jjFillToken();
               var2.specialToken = var1;
               if(jjnewLexState[this.jjmatchedKind] != -1) {
                  this.curLexState = jjnewLexState[this.jjmatchedKind];
               }

               return var2;
            }

            if((jjtoSkip[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
               if((jjtoSpecial[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
                  var2 = this.jjFillToken();
                  if(var1 == null) {
                     var1 = var2;
                  } else {
                     var2.specialToken = var1;
                     var1 = var1.next = var2;
                  }

                  this.SkipLexicalActions(var2);
               } else {
                  this.SkipLexicalActions((Token)null);
               }

               if(jjnewLexState[this.jjmatchedKind] != -1) {
                  this.curLexState = jjnewLexState[this.jjmatchedKind];
               }
               break;
            }

            this.jjimageLen += this.jjmatchedPos + 1;
            if(jjnewLexState[this.jjmatchedKind] != -1) {
               this.curLexState = jjnewLexState[this.jjmatchedKind];
            }

            var3 = 0;
            this.jjmatchedKind = Integer.MAX_VALUE;

            try {
               this.curChar = this.input_stream.readChar();
            } catch (IOException var11) {
               break label164;
            }
         }
      }

      int var4 = this.input_stream.getEndLine();
      int var5 = this.input_stream.getEndColumn();
      String var6 = null;
      boolean var7 = false;

      try {
         this.input_stream.readChar();
         this.input_stream.backup(1);
      } catch (IOException var10) {
         var7 = true;
         var6 = var3 <= 1?"":this.input_stream.GetImage();
         if(this.curChar != 10 && this.curChar != 13) {
            ++var5;
         } else {
            ++var4;
            var5 = 0;
         }
      }

      if(!var7) {
         this.input_stream.backup(1);
         var6 = var3 <= 1?"":this.input_stream.GetImage();
      }

      throw new TokenMgrError(var7, this.curLexState, var4, var5, var6, this.curChar, 0);
   }

   void SkipLexicalActions(Token var1) {
      switch(this.jjmatchedKind) {
      default:
      }
   }

   private void jjCheckNAdd(int var1) {
      if(this.jjrounds[var1] != this.jjround) {
         this.jjstateSet[this.jjnewStateCnt++] = var1;
         this.jjrounds[var1] = this.jjround;
      }

   }

   private void jjAddStates(int var1, int var2) {
      do {
         this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[var1];
      } while(var1++ != var2);

   }

   private void jjCheckNAddTwoStates(int var1, int var2) {
      this.jjCheckNAdd(var1);
      this.jjCheckNAdd(var2);
   }

   private void jjCheckNAddStates(int var1, int var2) {
      do {
         this.jjCheckNAdd(jjnextStates[var1]);
      } while(var1++ != var2);

   }

}
