package org.luaj.vm2.lib.jse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JavaConstructor;
import org.luaj.vm2.lib.jse.JavaInstance;
import org.luaj.vm2.lib.jse.JavaMethod;

class JavaClass extends JavaInstance implements CoerceJavaToLua.Coercion {

   static final Map classes = Collections.synchronizedMap(new HashMap());
   static final LuaValue NEW = valueOf("new");
   Map fields;
   Map methods;


   static JavaClass forClass(Class var0) {
      JavaClass var1 = (JavaClass)classes.get(var0);
      if(var1 == null) {
         classes.put(var0, var1 = new JavaClass(var0));
      }

      return var1;
   }

   JavaClass(Class var1) {
      super(var1);
      this.jclass = this;
   }

   public LuaValue coerce(Object var1) {
      return this;
   }

   Field getField(LuaValue var1) {
      if(this.fields == null) {
         HashMap var2 = new HashMap();
         Field[] var3 = ((Class)this.m_instance).getFields();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            Field var5 = var3[var4];
            if(Modifier.isPublic(var5.getModifiers())) {
               var2.put(LuaValue.valueOf(var5.getName()), var5);

               try {
                  if(!var5.isAccessible()) {
                     var5.setAccessible(true);
                  }
               } catch (SecurityException var7) {
                  ;
               }
            }
         }

         this.fields = var2;
      }

      return (Field)this.fields.get(var1);
   }

   LuaValue getMethod(LuaValue var1) {
      if(this.methods == null) {
         HashMap var2 = new HashMap();
         Method[] var3 = ((Class)this.m_instance).getMethods();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            Method var5 = var3[var4];
            if(Modifier.isPublic(var5.getModifiers())) {
               String var6 = var5.getName();
               Object var7 = (List)var2.get(var6);
               if(var7 == null) {
                  var2.put(var6, var7 = new ArrayList());
               }

               ((List)var7).add(JavaMethod.forMethod(var5));
            }
         }

         HashMap var11 = new HashMap();
         Constructor[] var12 = ((Class)this.m_instance).getConstructors();
         ArrayList var13 = new ArrayList();

         for(int var14 = 0; var14 < var12.length; ++var14) {
            if(Modifier.isPublic(var12[var14].getModifiers())) {
               var13.add(JavaConstructor.forConstructor(var12[var14]));
            }
         }

         switch(var13.size()) {
         case 0:
            break;
         case 1:
            var11.put(NEW, var13.get(0));
            break;
         default:
            var11.put(NEW, JavaConstructor.forConstructors((JavaConstructor[])((JavaConstructor[])var13.toArray(new JavaConstructor[var13.size()]))));
         }

         Iterator var15 = var2.entrySet().iterator();

         while(var15.hasNext()) {
            Entry var8 = (Entry)var15.next();
            String var9 = (String)var8.getKey();
            List var10 = (List)var8.getValue();
            var11.put(LuaValue.valueOf(var9), var10.size() == 1?var10.get(0):JavaMethod.forMethods((JavaMethod[])((JavaMethod[])var10.toArray(new JavaMethod[var10.size()]))));
         }

         this.methods = var11;
      }

      return (LuaValue)this.methods.get(var1);
   }

   public LuaValue getConstructor() {
      return this.getMethod(NEW);
   }

}
