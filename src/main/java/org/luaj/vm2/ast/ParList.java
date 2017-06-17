package org.luaj.vm2.ast;

import java.util.ArrayList;
import java.util.List;
import org.luaj.vm2.ast.Visitor;

public class ParList {

   public static final List EMPTY_NAMELIST = new ArrayList();
   public static final ParList EMPTY_PARLIST = new ParList(EMPTY_NAMELIST, false);
   public final List names;
   public final boolean isvararg;


   public ParList(List var1, boolean var2) {
      this.names = var1;
      this.isvararg = var2;
   }

   public void accept(Visitor var1) {
      var1.visit(this);
   }

}
