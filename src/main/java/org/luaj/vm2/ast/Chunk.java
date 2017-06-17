package org.luaj.vm2.ast;

import org.luaj.vm2.ast.Block;
import org.luaj.vm2.ast.Visitor;

public class Chunk {

   public final Block block;


   public Chunk(Block var1) {
      this.block = var1;
   }

   public void accept(Visitor var1) {
      var1.visit(this);
   }
}
