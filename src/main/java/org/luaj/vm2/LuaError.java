package org.luaj.vm2;

import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.DebugLib;

public class LuaError extends RuntimeException {

   private static final long serialVersionUID = 1L;
   private String traceback;
   private Throwable cause;


   private static String errorHook(String var0) {
      LuaThread var1 = LuaThread.getRunning();
      if(var1.err != null) {
         LuaValue var2 = var1.err;
         var1.err = null;

         String var4;
         try {
            String var3 = var2.call(LuaValue.valueOf(var0)).tojstring();
            return var3;
         } catch (Throwable var8) {
            var4 = "error in error handling";
         } finally {
            var1.err = var2;
         }

         return var4;
      } else {
         return var0;
      }
   }

   public LuaError(Throwable var1) {
      super(errorHook(addFileLine("vm error: " + var1)));
      this.cause = var1;
      this.traceback = DebugLib.traceback(1);
   }

   public LuaError(String var1) {
      super(errorHook(addFileLine(var1)));
      this.traceback = DebugLib.traceback(1);
   }

   public LuaError(String var1, int var2) {
      super(errorHook(addFileLine(var1, var2)));
      this.traceback = DebugLib.traceback(1);
   }

   private static String addFileLine(String var0, int var1) {
      if(var0 == null) {
         return null;
      } else if(var1 == 0) {
         return var0;
      } else {
         String var2 = DebugLib.fileline(var1 - 1);
         return var2 != null?var2 + ": " + var0:var0;
      }
   }

   private static String addFileLine(String var0) {
      if(var0 == null) {
         return null;
      } else {
         String var1 = DebugLib.fileline();
         return var1 != null?var1 + ": " + var0:var0;
      }
   }

   public void printStackTrace() {
      System.out.println(this.toString());
      if(this.traceback != null) {
         System.out.println(this.traceback);
      }

   }

   public Throwable getCause() {
      return this.cause;
   }
}
