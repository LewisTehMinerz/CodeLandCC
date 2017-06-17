package org.luaj.vm2.script;

import java.util.Arrays;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import org.luaj.vm2.script.LuaScriptEngine;

public class LuaScriptEngineFactory implements ScriptEngineFactory {

   private static final String[] EXTENSIONS = new String[]{"lua", ".lua"};
   private static final String[] MIMETYPES = new String[]{"text/plain", "text/lua", "application/lua"};
   private static final String[] NAMES = new String[]{"lua", "luaj"};
   private static final ThreadLocal engines = new ThreadLocal();
   private List extensions;
   private List mimeTypes;
   private List names;


   public LuaScriptEngineFactory() {
      this.extensions = Arrays.asList(EXTENSIONS);
      this.mimeTypes = Arrays.asList(MIMETYPES);
      this.names = Arrays.asList(NAMES);
   }

   public String getEngineName() {
      return this.getScriptEngine().get("javax.script.engine").toString();
   }

   public String getEngineVersion() {
      return this.getScriptEngine().get("javax.script.engine_version").toString();
   }

   public List getExtensions() {
      return this.extensions;
   }

   public List getMimeTypes() {
      return this.mimeTypes;
   }

   public List getNames() {
      return this.names;
   }

   public String getLanguageName() {
      return this.getScriptEngine().get("javax.script.language").toString();
   }

   public String getLanguageVersion() {
      return this.getScriptEngine().get("javax.script.language_version").toString();
   }

   public Object getParameter(String var1) {
      return this.getScriptEngine().get(var1).toString();
   }

   public String getMethodCallSyntax(String var1, String var2, String ... var3) {
      StringBuffer var4 = new StringBuffer();
      var4.append(var1 + ":" + var2 + "(");
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         if(var6 > 0) {
            var4.append(',');
         }

         var4.append(var3[var6]);
      }

      var4.append(")");
      return var4.toString();
   }

   public String getOutputStatement(String var1) {
      return "print(" + var1 + ")";
   }

   public String getProgram(String ... var1) {
      StringBuffer var2 = new StringBuffer();
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if(var4 > 0) {
            var2.append('\n');
         }

         var2.append(var1[var4]);
      }

      return var2.toString();
   }

   public ScriptEngine getScriptEngine() {
      Object var1 = (ScriptEngine)engines.get();
      if(var1 == null) {
         var1 = new LuaScriptEngine();
         engines.set(var1);
      }

      return (ScriptEngine)var1;
   }

}
