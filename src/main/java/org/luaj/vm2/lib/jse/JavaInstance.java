package org.luaj.vm2.lib.jse;

import java.lang.reflect.Field;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JavaClass;

class JavaInstance extends LuaUserdata {

   JavaClass jclass;


   JavaInstance(Object var1) {
      super(var1);
   }

   public LuaValue get(LuaValue var1) {
      if(this.jclass == null) {
         this.jclass = JavaClass.forClass(this.m_instance.getClass());
      }

      Field var2 = this.jclass.getField(var1);
      if(var2 != null) {
         try {
            return CoerceJavaToLua.coerce(var2.get(this.m_instance));
         } catch (Exception var4) {
            throw new LuaError(var4);
         }
      } else {
         LuaValue var3 = this.jclass.getMethod(var1);
         return var3 != null?var3:super.get(var1);
      }
   }

   public void set(LuaValue var1, LuaValue var2) {
      if(this.jclass == null) {
         this.jclass = JavaClass.forClass(this.m_instance.getClass());
      }

      Field var3 = this.jclass.getField(var1);
      if(var3 != null) {
         try {
            var3.set(this.m_instance, CoerceLuaToJava.coerce(var2, var3.getType()));
         } catch (Exception var5) {
            throw new LuaError(var5);
         }
      } else {
         super.set(var1, var2);
      }
   }
}
