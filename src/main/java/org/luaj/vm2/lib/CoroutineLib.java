package org.luaj.vm2.lib;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.VarArgFunction;

public class CoroutineLib extends VarArgFunction {

   private static final int INIT = 0;
   private static final int CREATE = 1;
   private static final int RESUME = 2;
   private static final int RUNNING = 3;
   private static final int STATUS = 4;
   private static final int YIELD = 5;
   private static final int WRAP = 6;
   private static final int WRAPPED = 7;
   static Class class$org$luaj$vm2$lib$CoroutineLib;


   private LuaTable init() {
      LuaTable var1 = new LuaTable();
      this.bind(var1, class$org$luaj$vm2$lib$CoroutineLib == null?(class$org$luaj$vm2$lib$CoroutineLib = class$("org.luaj.vm2.lib.CoroutineLib")):class$org$luaj$vm2$lib$CoroutineLib, new String[]{"create", "resume", "running", "status", "yield", "wrap"}, 1);
      this.env.set("coroutine", (LuaValue)var1);
      PackageLib.instance.LOADED.set("coroutine", var1);
      return var1;
   }

   public Varargs invoke(Varargs var1) {
      LuaThread var2;
      LuaValue var5;
      LuaThread var6;
      switch(this.opcode) {
      case 0:
         return this.init();
      case 1:
         var5 = var1.checkfunction(1);
         var6 = new LuaThread(var5, LuaThread.getGlobals());
         LuaThread.getRunning().addChild(var6);
         return var6;
      case 2:
         var2 = var1.checkthread(1);
         return var2.resume(var1.subargs(2));
      case 3:
         var2 = LuaThread.getRunning();
         return (Varargs)(LuaThread.isMainThread(var2)?NIL:var2);
      case 4:
         return valueOf(var1.checkthread(1).getStatus());
      case 5:
         return LuaThread.yield(var1);
      case 6:
         var5 = var1.checkfunction(1);
         var6 = new LuaThread(var5, var5.getfenv());
         LuaThread.getRunning().addChild(var6);
         CoroutineLib var4 = new CoroutineLib();
         var4.setfenv(var6);
         var4.name = "wrapped";
         var4.opcode = 7;
         return var4;
      case 7:
         var2 = (LuaThread)this.env;
         Varargs var3 = var2.resume(var1);
         if(var3.arg1().toboolean()) {
            return var3.subargs(2);
         } else {
            error(var3.arg(2).tojstring());
         }
      default:
         return NONE;
      }
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
