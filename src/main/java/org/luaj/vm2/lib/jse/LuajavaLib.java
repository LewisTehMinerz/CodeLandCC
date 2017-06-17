package org.luaj.vm2.lib.jse;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JavaClass;

public class LuajavaLib extends VarArgFunction {

   static final int INIT = 0;
   static final int BINDCLASS = 1;
   static final int NEWINSTANCE = 2;
   static final int NEW = 3;
   static final int CREATEPROXY = 4;
   static final int LOADLIB = 5;
   static final String[] NAMES = new String[]{"bindClass", "newInstance", "new", "createProxy", "loadLib"};
   static final int METHOD_MODIFIERS_VARARGS = 128;
   static Class class$org$luaj$vm2$lib$jse$LuajavaLib;
   static Class class$java$lang$Class;


   public Varargs invoke(Varargs var1) {
      try {
         Object var6;
         switch(this.opcode) {
         case 0:
            LuaTable var13 = new LuaTable();
            this.bind(var13, class$org$luaj$vm2$lib$jse$LuajavaLib == null?(class$org$luaj$vm2$lib$jse$LuajavaLib = class$("org.luaj.vm2.lib.jse.LuajavaLib")):class$org$luaj$vm2$lib$jse$LuajavaLib, NAMES, 1);
            this.env.set("luajava", (LuaValue)var13);
            PackageLib.instance.LOADED.set("luajava", var13);
            return var13;
         case 1:
            Class var12 = this.classForName(var1.checkjstring(1));
            return JavaClass.forClass(var12);
         case 2:
         case 3:
            LuaValue var11 = var1.checkvalue(1);
            Class var15 = this.opcode == 2?this.classForName(var11.tojstring()):(Class)var11.checkuserdata(class$java$lang$Class == null?(class$java$lang$Class = class$("java.lang.Class")):class$java$lang$Class);
            Varargs var17 = var1.subargs(2);
            return JavaClass.forClass(var15).getConstructor().invoke(var17);
         case 4:
            int var10 = var1.narg() - 1;
            if(var10 <= 0) {
               throw new LuaError("no interfaces");
            }

            final LuaTable var14 = var1.checktable(var10 + 1);
            Class[] var16 = new Class[var10];

            for(int var18 = 0; var18 < var10; ++var18) {
               var16[var18] = this.classForName(var1.checkjstring(var18 + 1));
            }

            InvocationHandler var19 = new InvocationHandler() {
               public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
                  String var4 = var2.getName();
                  LuaValue var5 = var14.get(var4);
                  if(var5.isnil()) {
                     return null;
                  } else {
                     boolean var6 = (var2.getModifiers() & 128) != 0;
                     int var7 = var3 != null?var3.length:0;
                     LuaValue[] var8;
                     if(var6) {
                        --var7;
                        Object var9 = var3[var7];
                        int var10 = Array.getLength(var9);
                        var8 = new LuaValue[var7 + var10];

                        int var11;
                        for(var11 = 0; var11 < var7; ++var11) {
                           var8[var11] = CoerceJavaToLua.coerce(var3[var11]);
                        }

                        for(var11 = 0; var11 < var10; ++var11) {
                           var8[var11 + var7] = CoerceJavaToLua.coerce(Array.get(var9, var11));
                        }
                     } else {
                        var8 = new LuaValue[var7];

                        for(int var12 = 0; var12 < var7; ++var12) {
                           var8[var12] = CoerceJavaToLua.coerce(var3[var12]);
                        }
                     }

                     LuaValue var13 = var5.invoke(var8).arg1();
                     return CoerceLuaToJava.coerce(var13, var2.getReturnType());
                  }
               }
            };
            var6 = Proxy.newProxyInstance(this.getClass().getClassLoader(), var16, var19);
            return LuaValue.userdataOf(var6);
         case 5:
            String var2 = var1.checkjstring(1);
            String var3 = var1.checkjstring(2);
            Class var4 = this.classForName(var2);
            Method var5 = var4.getMethod(var3, new Class[0]);
            var6 = var5.invoke(var4, new Object[0]);
            if(var6 instanceof LuaValue) {
               return (LuaValue)var6;
            }

            return NIL;
         default:
            throw new LuaError("not yet supported: " + this);
         }
      } catch (LuaError var7) {
         throw var7;
      } catch (InvocationTargetException var8) {
         throw new LuaError(var8.getTargetException());
      } catch (Exception var9) {
         throw new LuaError(var9);
      }
   }

   protected Class classForName(String var1) throws ClassNotFoundException {
      return Class.forName(var1, true, ClassLoader.getSystemClassLoader());
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

}
