package org.luaj.vm2.ast;

import java.util.ArrayList;
import java.util.List;
import org.luaj.vm2.ast.Name;

public class FuncName {

   public final Name name;
   public List dots;
   public String method;


   public FuncName(String var1) {
      this.name = new Name(var1);
   }

   public void adddot(String var1) {
      if(this.dots == null) {
         this.dots = new ArrayList();
      }

      this.dots.add(var1);
   }
}
