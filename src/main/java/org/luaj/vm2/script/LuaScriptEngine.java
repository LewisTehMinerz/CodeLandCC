package org.luaj.vm2.script;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.script.LuaScriptEngineFactory;

public class LuaScriptEngine implements ScriptEngine, Compilable {

   private static final String __ENGINE_VERSION__ = "Luaj-jse 2.0.3";
   private static final String __NAME__ = "Luaj";
   private static final String __SHORT_NAME__ = "Luaj";
   private static final String __LANGUAGE__ = "lua";
   private static final String __LANGUAGE_VERSION__ = "5.1";
   private static final String __ARGV__ = "arg";
   private static final String __FILENAME__ = "?";
   private static final ScriptEngineFactory myFactory = new LuaScriptEngineFactory();
   private ScriptContext defaultContext;
   private final LuaValue _G = JsePlatform.standardGlobals();


   public LuaScriptEngine() {
      SimpleScriptContext var1 = new SimpleScriptContext();
      var1.setBindings(this.createBindings(), 100);
      this.setContext(var1);
      this.put("javax.script.language_version", "5.1");
      this.put("javax.script.language", "lua");
      this.put("javax.script.engine", "Luaj");
      this.put("javax.script.engine_version", "Luaj-jse 2.0.3");
      this.put("javax.script.argv", "arg");
      this.put("javax.script.filename", "?");
      this.put("javax.script.name", "Luaj");
      this.put("THREADING", (Object)null);
   }

   public Object eval(String var1) throws ScriptException {
      return this.eval((Reader)(new StringReader(var1)));
   }

   public Object eval(String var1, ScriptContext var2) throws ScriptException {
      return this.eval((Reader)(new StringReader(var1)), var2);
   }

   public Object eval(String var1, Bindings var2) throws ScriptException {
      return this.eval((Reader)(new StringReader(var1)), var2);
   }

   public Object eval(Reader var1) throws ScriptException {
      return this.eval(var1, this.getContext());
   }

   public Object eval(Reader var1, ScriptContext var2) throws ScriptException {
      return this.compile(var1).eval(var2);
   }

   public Object eval(Reader var1, Bindings var2) throws ScriptException {
      ScriptContext var3 = this.getContext();
      Bindings var4 = var3.getBindings(100);
      var3.setBindings(var2, 100);
      Object var5 = this.eval(var1);
      var3.setBindings(var4, 100);
      return var5;
   }

   public void put(String var1, Object var2) {
      Bindings var3 = this.getBindings(100);
      var3.put(var1, var2);
   }

   public Object get(String var1) {
      Bindings var2 = this.getBindings(100);
      return var2.get(var1);
   }

   public Bindings getBindings(int var1) {
      return this.getContext().getBindings(var1);
   }

   public void setBindings(Bindings var1, int var2) {
      this.getContext().setBindings(var1, var2);
   }

   public Bindings createBindings() {
      return new SimpleBindings();
   }

   public ScriptContext getContext() {
      return this.defaultContext;
   }

   public void setContext(ScriptContext var1) {
      this.defaultContext = var1;
   }

   public ScriptEngineFactory getFactory() {
      return myFactory;
   }

   public CompiledScript compile(String var1) throws ScriptException {
      return this.compile((Reader)(new StringReader(var1)));
   }

