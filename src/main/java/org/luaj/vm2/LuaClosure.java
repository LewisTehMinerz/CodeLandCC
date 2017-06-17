package org.luaj.vm2;

import org.luaj.vm2.Buffer;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.TailcallVarargs;
import org.luaj.vm2.UpValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.DebugLib;

public class LuaClosure extends LuaFunction {

   private static final UpValue[] NOUPVALUES = new UpValue[0];
   public final Prototype p;
   public final UpValue[] upValues;


   LuaClosure() {
      this.p = null;
      this.upValues = null;
   }

   public LuaClosure(Prototype var1, LuaValue var2) {
      super(var2);
      this.p = var1;
      this.upValues = var1.nups > 0?new UpValue[var1.nups]:NOUPVALUES;
   }

   protected LuaClosure(int var1, LuaValue var2) {
      super(var2);
      this.p = null;
      this.upValues = var1 > 0?new UpValue[var1]:NOUPVALUES;
   }

   public boolean isclosure() {
      return true;
   }

   public LuaClosure optclosure(LuaClosure var1) {
      return this;
   }

   public LuaClosure checkclosure() {
      return this;
   }

   public LuaValue getmetatable() {
      return s_metatable;
   }

   public final LuaValue call() {
      LuaValue[] var1 = new LuaValue[this.p.maxstacksize];
      System.arraycopy(NILS, 0, var1, 0, this.p.maxstacksize);
      return this.execute(var1, NONE).arg1();
   }

   public final LuaValue call(LuaValue var1) {
      LuaValue[] var2 = new LuaValue[this.p.maxstacksize];
      System.arraycopy(NILS, 0, var2, 0, this.p.maxstacksize);
      switch(this.p.numparams) {
      case 0:
         return this.execute(var2, var1).arg1();
      default:
         var2[0] = var1;
         return this.execute(var2, NONE).arg1();
      }
   }

   public final LuaValue call(LuaValue var1, LuaValue var2) {
      LuaValue[] var3 = new LuaValue[this.p.maxstacksize];
      System.arraycopy(NILS, 0, var3, 0, this.p.maxstacksize);
      switch(this.p.numparams) {
      case 0:
         return this.execute(var3, (Varargs)(this.p.is_vararg != 0?varargsOf(var1, var2):NONE)).arg1();
      case 1:
         var3[0] = var1;
         return this.execute(var3, var2).arg1();
      default:
         var3[0] = var1;
         var3[1] = var2;
         return this.execute(var3, NONE).arg1();
      }
   }

