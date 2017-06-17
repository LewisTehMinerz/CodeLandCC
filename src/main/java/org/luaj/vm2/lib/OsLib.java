package org.luaj.vm2.lib;

import java.io.IOException;
import java.util.Date;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.VarArgFunction;

public class OsLib extends VarArgFunction {

   public static String TMP_PREFIX = ".luaj";
   public static String TMP_SUFFIX = "tmp";
   private static final int INIT = 0;
   private static final int CLOCK = 1;
   private static final int DATE = 2;
   private static final int DIFFTIME = 3;
   private static final int EXECUTE = 4;
   private static final int EXIT = 5;
   private static final int GETENV = 6;
   private static final int REMOVE = 7;
   private static final int RENAME = 8;
   private static final int SETLOCALE = 9;
   private static final int TIME = 10;
   private static final int TMPNAME = 11;
   private static final String[] NAMES = new String[]{"clock", "date", "difftime", "execute", "exit", "getenv", "remove", "rename", "setlocale", "time", "tmpname"};
   private static final long t0 = System.currentTimeMillis();
   private static long tmpnames = t0;
   static Class class$org$luaj$vm2$lib$OsLib;


   public LuaValue init() {
      LuaTable var1 = new LuaTable();
      this.bind(var1, this.getClass(), NAMES, 1);
      this.env.set("os", (LuaValue)var1);
      PackageLib.instance.LOADED.set("os", var1);
      return var1;
   }

   public Varargs invoke(Varargs var1) {
      try {
         String var2;
         switch(this.opcode) {
         case 0:
            return this.init();
         case 1:
            return valueOf(this.clock());
         case 2:
            var2 = var1.optjstring(1, (String)null);
            double var3 = var1.optdouble(2, -1.0D);
            return valueOf(this.date(var2, var3 == -1.0D?(double)System.currentTimeMillis() / 1000.0D:var3));
         case 3:
            return valueOf(this.difftime(var1.checkdouble(1), var1.checkdouble(2)));
         case 4:
            return valueOf(this.execute(var1.optjstring(1, (String)null)));
         case 5:
            this.exit(var1.optint(1, 0));
            return NONE;
         case 6:
            var2 = this.getenv(var1.checkjstring(1));
            return (Varargs)(var2 != null?valueOf(var2):NIL);
         case 7:
            this.remove(var1.checkjstring(1));
            return LuaValue.TRUE;
         case 8:
            this.rename(var1.checkjstring(1), var1.checkjstring(2));
            return LuaValue.TRUE;
         case 9:
            var2 = this.setlocale(var1.optjstring(1, (String)null), var1.optjstring(2, "all"));
            return (Varargs)(var2 != null?valueOf(var2):NIL);
         case 10:
            return valueOf((double)this.time(var1.arg1().isnil()?null:var1.checktable(1)));
         case 11:
            return valueOf(this.tmpname());
         default:
            return NONE;
         }
      } catch (IOException var5) {
         return varargsOf(NIL, valueOf(var5.getMessage()));
      }
   }

   protected double clock() {
      return (double)(System.currentTimeMillis() - t0) / 1000.0D;
   }

   protected double difftime(double var1, double var3) {
      return var1 - var3;
   }

   protected String date(String var1, double var2) {
      return (new Date((long)(var2 * 1000.0D))).toString();
   }

   protected int execute(String var1) {
      return 0;
   }

   protected void exit(int var1) {}

   protected String getenv(String var1) {
      return System.getProperty(var1);
   }

   protected void remove(String var1) throws IOException {
      throw new IOException("not implemented");
   }

   protected void rename(String var1, String var2) throws IOException {
      throw new IOException("not implemented");
   }

   protected String setlocale(String var1, String var2) {
      return "C";
   }

   protected long time(LuaTable var1) {
      return System.currentTimeMillis();
   }

   protected String tmpname() {
      synchronized(class$org$luaj$vm2$lib$OsLib == null?(class$org$luaj$vm2$lib$OsLib = class$("org.luaj.vm2.lib.OsLib")):class$org$luaj$vm2$lib$OsLib) {
         return TMP_PREFIX + tmpnames++ + TMP_SUFFIX;
      }
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

}
