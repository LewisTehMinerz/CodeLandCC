package org.luaj.vm2.ast;

import org.luaj.vm2.ast.Block;
import org.luaj.vm2.ast.NameScope;
import org.luaj.vm2.ast.ParList;
import org.luaj.vm2.ast.Visitor;

public class FuncBody {

   public ParList parlist;
   public Block block;
   public NameScope scope;


   public FuncBody(ParList var1, Block var2) {
      this.parlist = var1 != null?var1:ParList.EMPTY_PARLIST;
      this.block = var2;
   }

   public void accept(Visitor var1) {
      var1.visit(this);
   }
}
