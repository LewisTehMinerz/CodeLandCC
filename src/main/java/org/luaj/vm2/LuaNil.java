package org.luaj.vm2;

import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;

public class LuaNil extends LuaValue {

   static final LuaNil _NIL = new LuaNil();
   public static LuaValue s_metatable;


   public int type() {
      return 0;
   }

   public String typename() {
      return "nil";
   }

   public String tojstring() {
      return "nil";
   }

   public LuaValue not() {
      return LuaValue.TRUE;
   }

   public boolean toboolean() {
      return false;
   }

   public boolean isnil() {
      return true;
   }

   public LuaValue getmetatable() {
      return s_metatable;
   }

   public boolean equals(Object var1) {
      return var1 instanceof LuaNil;
   }

   public LuaValue checknotnil() {
      return this.argerror("value");
   }

   public LuaValue checkvalidkey() {
      return this.typerror("table index");
   }

   public boolean optboolean(boolean var1) {
      return var1;
   }

   public LuaClosure optclosure(LuaClosure var1) {
      return var1;
   }

   public double optdouble(double var1) {
      return var1;
   }

   public LuaFunction optfunction(LuaFunction var1) {
      return var1;
   }

   public int optint(int var1) {
      return var1;
   }

   public LuaInteger optinteger(LuaInteger var1) {
      return var1;
   }

   public long optlong(long var1) {
      return var1;
   }

   public LuaNumber optnumber(LuaNumber var1) {
      return var1;
   }

   public LuaTable opttable(LuaTable var1) {
      return var1;
   }

   public LuaThread optthread(LuaThread var1) {
      return var1;
   }

   public String optjstring(String var1) {
      return var1;
   }

   public LuaString optstring(LuaString var1) {
      return var1;
   }

   public Object optuserdata(Object var1) {
      return var1;
   }

   public Object optuserdata(Class var1, Object var2) {
      return var2;
   }

   public LuaValue optvalue(LuaValue var1) {
      return var1;
   }

}
