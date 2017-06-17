package org.luaj.vm2.luajc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.luajc.JavaGen;
import org.luaj.vm2.luajc.JavaLoader;

public class LuaJC implements LoadState.LuaCompiler {

   private static final String NON_IDENTIFIER = "[^a-zA-Z0-9_$/.\\-]";
   private static LuaJC instance;


   public static LuaJC getInstance() {
      if(instance == null) {
         instance = new LuaJC();
      }

      return instance;
   }

   public static final void install() {
      LoadState.compiler = getInstance();
   }

   public Hashtable compileAll(InputStream var1, String var2, String var3) throws IOException {
      String var4 = toStandardJavaClassName(var2);
      String var5 = toStandardLuaFileName(var3);
      Hashtable var6 = new Hashtable();
      LuaC var10000 = LuaC.instance;
      Prototype var7 = LuaC.compile(var1, var4);
      JavaGen var8 = new JavaGen(var7, var4, var5);
      this.insert(var6, var8);
      return var6;
   }

   private void insert(Hashtable var1, JavaGen var2) {
      var1.put(var2.classname, var2.bytecode);
      int var3 = 0;

      for(int var4 = var2.inners != null?var2.inners.length:0; var3 < var4; ++var3) {
         this.insert(var1, var2.inners[var3]);
      }

   }

   public LuaFunction load(InputStream var1, String var2, LuaValue var3) throws IOException {
      LuaC var10000 = LuaC.instance;
      Prototype var4 = LuaC.compile(var1, var2);
      String var5 = toStandardJavaClassName(var2);
      String var6 = toStandardLuaFileName(var2);
      JavaLoader var7 = new JavaLoader(var3);
      return var7.load(var4, var5, var6);
   }

   private static String toStandardJavaClassName(String var0) {
      String var1 = toStub(var0);
      String var2 = var1.replace('/', '.').replaceAll("[^a-zA-Z0-9_$/.\\-]", "_");
      char var3 = var2.charAt(0);
      if(var3 != 95 && !Character.isJavaIdentifierStart(var3)) {
         var2 = "_" + var2;
      }

      return var2;
   }

   private static String toStandardLuaFileName(String var0) {
      String var1 = toStub(var0);
      String var2 = var1.replace('.', '/') + ".lua";
      return var2;
   }

   private static String toStub(String var0) {
      String var1 = var0.endsWith(".lua")?var0.substring(0, var0.length() - 4):var0;
      return var1;
   }
}
