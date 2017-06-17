package org.luaj.vm2.lib.jse;

import java.io.File;
import java.io.IOException;
import org.luaj.vm2.lib.OsLib;

public class JseOsLib extends OsLib {

   public static int EXEC_IOEXCEPTION = 1;
   public static int EXEC_INTERRUPTED = -2;
   public static int EXEC_ERROR = -3;


   protected int execute(String var1) {
      Runtime var2 = Runtime.getRuntime();

      try {
         Process var3 = var2.exec(var1);

         int var4;
         try {
            var3.waitFor();
            var4 = var3.exitValue();
         } finally {
            var3.destroy();
         }

         return var4;
      } catch (IOException var11) {
         return EXEC_IOEXCEPTION;
      } catch (InterruptedException var12) {
         return EXEC_INTERRUPTED;
      } catch (Throwable var13) {
         return EXEC_ERROR;
      }
   }

   protected void remove(String var1) throws IOException {
      File var2 = new File(var1);
      if(!var2.exists()) {
         throw new IOException("No such file or directory");
      } else if(!var2.delete()) {
         throw new IOException("Failed to delete");
      }
   }

   protected void rename(String var1, String var2) throws IOException {
      File var3 = new File(var1);
      if(!var3.exists()) {
         throw new IOException("No such file or directory");
      } else if(!var3.renameTo(new File(var2))) {
         throw new IOException("Failed to delete");
      }
   }

   protected String tmpname() {
      try {
         File var1 = File.createTempFile(TMP_PREFIX, TMP_SUFFIX);
         return var1.getName();
      } catch (IOException var2) {
         return super.tmpname();
      }
   }

}
