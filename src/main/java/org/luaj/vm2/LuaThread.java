package org.luaj.vm2;

import java.lang.ref.WeakReference;
import java.util.Enumeration;
import java.util.Vector;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.OrphanedThread;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.DebugLib;

public class LuaThread extends LuaValue {

   public static LuaValue s_metatable;
   public static int coroutine_count = 0;
   static long thread_orphan_check_interval = 30000L;
   private static final int STATUS_INITIAL = 0;
   private static final int STATUS_SUSPENDED = 1;
   private static final int STATUS_RUNNING = 2;
   private static final int STATUS_NORMAL = 3;
   private static final int STATUS_DEAD = 4;
   private static final String[] STATUS_NAMES = new String[]{"suspended", "suspended", "running", "normal", "dead"};
   private LuaValue env;
   private final LuaThread.State state;
   public LuaValue err;
   final LuaThread.CallStack callstack = new LuaThread.CallStack();
   public static final int MAX_CALLSTACK = 256;
   private static final LuaThread main_thread = new LuaThread();
   private static LuaThread running_thread = main_thread;
   public static int GC_INTERVAL = 30000;
   public Object debugState;
   private Vector children = new Vector();


   private LuaThread() {
      this.state = new LuaThread.State(this, (LuaValue)null);
      this.state.status = 2;
   }

   public LuaThread(LuaValue var1, LuaValue var2) {
      LuaValue.assert_(var1 != null, "function cannot be null");
      this.env = var2;
      this.state = new LuaThread.State(this, var1);
   }

   public int type() {
      return 8;
   }

   public String typename() {
      return "thread";
   }

   public boolean isthread() {
      return true;
   }

   public LuaThread optthread(LuaThread var1) {
      return this;
   }

   public LuaThread checkthread() {
      return this;
   }

   public LuaValue getmetatable() {
      return s_metatable;
   }

   public LuaValue getfenv() {
      return this.env;
   }

   public void setfenv(LuaValue var1) {
      this.env = var1;
   }

   public String getStatus() {
      return STATUS_NAMES[this.state.status];
   }

   public static LuaThread getRunning() {
      return running_thread;
   }

   public static boolean isMainThread(LuaThread var0) {
      return var0 == main_thread;
   }

   public static void setGlobals(LuaValue var0) {
      running_thread.env = var0;
   }

   public static LuaValue getGlobals() {
      LuaValue var0 = running_thread.env;
      return var0 != null?var0:LuaValue.error("LuaThread.setGlobals() not initialized");
   }

   public static final LuaThread.CallStack onCall(LuaFunction var0) {
      LuaThread.CallStack var1 = running_thread.callstack;
      var1.onCall(var0);
      return var1;
   }

   public static final LuaFunction getCallstackFunction(int var0) {
      return running_thread.callstack.getFunction(var0);
   }

   public static LuaValue setErrorFunc(LuaValue var0) {
      LuaValue var1 = running_thread.err;
      running_thread.err = var0;
      return var1;
   }

   public static Varargs yield(Varargs var0) {
      LuaThread.State var1 = running_thread.state;
      if(var1.function == null) {
         throw new LuaError("cannot yield main thread");
      } else {
         return var1.lua_yield(var0);
      }
   }

   public Varargs resume(Varargs var1) {
      return this.state.status > 1?LuaValue.varargsOf((LuaValue)LuaValue.FALSE, LuaValue.valueOf("cannot resume " + STATUS_NAMES[this.state.status] + " coroutine")):this.state.lua_resume(this, var1);
   }

   public void addChild(LuaThread var1) {
      this.children.addElement(new WeakReference(var1));
   }

   public Varargs abandon() {
      if(this.state.status > 1) {
         return LuaValue.varargsOf((LuaValue)LuaValue.FALSE, LuaValue.valueOf("cannot abandon " + STATUS_NAMES[this.state.status] + " coroutine"));
      } else {
         this.state.lua_abandon(this);
         Enumeration var1 = this.children.elements();

         while(var1.hasMoreElements()) {
            WeakReference var2 = (WeakReference)var1.nextElement();
            LuaThread var3 = (LuaThread)var2.get();
            if(var3 != null && !var3.getStatus().equals("dead")) {
               var3.abandon();
            }
         }

         this.children.removeAllElements();
         return LuaValue.varargsOf(new LuaValue[]{LuaValue.TRUE});
      }
   }

