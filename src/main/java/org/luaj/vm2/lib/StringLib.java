package org.luaj.vm2.lib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.luaj.vm2.Buffer;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.compiler.DumpState;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.VarArgFunction;

public class StringLib extends OneArgFunction {

   public static LuaTable instance;
   private static final String FLAGS = "-+ #0";
   private static final int L_ESC = 37;
   private static final LuaString SPECIALS = valueOf("^$*+?.([%-");
   private static final int MAX_CAPTURES = 32;
   private static final int CAP_UNFINISHED = -1;
   private static final int CAP_POSITION = -2;
   private static final byte MASK_ALPHA = 1;
   private static final byte MASK_LOWERCASE = 2;
   private static final byte MASK_UPPERCASE = 4;
   private static final byte MASK_DIGIT = 8;
   private static final byte MASK_PUNCT = 16;
   private static final byte MASK_SPACE = 32;
   private static final byte MASK_CONTROL = 64;
   private static final byte MASK_HEXDIGIT = -128;
   private static final byte[] CHAR_TABLE = new byte[256];
   static Class class$org$luaj$vm2$lib$StringLib$StringLib1;
   static Class class$org$luaj$vm2$lib$StringLib$StringLibV;


   public LuaValue call(LuaValue var1) {
      LuaTable var2 = new LuaTable();
      this.bind(var2, class$org$luaj$vm2$lib$StringLib$StringLib1 == null?(class$org$luaj$vm2$lib$StringLib$StringLib1 = class$("org.luaj.vm2.lib.StringLib$StringLib1")):class$org$luaj$vm2$lib$StringLib$StringLib1, new String[]{"dump", "len", "lower", "reverse", "upper"});
      this.bind(var2, class$org$luaj$vm2$lib$StringLib$StringLibV == null?(class$org$luaj$vm2$lib$StringLib$StringLibV = class$("org.luaj.vm2.lib.StringLib$StringLibV")):class$org$luaj$vm2$lib$StringLib$StringLibV, new String[]{"byte", "char", "find", "format", "gmatch", "gsub", "match", "rep", "sub"});
      this.env.set("string", (LuaValue)var2);
      instance = var2;
      if(LuaString.s_metatable == null) {
         LuaString.s_metatable = tableOf(new LuaValue[]{INDEX, var2});
      }

      PackageLib.instance.LOADED.set("string", var2);
      return var2;
   }

   static Varargs byte_(Varargs var0) {
      LuaString var1 = var0.checkstring(1);
      int var2 = var1.m_length;
      int var3 = posrelat(var0.optint(2, 1), var2);
      int var4 = posrelat(var0.optint(3, var3), var2);
      if(var3 <= 0) {
         var3 = 1;
      }

      if(var4 > var2) {
         var4 = var2;
      }

      if(var3 > var4) {
         return NONE;
      } else {
         int var5 = var4 - var3 + 1;
         if(var3 + var5 <= var4) {
            error("string slice too long");
         }

         LuaValue[] var7 = new LuaValue[var5];

         for(int var6 = 0; var6 < var5; ++var6) {
            var7[var6] = valueOf(var1.luaByte(var3 + var6 - 1));
         }

         return varargsOf(var7);
      }
   }

   public static Varargs char_(Varargs var0) {
      int var1 = var0.narg();
      byte[] var2 = new byte[var1];
      int var3 = 0;

      for(int var4 = 1; var3 < var1; ++var4) {
         int var5 = var0.checkint(var4);
         if(var5 < 0 || var5 >= 256) {
            argerror(var4, "invalid value");
         }

         var2[var3] = (byte)var5;
         ++var3;
      }

      return LuaString.valueOf(var2);
   }

   static LuaValue dump(LuaValue var0) {
      LuaValue var1 = var0.checkfunction();
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();

      try {
         DumpState.dump(((LuaClosure)var1).p, var2, true);
         return LuaString.valueOf(var2.toByteArray());
      } catch (IOException var4) {
         return error(var4.getMessage());
      }
   }

