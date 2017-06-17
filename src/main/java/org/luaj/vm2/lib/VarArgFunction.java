package org.luaj.vm2.lib;

import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class VarArgFunction extends LibFunction {

   public VarArgFunction() {}

   public VarArgFunction(LuaValue var1) {
      this.env = var1;
   }

   public LuaValue call() {
      return this.invoke(NONE).arg1();
   }

   public LuaValue call(LuaValue var1) {
      return this.invoke(var1).arg1();
   }

   public LuaValue call(LuaValue var1, LuaValue var2) {
      return this.invoke(varargsOf(var1, var2)).arg1();
   }

   public LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3) {
      return this.invoke(varargsOf(var1, var2, var3)).arg1();
   }

   public Varargs invoke(Varargs var1) {
      LuaThread.CallStack var2 = LuaThread.onCall(this);

      Varargs var3;
      try {
         var3 = this.onInvoke(var1).eval();
      } finally {
         var2.onReturn();
      }

      return var3;
   }

   public Varargs onInvoke(Varargs var1) {
      return this.invoke(var1);
   }
}
