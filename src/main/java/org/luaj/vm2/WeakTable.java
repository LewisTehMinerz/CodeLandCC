package org.luaj.vm2;

import java.lang.ref.WeakReference;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;

public class WeakTable extends LuaTable {

   private boolean weakkeys;
   private boolean weakvalues;


   public WeakTable(boolean var1, boolean var2) {
      this(var1, var2, 0, 0);
   }

   protected WeakTable(boolean var1, boolean var2, int var3, int var4) {
      super(var3, var4);
      this.weakkeys = var1;
      this.weakvalues = var2;
   }

   protected WeakTable(boolean var1, boolean var2, LuaTable var3) {
      this(var1, var2, var3.getArrayLength(), var3.getHashLength());
      LuaValue var5 = NIL;

      Varargs var4;
      while(!(var5 = (var4 = var3.next(var5)).arg1()).isnil()) {
         this.rawset(var5, var4.arg(2));
      }

      this.m_metatable = var3.m_metatable;
   }

   public void presize(int var1) {
      super.presize(var1);
   }

   public void presize(int var1, int var2) {
      super.presize(var1, var2);
   }

   protected int getArrayLength() {
      return super.getArrayLength();
   }

   protected int getHashLength() {
      return super.getHashLength();
   }

   protected LuaTable changemode(boolean var1, boolean var2) {
      this.weakkeys = var1;
      this.weakvalues = var2;
      return this;
   }

   LuaValue weaken(LuaValue var1) {
      switch(var1.type()) {
      case 5:
      case 6:
      case 8:
         return new WeakTable.WeakValue(var1);
      case 7:
         return new WeakTable.WeakUserdata(var1, null);
      default:
         return var1;
      }
   }

   public void rawset(int var1, LuaValue var2) {
      if(this.weakvalues) {
         var2 = this.weaken(var2);
      }

      super.rawset(var1, var2);
   }

   public void rawset(LuaValue var1, LuaValue var2) {
      if(this.weakvalues) {
         var2 = this.weaken((LuaValue)var2);
      }

      if(this.weakkeys) {
         switch(((LuaValue)var1).type()) {
         case 5:
         case 6:
         case 7:
         case 8:
            var1 = var2 = new WeakTable.WeakEntry(this, (LuaValue)var1, (LuaValue)var2, null);
         }
      }

      super.rawset((LuaValue)var1, (LuaValue)var2);
   }

   public LuaValue rawget(int var1) {
      return super.rawget(var1).strongvalue();
   }

   public LuaValue rawget(LuaValue var1) {
      return super.rawget(var1).strongvalue();
   }

   protected LuaValue hashget(LuaValue var1) {
      if(this.hashEntries > 0) {
         int var2 = this.hashFindSlot(var1);
         if(this.hashEntries == 0) {
            return NIL;
         } else {
            LuaValue var3 = this.hashValues[var2];
            return var3 != null?var3:NIL;
         }
      } else {
         return NIL;
      }
   }

   public int hashFindSlot(LuaValue var1) {
      int var2 = (var1.hashCode() & Integer.MAX_VALUE) % this.hashKeys.length;

      LuaValue var3;
      while((var3 = this.hashKeys[var2]) != null) {
         if(var3.isweaknil()) {
            this.hashClearSlot(var2);
            if(this.hashEntries == 0) {
               return 0;
            }
         } else {
            if(var3.raweq(var1.strongkey())) {
               return var2;
            }

            var2 = (var2 + 1) % this.hashKeys.length;
         }
      }

      return var2;
   }

   public int maxn() {
      return super.maxn();
   }

   public Varargs next(LuaValue var1) {
      while(true) {
         Varargs var2 = super.next(var1);
         LuaValue var3 = var2.arg1();
         if(var3.isnil()) {
            return NIL;
         }

         LuaValue var4 = var3.strongkey();
         LuaValue var5 = var2.arg(2).strongvalue();
         if(!var4.isnil() && !var5.isnil()) {
            return varargsOf(var4, var5);
         }

         super.rawset(var3, NIL);
      }
   }

   public void sort(final LuaValue var1) {
      super.sort(new TwoArgFunction() {
         public LuaValue call(LuaValue var1x, LuaValue var2) {
            return var1.call(var1x.strongvalue(), var2.strongvalue());
         }
      });
   }

   static final class WeakEntry extends LuaValue {

      final LuaValue weakkey;
      LuaValue weakvalue;
      final int keyhash;


      private WeakEntry(WeakTable var1, LuaValue var2, LuaValue var3) {
         this.weakkey = var1.weaken(var2);
         this.keyhash = var2.hashCode();
         this.weakvalue = var3;
      }

      public LuaValue strongkey() {
         return this.weakkey.strongvalue();
      }

      public LuaValue strongvalue() {
         LuaValue var1 = this.weakkey.strongvalue();
         return var1.isnil()?(this.weakvalue = NIL):this.weakvalue.strongvalue();
      }

      public int type() {
         return -1;
      }

      public String typename() {
         this.illegal("typename", "weak entry");
         return null;
      }

      public String toString() {
         return "weak<" + this.weakkey.strongvalue() + "," + this.strongvalue() + ">";
      }

      public int hashCode() {
         return this.keyhash;
      }

      public boolean raweq(LuaValue var1) {
         return this.weakkey.raweq(var1);
      }

      public boolean isweaknil() {
         return this.weakkey.isweaknil() || this.weakvalue.isweaknil();
      }

      WeakEntry(WeakTable var1, LuaValue var2, LuaValue var3, Object var4) {
         this(var1, var2, var3);
      }
   }

   static class WeakValue extends LuaValue {

      final WeakReference ref;


      protected WeakValue(LuaValue var1) {
         this.ref = new WeakReference(var1);
      }

      public int type() {
         this.illegal("type", "weak value");
         return 0;
      }

      public String typename() {
         this.illegal("typename", "weak value");
         return null;
      }

      public String toString() {
         return "weak<" + this.ref.get() + ">";
      }

      public LuaValue strongvalue() {
         Object var1 = this.ref.get();
         return var1 != null?(LuaValue)var1:NIL;
      }

      public boolean raweq(LuaValue var1) {
         Object var2 = this.ref.get();
         return var2 != null && var1.raweq((LuaValue)var2);
      }

      public boolean isweaknil() {
         return this.ref.get() == null;
      }
   }

   static final class WeakUserdata extends WeakTable.WeakValue {

      private final WeakReference ob;
      private final LuaValue mt;


      private WeakUserdata(LuaValue var1) {
         super(var1);
         this.ob = new WeakReference(var1.touserdata());
         this.mt = var1.getmetatable();
      }

      public LuaValue strongvalue() {
         Object var1 = this.ref.get();
         if(var1 != null) {
            return (LuaValue)var1;
         } else {
            Object var2 = this.ob.get();
            return (LuaValue)(var2 != null?userdataOf(var2, this.mt):NIL);
         }
      }

      public boolean raweq(LuaValue var1) {
         if(!var1.isuserdata()) {
            return false;
         } else {
            LuaValue var2 = (LuaValue)this.ref.get();
            return var2 != null && var2.raweq(var1)?true:var1.touserdata() == this.ob.get();
         }
      }

      public boolean isweaknil() {
         return this.ob.get() == null || this.ref.get() == null;
      }

      WeakUserdata(LuaValue var1, Object var2) {
         this(var1);
      }
   }
}
