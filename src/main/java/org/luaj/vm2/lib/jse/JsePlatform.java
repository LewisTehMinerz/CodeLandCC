package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseOsLib;
import org.luaj.vm2.lib.jse.LuajavaLib;

public class JsePlatform {

   public static LuaTable standardGlobals() {
      LuaTable var0 = new LuaTable();
      var0.load(new JseBaseLib());
      var0.load(new PackageLib());
      var0.load(new TableLib());
      var0.load(new StringLib());
      var0.load(new CoroutineLib());
      var0.load(new JseMathLib());
      var0.load(new JseIoLib());
      var0.load(new JseOsLib());
      var0.load(new LuajavaLib());
      LuaThread.setGlobals(var0);
      LuaC.install();
      return var0;
   }

   public static LuaTable debugGlobals() {
      LuaTable var0 = standardGlobals();
      var0.load(new DebugLib());
      return var0;
   }
}
