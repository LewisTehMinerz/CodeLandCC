package org.luaj.vm2.luajc;

import java.util.HashMap;
import java.util.Map;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.luajc.JavaGen;

public class JavaLoader extends ClassLoader {

   private final LuaValue env;
   private Map unloaded = new HashMap();


   public JavaLoader(LuaValue var1) {
      this.env = var1;
   }

   public LuaFunction load(Prototype var1, String var2, String var3) {
      JavaGen var4 = new JavaGen(var1, var2, var3);
      return this.load(var4);
   }

   public LuaFunction load(JavaGen var1) {
      this.include(var1);
      return this.load(var1.classname);
   }

   public LuaFunction load(String var1) {
      try {
         Class var2 = this.loadClass(var1);
         LuaFunction var3 = (LuaFunction)var2.newInstance();
         var3.setfenv(this.env);
         return var3;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalStateException("bad class gen: " + var4);
      }
   }

   public void include(JavaGen var1) {
      this.unloaded.put(var1.classname, var1.bytecode);
      int var2 = 0;

      for(int var3 = var1.inners != null?var1.inners.length:0; var2 < var3; ++var2) {
         this.include(var1.inners[var2]);
      }

   }

   public Class findClass(String var1) throws ClassNotFoundException {
      byte[] var2 = (byte[])((byte[])this.unloaded.get(var1));
      return var2 != null?this.defineClass(var1, var2, 0, var2.length):super.findClass(var1);
   }
}
