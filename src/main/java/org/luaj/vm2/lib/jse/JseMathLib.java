package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.MathLib;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

public class JseMathLib extends MathLib {

   static Class class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib1;
   static Class class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib2;


   public LuaValue call(LuaValue var1) {
      LuaValue var2 = super.call(var1);
      this.bind(var2, class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib1 == null?(class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib1 = class$("org.luaj.vm2.lib.jse.JseMathLib$JseMathLib1")):class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib1, new String[]{"acos", "asin", "atan", "cosh", "exp", "log", "log10", "sinh", "tanh"});
      this.bind(var2, class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib2 == null?(class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib2 = class$("org.luaj.vm2.lib.jse.JseMathLib$JseMathLib2")):class$org$luaj$vm2$lib$jse$JseMathLib$JseMathLib2, new String[]{"atan2", "pow"});
      return var2;
   }

   public double dpow_lib(double var1, double var3) {
      return Math.pow(var1, var3);
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public static final class JseMathLib1 extends OneArgFunction {

      public LuaValue call(LuaValue var1) {
         switch(this.opcode) {
         case 0:
            return valueOf(Math.acos(var1.checkdouble()));
         case 1:
            return valueOf(Math.asin(var1.checkdouble()));
         case 2:
            return valueOf(Math.atan(var1.checkdouble()));
         case 3:
            return valueOf(Math.cosh(var1.checkdouble()));
         case 4:
            return valueOf(Math.exp(var1.checkdouble()));
         case 5:
            return valueOf(Math.log(var1.checkdouble()));
         case 6:
            return valueOf(Math.log10(var1.checkdouble()));
         case 7:
            return valueOf(Math.sinh(var1.checkdouble()));
         case 8:
            return valueOf(Math.tanh(var1.checkdouble()));
         default:
            return NIL;
         }
      }
   }

   public static final class JseMathLib2 extends TwoArgFunction {

      public LuaValue call(LuaValue var1, LuaValue var2) {
         switch(this.opcode) {
         case 0:
            return valueOf(Math.atan2(var1.checkdouble(), var2.checkdouble()));
         case 1:
            return valueOf(Math.pow(var1.checkdouble(), var2.checkdouble()));
         default:
            return NIL;
         }
      }
   }
}
