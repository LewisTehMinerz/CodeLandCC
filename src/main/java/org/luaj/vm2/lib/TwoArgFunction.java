package org.luaj.vm2.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class TwoArgFunction extends LibFunction {

   public abstract LuaValue call(LuaValue var1, LuaValue var2);

   public TwoArgFunction() {}

   public TwoArgFunction(LuaValue var1) {
      this.env = var1;
   }

   public final LuaValue call() {
      return this.call(NIL, NIL);
   }

   public final LuaValue call(LuaValue var1) {
      return this.call(var1, NIL);
   }

   public LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3) {
      return this.call(var1, var2);
   }

   public Varargs invoke(Varargs var1) {
      return this.call(var1.arg1(), var1.arg(2));
   }
}
