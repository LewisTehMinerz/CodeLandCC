package org.luaj.vm2.lib;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

public abstract class LibFunction extends LuaFunction {

   protected int opcode;
   protected String name;


   public String tojstring() {
      return this.name != null?this.name:super.tojstring();
   }

   protected void bind(LuaValue var1, Class var2, String[] var3) {
      this.bind(var1, var2, var3, 0);
   }

   protected void bind(LuaValue var1, Class var2, String[] var3, int var4) {
      try {
         int var5 = 0;

         for(int var6 = var3.length; var5 < var6; ++var5) {
            LibFunction var7 = (LibFunction)var2.newInstance();
            var7.opcode = var4 + var5;
            var7.name = var3[var5];
            var7.env = var1;
            var1.set(var7.name, (LuaValue)var7);
         }

      } catch (Exception var8) {
         throw new LuaError("bind failed: " + var8);
      }
   }

   protected static LuaValue[] newupe() {
      return new LuaValue[1];
   }

   protected static LuaValue[] newupn() {
      return new LuaValue[]{NIL};
   }

   protected static LuaValue[] newupl(LuaValue var0) {
      return new LuaValue[]{var0};
   }
}
