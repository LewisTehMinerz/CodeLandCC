package org.luaj.vm2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.luaj.vm2.LocVars;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;

public class LoadState {

   public static final int NUMBER_FORMAT_FLOATS_OR_DOUBLES = 0;
   public static final int NUMBER_FORMAT_INTS_ONLY = 1;
   public static final int NUMBER_FORMAT_NUM_PATCH_INT32 = 4;
   public static final int LUA_TINT = -2;
   public static final int LUA_TNONE = -1;
   public static final int LUA_TNIL = 0;
   public static final int LUA_TBOOLEAN = 1;
   public static final int LUA_TLIGHTUSERDATA = 2;
   public static final int LUA_TNUMBER = 3;
   public static final int LUA_TSTRING = 4;
   public static final int LUA_TTABLE = 5;
   public static final int LUA_TFUNCTION = 6;
   public static final int LUA_TUSERDATA = 7;
   public static final int LUA_TTHREAD = 8;
   public static final int LUA_TVALUE = 9;
   public static LoadState.LuaCompiler compiler = null;
   private static final byte[] LUA_SIGNATURE = new byte[]{(byte)27, (byte)76, (byte)117, (byte)97};
   public static final String SOURCE_BINARY_STRING = "binary string";
   public static final int LUAC_VERSION = 81;
   public static final int LUAC_FORMAT = 0;
   public static final int LUAC_HEADERSIZE = 12;
   private int luacVersion;
   private int luacFormat;
   private boolean luacLittleEndian;
   private int luacSizeofInt;
   private int luacSizeofSizeT;
   private int luacSizeofInstruction;
   private int luacSizeofLuaNumber;
   private int luacNumberFormat;
   public final DataInputStream is;
   String name;
   private static final LuaValue[] NOVALUES = new LuaValue[0];
   private static final Prototype[] NOPROTOS = new Prototype[0];
   private static final LocVars[] NOLOCVARS = new LocVars[0];
   private static final LuaString[] NOSTRVALUES = new LuaString[0];
   private static final int[] NOINTS = new int[0];
   private byte[] buf = new byte[512];


   int loadInt() throws IOException {
      this.is.readFully(this.buf, 0, 4);
      return this.luacLittleEndian?this.buf[3] << 24 | (255 & this.buf[2]) << 16 | (255 & this.buf[1]) << 8 | 255 & this.buf[0]:this.buf[0] << 24 | (255 & this.buf[1]) << 16 | (255 & this.buf[2]) << 8 | 255 & this.buf[3];
   }

   int[] loadIntArray() throws IOException {
      int var1 = this.loadInt();
      if(var1 == 0) {
         return NOINTS;
      } else {
         int var2 = var1 << 2;
         if(this.buf.length < var2) {
            this.buf = new byte[var2];
         }

         this.is.readFully(this.buf, 0, var2);
         int[] var3 = new int[var1];
         int var4 = 0;

         for(int var5 = 0; var4 < var1; var5 += 4) {
            var3[var4] = this.luacLittleEndian?this.buf[var5 + 3] << 24 | (255 & this.buf[var5 + 2]) << 16 | (255 & this.buf[var5 + 1]) << 8 | 255 & this.buf[var5 + 0]:this.buf[var5 + 0] << 24 | (255 & this.buf[var5 + 1]) << 16 | (255 & this.buf[var5 + 2]) << 8 | 255 & this.buf[var5 + 3];
            ++var4;
         }

         return var3;
      }
   }

   long loadInt64() throws IOException {
      int var1;
      int var2;
      if(this.luacLittleEndian) {
         var1 = this.loadInt();
         var2 = this.loadInt();
      } else {
         var2 = this.loadInt();
         var1 = this.loadInt();
      }

      return (long)var2 << 32 | (long)var1 & 4294967295L;
   }

   LuaString loadString() throws IOException {
      int var1 = this.luacSizeofSizeT == 8?(int)this.loadInt64():this.loadInt();
      if(var1 == 0) {
         return null;
      } else {
         byte[] var2 = new byte[var1];
         this.is.readFully(var2, 0, var1);
         return LuaString.valueOf(var2, 0, var2.length - 1);
      }
   }

   public static LuaValue longBitsToLuaNumber(long var0) {
      if((var0 & Long.MAX_VALUE) == 0L) {
         return LuaValue.ZERO;
      } else {
         int var2 = (int)(var0 >> 52 & 2047L) - 1023;
         if(var2 >= 0 && var2 < 31) {
            long var3 = var0 & 4503599627370495L;
            int var5 = 52 - var2;
            long var6 = (1L << var5) - 1L;
            if((var3 & var6) == 0L) {
               int var8 = (int)(var3 >> var5) | 1 << var2;
               return LuaInteger.valueOf(var0 >> 63 != 0L?-var8:var8);
            }
         }

         return LuaValue.valueOf(Double.longBitsToDouble(var0));
      }
   }