   public CompiledScript compile(Reader var1) throws ScriptException {
      try {
         LuaScriptEngine.Utf8Encoder var2 = new LuaScriptEngine.Utf8Encoder(var1, null);

         LuaScriptEngine.CompiledScriptImpl var5;
         try {
            LuaFunction var3 = LoadState.load(var2, "script", (LuaValue)null);
            if(var3.isclosure()) {
               final Prototype var13 = var3.checkclosure().p;
               var5 = new LuaScriptEngine.CompiledScriptImpl() {
                  protected LuaFunction newFunctionInstance() {
                     return new LuaClosure(var13, (LuaValue)null);
                  }
               };
               return var5;
            }

            final Class var4 = var3.getClass();
            var5 = new LuaScriptEngine.CompiledScriptImpl() {
               protected LuaFunction newFunctionInstance() throws ScriptException {
                  try {
                     return (LuaFunction)var4.newInstance();
                  } catch (Exception var2) {
                     throw new ScriptException("instantiation failed: " + var2.toString());
                  }
               }
            };
         } catch (LuaError var10) {
            throw new ScriptException(var10.getMessage());
         } finally {
            var2.close();
         }

         return var5;
      } catch (Exception var12) {
         throw new ScriptException("eval threw " + var12.toString());
      }
   }


   private final class Utf8Encoder extends InputStream {

      private final Reader r;
      private final int[] buf;
      private int n;


      private Utf8Encoder(Reader var2) {
         this.buf = new int[2];
         this.r = var2;
      }

      public int read() throws IOException {
         if(this.n > 0) {
            return this.buf[--this.n];
         } else {
            int var1 = this.r.read();
            if(var1 < 128) {
               return var1;
            } else {
               this.n = 0;
               if(var1 < 2048) {
                  this.buf[this.n++] = 128 | var1 & 63;
                  return 192 | var1 >> 6 & 31;
               } else {
                  this.buf[this.n++] = 128 | var1 & 63;
                  this.buf[this.n++] = 128 | var1 >> 6 & 63;
                  return 224 | var1 >> 12 & 15;
               }
            }
         }
      }

      // $FF: synthetic method
      Utf8Encoder(Reader var2, Object var3) {
         this(var2);
      }
   }

   public class ClientBindings {

      public final Bindings b;
      public final LuaTable env;


      public ClientBindings(Bindings var2) {
         this.b = var2;
         this.env = new LuaTable();
         this.env.setmetatable(LuaTable.tableOf(new LuaValue[]{LuaValue.INDEX, LuaScriptEngine.this._G}));
         this.copyBindingsToGlobals();
      }

      public void copyBindingsToGlobals() {
         Iterator var1 = this.b.keySet().iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();
            Object var3 = this.b.get(var2);
            LuaValue var4 = this.toLua(var2);
            LuaValue var5 = this.toLua(var3);
            this.env.set(var4, var5);
            var1.remove();
         }

      }

      private LuaValue toLua(Object var1) {
         return var1 == null?LuaValue.NIL:(var1 instanceof LuaValue?(LuaValue)var1:CoerceJavaToLua.coerce(var1));
      }

      public void copyGlobalsToBindings() {
         LuaValue[] var1 = this.env.keys();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            LuaValue var3 = var1[var2];
            LuaValue var4 = this.env.get(var3);
            String var5 = var3.tojstring();
            Object var6 = this.toJava(var4);
            this.b.put(var5, var6);
         }

      }

      private Object toJava(LuaValue var1) {
         switch(var1.type()) {
         case 0:
            return null;
         case 1:
         case 2:
         case 5:
         case 6:
         default:
            return var1;
         case 3:
            return var1.isinttype()?new Integer(var1.toint()):new Double(var1.todouble());
         case 4:
            return var1.tojstring();
         case 7:
            return var1.checkuserdata(Object.class);
         }
      }
   }

   protected abstract class CompiledScriptImpl extends CompiledScript {

      protected abstract LuaFunction newFunctionInstance() throws ScriptException;

      public ScriptEngine getEngine() {
         return LuaScriptEngine.this;
      }

      public Object eval(ScriptContext var1) throws ScriptException {
         Bindings var2 = var1.getBindings(100);
         LuaFunction var3 = this.newFunctionInstance();
         LuaScriptEngine.ClientBindings var4 = LuaScriptEngine.this.new ClientBindings(var2);
         var3.setfenv(var4.env);
         Varargs var5 = var3.invoke(LuaValue.NONE);
         var4.copyGlobalsToBindings();
         return var5;
      }
   }
}
