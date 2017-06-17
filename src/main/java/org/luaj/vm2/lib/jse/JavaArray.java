package org.luaj.vm2.lib.jse;

import java.lang.reflect.Array;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

class JavaArray extends LuaUserdata {

   static final LuaValue LENGTH = valueOf("length");


   JavaArray(Object var1) {
      super(var1);
   }

   public LuaValue get(LuaValue var1) {
      if(var1.equals(LENGTH)) {
         return valueOf(Array.getLength(this.m_instance));
      } else if(!var1.isint()) {
         return super.get(var1);
      } else {
         int var2 = var1.toint() - 1;
         return var2 >= 0 && var2 < Array.getLength(this.m_instance)?CoerceJavaToLua.coerce(Array.get(this.m_instance, var1.toint() - 1)):NIL;
      }
   }

   public void set(LuaValue var1, LuaValue var2) {
      if(var1.isint()) {
         int var3 = var1.toint() - 1;
         if(var3 >= 0 && var3 < Array.getLength(this.m_instance)) {
            Array.set(this.m_instance, var3, CoerceLuaToJava.coerce(var2, this.m_instance.getClass().getComponentType()));
         } else if(this.m_metatable == null || !settable(this, var1, var2)) {
            error("array index out of bounds");
         }
      } else {
         super.set(var1, var2);
      }

   }

}
