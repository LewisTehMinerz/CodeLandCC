package org.luaj.vm2;

import org.luaj.vm2.LuaValue;

public abstract class LuaFunction extends LuaValue {

   public static LuaValue s_metatable;
   protected LuaValue env;


   public LuaFunction() {
      this.env = NIL;
   }

   public LuaFunction(LuaValue var1) {
      this.env = var1;
   }

   public int type() {
      return 6;
   }

   public String typename() {
      return "function";
   }

   public boolean isfunction() {
      return true;
   }

   public LuaValue checkfunction() {
      return this;
   }

   public LuaFunction optfunction(LuaFunction var1) {
      return this;
   }

   public LuaValue getmetatable() {
      return s_metatable;
   }

   public LuaValue getfenv() {
      return this.env;
   }

   public void setfenv(LuaValue var1) {
      this.env = var1 != null?var1:NIL;
   }
}
