package org.luaj.vm2.lib;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.VarArgFunction;

public class TableLib extends OneArgFunction {

   static Class class$org$luaj$vm2$lib$TableLib;
   static Class class$org$luaj$vm2$lib$TableLib$TableLibV;


   private LuaTable init() {
      LuaTable var1 = new LuaTable();
      this.bind(var1, class$org$luaj$vm2$lib$TableLib == null?(class$org$luaj$vm2$lib$TableLib = class$("org.luaj.vm2.lib.TableLib")):class$org$luaj$vm2$lib$TableLib, new String[]{"getn", "maxn"}, 1);
      this.bind(var1, class$org$luaj$vm2$lib$TableLib$TableLibV == null?(class$org$luaj$vm2$lib$TableLib$TableLibV = class$("org.luaj.vm2.lib.TableLib$TableLibV")):class$org$luaj$vm2$lib$TableLib$TableLibV, new String[]{"remove", "concat", "insert", "sort", "foreach", "foreachi"});
      this.env.set("table", (LuaValue)var1);
      PackageLib.instance.LOADED.set("table", var1);
      return var1;
   }

   public LuaValue call(LuaValue var1) {
      switch(this.opcode) {
      case 0:
         return this.init();
      case 1:
         return var1.checktable().getn();
      case 2:
         return valueOf(var1.checktable().maxn());
      default:
         return NIL;
      }
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static final class TableLibV extends VarArgFunction {

      public Varargs invoke(Varargs var1) {
         LuaTable var2;
         int var5;
         switch(this.opcode) {
         case 0:
            var2 = var1.checktable(1);
            var5 = var1.narg() > 1?var1.checkint(2):0;
            return var2.remove(var5);
         case 1:
            var2 = var1.checktable(1);
            return var2.concat(var1.optstring(2, LuaValue.EMPTYSTRING), var1.optint(3, 1), var1.isvalue(4)?var1.checkint(4):var2.length());
         case 2:
            var2 = var1.checktable(1);
            var5 = var1.narg() > 2?var1.checkint(2):0;
            LuaValue var4 = var1.arg(var1.narg() > 2?3:2);
            var2.insert(var5, var4);
            return NONE;
         case 3:
            var2 = var1.checktable(1);
            LuaValue var3 = var1.isnoneornil(2)?NIL:var1.checkfunction(2);
            var2.sort(var3);
            return NONE;
         case 4:
            return var1.checktable(1).foreach(var1.checkfunction(2));
         case 5:
            return var1.checktable(1).foreachi(var1.checkfunction(2));
         default:
            return NONE;
         }
      }
   }
}
