package org.luaj.vm2;

import org.luaj.vm2.LuaValue;

public final class UpValue {

   LuaValue[] array;
   int index;


   public UpValue(LuaValue[] var1, int var2) {
      this.array = var1;
      this.index = var2;
   }

   public String tojstring() {
      return this.array[this.index].tojstring();
   }

   public final LuaValue getValue() {
      return this.array[this.index];
   }

   public final void setValue(LuaValue var1) {
      this.array[this.index] = var1;
   }

   public final void close() {
      this.array = new LuaValue[]{this.array[this.index]};
      this.index = 0;
   }
}
