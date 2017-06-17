package org.luaj.vm2.ast;

import java.util.ArrayList;
import java.util.List;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.ast.Exp;
import org.luaj.vm2.ast.TableConstructor;
import org.luaj.vm2.ast.Visitor;

public class FuncArgs {

   public final List exps;


   public static FuncArgs explist(List var0) {
      return new FuncArgs(var0);
   }

   public static FuncArgs tableconstructor(TableConstructor var0) {
      return new FuncArgs(var0);
   }

   public static FuncArgs string(LuaString var0) {
      return new FuncArgs(var0);
   }

   public FuncArgs(List var1) {
      this.exps = var1;
   }

   public FuncArgs(LuaString var1) {
      this.exps = new ArrayList();
      this.exps.add(Exp.constant(var1));
   }

   public FuncArgs(TableConstructor var1) {
      this.exps = new ArrayList();
      this.exps.add(var1);
   }

   public void accept(Visitor var1) {
      var1.visit(this);
   }
}
