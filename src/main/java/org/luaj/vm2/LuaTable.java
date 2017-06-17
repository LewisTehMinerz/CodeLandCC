package org.luaj.vm2;

import java.util.Vector;
import org.luaj.vm2.Buffer;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.WeakTable;

public class LuaTable extends LuaValue {

   private static final int MIN_HASH_CAPACITY = 2;
   private static final LuaString N = valueOf("n");
   protected LuaValue[] array;
   protected LuaValue[] hashKeys;
   protected LuaValue[] hashValues;
   protected int hashEntries;
   protected LuaValue m_metatable;


   public LuaTable() {
      this.array = NOVALS;
      this.hashKeys = NOVALS;
      this.hashValues = NOVALS;
   }

   public LuaTable(int var1, int var2) {
      this.presize(var1, var2);
   }

   public LuaTable(LuaValue[] var1, LuaValue[] var2, Varargs var3) {
      int var4 = var1 != null?var1.length:0;
      int var5 = var2 != null?var2.length:0;
      int var6 = var3 != null?var3.narg():0;
      this.presize(var5 + var6, var4 - (var4 >> 1));

      int var7;
      for(var7 = 0; var7 < var5; ++var7) {
         this.rawset(var7 + 1, var2[var7]);
      }

      if(var3 != null) {
         var7 = 1;

         for(int var8 = var3.narg(); var7 <= var8; ++var7) {
            this.rawset(var5 + var7, var3.arg(var7));
         }
      }

      for(var7 = 0; var7 < var4; var7 += 2) {
         if(!var1[var7 + 1].isnil()) {
            this.rawset(var1[var7], var1[var7 + 1]);
         }
      }

   }

   public LuaTable(Varargs var1) {
      this(var1, 1);
   }

   public LuaTable(Varargs var1, int var2) {
      int var3 = var2 - 1;
      int var4 = Math.max(var1.narg() - var3, 0);
      this.presize(var4, 1);
      this.set(N, valueOf(var4));

      for(int var5 = 1; var5 <= var4; ++var5) {
         this.set(var5, var1.arg(var5 + var3));
      }

   }

   public int type() {
      return 5;
   }

   public String typename() {
      return "table";
   }

   public boolean istable() {
      return true;
   }

   public LuaTable checktable() {
      return this;
   }

   public LuaTable opttable(LuaTable var1) {
      return this;
   }

   public void presize(int var1) {
      if(var1 > this.array.length) {
         this.array = resize(this.array, var1);
      }

   }

   public void presize(int var1, int var2) {
      if(var2 > 0 && var2 < 2) {
         var2 = 2;
      }

      this.array = var1 > 0?new LuaValue[var1]:NOVALS;
      this.hashKeys = var2 > 0?new LuaValue[var2]:NOVALS;
      this.hashValues = var2 > 0?new LuaValue[var2]:NOVALS;
      this.hashEntries = 0;
   }

   private static LuaValue[] resize(LuaValue[] var0, int var1) {
      LuaValue[] var2 = new LuaValue[var1];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      return var2;
   }

   protected int getArrayLength() {
      return this.array.length;
   }

   protected int getHashLength() {
      return this.hashValues.length;
   }

   public LuaValue getmetatable() {
      return this.m_metatable;
   }

   public LuaValue setmetatable(LuaValue var1) {
      this.m_metatable = var1;
      LuaValue var2;
      if(this.m_metatable != null && (var2 = this.m_metatable.rawget((LuaValue)MODE)).isstring()) {
         String var3 = var2.tojstring();
         boolean var4 = var3.indexOf(107) >= 0;
         boolean var5 = var3.indexOf(118) >= 0;
         return this.changemode(var4, var5);
      } else {
         return this;
      }
   }

   protected LuaTable changemode(boolean var1, boolean var2) {
      return (LuaTable)(!var1 && !var2?this:new WeakTable(var1, var2, this));
   }

   public LuaValue get(int var1) {
      LuaValue var2 = this.rawget(var1);
      return var2.isnil() && this.m_metatable != null?gettable(this, valueOf(var1)):var2;
   }

   public LuaValue get(LuaValue var1) {
      LuaValue var2 = this.rawget(var1);
      return var2.isnil() && this.m_metatable != null?gettable(this, var1):var2;
   }