   static Varargs find(Varargs var0) {
      return str_find_aux(var0, true);
   }

   static Varargs format(Varargs var0) {
      LuaString var1 = var0.checkstring(1);
      int var2 = var1.length();
      Buffer var3 = new Buffer(var2);
      int var4 = 1;
      int var6 = 0;

      while(var6 < var2) {
         int var5;
         switch(var5 = var1.luaByte(var6++)) {
         case 10:
            var3.append("\n");
            break;
         case 37:
            if(var6 < var2) {
               if(var1.luaByte(var6) == 37) {
                  ++var6;
                  var3.append((byte)37);
               } else {
                  ++var4;
                  StringLib.FormatDesc var7 = new StringLib.FormatDesc(var0, var1, var6);
                  var6 += var7.length;
                  switch(var7.conversion) {
                  case 69:
                  case 71:
                  case 101:
                  case 102:
                  case 103:
                     var7.format(var3, var0.checkdouble(var4));
                     break;
                  case 70:
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
                  case 89:
                  case 90:
                  case 91:
                  case 92:
                  case 93:
                  case 94:
                  case 95:
                  case 96:
                  case 97:
                  case 98:
                  case 104:
                  case 106:
                  case 107:
                  case 108:
                  case 109:
                  case 110:
                  case 112:
                  case 114:
                  case 116:
                  case 118:
                  case 119:
                  default:
                     error("invalid option \'%" + (char)var7.conversion + "\' to \'format\'");
                     break;
                  case 88:
                  case 111:
                  case 117:
                  case 120:
                     var7.format(var3, var0.checklong(var4));
                     break;
                  case 99:
                     var7.format(var3, (byte)var0.checkint(var4));
                     break;
                  case 100:
                  case 105:
                     var7.format(var3, (long)var0.checkint(var4));
                     break;
                  case 113:
                     addquoted(var3, var0.checkstring(var4));
                     break;
                  case 115:
                     LuaString var8 = var0.checkstring(var4);
                     if(var7.precision == -1 && var8.length() >= 100) {
                        var3.append(var8);
                     } else {
                        var7.format(var3, var8);
                     }
                  }
               }
            }
            break;
         default:
            var3.append((byte)var5);
         }
      }

      return var3.tostring();
   }

   private static void addquoted(Buffer var0, LuaString var1) {
      var0.append((byte)34);
      int var3 = 0;

      for(int var4 = var1.length(); var3 < var4; ++var3) {
         int var2;
         switch(var2 = var1.luaByte(var3)) {
         case 0:
            var0.append("\\000");
            break;
         case 10:
         case 34:
         case 92:
            var0.append((byte)92);
            var0.append((byte)var2);
            break;
         case 13:
            var0.append("\\r");
            break;
         default:
            if((var2 < 32 || var2 > 126) && (var2 < 160 || var2 > 255)) {
               String var5;
               for(var5 = Integer.toString(var2); var5.length() < 3; var5 = "0" + var5) {
                  ;
               }

               var0.append("\\" + var5);
            } else {
               var0.append((byte)var2);
            }
         }
      }

      var0.append((byte)34);
   }

   static Varargs gmatch(Varargs var0) {
      LuaString var1 = var0.checkstring(1);
      LuaString var2 = var0.checkstring(2);
      return new StringLib.GMatchAux(var0, var1, var2);
   }

