package org.luaj.vm2;

import org.luaj.vm2.Buffer;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;

public abstract class Varargs {

   public abstract LuaValue arg(int var1);

   public abstract int narg();

   public abstract LuaValue arg1();

   public Varargs eval() {
      return this;
   }

   public boolean isTailcall() {
      return false;
   }

   public int type(int var1) {
      return this.arg(var1).type();
   }

   public boolean isnil(int var1) {
      return this.arg(var1).isnil();
   }

   public boolean isfunction(int var1) {
      return this.arg(var1).isfunction();
   }

   public boolean isnumber(int var1) {
      return this.arg(var1).isnumber();
   }

   public boolean isstring(int var1) {
      return this.arg(var1).isstring();
   }

   public boolean istable(int var1) {
      return this.arg(var1).istable();
   }

   public boolean isthread(int var1) {
      return this.arg(var1).isthread();
   }

   public boolean isuserdata(int var1) {
      return this.arg(var1).isuserdata();
   }

   public boolean isvalue(int var1) {
      return var1 > 0 && var1 <= this.narg();
   }

   public boolean optboolean(int var1, boolean var2) {
      return this.arg(var1).optboolean(var2);
   }

   public LuaClosure optclosure(int var1, LuaClosure var2) {
      return this.arg(var1).optclosure(var2);
   }

   public double optdouble(int var1, double var2) {
      return this.arg(var1).optdouble(var2);
   }

   public LuaFunction optfunction(int var1, LuaFunction var2) {
      return this.arg(var1).optfunction(var2);
   }

   public int optint(int var1, int var2) {
      return this.arg(var1).optint(var2);
   }

   public LuaInteger optinteger(int var1, LuaInteger var2) {
      return this.arg(var1).optinteger(var2);
   }

   public long optlong(int var1, long var2) {
      return this.arg(var1).optlong(var2);
   }

   public LuaNumber optnumber(int var1, LuaNumber var2) {
      return this.arg(var1).optnumber(var2);
   }

   public String optjstring(int var1, String var2) {
      return this.arg(var1).optjstring(var2);
   }

   public LuaString optstring(int var1, LuaString var2) {
      return this.arg(var1).optstring(var2);
   }

   public LuaTable opttable(int var1, LuaTable var2) {
      return this.arg(var1).opttable(var2);
   }

   public LuaThread optthread(int var1, LuaThread var2) {
      return this.arg(var1).optthread(var2);
   }

   public Object optuserdata(int var1, Object var2) {
      return this.arg(var1).optuserdata(var2);
   }

   public Object optuserdata(int var1, Class var2, Object var3) {
      return this.arg(var1).optuserdata(var2, var3);
   }

   public LuaValue optvalue(int var1, LuaValue var2) {
      return var1 > 0 && var1 <= this.narg()?this.arg(var1):var2;
   }

   public boolean checkboolean(int var1) {
      return this.arg(var1).checkboolean();
   }

   public LuaClosure checkclosure(int var1) {
      return this.arg(var1).checkclosure();
   }

   public double checkdouble(int var1) {
      return this.arg(var1).checknumber().todouble();
   }

   public LuaValue checkfunction(int var1) {
      return this.arg(var1).checkfunction();
   }

   public int checkint(int var1) {
      return this.arg(var1).checknumber().toint();
   }

   public LuaInteger checkinteger(int var1) {
      return this.arg(var1).checkinteger();
   }

   public long checklong(int var1) {
      return this.arg(var1).checknumber().tolong();
   }

   public LuaNumber checknumber(int var1) {
      return this.arg(var1).checknumber();
   }

   public String checkjstring(int var1) {
      return this.arg(var1).checkjstring();
   }

   public LuaString checkstring(int var1) {
      return this.arg(var1).checkstring();
   }

   public LuaTable checktable(int var1) {
      return this.arg(var1).checktable();
   }

   public LuaThread checkthread(int var1) {
      return this.arg(var1).checkthread();
   }

   public Object checkuserdata(int var1) {
      return this.arg(var1).checkuserdata();
   }

   public Object checkuserdata(int var1, Class var2) {
      return this.arg(var1).checkuserdata(var2);
   }

   public LuaValue checkvalue(int var1) {
      return var1 <= this.narg()?this.arg(var1):LuaValue.argerror(var1, "value expected");
   }

   public LuaValue checknotnil(int var1) {
      return this.arg(var1).checknotnil();
   }

   public void argcheck(boolean var1, int var2, String var3) {
      if(!var1) {
         LuaValue.argerror(var2, var3);
      }

   }

   public boolean isnoneornil(int var1) {
      return var1 > this.narg() || this.arg(var1).isnil();
   }

   public boolean toboolean(int var1) {
      return this.arg(var1).toboolean();
   }

   public byte tobyte(int var1) {
      return this.arg(var1).tobyte();
   }

   public char tochar(int var1) {
      return this.arg(var1).tochar();
   }

   public double todouble(int var1) {
      return this.arg(var1).todouble();
   }

   public float tofloat(int var1) {
      return this.arg(var1).tofloat();
   }

   public int toint(int var1) {
      return this.arg(var1).toint();
   }

   public long tolong(int var1) {
      return this.arg(var1).tolong();
   }

   public String tojstring(int var1) {
      return this.arg(var1).tojstring();
   }

   public short toshort(int var1) {
      return this.arg(var1).toshort();
   }

   public Object touserdata(int var1) {
      return this.arg(var1).touserdata();
   }

   public Object touserdata(int var1, Class var2) {
      return this.arg(var1).touserdata(var2);
   }

   public String tojstring() {
      Buffer var1 = new Buffer();
      var1.append("(");
      int var2 = 1;

      for(int var3 = this.narg(); var2 <= var3; ++var2) {
         if(var2 > 1) {
            var1.append(",");
         }

         var1.append(this.arg(var2).tojstring());
      }

      var1.append(")");
      return var1.tojstring();
   }

   public String toString() {
      return this.tojstring();
   }

   public Varargs subargs(int var1) {
      int var2 = this.narg();
      switch(var2 - var1) {
      case 0:
         return this.arg(var1);
      case 1:
         return new LuaValue.PairVarargs(this.arg(var1), this.arg(var2));
      default:
         return (Varargs)(var2 < var1?LuaValue.NONE:new Varargs.SubVarargs(this, var1, var2));
      }
   }

   private static class SubVarargs extends Varargs {

      private final Varargs v;
      private final int start;
      private final int end;


      public SubVarargs(Varargs var1, int var2, int var3) {
         this.v = var1;
         this.start = var2;
         this.end = var3;
      }

      public LuaValue arg(int var1) {
         var1 += this.start - 1;
         return var1 >= this.start && var1 <= this.end?this.v.arg(var1):LuaValue.NIL;
      }

      public LuaValue arg1() {
         return this.v.arg(this.start);
      }

      public int narg() {
         return this.end + 1 - this.start;
      }
   }
}