   public LuaValue rawget(int var1) {
      return var1 > 0 && var1 <= this.array.length?(this.array[var1 - 1] != null?this.array[var1 - 1]:NIL):this.hashget(LuaInteger.valueOf(var1));
   }

   public LuaValue rawget(LuaValue var1) {
      if(var1.isinttype()) {
         int var2 = var1.toint();
         if(var2 > 0 && var2 <= this.array.length) {
            return this.array[var2 - 1] != null?this.array[var2 - 1]:NIL;
         }
      }

      return this.hashget(var1);
   }

   protected LuaValue hashget(LuaValue var1) {
      if(this.hashEntries > 0) {
         LuaValue var2 = this.hashValues[this.hashFindSlot(var1)];
         return var2 != null?var2:NIL;
      } else {
         return NIL;
      }
   }

   public void set(int var1, LuaValue var2) {
      if(this.m_metatable == null || !this.rawget(var1).isnil() || !settable(this, LuaInteger.valueOf(var1), var2)) {
         this.rawset(var1, var2);
      }

   }

   public void set(LuaValue var1, LuaValue var2) {
      var1.checkvalidkey();
      if(this.m_metatable == null || !this.rawget(var1).isnil() || !settable(this, var1, var2)) {
         this.rawset(var1, var2);
      }

   }

   public void rawset(int var1, LuaValue var2) {
      if(!this.arrayset(var1, var2)) {
         this.hashset(LuaInteger.valueOf(var1), var2);
      }

   }

   public void rawset(LuaValue var1, LuaValue var2) {
      if(!var1.isinttype() || !this.arrayset(var1.toint(), var2)) {
         this.hashset(var1, var2);
      }

   }

   private boolean arrayset(int var1, LuaValue var2) {
      if(var1 > 0 && var1 <= this.array.length) {
         this.array[var1 - 1] = var2.isnil()?null:var2;
         return true;
      } else if(var1 == this.array.length + 1 && !var2.isnil()) {
         this.expandarray();
         this.array[var1 - 1] = var2;
         return true;
      } else {
         return false;
      }
   }

   private void expandarray() {
      int var1 = this.array.length;
      int var2 = Math.max(2, var1 * 2);
      this.array = resize(this.array, var2);

      for(int var3 = var1; var3 < var2; ++var3) {
         LuaInteger var4 = LuaInteger.valueOf(var3 + 1);
         LuaValue var5 = this.hashget(var4);
         if(!var5.isnil()) {
            this.hashset(var4, NIL);
            this.array[var3] = var5;
         }
      }

   }

   public LuaValue remove(int var1) {
      int var2 = this.length();
      if(var1 == 0) {
         var1 = var2;
      } else if(var1 > var2) {
         return NONE;
      }

      LuaValue var3 = this.rawget(var1);
      LuaValue var4 = var3;

      while(!var4.isnil()) {
         var4 = this.rawget(var1 + 1);
         this.rawset(var1++, var4);
      }

      return var3.isnil()?NONE:var3;
   }

   public void insert(int var1, LuaValue var2) {
      if(var1 == 0) {
         var1 = this.length() + 1;
      }

      while(!var2.isnil()) {
         LuaValue var3 = this.rawget(var1);
         this.rawset(var1++, var2);
         var2 = var3;
      }

   }

   public LuaValue concat(LuaString var1, int var2, int var3) {
      Buffer var4 = new Buffer();
      if(var2 <= var3) {
         var4.append(this.get(var2).checkstring());

         while(true) {
            ++var2;
            if(var2 > var3) {
               break;
            }

            var4.append(var1);
            var4.append(this.get(var2).checkstring());
         }
      }

      return var4.tostring();
   }

   public LuaValue getn() {
      for(int var1 = this.getArrayLength(); var1 > 0; --var1) {
         if(!this.rawget(var1).isnil()) {
            return LuaInteger.valueOf(var1);
         }
      }

      return ZERO;
   }

   public int length() {
      int var1 = this.getArrayLength();
      int var2 = var1 + 1;

      int var3;
      for(var3 = 0; !this.rawget(var2).isnil(); var2 += var1 + this.getHashLength() + 1) {
         var3 = var2;
      }

      while(var2 > var3 + 1) {
         int var4 = (var2 + var3) / 2;
         if(!this.rawget(var4).isnil()) {
            var3 = var4;
         } else {
            var2 = var4;
         }
      }

      return var3;
   }