   static LuaThread access$000() {
      return running_thread;
   }

   static LuaThread access$002(LuaThread var0) {
      running_thread = var0;
      return var0;
   }

   static LuaThread.State access$100(LuaThread var0) {
      return var0.state;
   }


   public static class CallStack {

      final LuaFunction[] functions = new LuaFunction[256];
      int calls = 0;


      final void onCall(LuaFunction var1) {
         this.functions[this.calls++] = var1;
         if(DebugLib.DEBUG_ENABLED) {
            DebugLib.debugOnCall(LuaThread.running_thread, this.calls, var1);
         }

      }

      public final void onReturn() {
         this.functions[--this.calls] = null;
         if(DebugLib.DEBUG_ENABLED) {
            DebugLib.debugOnReturn(LuaThread.running_thread, this.calls);
         }

      }

      public final int getCallstackDepth() {
         return this.calls;
      }

      LuaFunction getFunction(int var1) {
         return var1 > 0 && var1 <= this.calls?this.functions[this.calls - var1]:null;
      }
   }

   static class State implements Runnable {

      final WeakReference lua_thread;
      final LuaValue function;
      Varargs args;
      Varargs result;
      String error;
      int status;
      boolean abandoned;


      State(LuaThread var1, LuaValue var2) {
         this.args = LuaValue.NONE;
         this.result = LuaValue.NONE;
         this.error = null;
         this.status = 0;
         this.abandoned = false;
         this.lua_thread = new WeakReference(var1);
         this.function = var2;
      }

      public synchronized void run() {
         try {
            Varargs var1 = this.args;
            this.args = LuaValue.NONE;
            this.result = this.function.invoke(var1);
         } catch (Throwable var5) {
            this.error = var5.getMessage();
         } finally {
            this.status = 4;
            this.notify();
         }

      }

      synchronized Varargs lua_resume(LuaThread var1, Varargs var2) {
         LuaThread var3 = LuaThread.running_thread;

         Varargs var4;
         try {
            LuaThread.running_thread = var1;
            this.args = var2;
            if(this.status == 0) {
               this.status = 2;
               (new Thread(this, "Coroutine-" + ++LuaThread.coroutine_count)).start();
            } else {
               this.notify();
            }

            var3.state.status = 3;
            this.status = 2;
            this.wait();
            var4 = this.error != null?LuaValue.varargsOf((LuaValue)LuaValue.FALSE, LuaValue.valueOf(this.error)):LuaValue.varargsOf((LuaValue)LuaValue.TRUE, this.result);
         } catch (InterruptedException var8) {
            throw new OrphanedThread();
         } finally {
            LuaThread.running_thread = var3;
            LuaThread.running_thread.state.status = 2;
            this.args = LuaValue.NONE;
            this.result = LuaValue.NONE;
            this.error = null;
         }

         return var4;
      }

      synchronized Varargs lua_yield(Varargs var1) {
         try {
            this.result = var1;
            this.status = 1;
            this.notify();

            do {
               this.wait(LuaThread.thread_orphan_check_interval);
               if(this.abandoned || this.lua_thread.get() == null) {
                  this.status = 4;
                  throw new OrphanedThread();
               }
            } while(this.status == 1);

            Varargs var2 = this.args;
            return var2;
         } catch (InterruptedException var6) {
            this.status = 4;
            throw new OrphanedThread();
         } finally {
            this.args = LuaValue.NONE;
            this.result = LuaValue.NONE;
         }
      }

      synchronized void lua_abandon(LuaThread var1) {
         LuaThread var2 = LuaThread.running_thread;

         try {
            var2.state.status = 3;
            this.abandoned = true;
            if(this.status == 0) {
               this.status = 4;
            } else {
               this.notify();
               this.wait();
            }
         } catch (InterruptedException var7) {
            this.status = 4;
         } finally {
            var2.state.status = 2;
            this.args = LuaValue.NONE;
            this.result = LuaValue.NONE;
            this.error = null;
         }

      }
   }
}
