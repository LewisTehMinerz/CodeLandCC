package org.luaj.vm2.lib.jse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.luaj.vm2.lib.BaseLib;

public class JseBaseLib extends BaseLib {

   public JseBaseLib() {
      this.STDIN = System.in;
   }

   public InputStream findResource(String var1) {
      File var2 = new File(var1);
      if(!var2.exists()) {
         return super.findResource(var1);
      } else {
         try {
            return new FileInputStream(var2);
         } catch (IOException var4) {
            return null;
         }
      }
   }
}