   public LuaValue len() {
      return LuaInteger.valueOf(this.length());
   }

   public int maxn() {
      int var1 = 0;

      int var2;
      for(var2 = 0; var2 < this.array.length; ++var2) {
         if(this.array[var2] != null) {
            var1 = var2 + 1;
         }
      }

      for(var2 = 0; var2 < this.hashKeys.length; ++var2) {
         LuaValue var3 = this.hashKeys[var2];
         if(var3 != null && var3.isinttype()) {
            int var4 = var3.toint();
            if(var4 > var1) {
               var1 = var4;
            }
         }
      }

      return var1;
   }

   public Varargs next(LuaValue var1) {
      int var2 = 0;
      if(!var1.isnil()) {
         label48: {
            if(var1.isinttype()) {
               var2 = var1.toint();
               if(var2 > 0 && var2 <= this.array.length) {
                  if(this.array[var2 - 1] == null) {
                     error("invalid key to \'next\'");
                  }
                  break label48;
               }
            }

            if(this.hashKeys.length == 0) {
               error("invalid key to \'next\'");
            }

            var2 = this.hashFindSlot(var1);
            if(this.hashKeys[var2] == null) {
               error("invalid key to \'next\'");
            }

            var2 += 1 + this.array.length;
         }
      }

      while(var2 < this.array.length) {
         if(this.array[var2] != null) {
            return varargsOf(LuaInteger.valueOf(var2 + 1), this.array[var2]);
         }

         ++var2;
      }

      for(var2 -= this.array.length; var2 < this.hashKeys.length; ++var2) {
         if(this.hashKeys[var2] != null) {
            return varargsOf(this.hashKeys[var2], this.hashValues[var2]);
         }
      }

      return NIL;
   }

   public Varargs inext(LuaValue var1) {
      int var2 = var1.checkint() + 1;
      LuaValue var3 = this.rawget(var2);
      return (Varargs)(var3.isnil()?NONE:varargsOf(LuaInteger.valueOf(var2), var3));
   }

   public LuaValue foreach(LuaValue var1) {
      LuaValue var3 = NIL;

      Varargs var2;
      LuaValue var4;
      do {
         if((var3 = (var2 = this.next(var3)).arg1()).isnil()) {
            return NIL;
         }
      } while((var4 = var1.call(var3, var2.arg(2))).isnil());

      return var4;
   }

   public LuaValue foreachi(LuaValue var1) {
      int var4 = 0;

      LuaValue var2;
      LuaValue var3;
      do {
         ++var4;
         if((var2 = this.rawget(var4)).isnil()) {
            return NIL;
         }
      } while((var3 = var1.call(valueOf(var4), var2)).isnil());

      return var3;
   }

   public void hashset(LuaValue var1, LuaValue var2) {
      if(var2.isnil()) {
         this.hashRemove(var1);
      } else {
         if(this.hashKeys.length == 0) {
            this.hashKeys = new LuaValue[2];
            this.hashValues = new LuaValue[2];
         }

         int var3 = this.hashFindSlot(var1);
         if(this.hashFillSlot(var3, var2)) {
            return;
         }

         this.hashKeys[var3] = var1;
         this.hashValues[var3] = var2;
         if(this.checkLoadFactor()) {
            this.rehash();
         }
      }

   }

   public int hashFindSlot(LuaValue var1) {
      int var2;
      LuaValue var3;
      for(var2 = (var1.hashCode() & Integer.MAX_VALUE) % this.hashKeys.length; (var3 = this.hashKeys[var2]) != null && !var3.raweq(var1); var2 = (var2 + 1) % this.hashKeys.length) {
         ;
      }

      return var2;
   }

   private boolean hashFillSlot(int var1, LuaValue var2) {
      this.hashValues[var1] = var2;
      if(this.hashKeys[var1] != null) {
         return true;
      } else {
         ++this.hashEntries;
         return false;
      }
   }

   private void hashRemove(LuaValue var1) {
      if(this.hashKeys.length > 0) {
         int var2 = this.hashFindSlot(var1);
         this.hashClearSlot(var2);
      }

   }

