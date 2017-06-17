package org.luaj.vm2.ast;

import java.util.List;
import org.luaj.vm2.ast.Exp;
import org.luaj.vm2.ast.Visitor;

public class TableConstructor extends Exp {

   public List fields;


   public void accept(Visitor var1) {
      var1.visit(this);
   }
}