   static Varargs gsub(Varargs var0) {
      LuaString var1 = var0.checkstring(1);
      int var2 = var1.length();
      LuaString var3 = var0.checkstring(2);
      LuaValue var4 = var0.arg(3);
      int var5 = var0.optint(4, var2 + 1);
      boolean var6 = var3.length() > 0 && var3.charAt(0) == 94;
      Buffer var7 = new Buffer(var2);
      StringLib.MatchState var8 = new StringLib.MatchState(var0, var1, var3);
      int var9 = 0;
      int var10 = 0;

      while(var10 < var5) {
         var8.reset();
         int var11 = var8.match(var9, var6?1:0);
         if(var11 != -1) {
            ++var10;
            var8.add_value(var7, var9, var11, var4);
         }

         if(var11 != -1 && var11 > var9) {
            var9 = var11;
         } else {
            if(var9 >= var2) {
               break;
            }

            var7.append((byte)var1.luaByte(var9++));
         }

         if(var6) {
            break;
         }
      }

      var7.append(var1.substring(var9, var2));
      return varargsOf(var7.tostring(), valueOf(var10));
   }

   static LuaValue len(LuaValue var0) {
      return var0.checkstring().len();
   }

   static LuaValue lower(LuaValue var0) {
      return valueOf(var0.checkjstring().toLowerCase());
   }

   static Varargs match(Varargs var0) {
      return str_find_aux(var0, false);
   }

   static Varargs rep(Varargs var0) {
      LuaString var1 = var0.checkstring(1);
      int var2 = var0.checkint(2);
      byte[] var3 = new byte[var1.length() * var2];
      int var4 = var1.length();

      for(int var5 = 0; var5 < var3.length; var5 += var4) {
         var1.copyInto(0, var3, var5, var4);
      }

      return LuaString.valueOf(var3);
   }

   static LuaValue reverse(LuaValue var0) {
      LuaString var1 = var0.checkstring();
      int var2 = var1.length();
      byte[] var3 = new byte[var2];
      int var4 = 0;

      for(int var5 = var2 - 1; var4 < var2; --var5) {
         var3[var5] = (byte)var1.luaByte(var4);
         ++var4;
      }

      return LuaString.valueOf(var3);
   }

   static Varargs sub(Varargs var0) {
      LuaString var1 = var0.checkstring(1);
      int var2 = var1.length();
      int var3 = posrelat(var0.checkint(2), var2);
      int var4 = posrelat(var0.optint(3, -1), var2);
      if(var3 < 1) {
         var3 = 1;
      }

      if(var4 > var2) {
         var4 = var2;
      }

      return var3 <= var4?var1.substring(var3 - 1, var4):EMPTYSTRING;
   }

   static LuaValue upper(LuaValue var0) {
      return valueOf(var0.checkjstring().toUpperCase());
   }

   static Varargs str_find_aux(Varargs var0, boolean var1) {
      LuaString var2 = var0.checkstring(1);
      LuaString var3 = var0.checkstring(2);
      int var4 = var0.optint(3, 1);
      if(var4 > 0) {
         var4 = Math.min(var4 - 1, var2.length());
      } else if(var4 < 0) {
         var4 = Math.max(0, var2.length() + var4);
      }

      boolean var5 = var1 && (var0.arg(4).toboolean() || var3.indexOfAny(SPECIALS) == -1);
      if(var5) {
         int var6 = var2.indexOf(var3, var4);
         if(var6 != -1) {
            return varargsOf(valueOf(var6 + 1), valueOf(var6 + var3.length()));
         }
      } else {
         StringLib.MatchState var11 = new StringLib.MatchState(var0, var2, var3);
         boolean var7 = false;
         byte var8 = 0;
         if(var3.luaByte(0) == 94) {
            var7 = true;
            var8 = 1;
         }

         int var9 = var4;

         do {
            var11.reset();
            int var10;
            if((var10 = var11.match(var9, var8)) != -1) {
               if(var1) {
                  return varargsOf(valueOf(var9 + 1), valueOf(var10), var11.push_captures(false, var9, var10));
               }

               return var11.push_captures(true, var9, var10);
            }
         } while(var9++ < var2.length() && !var7);
      }

      return NIL;
   }

