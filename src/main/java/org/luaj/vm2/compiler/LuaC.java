package org.luaj.vm2.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LocVars;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.FuncState;
import org.luaj.vm2.compiler.InstructionPtr;
import org.luaj.vm2.compiler.LexState;

public class LuaC extends Lua implements LoadState.LuaCompiler {

   public static final LuaC instance = new LuaC();
   public static final int MAXSTACK = 250;
   static final int LUAI_MAXUPVALUES = 60;
   static final int LUAI_MAXVARS = 200;
   static final int NO_REG = 255;
   static final int iABC = 0;
   static final int iABx = 1;
   static final int iAsBx = 2;
   static final int OpArgN = 0;
   static final int OpArgU = 1;
   static final int OpArgR = 2;
   static final int OpArgK = 3;
   public int nCcalls;
   Hashtable strings;


   public static void install() {
      LoadState.compiler = instance;
   }

   protected static void _assert(boolean var0) {
      if(!var0) {
         throw new LuaError("compiler assert failed");
      }
   }

   static void SET_OPCODE(InstructionPtr var0, int var1) {
      var0.set(var0.get() & -64 | var1 << 0 & 63);
   }

   static void SETARG_A(InstructionPtr var0, int var1) {
      var0.set(var0.get() & -16321 | var1 << 6 & 16320);
   }

   static void SETARG_B(InstructionPtr var0, int var1) {
      var0.set(var0.get() & 8388607 | var1 << 23 & -8388608);
   }

   static void SETARG_C(InstructionPtr var0, int var1) {
      var0.set(var0.get() & -8372225 | var1 << 14 & 8372224);
   }

   static void SETARG_Bx(InstructionPtr var0, int var1) {
      var0.set(var0.get() & 16383 | var1 << 14 & -16384);
   }

   static void SETARG_sBx(InstructionPtr var0, int var1) {
      SETARG_Bx(var0, var1 + 131071);
   }

   static int CREATE_ABC(int var0, int var1, int var2, int var3) {
      return var0 << 0 & 63 | var1 << 6 & 16320 | var2 << 23 & -8388608 | var3 << 14 & 8372224;
   }

   static int CREATE_ABx(int var0, int var1, int var2) {
      return var0 << 0 & 63 | var1 << 6 & 16320 | var2 << 14 & -16384;
   }

   static LuaValue[] realloc(LuaValue[] var0, int var1) {
      LuaValue[] var2 = new LuaValue[var1];
      if(var0 != null) {
         System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      }

      return var2;
   }

   static Prototype[] realloc(Prototype[] var0, int var1) {
      Prototype[] var2 = new Prototype[var1];
      if(var0 != null) {
         System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      }

      return var2;
   }

   static LuaString[] realloc(LuaString[] var0, int var1) {
      LuaString[] var2 = new LuaString[var1];
      if(var0 != null) {
         System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      }

      return var2;
   }

   static LocVars[] realloc(LocVars[] var0, int var1) {
      LocVars[] var2 = new LocVars[var1];
      if(var0 != null) {
         System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      }

      return var2;
   }

   static int[] realloc(int[] var0, int var1) {
      int[] var2 = new int[var1];
      if(var0 != null) {
         System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      }

      return var2;
   }

   static byte[] realloc(byte[] var0, int var1) {
      byte[] var2 = new byte[var1];
      if(var0 != null) {
         System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      }

      return var2;
   }

   protected LuaC() {}

   private LuaC(Hashtable var1) {
      this.strings = var1;
   }

   public LuaFunction load(InputStream var1, String var2, LuaValue var3) throws IOException {
      Prototype var4 = compile(var1, var2);
      return new LuaClosure(var4, var3);
   }

   public static Prototype compile(InputStream var0, String var1) throws IOException {
      int var2 = var0.read();
      return var2 == 27?LoadState.loadBinaryChunk(var2, var0, var1):(new LuaC(new Hashtable())).luaY_parser(var2, var0, var1);
   }

   private Prototype luaY_parser(int var1, InputStream var2, String var3) {
      LexState var4 = new LexState(this, var2);
      FuncState var5 = new FuncState();
      var4.setinput(this, var1, var2, LuaValue.valueOf(var3));
      var4.open_func(var5);
      var5.f.is_vararg = 2;
      var5.f.source = LuaValue.valueOf(var3);
      var4.next();
      var4.chunk();
      var4.check(287);
      var4.close_func();
      _assert(var5.prev == null);
      _assert(var5.f.nups == 0);
      _assert(var4.fs == null);
      return var5.f;
   }

   public LuaString newTString(byte[] var1, int var2, int var3) {
      LuaString var4 = LuaString.valueOf(var1, var2, var3);
      LuaString var5 = (LuaString)this.strings.get(var4);
      if(var5 == null) {
         byte[] var6 = new byte[var3];
         System.arraycopy(var1, var2, var6, 0, var3);
         var5 = LuaString.valueOf(var6);
         this.strings.put(var5, var5);
      }

      return var5;
   }

   public String pushfstring(String var1) {
      return var1;
   }

   public LuaFunction load(Prototype var1, String var2, LuaValue var3) {
      return new LuaClosure(var1, var3);
   }

}
