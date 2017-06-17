package org.luaj.vm2.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class ZeroArgFunction extends LibFunction {

   public abstract LuaValue call();

   public ZeroArgFunction() {}

   public ZeroArgFunction(LuaValue var1) {
      this.env = var1;
   }

   public LuaValue call(LuaValue var1) {
      return this.call();
   }

   public LuaValue call(LuaValue var1, LuaValue var2) {
      return this.call();
   }

   public LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3) {
      return this.call();
   }

   public Varargs invoke(Varargs var1) {
      return this.call();
   }
}
