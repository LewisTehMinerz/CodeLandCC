package org.luaj.vm2;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.MathLib;

public class LuaDouble extends LuaNumber {

   public static final LuaDouble NAN = new LuaDouble(Double.NaN);
   public static final LuaDouble POSINF = new LuaDouble(Double.POSITIVE_INFINITY);
   public static final LuaDouble NEGINF = new LuaDouble(Double.NEGATIVE_INFINITY);
   public static final String JSTR_NAN = "nan";
   public static final String JSTR_POSINF = "inf";
   public static final String JSTR_NEGINF = "-inf";
   final double v;


   public static LuaNumber valueOf(double var0) {
      int var2 = (int)var0;
      return (LuaNumber)(var0 == (double)var2?LuaInteger.valueOf(var2):new LuaDouble(var0));
   }

   private LuaDouble(double var1) {
      this.v = var1;
   }

   public int hashCode() {
      long var1 = Double.doubleToLongBits(this.v);
      return (int)(var1 >> 32) | (int)var1;
   }

   public boolean islong() {
      return this.v == (double)((long)this.v);
   }

   public byte tobyte() {
      return (byte)((int)((long)this.v));
   }

   public char tochar() {
      return (char)((int)((long)this.v));
   }

   public double todouble() {
      return this.v;
   }

   public float tofloat() {
      return (float)this.v;
   }

   public int toint() {
      return (int)((long)this.v);
   }

   public long tolong() {
      return (long)this.v;
   }

   public short toshort() {
      return (short)((int)((long)this.v));
   }

   public double optdouble(double var1) {
      return this.v;
   }

   public int optint(int var1) {
      return (int)((long)this.v);
   }

   public LuaInteger optinteger(LuaInteger var1) {
      return LuaInteger.valueOf((int)((long)this.v));
   }

   public long optlong(long var1) {
      return (long)this.v;
   }

   public LuaInteger checkinteger() {
      return LuaInteger.valueOf((int)((long)this.v));
   }

   public LuaValue neg() {
      return valueOf(-this.v);
   }

   public boolean equals(Object var1) {
      return var1 instanceof LuaDouble?((LuaDouble)var1).v == this.v:false;
   }

   public LuaValue eq(LuaValue var1) {
      return var1.raweq(this.v)?TRUE:FALSE;
   }

   public boolean eq_b(LuaValue var1) {
      return var1.raweq(this.v);
   }

   public boolean raweq(LuaValue var1) {
      return var1.raweq(this.v);
   }

   public boolean raweq(double var1) {
      return this.v == var1;
   }

   public boolean raweq(int var1) {
      return this.v == (double)var1;
   }

   public LuaValue add(LuaValue var1) {
      return var1.add(this.v);
   }

   public LuaValue add(double var1) {
      return valueOf(var1 + this.v);
   }

   public LuaValue sub(LuaValue var1) {
      return var1.subFrom(this.v);
   }

   public LuaValue sub(double var1) {
      return valueOf(this.v - var1);
   }

   public LuaValue sub(int var1) {
      return valueOf(this.v - (double)var1);
   }

   public LuaValue subFrom(double var1) {
      return valueOf(var1 - this.v);
   }

   public LuaValue mul(LuaValue var1) {
      return var1.mul(this.v);
   }

   public LuaValue mul(double var1) {
      return valueOf(var1 * this.v);
   }

   public LuaValue mul(int var1) {
      return valueOf((double)var1 * this.v);
   }

   public LuaValue pow(LuaValue var1) {
      return var1.powWith(this.v);
   }

   public LuaValue pow(double var1) {
      return MathLib.dpow(this.v, var1);
   }

   public LuaValue pow(int var1) {
      return MathLib.dpow(this.v, (double)var1);
   }

   public LuaValue powWith(double var1) {
      return MathLib.dpow(var1, this.v);
   }

   public LuaValue powWith(int var1) {
      return MathLib.dpow((double)var1, this.v);
   }

   public LuaValue div(LuaValue var1) {
      return var1.divInto(this.v);
   }

   public LuaValue div(double var1) {
      return ddiv(this.v, var1);
   }

   public LuaValue div(int var1) {
      return ddiv(this.v, (double)var1);
   }

   public LuaValue divInto(double var1) {
      return ddiv(var1, this.v);
   }

   public LuaValue mod(LuaValue var1) {
      return var1.modFrom(this.v);
   }

   public LuaValue mod(double var1) {
      return dmod(this.v, var1);
   }

   public LuaValue mod(int var1) {
      return dmod(this.v, (double)var1);
   }

   public LuaValue modFrom(double var1) {
      return dmod(var1, this.v);
   }

