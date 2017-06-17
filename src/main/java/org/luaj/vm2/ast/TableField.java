package org.luaj.vm2.ast;

import org.luaj.vm2.ast.Exp;
import org.luaj.vm2.ast.Visitor;

public class TableField {

   public final Exp index;
   public final String name;
   public final Exp rhs;


   public TableField(Exp var1, String var2, Exp var3) {
      this.index = var1;
      this.name = var2;
      this.rhs = var3;
   }

   public static TableField keyedField(Exp var0, Exp var1) {
      return new TableField(var0, (String)null, var1);
   }

   public static TableField namedField(String var0, Exp var1) {
      return new TableField((Exp)null, var0, var1);
   }

   public static TableField listField(Exp var0) {
      return new TableField((Exp)null, (String)null, var0);
   }

   public void accept(Visitor var1) {
      var1.visit(this);
   }
}
