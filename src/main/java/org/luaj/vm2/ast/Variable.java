package org.luaj.vm2.ast;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.ast.NameScope;

public class Variable {

   public final String name;
   public final NameScope definingScope;
   public boolean isupvalue;
   public boolean hasassignments;
   public LuaValue initialValue;


   public Variable(String var1) {
      this.name = var1;
      this.definingScope = null;
   }

   public Variable(String var1, NameScope var2) {
      this.name = var1;
      this.definingScope = var2;
   }

   public boolean isLocal() {
      return this.definingScope != null;
   }

   public boolean isConstant() {
      return !this.hasassignments && this.initialValue != null;
   }
}
