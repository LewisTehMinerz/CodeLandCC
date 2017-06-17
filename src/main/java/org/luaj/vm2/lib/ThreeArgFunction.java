package org.luaj.vm2.lib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class ThreeArgFunction extends LibFunction {

   public abstract LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3);

   public ThreeArgFunction() {}

   public ThreeArgFunction(LuaValue var1) {
      this.env = var1;
   }

   public final LuaValue call() {
      return this.call(NIL, NIL, NIL);
   }

   public final LuaValue call(LuaValue var1) {
      return this.call(var1, NIL, NIL);
   }

   public LuaValue call(LuaValue var1, LuaValue var2) {
      return this.call(var1, var2, NIL);
   }

   public Varargs invoke(Varargs var1) {
      return this.call(var1.arg1(), var1.arg(2), var1.arg(3));
   }
}
