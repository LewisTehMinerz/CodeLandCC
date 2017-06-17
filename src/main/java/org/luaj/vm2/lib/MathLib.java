package org.luaj.vm2.lib;

import java.util.Random;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

public class MathLib extends OneArgFunction {

   public static MathLib MATHLIB = null;
   private Random random;
   static Class class$org$luaj$vm2$lib$MathLib$MathLib1;
   static Class class$org$luaj$vm2$lib$MathLib$MathLib2;
   static Class class$org$luaj$vm2$lib$MathLib$MathLibV;


   public MathLib() {
      MATHLIB = this;
   }

   public LuaValue call(LuaValue var1) {
      LuaTable var2 = new LuaTable(0, 30);
      var2.set("pi", 3.141592653589793D);
      var2.set("huge", LuaDouble.POSINF);
      this.bind(var2, class$org$luaj$vm2$lib$MathLib$MathLib1 == null?(class$org$luaj$vm2$lib$MathLib$MathLib1 = class$("org.luaj.vm2.lib.MathLib$MathLib1")):class$org$luaj$vm2$lib$MathLib$MathLib1, new String[]{"abs", "ceil", "cos", "deg", "exp", "floor", "rad", "sin", "sqrt", "tan"});
      this.bind(var2, class$org$luaj$vm2$lib$MathLib$MathLib2 == null?(class$org$luaj$vm2$lib$MathLib$MathLib2 = class$("org.luaj.vm2.lib.MathLib$MathLib2")):class$org$luaj$vm2$lib$MathLib$MathLib2, new String[]{"fmod", "ldexp", "pow"});
      this.bind(var2, class$org$luaj$vm2$lib$MathLib$MathLibV == null?(class$org$luaj$vm2$lib$MathLib$MathLibV = class$("org.luaj.vm2.lib.MathLib$MathLibV")):class$org$luaj$vm2$lib$MathLib$MathLibV, new String[]{"frexp", "max", "min", "modf", "randomseed", "random"});
      ((MathLib.MathLibV)var2.get("randomseed")).mathlib = this;
      ((MathLib.MathLibV)var2.get("random")).mathlib = this;
      this.env.set("math", (LuaValue)var2);
      PackageLib.instance.LOADED.set("math", var2);
      return var2;
   }

   public static LuaValue dpow(double var0, double var2) {
      return LuaDouble.valueOf(MATHLIB != null?MATHLIB.dpow_lib(var0, var2):dpow_default(var0, var2));
   }

   public static double dpow_d(double var0, double var2) {
      return MATHLIB != null?MATHLIB.dpow_lib(var0, var2):dpow_default(var0, var2);
   }

   public double dpow_lib(double var1, double var3) {
      return dpow_default(var1, var3);
   }