   private static int posrelat(int var0, int var1) {
      return var0 >= 0?var0:var1 + var0 + 1;
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static byte[] access$100() {
      return CHAR_TABLE;
   }

   static {
      for(int var0 = 0; var0 < 256; ++var0) {
         char var1 = (char)var0;
         CHAR_TABLE[var0] = (byte)((Character.isDigit(var1)?8:0) | (Character.isLowerCase(var1)?2:0) | (Character.isUpperCase(var1)?4:0) | (var1 >= 32 && var1 != 127?0:64));
         if(var1 >= 97 && var1 <= 102 || var1 >= 65 && var1 <= 70 || var1 >= 48 && var1 <= 57) {
            CHAR_TABLE[var0] |= -128;
         }

         if(var1 >= 33 && var1 <= 47 || var1 >= 58 && var1 <= 64) {
            CHAR_TABLE[var0] = (byte)(CHAR_TABLE[var0] | 16);
         }

         if((CHAR_TABLE[var0] & 6) != 0) {
            CHAR_TABLE[var0] = (byte)(CHAR_TABLE[var0] | 1);
         }
      }

      CHAR_TABLE[32] = 32;
      CHAR_TABLE[13] = (byte)(CHAR_TABLE[13] | 32);
      CHAR_TABLE[10] = (byte)(CHAR_TABLE[10] | 32);
      CHAR_TABLE[9] = (byte)(CHAR_TABLE[9] | 32);
      CHAR_TABLE[11] = (byte)(CHAR_TABLE[11] | 32);
      CHAR_TABLE[12] = (byte)(CHAR_TABLE[12] | 32);
   }

   static final class StringLib1 extends OneArgFunction {

      public LuaValue call(LuaValue var1) {
         switch(this.opcode) {
         case 0:
            return StringLib.dump(var1);
         case 1:
            return StringLib.len(var1);
         case 2:
            return StringLib.lower(var1);
         case 3:
            return StringLib.reverse(var1);
         case 4:
            return StringLib.upper(var1);
         default:
            return NIL;
         }
      }
   }

   static final class StringLibV extends VarArgFunction {

      public Varargs invoke(Varargs var1) {
         switch(this.opcode) {
         case 0:
            return StringLib.byte_(var1);
         case 1:
            return StringLib.char_(var1);
         case 2:
            return StringLib.find(var1);
         case 3:
            return StringLib.format(var1);
         case 4:
            return StringLib.gmatch(var1);
         case 5:
            return StringLib.gsub(var1);
         case 6:
            return StringLib.match(var1);
         case 7:
            return StringLib.rep(var1);
         case 8:
            return StringLib.sub(var1);
         default:
            return NONE;
         }
      }
   }

   static class GMatchAux extends VarArgFunction {

      private final int srclen;
      private final StringLib.MatchState ms;
      private int soffset;


      public GMatchAux(Varargs var1, LuaString var2, LuaString var3) {
         this.srclen = var2.length();
         this.ms = new StringLib.MatchState(var1, var2, var3);
         this.soffset = 0;
      }

      public Varargs invoke(Varargs var1) {
         while(this.soffset < this.srclen) {
            this.ms.reset();
            int var2 = this.ms.match(this.soffset, 0);
            if(var2 >= 0) {
               int var3 = this.soffset;
               this.soffset = var2;
               if(var2 == var3) {
                  ++this.soffset;
               }

               return this.ms.push_captures(true, var3, var2);
            }

            ++this.soffset;
         }

         return NIL;
      }
   }

   static class FormatDesc {

      private boolean leftAdjust;
      private boolean zeroPad;
      private boolean explicitPlus;
      private boolean space;
      private boolean alternateForm;
      private static final int MAX_FLAGS = 5;
      private int width;
      private int precision;
      public final int conversion;
      public final int length;


      public FormatDesc(Varargs var1, LuaString var2, int var3) {
         int var4 = var3;
         int var5 = var2.length();
         int var6 = 0;
         boolean var7 = true;

         while(var7) {
            switch(var6 = var4 < var5?var2.luaByte(var4++):0) {
            case 32:
               this.space = true;
               break;
            case 35:
               this.alternateForm = true;
               break;
            case 43:
               this.explicitPlus = true;
               break;
            case 45:
               this.leftAdjust = true;
               break;
            case 48:
               this.zeroPad = true;
               break;
            default:
               var7 = false;
            }
         }

         if(var4 - var3 > 5) {
            LuaValue.error("invalid format (repeated flags)");
         }

         this.width = -1;
         if(Character.isDigit((char)var6)) {
            this.width = var6 - 48;
            var6 = var4 < var5?var2.luaByte(var4++):0;
            if(Character.isDigit((char)var6)) {
               this.width = this.width * 10 + (var6 - 48);
               var6 = var4 < var5?var2.luaByte(var4++):0;
            }
         }

         this.precision = -1;
         if(var6 == 46) {
            var6 = var4 < var5?var2.luaByte(var4++):0;
            if(Character.isDigit((char)var6)) {
               this.precision = var6 - 48;
               var6 = var4 < var5?var2.luaByte(var4++):0;
               if(Character.isDigit((char)var6)) {
                  this.precision = this.precision * 10 + (var6 - 48);
                  var6 = var4 < var5?var2.luaByte(var4++):0;
               } else {
                  this.precision = 0;
               }
            }
         }

         if(Character.isDigit((char)var6)) {
            LuaValue.error("invalid format (width or precision too long)");
         }

         this.zeroPad &= !this.leftAdjust;
         this.conversion = var6;
         this.length = var4 - var3;
      }

      public void format(Buffer var1, byte var2) {
         var1.append(var2);
      }

      public void format(Buffer var1, long var2) {
         String var4;
         if(var2 == 0L && this.precision == 0) {
            var4 = "";
         } else {
            byte var5;
            switch(this.conversion) {
            case 88:
            case 120:
               var5 = 16;
               break;
            case 111:
               var5 = 8;
               break;
            default:
               var5 = 10;
            }

            var4 = Long.toString(var2, var5);
            if(this.conversion == 88) {
               var4 = var4.toUpperCase();
            }
         }

         int var9 = var4.length();
         int var6 = var9;
         if(var2 < 0L) {
            var6 = var9 - 1;
         } else if(this.explicitPlus || this.space) {
            ++var9;
         }

         int var7;
         if(this.precision > var6) {
            var7 = this.precision - var6;
         } else if(this.precision == -1 && this.zeroPad && this.width > var9) {
            var7 = this.width - var9;
         } else {
            var7 = 0;
         }

         var9 += var7;
         int var8 = this.width > var9?this.width - var9:0;
         if(!this.leftAdjust) {
            pad(var1, ' ', var8);
         }

         if(var2 < 0L) {
            if(var7 > 0) {
               var1.append((byte)45);
               var4 = var4.substring(1);
            }
         } else if(this.explicitPlus) {
            var1.append((byte)43);
         } else if(this.space) {
            var1.append((byte)32);
         }

         if(var7 > 0) {
            pad(var1, '0', var7);
         }

         var1.append(var4);
         if(this.leftAdjust) {
            pad(var1, ' ', var8);
         }

      }

      public void format(Buffer var1, double var2) {
         var1.append(String.valueOf(var2));
      }

      public void format(Buffer var1, LuaString var2) {
         int var3 = var2.indexOf((byte)0, 0);
         if(var3 != -1) {
            var2 = var2.substring(0, var3);
         }

         var1.append(var2);
      }

      public static final void pad(Buffer var0, char var1, int var2) {
         byte var3 = (byte)var1;

         while(var2-- > 0) {
            var0.append(var3);
         }

      }

      static int access$000(StringLib.FormatDesc var0) {
         return var0.precision;
      }
   }

   static class MatchState {

      final LuaString s;
      final LuaString p;
      final Varargs args;
      int level;
      int[] cinit;
      int[] clen;


      MatchState(Varargs var1, LuaString var2, LuaString var3) {
         this.s = var2;
         this.p = var3;
         this.args = var1;
         this.level = 0;
         this.cinit = new int[32];
         this.clen = new int[32];
      }

      void reset() {
         this.level = 0;
      }

      private void add_s(Buffer var1, LuaString var2, int var3, int var4) {
         int var5 = var2.length();

         for(int var6 = 0; var6 < var5; ++var6) {
            byte var7 = (byte)var2.luaByte(var6);
            if(var7 != 37) {
               var1.append(var7);
            } else {
               ++var6;
               var7 = (byte)var2.luaByte(var6);
               if(!Character.isDigit((char)var7)) {
                  var1.append(var7);
               } else if(var7 == 48) {
                  var1.append(this.s.substring(var3, var4));
               } else {
                  var1.append(this.push_onecapture(var7 - 49, var3, var4).strvalue());
               }
            }
         }

      }

      public void add_value(Buffer var1, int var2, int var3, LuaValue var4) {
         Object var5;
         switch(var4.type()) {
         case 3:
         case 4:
            this.add_s(var1, var4.strvalue(), var2, var3);
            return;
         case 5:
            var5 = var4.get(this.push_onecapture(0, var2, var3));
            break;
         case 6:
            var5 = var4.invoke(this.push_captures(true, var2, var3)).arg1();
            break;
         default:
            LuaValue.error("bad argument: string/function/table expected");
            return;
         }

         if(!((LuaValue)var5).toboolean()) {
            var5 = this.s.substring(var2, var3);
         } else if(!((LuaValue)var5).isstring()) {
            LuaValue.error("invalid replacement value (a " + ((LuaValue)var5).typename() + ")");
         }

         var1.append(((LuaValue)var5).strvalue());
      }

      Varargs push_captures(boolean var1, int var2, int var3) {
         int var4 = this.level == 0 && var1?1:this.level;
         switch(var4) {
         case 0:
            return LuaValue.NONE;
         case 1:
            return this.push_onecapture(0, var2, var3);
         default:
            LuaValue[] var5 = new LuaValue[var4];

            for(int var6 = 0; var6 < var4; ++var6) {
               var5[var6] = this.push_onecapture(var6, var2, var3);
            }

            return LuaValue.varargsOf(var5);
         }
      }

      private LuaValue push_onecapture(int var1, int var2, int var3) {
         if(var1 >= this.level) {
            return (LuaValue)(var1 == 0?this.s.substring(var2, var3):LuaValue.error("invalid capture index"));
         } else {
            int var4 = this.clen[var1];
            if(var4 == -1) {
               return LuaValue.error("unfinished capture");
            } else if(var4 == -2) {
               return LuaValue.valueOf(this.cinit[var1] + 1);
            } else {
               int var5 = this.cinit[var1];
               return this.s.substring(var5, var5 + var4);
            }
         }
      }

      private int check_capture(int var1) {
         var1 -= 49;
         if(var1 < 0 || var1 >= this.level || this.clen[var1] == -1) {
            LuaValue.error("invalid capture index");
         }

         return var1;
      }

      private int capture_to_close() {
         int var1 = this.level;
         --var1;

         while(var1 >= 0) {
            if(this.clen[var1] == -1) {
               return var1;
            }

            --var1;
         }

         LuaValue.error("invalid pattern capture");
         return 0;
      }

      int classend(int var1) {
         switch(this.p.luaByte(var1++)) {
         case 37:
            if(var1 == this.p.length()) {
               LuaValue.error("malformed pattern (ends with %)");
            }

            return var1 + 1;
         case 91:
            if(this.p.luaByte(var1) == 94) {
               ++var1;
            }

            do {
               if(var1 == this.p.length()) {
                  LuaValue.error("malformed pattern (missing ])");
               }

               if(this.p.luaByte(var1++) == 37 && var1 != this.p.length()) {
                  ++var1;
               }
            } while(this.p.luaByte(var1) != 93);

            return var1 + 1;
         default:
            return var1;
         }
      }

      static boolean match_class(int var0, int var1) {
         char var2 = Character.toLowerCase((char)var1);
         byte var3 = StringLib.CHAR_TABLE[var0];
         boolean var4;
         switch(var2) {
         case 97:
            var4 = (var3 & 1) != 0;
            break;
         case 98:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 109:
         case 110:
         case 111:
         case 113:
         case 114:
         case 116:
         case 118:
         case 121:
         default:
            return var1 == var0;
         case 99:
            var4 = (var3 & 64) != 0;
            break;
         case 100:
            var4 = (var3 & 8) != 0;
            break;
         case 108:
            var4 = (var3 & 2) != 0;
            break;
         case 112:
            var4 = (var3 & 16) != 0;
            break;
         case 115:
            var4 = (var3 & 32) != 0;
            break;
         case 117:
            var4 = (var3 & 4) != 0;
            break;
         case 119:
            var4 = (var3 & 9) != 0;
            break;
         case 120:
            var4 = (var3 & -128) != 0;
            break;
         case 122:
            var4 = var0 == 0;
         }

         return var2 == var1?var4:!var4;
      }

      boolean matchbracketclass(int var1, int var2, int var3) {
         boolean var4 = true;
         if(this.p.luaByte(var2 + 1) == 94) {
            var4 = false;
            ++var2;
         }

         while(true) {
            ++var2;
            if(var2 >= var3) {
               return !var4;
            }

            if(this.p.luaByte(var2) == 37) {
               ++var2;
               if(match_class(var1, this.p.luaByte(var2))) {
                  return var4;
               }
            } else if(this.p.luaByte(var2 + 1) == 45 && var2 + 2 < var3) {
               var2 += 2;
               if(this.p.luaByte(var2 - 2) <= var1 && var1 <= this.p.luaByte(var2)) {
                  return var4;
               }
            } else if(this.p.luaByte(var2) == var1) {
               return var4;
            }
         }
      }

      boolean singlematch(int var1, int var2, int var3) {
         switch(this.p.luaByte(var2)) {
         case 37:
            return match_class(var1, this.p.luaByte(var2 + 1));
         case 46:
            return true;
         case 91:
            return this.matchbracketclass(var1, var2, var3 - 1);
         default:
            return this.p.luaByte(var2) == var1;
         }
      }

      int match(int var1, int var2) {
         while(var2 != this.p.length()) {
            int var3;
            switch(this.p.luaByte(var2)) {
            case 37:
               if(var2 + 1 == this.p.length()) {
                  LuaValue.error("malformed pattern (ends with \'%\')");
               }

               switch(this.p.luaByte(var2 + 1)) {
               case 98:
                  var1 = this.matchbalance(var1, var2 + 2);
                  if(var1 == -1) {
                     return -1;
                  }

                  var2 += 4;
                  continue;
               case 102:
                  var2 += 2;
                  if(this.p.luaByte(var2) != 91) {
                     LuaValue.error("Missing [ after %f in pattern");
                  }

                  var3 = this.classend(var2);
                  int var4 = var1 == 0?-1:this.s.luaByte(var1 - 1);
                  if(!this.matchbracketclass(var4, var2, var3 - 1) && !this.matchbracketclass(this.s.luaByte(var1), var2, var3 - 1)) {
                     var2 = var3;
                     continue;
                  }

                  return -1;
               default:
                  var3 = this.p.luaByte(var2 + 1);
                  if(Character.isDigit((char)var3)) {
                     var1 = this.match_capture(var1, var3);
                     if(var1 == -1) {
                        return -1;
                     }

                     return this.match(var1, var2 + 2);
                  }
               }
            case 36:
               if(var2 + 1 == this.p.length()) {
                  return var1 == this.s.length()?var1:-1;
               }
            case 38:
            case 39:
            default:
               break;
            case 40:
               ++var2;
               if(var2 < this.p.length() && this.p.luaByte(var2) == 41) {
                  return this.start_capture(var1, var2 + 1, -2);
               }

               return this.start_capture(var1, var2, -1);
            case 41:
               return this.end_capture(var1, var2 + 1);
            }

            var3 = this.classend(var2);
            boolean var7 = var1 < this.s.length() && this.singlematch(this.s.luaByte(var1), var2, var3);
            int var5 = var3 < this.p.length()?this.p.luaByte(var3):0;
            switch(var5) {
            case 42:
               return this.max_expand(var1, var2, var3);
            case 43:
               return var7?this.max_expand(var1 + 1, var2, var3):-1;
            case 45:
               return this.min_expand(var1, var2, var3);
            case 63:
               int var6;
               if(var7 && (var6 = this.match(var1 + 1, var3 + 1)) != -1) {
                  return var6;
               }

               var2 = var3 + 1;
               break;
            default:
               if(!var7) {
                  return -1;
               }

               ++var1;
               var2 = var3;
            }
         }

         return var1;
      }

      int max_expand(int var1, int var2, int var3) {
         int var4;
         for(var4 = 0; var1 + var4 < this.s.length() && this.singlematch(this.s.luaByte(var1 + var4), var2, var3); ++var4) {
            ;
         }

         while(var4 >= 0) {
            int var5 = this.match(var1 + var4, var3 + 1);
            if(var5 != -1) {
               return var5;
            }

            --var4;
         }

         return -1;
      }

      int min_expand(int var1, int var2, int var3) {
         while(true) {
            int var4 = this.match(var1, var3 + 1);
            if(var4 != -1) {
               return var4;
            }

            if(var1 >= this.s.length() || !this.singlematch(this.s.luaByte(var1), var2, var3)) {
               return -1;
            }

            ++var1;
         }
      }

      int start_capture(int var1, int var2, int var3) {
         int var5 = this.level;
         if(var5 >= 32) {
            LuaValue.error("too many captures");
         }

         this.cinit[var5] = var1;
         this.clen[var5] = var3;
         this.level = var5 + 1;
         int var4;
         if((var4 = this.match(var1, var2)) == -1) {
            --this.level;
         }

         return var4;
      }

      int end_capture(int var1, int var2) {
         int var3 = this.capture_to_close();
         this.clen[var3] = var1 - this.cinit[var3];
         int var4;
         if((var4 = this.match(var1, var2)) == -1) {
            this.clen[var3] = -1;
         }

         return var4;
      }

      int match_capture(int var1, int var2) {
         var2 = this.check_capture(var2);
         int var3 = this.clen[var2];
         return this.s.length() - var1 >= var3 && LuaString.equals(this.s, this.cinit[var2], this.s, var1, var3)?var1 + var3:-1;
      }

      int matchbalance(int var1, int var2) {
         int var3 = this.p.length();
         if(var2 == var3 || var2 + 1 == var3) {
            LuaValue.error("unbalanced pattern");
         }

         if(var1 >= this.s.length()) {
            return -1;
         } else if(this.s.luaByte(var1) != this.p.luaByte(var2)) {
            return -1;
         } else {
            int var4 = this.p.luaByte(var2);
            int var5 = this.p.luaByte(var2 + 1);
            int var6 = 1;

            while(true) {
               ++var1;
               if(var1 >= this.s.length()) {
                  return -1;
               }

               if(this.s.luaByte(var1) == var5) {
                  --var6;
                  if(var6 == 0) {
                     return var1 + 1;
                  }
               } else if(this.s.luaByte(var1) == var4) {
                  ++var6;
               }
            }
         }
      }
   }
}