   LuaValue loadNumber() throws IOException {
      return (LuaValue)(this.luacNumberFormat == 1?LuaInteger.valueOf(this.loadInt()):longBitsToLuaNumber(this.loadInt64()));
   }

   void loadConstants(Prototype var1) throws IOException {
      int var2 = this.loadInt();
      LuaValue[] var3 = var2 > 0?new LuaValue[var2]:NOVALUES;

      for(int var4 = 0; var4 < var2; ++var4) {
         switch(this.is.readByte()) {
         case -2:
            var3[var4] = LuaInteger.valueOf(this.loadInt());
            break;
         case -1:
         case 2:
         default:
            throw new IllegalStateException("bad constant");
         case 0:
            var3[var4] = LuaValue.NIL;
            break;
         case 1:
            var3[var4] = 0 != this.is.readUnsignedByte()?LuaValue.TRUE:LuaValue.FALSE;
            break;
         case 3:
            var3[var4] = this.loadNumber();
            break;
         case 4:
            var3[var4] = this.loadString();
         }
      }

      var1.k = var3;
      var2 = this.loadInt();
      Prototype[] var6 = var2 > 0?new Prototype[var2]:NOPROTOS;

      for(int var5 = 0; var5 < var2; ++var5) {
         var6[var5] = this.loadFunction(var1.source);
      }

      var1.p = var6;
   }

   void loadDebug(Prototype var1) throws IOException {
      var1.lineinfo = this.loadIntArray();
      int var2 = this.loadInt();
      var1.locvars = var2 > 0?new LocVars[var2]:NOLOCVARS;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         LuaString var4 = this.loadString();
         int var5 = this.loadInt();
         int var6 = this.loadInt();
         var1.locvars[var3] = new LocVars(var4, var5, var6);
      }

      var2 = this.loadInt();
      var1.upvalues = var2 > 0?new LuaString[var2]:NOSTRVALUES;

      for(var3 = 0; var3 < var2; ++var3) {
         var1.upvalues[var3] = this.loadString();
      }

   }

   public Prototype loadFunction(LuaString var1) throws IOException {
      Prototype var2 = new Prototype();
      var2.source = this.loadString();
      if(var2.source == null) {
         var2.source = var1;
      }

      var2.linedefined = this.loadInt();
      var2.lastlinedefined = this.loadInt();
      var2.nups = this.is.readUnsignedByte();
      var2.numparams = this.is.readUnsignedByte();
      var2.is_vararg = this.is.readUnsignedByte();
      var2.maxstacksize = this.is.readUnsignedByte();
      var2.code = this.loadIntArray();
      this.loadConstants(var2);
      this.loadDebug(var2);
      return var2;
   }

   public void loadHeader() throws IOException {
      this.luacVersion = this.is.readByte();
      this.luacFormat = this.is.readByte();
      this.luacLittleEndian = 0 != this.is.readByte();
      this.luacSizeofInt = this.is.readByte();
      this.luacSizeofSizeT = this.is.readByte();
      this.luacSizeofInstruction = this.is.readByte();
      this.luacSizeofLuaNumber = this.is.readByte();
      this.luacNumberFormat = this.is.readByte();
   }

   public static LuaFunction load(InputStream var0, String var1, LuaValue var2) throws IOException {
      if(compiler != null) {
         return compiler.load(var0, var1, var2);
      } else {
         int var3 = var0.read();
         if(var3 != LUA_SIGNATURE[0]) {
            throw new LuaError("no compiler");
         } else {
            Prototype var4 = loadBinaryChunk(var3, var0, var1);
            return new LuaClosure(var4, var2);
         }
      }
   }

   public static Prototype loadBinaryChunk(int var0, InputStream var1, String var2) throws IOException {
      if(var0 == LUA_SIGNATURE[0] && var1.read() == LUA_SIGNATURE[1] && var1.read() == LUA_SIGNATURE[2] && var1.read() == LUA_SIGNATURE[3]) {
         String var3 = getSourceName(var2);
         LoadState var4 = new LoadState(var1, var3);
         var4.loadHeader();
         switch(var4.luacNumberFormat) {
         case 0:
         case 1:
         case 4:
            return var4.loadFunction(LuaString.valueOf(var3));
         case 2:
         case 3:
         default:
            throw new LuaError("unsupported int size");
         }
      } else {
         throw new IllegalArgumentException("bad signature");
      }
   }

   public static String getSourceName(String var0) {
      String var1 = var0;
      if(!var0.startsWith("@") && !var0.startsWith("=")) {
         if(var0.startsWith("")) {
            var1 = "binary string";
         }
      } else {
         var1 = var0.substring(1);
      }

      return var1;
   }

   private LoadState(InputStream var1, String var2) {
      this.name = var2;
      this.is = new DataInputStream(var1);
   }


   public interface LuaCompiler {

      LuaFunction load(InputStream var1, String var2, LuaValue var3) throws IOException;
   }
}
