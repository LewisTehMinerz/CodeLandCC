package org.luaj.vm2.ast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import org.luaj.vm2.LuaString;

public class Str {

   public static LuaString quoteString(String var0) {
      String var1 = var0.substring(1, var0.length() - 1);
      byte[] var2 = unquote(var1);
      return LuaString.valueOf(var2);
   }

   public static LuaString charString(String var0) {
      String var1 = var0.substring(1, var0.length() - 1);
      byte[] var2 = unquote(var1);
      return LuaString.valueOf(var2);
   }

   public static LuaString longString(String var0) {
      int var1 = var0.indexOf(91, var0.indexOf(91) + 1) + 1;
      String var2 = var0.substring(var1, var0.length() - var1);
      byte[] var3 = iso88591bytes(var2);
      return LuaString.valueOf(var3);
   }

   public static byte[] iso88591bytes(String var0) {
      try {
         return var0.getBytes("ISO8859-1");
      } catch (UnsupportedEncodingException var2) {
         throw new IllegalStateException("ISO8859-1 not supported");
      }
   }

   public static byte[] unquote(String var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      char[] var2 = var0.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if(var2[var4] == 92 && var4 < var3) {
            ++var4;
            switch(var2[var4]) {
            case 34:
               var1.write(34);
               break;
            case 35:
            case 36:
            case 37:
            case 38:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
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
            case 91:
            case 93:
            case 94:
            case 95:
            case 96:
            case 99:
            case 100:
            case 101:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 111:
            case 112:
            case 113:
            case 115:
            case 117:
            default:
               var1.write((byte)var2[var4]);
               break;
            case 39:
               var1.write(39);
               break;
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
               int var5 = var2[var4++] - 48;

               for(int var6 = 0; var4 < var3 && var6 < 2 && var2[var4] >= 48 && var2[var4] <= 57; ++var6) {
                  var5 = var5 * 10 + (var2[var4] - 48);
                  ++var4;
               }

               var1.write((byte)var5);
               --var4;
               break;
            case 92:
               var1.write(92);
               break;
            case 97:
               var1.write(7);
               break;
            case 98:
               var1.write(8);
               break;
            case 102:
               var1.write(12);
               break;
            case 110:
               var1.write(10);
               break;
            case 114:
               var1.write(13);
               break;
            case 116:
               var1.write(9);
               break;
            case 118:
               var1.write(11);
            }
         } else {
            var1.write((byte)var2[var4]);
         }
      }

      return var1.toByteArray();
   }
}
