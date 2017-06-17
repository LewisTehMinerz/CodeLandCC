package org.luaj.vm2.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class OneArgFunction extends LibFunction {

   public abstract LuaValue call(LuaValue var1);

   public OneArgFunction() {}

   public OneArgFunction(LuaValue var1) {
      this.env = var1;
   }

   public final LuaValue call() {
      return this.call(NIL);
   }

   public final LuaValue call(LuaValue var1, LuaValue var2) {
      return this.call(var1);
   }

   public LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3) {
      return this.call(var1);
   }

   public Varargs invoke(Varargs var1) {
      return this.call(var1.arg1());
   }
}
