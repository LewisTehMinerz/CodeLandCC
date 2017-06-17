package org.luaj.vm2.lib.jse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JseProcess {

   final Process process;
   final Thread input;
   final Thread output;
   final Thread error;


   public JseProcess(String[] var1, InputStream var2, OutputStream var3, OutputStream var4) throws IOException {
      this(Runtime.getRuntime().exec(var1), var2, var3, var4);
   }

   public JseProcess(String var1, InputStream var2, OutputStream var3, OutputStream var4) throws IOException {
      this(Runtime.getRuntime().exec(var1), var2, var3, var4);
   }

   private JseProcess(Process var1, InputStream var2, OutputStream var3, OutputStream var4) {
      this.process = var1;
      this.input = var2 == null?null:this.copyBytes(var2, var1.getOutputStream(), (InputStream)null, var1.getOutputStream());
      this.output = var3 == null?null:this.copyBytes(var1.getInputStream(), var3, var1.getInputStream(), (OutputStream)null);
      this.error = var4 == null?null:this.copyBytes(var1.getErrorStream(), var4, var1.getErrorStream(), (OutputStream)null);
   }

   public int exitValue() {
      return this.process.exitValue();
   }

   public int waitFor() throws InterruptedException {
      int var1 = this.process.waitFor();
      if(this.input != null) {
         this.input.join();
      }

      if(this.output != null) {
         this.output.join();
      }

      if(this.error != null) {
         this.error.join();
      }

      this.process.destroy();
      return var1;
   }

   private Thread copyBytes(final InputStream var1, final OutputStream var2, final InputStream var3, final OutputStream var4) {
      Thread var5 = new Thread() {
         public void run() {
            try {
               byte[] var1x = new byte[1024];

               int var2x;
               try {
                  while((var2x = var1.read(var1x)) >= 0) {
                     var2.write(var1x, 0, var2x);
                  }
               } finally {
                  if(var3 != null) {
                     var3.close();
                  }

                  if(var4 != null) {
                     var4.close();
                  }

               }
            } catch (IOException var7) {
               var7.printStackTrace();
            }

         }
      };
      var5.start();
      return var5;
   }
}