   public final LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3) {
      LuaValue[] var4 = new LuaValue[this.p.maxstacksize];
      System.arraycopy(NILS, 0, var4, 0, this.p.maxstacksize);
      switch(this.p.numparams) {
      case 0:
         return this.execute(var4, (Varargs)(this.p.is_vararg != 0?varargsOf(var1, var2, var3):NONE)).arg1();
      case 1:
         var4[0] = var1;
         return this.execute(var4, (Varargs)(this.p.is_vararg != 0?varargsOf(var2, var3):NONE)).arg1();
      case 2:
         var4[0] = var1;
         var4[1] = var2;
         return this.execute(var4, var3).arg1();
      default:
         var4[0] = var1;
         var4[1] = var2;
         var4[2] = var3;
         return this.execute(var4, NONE).arg1();
      }
   }

   public final Varargs invoke(Varargs var1) {
      return this.onInvoke(var1).eval();
   }

   public Varargs onInvoke(Varargs var1) {
      LuaValue[] var2 = new LuaValue[this.p.maxstacksize];
      System.arraycopy(NILS, 0, var2, 0, this.p.maxstacksize);

      for(int var3 = 0; var3 < this.p.numparams; ++var3) {
         var2[var3] = var1.arg(var3 + 1);
      }

      return this.execute(var2, (Varargs)(this.p.is_vararg != 0?var1.subargs(this.p.numparams + 1):NONE));
   }

   protected Varargs execute(LuaValue[] var1, Varargs var2) {
      int var7 = 0;
      int var8 = 0;
      Object var10 = NONE;
      int[] var11 = this.p.code;
      LuaValue[] var12 = this.p.k;
      UpValue[] var13 = this.p.p.length > 0?new UpValue[var1.length]:null;
      if(this.p.is_vararg >= 4) {
         var1[this.p.numparams] = new LuaTable(var2);
      }

      if(DebugLib.DEBUG_ENABLED) {
         DebugLib.debugSetupCall(var2, var1);
      }

      LuaThread.CallStack var14 = LuaThread.onCall(this);

      label1136:
      while(true) {
         boolean var24 = false;

         int var31;
         TailcallVarargs var36;
         label1140: {
            label1141: {
               LuaValue var32;
               label1142: {
                  label1143: {
                     Varargs var35;
                     label1144: {
                        label1145: {
                           label1146: {
                              label1147: {
                                 try {
                                    var24 = true;
                                    if(DebugLib.DEBUG_ENABLED) {
                                       DebugLib.debugBytecode(var7, (Varargs)var10, var8);
                                    }

                                    int var3 = var11[var7++];
                                    int var4 = var3 >> 6 & 255;
                                    int var5;
                                    int var6;
                                    LuaValue var9;
                                    int var15;
                                    int var17;
                                    Varargs var28;
                                    label1110:
                                    switch(var3 & 63) {
                                    case 0:
                                       var1[var4] = var1[var3 >>> 23];
                                       continue;
                                    case 1:
                                       var1[var4] = var12[var3 >>> 14];
                                       continue;
                                    case 2:
                                       var1[var4] = var3 >>> 23 != 0?LuaValue.TRUE:LuaValue.FALSE;
                                       if((var3 & 8372224) != 0) {
                                          ++var7;
                                       }
                                       continue;
                                    case 3:
                                       var5 = var3 >>> 23;

                                       while(true) {
                                          if(var4 > var5) {
                                             continue label1136;
                                          }

                                          var1[var4++] = LuaValue.NIL;
                                       }
                                    case 4:
                                       var1[var4] = this.upValues[var3 >>> 23].getValue();
                                       continue;
                                    case 5:
                                       var1[var4] = this.env.get(var12[var3 >>> 14]);
                                       continue;
                                    case 6:
                                       var1[var4] = var1[var3 >>> 23].get((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 7:
                                       this.env.set(var12[var3 >>> 14], var1[var4]);
                                       continue;
                                    case 8:
                                       this.upValues[var3 >>> 23].setValue(var1[var4]);
                                       continue;
                                    case 9:
                                       var1[var4].set((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5], (var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 10:
                                       var1[var4] = new LuaTable(var3 >>> 23, var3 >> 14 & 511);
                                       continue;
                                    case 11:
                                       var1[var4 + 1] = var9 = var1[var3 >>> 23];
                                       var1[var4] = var9.get((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 12:
                                       var1[var4] = ((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).add((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 13:
                                       var1[var4] = ((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).sub((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 14:
                                       var1[var4] = ((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).mul((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 15:
                                       var1[var4] = ((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).div((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 16:
                                       var1[var4] = ((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).mod((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 17:
                                       var1[var4] = ((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).pow((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]);
                                       continue;
                                    case 18:
                                       var1[var4] = var1[var3 >>> 23].neg();
                                       continue;
                                    case 19:
                                       var1[var4] = var1[var3 >>> 23].not();
                                       continue;
                                    case 20:
                                       var1[var4] = var1[var3 >>> 23].len();
                                       continue;
                                    case 21:
                                       var5 = var3 >>> 23;
                                       var6 = var3 >> 14 & 511;
                                       if(var6 <= var5 + 1) {
                                          var1[var4] = var1[var6 - 1].concat(var1[var6]);
                                          continue;
                                       }

                                       Buffer var39 = var1[var6].buffer();

                                       while(true) {
                                          --var6;
                                          if(var6 < var5) {
                                             var1[var4] = var39.value();
                                             continue label1136;
                                          }

                                          var39 = var1[var6].concat(var39);
                                       }
                                    case 22:
                                       var7 += (var3 >>> 14) - 131071;
                                       continue;
                                    case 23:
                                       if(((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).eq_b((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]) != (var4 != 0)) {
                                          ++var7;
                                       }
                                       continue;
                                    case 24:
                                       if(((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).lt_b((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]) != (var4 != 0)) {
                                          ++var7;
                                       }
                                       continue;
                                    case 25:
                                       if(((var5 = var3 >>> 23) > 255?var12[var5 & 255]:var1[var5]).lteq_b((var6 = var3 >> 14 & 511) > 255?var12[var6 & 255]:var1[var6]) != (var4 != 0)) {
                                          ++var7;
                                       }
                                       continue;
                                    case 26:
                                       if(var1[var4].toboolean() != ((var3 & 8372224) != 0)) {
                                          ++var7;
                                       }
                                       continue;
                                    case 27:
                                       if((var9 = var1[var3 >>> 23]).toboolean() != ((var3 & 8372224) != 0)) {
                                          ++var7;
                                       } else {
                                          var1[var4] = var9;
                                       }
                                       continue;
                                    case 28:
                                       switch(var3 & -16384) {
                                       case 8388608:
                                          var10 = var1[var4].invoke((Varargs)NONE);
                                          var8 = var4 + ((Varargs)var10).narg();
                                          continue;
                                       case 8404992:
                                          var1[var4].call();
                                          continue;
                                       case 8421376:
                                          var1[var4] = var1[var4].call();
                                          continue;
                                       case 16777216:
                                          var10 = var1[var4].invoke((Varargs)var1[var4 + 1]);
                                          var8 = var4 + ((Varargs)var10).narg();
                                          continue;
                                       case 16793600:
                                          var1[var4].call(var1[var4 + 1]);
                                          continue;
                                       case 16809984:
                                          var1[var4] = var1[var4].call(var1[var4 + 1]);
                                          continue;
                                       case 25182208:
                                          var1[var4].call(var1[var4 + 1], var1[var4 + 2]);
                                          continue;
                                       case 25198592:
                                          var1[var4] = var1[var4].call(var1[var4 + 1], var1[var4 + 2]);
                                          continue;
                                       case 33570816:
                                          var1[var4].call(var1[var4 + 1], var1[var4 + 2], var1[var4 + 3]);
                                          continue;
                                       case 33587200:
                                          var1[var4] = var1[var4].call(var1[var4 + 1], var1[var4 + 2], var1[var4 + 3]);
                                          continue;
                                       default:
                                          var5 = var3 >>> 23;
                                          var6 = var3 >> 14 & 511;
                                          var28 = var5 > 0?varargsOf(var1, var4 + 1, var5 - 1):varargsOf(var1, var4 + 1, var8 - ((Varargs)var10).narg() - (var4 + 1), (Varargs)var10);
                                          var10 = var1[var4].invoke(var28);
                                          if(var6 <= 0) {
                                             var8 = var4 + ((Varargs)var10).narg();
                                             continue;
                                          }
                                       }

                                       while(true) {
                                          --var6;
                                          if(var6 <= 0) {
                                             var10 = NONE;
                                             continue label1136;
                                          }

                                          var1[var4 + var6 - 1] = ((Varargs)var10).arg(var6);
                                       }
                                    case 29:
                                       switch(var3 & -8388608) {
                                       case 8388608:
                                          var36 = new TailcallVarargs(var1[var4], NONE);
                                          var24 = false;
                                          break label1140;
                                       case 16777216:
                                          var36 = new TailcallVarargs(var1[var4], var1[var4 + 1]);
                                          var24 = false;
                                          break label1110;
                                       case 25165824:
                                          var36 = new TailcallVarargs(var1[var4], varargsOf(var1[var4 + 1], var1[var4 + 2]));
                                          var24 = false;
                                          break label1147;
                                       case 33554432:
                                          var36 = new TailcallVarargs(var1[var4], varargsOf(var1[var4 + 1], var1[var4 + 2], var1[var4 + 3]));
                                          var24 = false;
                                          break label1146;
                                       default:
                                          var5 = var3 >>> 23;
                                          var28 = var5 > 0?varargsOf(var1, var4 + 1, var5 - 1):varargsOf(var1, var4 + 1, var8 - ((Varargs)var10).narg() - (var4 + 1), (Varargs)var10);
                                          var36 = new TailcallVarargs(var1[var4], var28);
                                          var24 = false;
                                          break label1141;
                                       }
                                    case 30:
                                       var5 = var3 >>> 23;
                                       switch(var5) {
                                       case 0:
                                          var35 = varargsOf(var1, var4, var8 - ((Varargs)var10).narg() - var4, (Varargs)var10);
                                          var24 = false;
                                          break label1144;
                                       case 1:
                                          var32 = NONE;
                                          var24 = false;
                                          break label1143;
                                       case 2:
                                          var32 = var1[var4];
                                          var24 = false;
                                          break label1142;
                                       default:
                                          var35 = varargsOf(var1, var4, var5 - 1);
                                          var24 = false;
                                          break label1145;
                                       }
                                    case 31:
                                       var32 = var1[var4 + 1];
                                       LuaValue var34 = var1[var4 + 2];
                                       LuaValue var38 = var34.add(var1[var4]);
                                       if(var34.gt_b(0)) {
                                          if(!var38.lteq_b(var32)) {
                                             continue;
                                          }
                                       } else if(!var38.gteq_b(var32)) {
                                          continue;
                                       }

                                       var1[var4] = var38;
                                       var1[var4 + 3] = var38;
                                       var7 += (var3 >>> 14) - 131071;
                                       continue;
                                    case 32:
                                       LuaNumber var30 = var1[var4].checknumber("\'for\' initial value must be a number");
                                       LuaNumber var33 = var1[var4 + 1].checknumber("\'for\' limit must be a number");
                                       LuaNumber var37 = var1[var4 + 2].checknumber("\'for\' step must be a number");
                                       var1[var4] = var30.sub(var37);
                                       var1[var4 + 1] = var33;
                                       var1[var4 + 2] = var37;
                                       var7 += (var3 >>> 14) - 131071;
                                       continue;
                                    case 33:
                                       var10 = var1[var4].invoke(varargsOf(var1[var4 + 1], var1[var4 + 2]));
                                       if((var9 = ((Varargs)var10).arg1()).isnil()) {
                                          ++var7;
                                          continue;
                                       }

                                       var1[var4 + 2] = var1[var4 + 3] = var9;

                                       for(var6 = var3 >> 14 & 511; var6 > 1; --var6) {
                                          var1[var4 + 2 + var6] = ((Varargs)var10).arg(var6);
                                       }

                                       var10 = NONE;
                                       continue;
                                    case 34:
                                       if((var6 = var3 >> 14 & 511) == 0) {
                                          var6 = var11[var7++];
                                       }

                                       var15 = (var6 - 1) * 50;
                                       var9 = var1[var4];
                                       if((var5 = var3 >>> 23) != 0) {
                                          var9.presize(var15 + var5);
                                          var31 = 1;

                                          while(true) {
                                             if(var31 > var5) {
                                                continue label1136;
                                             }

                                             var9.set(var15 + var31, var1[var4 + var31]);
                                             ++var31;
                                          }
                                       }

                                       var5 = var8 - var4 - 1;
                                       var31 = var5 - ((Varargs)var10).narg();

                                       for(var17 = 1; var17 <= var31; ++var17) {
                                          var9.set(var15 + var17, var1[var4 + var17]);
                                       }

                                       while(true) {
                                          if(var17 > var5) {
                                             continue label1136;
                                          }

                                          var9.set(var15 + var17, ((Varargs)var10).arg(var17 - var31));
                                          ++var17;
                                       }
                                    case 35:
                                       var5 = var13.length;

                                       while(true) {
                                          --var5;
                                          if(var5 < var4) {
                                             continue label1136;
                                          }

                                          if(var13[var5] != null) {
                                             var13[var5].close();
                                             var13[var5] = null;
                                          }
                                       }
                                    case 36:
                                       Prototype var29 = this.p.p[var3 >>> 14];
                                       LuaClosure var16 = new LuaClosure(var29, this.env);
                                       var17 = 0;

                                       for(int var18 = var29.nups; var17 < var18; ++var17) {
                                          var3 = var11[var7++];
                                          var5 = var3 >>> 23;
                                          var16.upValues[var17] = (var3 & 4) != 0?this.upValues[var5]:(var13[var5] != null?var13[var5]:(var13[var5] = new UpValue(var1, var5)));
                                       }

                                       var1[var4] = var16;
                                       continue;
                                    case 37:
                                       var5 = var3 >>> 23;
                                       if(var5 != 0) {
                                          var15 = 1;

                                          while(true) {
                                             if(var15 >= var5) {
                                                continue label1136;
                                             }

                                             var1[var4 + var15 - 1] = var2.arg(var15);
                                             ++var15;
                                          }
                                       }

                                       var8 = var4 + var2.narg();
                                       var10 = var2;
                                    default:
                                       continue;
                                    }
                                 } catch (LuaError var25) {
                                    throw var25;
                                 } catch (Exception var26) {
                                    throw new LuaError(var26);
                                 } finally {
                                    if(var24) {
                                       var14.onReturn();
                                       if(var13 != null) {
                                          int var20 = var13.length;

                                          while(true) {
                                             --var20;
                                             if(var20 < 0) {
                                                break;
                                             }

                                             if(var13[var20] != null) {
                                                var13[var20].close();
                                             }
                                          }
                                       }

                                    }
                                 }

                                 var14.onReturn();
                                 if(var13 != null) {
                                    var31 = var13.length;

                                    while(true) {
                                       --var31;
                                       if(var31 < 0) {
                                          break;
                                       }

                                       if(var13[var31] != null) {
                                          var13[var31].close();
                                       }
                                    }
                                 }

                                 return var36;
                              }

                              var14.onReturn();
                              if(var13 != null) {
                                 var31 = var13.length;

                                 while(true) {
                                    --var31;
                                    if(var31 < 0) {
                                       break;
                                    }

                                    if(var13[var31] != null) {
                                       var13[var31].close();
                                    }
                                 }
                              }

                              return var36;
                           }

                           var14.onReturn();
                           if(var13 != null) {
                              var31 = var13.length;

                              while(true) {
                                 --var31;
                                 if(var31 < 0) {
                                    break;
                                 }

                                 if(var13[var31] != null) {
                                    var13[var31].close();
                                 }
                              }
                           }

                           return var36;
                        }

                        var14.onReturn();
                        if(var13 != null) {
                           var31 = var13.length;

                           while(true) {
                              --var31;
                              if(var31 < 0) {
                                 break;
                              }

                              if(var13[var31] != null) {
                                 var13[var31].close();
                              }
                           }
                        }

                        return var35;
                     }

                     var14.onReturn();
                     if(var13 != null) {
                        var31 = var13.length;

                        while(true) {
                           --var31;
                           if(var31 < 0) {
                              break;
                           }

                           if(var13[var31] != null) {
                              var13[var31].close();
                           }
                        }
                     }

                     return var35;
                  }

                  var14.onReturn();
                  if(var13 != null) {
                     var31 = var13.length;

                     while(true) {
                        --var31;
                        if(var31 < 0) {
                           break;
                        }

                        if(var13[var31] != null) {
                           var13[var31].close();
                        }
                     }
                  }

                  return var32;
               }

               var14.onReturn();
               if(var13 != null) {
                  var31 = var13.length;

                  while(true) {
                     --var31;
                     if(var31 < 0) {
                        break;
                     }

                     if(var13[var31] != null) {
                        var13[var31].close();
                     }
                  }
               }

               return var32;
            }

            var14.onReturn();
            if(var13 != null) {
               var31 = var13.length;

               while(true) {
                  --var31;
                  if(var31 < 0) {
                     break;
                  }

                  if(var13[var31] != null) {
                     var13[var31].close();
                  }
               }
            }

            return var36;
         }

         var14.onReturn();
         if(var13 != null) {
            var31 = var13.length;

            while(true) {
               --var31;
               if(var31 < 0) {
                  break;
               }

               if(var13[var31] != null) {
                  var13[var31].close();
               }
            }
         }

         return var36;
      }
   }

   protected LuaValue getUpvalue(int var1) {
      return this.upValues[var1].getValue();
   }

   protected void setUpvalue(int var1, LuaValue var2) {
      this.upValues[var1].setValue(var2);
   }

}
