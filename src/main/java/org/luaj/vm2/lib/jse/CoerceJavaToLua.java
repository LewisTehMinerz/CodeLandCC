package org.luaj.vm2.lib.jse;

import java.util.HashMap;
import java.util.Map;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JavaArray;
import org.luaj.vm2.lib.jse.JavaClass;
import org.luaj.vm2.lib.jse.JavaInstance;

public class CoerceJavaToLua {

   static final Map COERCIONS = new HashMap();
   static final CoerceJavaToLua.Coercion instanceCoercion;
   static final CoerceJavaToLua.Coercion arrayCoercion;
   static Class class$java$lang$Boolean;
   static Class class$java$lang$Byte;
   static Class class$java$lang$Character;
   static Class class$java$lang$Short;
   static Class class$java$lang$Integer;
   static Class class$java$lang$Long;
   static Class class$java$lang$Float;
   static Class class$java$lang$Double;
   static Class class$java$lang$String;


   public static LuaValue coerce(Object var0) {
      if(var0 == null) {
         return LuaValue.NIL;
      } else {
         Class var1 = var0.getClass();
         Object var2 = (CoerceJavaToLua.Coercion)COERCIONS.get(var1);
         if(var2 == null) {
            var2 = var0 instanceof Class?JavaClass.forClass((Class)var0):(var1.isArray()?arrayCoercion:instanceCoercion);
            COERCIONS.put(var1, var2);
         }

         return ((CoerceJavaToLua.Coercion)var2).coerce(var0);
      }
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      CoerceJavaToLua.Coercion var0 = new CoerceJavaToLua.Coercion() {
         public LuaValue coerce(Object var1) {
            Boolean var2 = (Boolean)var1;
            return var2.booleanValue()?LuaValue.TRUE:LuaValue.FALSE;
         }
      };
      CoerceJavaToLua.Coercion var1 = new CoerceJavaToLua.Coercion() {
         public LuaValue coerce(Object var1) {
            Number var2 = (Number)var1;
            return LuaInteger.valueOf(var2.intValue());
         }
      };
      CoerceJavaToLua.Coercion var2 = new CoerceJavaToLua.Coercion() {
         public LuaValue coerce(Object var1) {
            Character var2 = (Character)var1;
            return LuaInteger.valueOf(var2.charValue());
         }
      };
      CoerceJavaToLua.Coercion var3 = new CoerceJavaToLua.Coercion() {
         public LuaValue coerce(Object var1) {
            Number var2 = (Number)var1;
            return LuaDouble.valueOf(var2.doubleValue());
         }
      };
      CoerceJavaToLua.Coercion var4 = new CoerceJavaToLua.Coercion() {
         public LuaValue coerce(Object var1) {
            return LuaString.valueOf(var1.toString());
         }
      };
      COERCIONS.put(class$java$lang$Boolean == null?(class$java$lang$Boolean = class$("java.lang.Boolean")):class$java$lang$Boolean, var0);
      COERCIONS.put(class$java$lang$Byte == null?(class$java$lang$Byte = class$("java.lang.Byte")):class$java$lang$Byte, var1);
      COERCIONS.put(class$java$lang$Character == null?(class$java$lang$Character = class$("java.lang.Character")):class$java$lang$Character, var2);
      COERCIONS.put(class$java$lang$Short == null?(class$java$lang$Short = class$("java.lang.Short")):class$java$lang$Short, var1);
      COERCIONS.put(class$java$lang$Integer == null?(class$java$lang$Integer = class$("java.lang.Integer")):class$java$lang$Integer, var1);
      COERCIONS.put(class$java$lang$Long == null?(class$java$lang$Long = class$("java.lang.Long")):class$java$lang$Long, var3);
      COERCIONS.put(class$java$lang$Float == null?(class$java$lang$Float = class$("java.lang.Float")):class$java$lang$Float, var3);
      COERCIONS.put(class$java$lang$Double == null?(class$java$lang$Double = class$("java.lang.Double")):class$java$lang$Double, var3);
      COERCIONS.put(class$java$lang$String == null?(class$java$lang$String = class$("java.lang.String")):class$java$lang$String, var4);
      instanceCoercion = new CoerceJavaToLua.Coercion() {
         public LuaValue coerce(Object var1) {
            return new JavaInstance(var1);
         }
      };
      arrayCoercion = new CoerceJavaToLua.Coercion() {
         public LuaValue coerce(Object var1) {
            return new JavaArray(var1);
         }
      };
   }

   interface Coercion {

      LuaValue coerce(Object var1);
   }
}