   public static LuaValue ddiv(double var0, double var2) {
      return (LuaValue)(var2 != 0.0D?valueOf(var0 / var2):(var0 > 0.0D?POSINF:(var0 == 0.0D?NAN:NEGINF)));
   }

   public static double ddiv_d(double var0, double var2) {
      return var2 != 0.0D?var0 / var2:(var0 > 0.0D?Double.POSITIVE_INFINITY:(var0 == 0.0D?Double.NaN:Double.NEGATIVE_INFINITY));
   }

   public static LuaValue dmod(double var0, double var2) {
      return (LuaValue)(var2 != 0.0D?valueOf(var0 - var2 * Math.floor(var0 / var2)):NAN);
   }

   public static double dmod_d(double var0, double var2) {
      return var2 != 0.0D?var0 - var2 * Math.floor(var0 / var2):Double.NaN;
   }

   public LuaValue lt(LuaValue var1) {
      return var1.gt_b(this.v)?LuaValue.TRUE:FALSE;
   }

   public LuaValue lt(double var1) {
      return this.v < var1?TRUE:FALSE;
   }

   public LuaValue lt(int var1) {
      return this.v < (double)var1?TRUE:FALSE;
   }

   public boolean lt_b(LuaValue var1) {
      return var1.gt_b(this.v);
   }

   public boolean lt_b(int var1) {
      return this.v < (double)var1;
   }

   public boolean lt_b(double var1) {
      return this.v < var1;
   }

   public LuaValue lteq(LuaValue var1) {
      return var1.gteq_b(this.v)?LuaValue.TRUE:FALSE;
   }

   public LuaValue lteq(double var1) {
      return this.v <= var1?TRUE:FALSE;
   }

   public LuaValue lteq(int var1) {
      return this.v <= (double)var1?TRUE:FALSE;
   }

   public boolean lteq_b(LuaValue var1) {
      return var1.gteq_b(this.v);
   }

   public boolean lteq_b(int var1) {
      return this.v <= (double)var1;
   }

   public boolean lteq_b(double var1) {
      return this.v <= var1;
   }

   public LuaValue gt(LuaValue var1) {
      return var1.lt_b(this.v)?LuaValue.TRUE:FALSE;
   }

   public LuaValue gt(double var1) {
      return this.v > var1?TRUE:FALSE;
   }

   public LuaValue gt(int var1) {
      return this.v > (double)var1?TRUE:FALSE;
   }

   public boolean gt_b(LuaValue var1) {
      return var1.lt_b(this.v);
   }

   public boolean gt_b(int var1) {
      return this.v > (double)var1;
   }

   public boolean gt_b(double var1) {
      return this.v > var1;
   }

   public LuaValue gteq(LuaValue var1) {
      return var1.lteq_b(this.v)?LuaValue.TRUE:FALSE;
   }

   public LuaValue gteq(double var1) {
      return this.v >= var1?TRUE:FALSE;
   }

   public LuaValue gteq(int var1) {
      return this.v >= (double)var1?TRUE:FALSE;
   }

   public boolean gteq_b(LuaValue var1) {
      return var1.lteq_b(this.v);
   }

   public boolean gteq_b(int var1) {
      return this.v >= (double)var1;
   }

   public boolean gteq_b(double var1) {
      return this.v >= var1;
   }

   public int strcmp(LuaString var1) {
      this.typerror("attempt to compare number with string");
      return 0;
   }

   public String tojstring() {
      long var1 = (long)this.v;
      return (double)var1 == this.v?Long.toString(var1):(Double.isNaN(this.v)?"nan":(Double.isInfinite(this.v)?(this.v < 0.0D?"-inf":"inf"):Float.toString((float)this.v)));
   }

   public LuaString strvalue() {
      return LuaString.valueOf(this.tojstring());
   }

   public LuaString optstring(LuaString var1) {
      return LuaString.valueOf(this.tojstring());
   }

   public LuaValue tostring() {
      return LuaString.valueOf(this.tojstring());
   }

   public String optjstring(String var1) {
      return this.tojstring();
   }

   public LuaNumber optnumber(LuaNumber var1) {
      return this;
   }

   public boolean isnumber() {
      return true;
   }

   public boolean isstring() {
      return true;
   }

   public LuaValue tonumber() {
      return this;
   }

   public int checkint() {
      return (int)((long)this.v);
   }

   public long checklong() {
      return (long)this.v;
   }

   public LuaNumber checknumber() {
      return this;
   }

   public double checkdouble() {
      return this.v;
   }

   public String checkjstring() {
      return this.tojstring();
   }

   public LuaString checkstring() {
      return LuaString.valueOf(this.tojstring());
   }

   public LuaValue checkvalidkey() {
      if(Double.isNaN(this.v)) {
         throw new LuaError("table index expected, got nan");
      } else {
         return this;
      }
   }

}