   protected void hashClearSlot(int var1) {
      if(this.hashKeys[var1] != null) {
         int var2 = var1;
         int var3 = this.hashKeys.length;

         while(this.hashKeys[var2 = (var2 + 1) % var3] != null) {
            int var4 = (this.hashKeys[var2].hashCode() & Integer.MAX_VALUE) % var3;
            if(var2 > var1 && (var4 <= var1 || var4 > var2) || var2 < var1 && var4 <= var1 && var4 > var2) {
               this.hashKeys[var1] = this.hashKeys[var2];
               this.hashValues[var1] = this.hashValues[var2];
               var1 = var2;
            }
         }

         --this.hashEntries;
         this.hashKeys[var1] = null;
         this.hashValues[var1] = null;
         if(this.hashEntries == 0) {
            this.hashKeys = NOVALS;
            this.hashValues = NOVALS;
         }
      }

   }

   private boolean checkLoadFactor() {
      int var1 = this.hashKeys.length;
      return this.hashEntries >= var1 - (var1 >> 3);
   }

   private void rehash() {
      int var1 = this.hashKeys.length;
      int var2 = var1 + (var1 >> 2) + 2;
      LuaValue[] var3 = this.hashKeys;
      LuaValue[] var4 = this.hashValues;
      this.hashKeys = new LuaValue[var2];
      this.hashValues = new LuaValue[var2];

      for(int var5 = 0; var5 < var1; ++var5) {
         LuaValue var6 = var3[var5];
         if(var6 != null) {
            LuaValue var7 = var4[var5];
            int var8 = this.hashFindSlot(var6);
            this.hashKeys[var8] = var6;
            this.hashValues[var8] = var7;
         }
      }

   }

   public void sort(LuaValue var1) {
      int var2;
      for(var2 = this.array.length; var2 > 0 && this.array[var2 - 1] == null; --var2) {
         ;
      }

      if(var2 > 1) {
         this.heapSort(var2, var1);
      }

   }

   private void heapSort(int var1, LuaValue var2) {
      this.heapify(var1, var2);
      int var3 = var1 - 1;

      while(var3 > 0) {
         this.swap(var3, 0);
         --var3;
         this.siftDown(0, var3, var2);
      }

   }

   private void heapify(int var1, LuaValue var2) {
      for(int var3 = var1 / 2 - 1; var3 >= 0; --var3) {
         this.siftDown(var3, var1 - 1, var2);
      }

   }

   private void siftDown(int var1, int var2, LuaValue var3) {
      int var5;
      for(int var4 = var1; var4 * 2 + 1 <= var2; var4 = var5) {
         var5 = var4 * 2 + 1;
         if(var5 < var2 && this.compare(var5, var5 + 1, var3)) {
            ++var5;
         }

         if(!this.compare(var4, var5, var3)) {
            return;
         }

         this.swap(var4, var5);
      }

   }

   private boolean compare(int var1, int var2, LuaValue var3) {
      LuaValue var4 = this.array[var1];
      LuaValue var5 = this.array[var2];
      return var4 != null && var5 != null?(!var3.isnil()?var3.call(var4, var5).toboolean():var4.lt_b(var5)):false;
   }

   private void swap(int var1, int var2) {
      LuaValue var3 = this.array[var1];
      this.array[var1] = this.array[var2];
      this.array[var2] = var3;
   }

   public int keyCount() {
      LuaValue var1 = LuaValue.NIL;
      int var2 = 0;

      while(true) {
         Varargs var3 = this.next(var1);
         if((var1 = var3.arg1()).isnil()) {
            return var2;
         }

         ++var2;
      }
   }

   public LuaValue[] keys() {
      Vector var1 = new Vector();
      LuaValue var2 = LuaValue.NIL;

      while(true) {
         Varargs var3 = this.next(var2);
         if((var2 = var3.arg1()).isnil()) {
            LuaValue[] var4 = new LuaValue[var1.size()];
            var1.copyInto(var4);
            return var4;
         }

         var1.addElement(var2);
      }
   }

   public LuaValue eq(LuaValue var1) {
      return this.eq_b(var1)?TRUE:FALSE;
   }

   public boolean eq_b(LuaValue var1) {
      if(this == var1) {
         return true;
      } else if(this.m_metatable != null && var1.istable()) {
         LuaValue var2 = var1.getmetatable();
         return var2 != null && LuaValue.eqmtcall(this, this.m_metatable, var1, var2);
      } else {
         return false;
      }
   }

}