   protected static double dpow_default(double var0, double var2) {
      if(var2 < 0.0D) {
         return 1.0D / dpow_default(var0, -var2);
      } else {
         double var4 = 1.0D;
         int var6 = (int)var2;

         for(double var7 = var0; var6 > 0; var7 *= var7) {
            if((var6 & 1) != 0) {
               var4 *= var7;
            }

            var6 >>= 1;
         }

         if((var2 -= (double)var6) > 0.0D) {
            for(int var9 = (int)(65536.0D * var2); (var9 & '\uffff') != 0; var9 <<= 1) {
               var0 = Math.sqrt(var0);
               if((var9 & '\u8000') != 0) {
                  var4 *= var0;
               }
            }
         }

         return var4;
      }
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static Random access$002(MathLib var0, Random var1) {
      return var0.random = var1;
   }

   static Random access$000(MathLib var0) {
      return var0.random;
   }


   static final class MathLibV extends VarArgFunction {

      protected MathLib mathlib;


      public Varargs invoke(Varargs var1) {
         int var5;
         double var6;
         double var11;
         int var12;
         switch(this.opcode) {
         case 0:
            var11 = var1.checkdouble(1);
            if(var11 == 0.0D) {
               return varargsOf(ZERO, ZERO);
            }

            long var13 = Double.doubleToLongBits(var11);
            var6 = (double)((var13 & 4503599627370495L) + 4503599627370496L) * (var13 >= 0L?1.1102230246251565E-16D:-1.1102230246251565E-16D);
            double var8 = (double)(((int)(var13 >> 52) & 2047) - 1022);
            return varargsOf(valueOf(var6), valueOf(var8));
         case 1:
            var11 = var1.checkdouble(1);
            var12 = 2;

            for(var5 = var1.narg(); var12 <= var5; ++var12) {
               var11 = Math.max(var11, var1.checkdouble(var12));
            }

            return valueOf(var11);
         case 2:
            var11 = var1.checkdouble(1);
            var12 = 2;

            for(var5 = var1.narg(); var12 <= var5; ++var12) {
               var11 = Math.min(var11, var1.checkdouble(var12));
            }

            return valueOf(var11);
         case 3:
            var11 = var1.checkdouble(1);
            double var4 = var11 > 0.0D?Math.floor(var11):Math.ceil(var11);
            var6 = var11 - var4;
            return varargsOf(valueOf(var4), valueOf(var6));
         case 4:
            long var10 = var1.checklong(1);
            this.mathlib.random = new Random(var10);
            return NONE;
         case 5:
            if(this.mathlib.random == null) {
               this.mathlib.random = new Random();
            }

            int var2;
            switch(var1.narg()) {
            case 0:
               return valueOf(this.mathlib.random.nextDouble());
            case 1:
               var2 = var1.checkint(1);
               if(var2 < 1) {
                  argerror(1, "interval is empty");
               }

               return valueOf(1 + this.mathlib.random.nextInt(var2));
            default:
               var2 = var1.checkint(1);
               int var3 = var1.checkint(2);
               if(var3 < var2) {
                  argerror(2, "interval is empty");
               }

               return valueOf(var2 + this.mathlib.random.nextInt(var3 + 1 - var2));
            }
         default:
            return NONE;
         }
      }
   }

   static final class MathLib2 extends TwoArgFunction {

      protected MathLib mathlib;


      public LuaValue call(LuaValue var1, LuaValue var2) {
         double var3;
         double var5;
         switch(this.opcode) {
         case 0:
            var3 = var1.checkdouble();
            var5 = var2.checkdouble();
            double var11 = var3 / var5;
            double var9 = var3 - var5 * (var11 >= 0.0D?Math.floor(var11):Math.ceil(var11));
            return valueOf(var9);
         case 1:
            var3 = var1.checkdouble();
            var5 = var2.checkdouble() + 1023.5D;
            long var7 = (long)(0 != (1 & (int)var5)?Math.floor(var5):Math.ceil(var5 - 1.0D));
            return valueOf(var3 * Double.longBitsToDouble(var7 << 52));
         case 2:
            return MathLib.dpow(var1.checkdouble(), var2.checkdouble());
         default:
            return NIL;
         }
      }
   }

   static final class MathLib1 extends OneArgFunction {

      public LuaValue call(LuaValue var1) {
         switch(this.opcode) {
         case 0:
            return valueOf(Math.abs(var1.checkdouble()));
         case 1:
            return valueOf(Math.ceil(var1.checkdouble()));
         case 2:
            return valueOf(Math.cos(var1.checkdouble()));
         case 3:
            return valueOf(Math.toDegrees(var1.checkdouble()));
         case 4:
            return MathLib.dpow(2.718281828459045D, var1.checkdouble());
         case 5:
            return valueOf(Math.floor(var1.checkdouble()));
         case 6:
            return valueOf(Math.toRadians(var1.checkdouble()));
         case 7:
            return valueOf(Math.sin(var1.checkdouble()));
         case 8:
            return valueOf(Math.sqrt(var1.checkdouble()));
         case 9:
            return valueOf(Math.tan(var1.checkdouble()));
         default:
            return NIL;
         }
      }
   }
}
