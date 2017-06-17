package org.luaj.vm2.ast;

import org.luaj.vm2.ast.Variable;

public class Name {

   public final String name;
   public Variable variable;


   public Name(String var1) {
      this.name = var1;
   }
}
