package org.luaj.vm2.lua2java;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.luaj.vm2.ast.Block;
import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.ast.Exp;
import org.luaj.vm2.ast.FuncBody;
import org.luaj.vm2.ast.NameScope;
import org.luaj.vm2.ast.Stat;
import org.luaj.vm2.ast.Variable;
import org.luaj.vm2.ast.Visitor;

public class JavaScope extends NameScope {

   private static final int MAX_CONSTNAME_LEN = 8;
   public static final Set SPECIALS = new HashSet();
   private static final String[] specials = new String[]{"name", "opcode", "env", "abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while", "false", "null", "true"};
   public int nreturns;
   public boolean needsbinoptmp;
   public boolean usesvarargs;
   final Set staticnames;
   final Set javanames = new HashSet();
   final Map astele2javaname = new HashMap();
   static Class class$org$luaj$vm2$lib$LibFunction;


   private JavaScope(Set var1, JavaScope var2) {
      super(var2);
      this.staticnames = var1;
   }

   public static JavaScope newJavaScope(Chunk var0) {
      return (new JavaScope(new HashSet(), (JavaScope)null)).initialize(var0.block, -1);
   }

   public JavaScope pushJavaScope(FuncBody var1) {
      return (new JavaScope(this.staticnames, this)).initialize(var1.block, 0);
   }

   public JavaScope popJavaScope() {
      return (JavaScope)this.outerScope;
   }

   final String getJavaName(Variable var1) {
      for(JavaScope var2 = this; var2 != null; var2 = (JavaScope)var2.outerScope) {
         if(var2.astele2javaname.containsKey(var1)) {
            return (String)var2.astele2javaname.get(var1);
         }
      }

      return this.allocateJavaName(var1, var1.name);
   }

   private final String allocateJavaName(Object var1, String var2) {
      int var3 = 0;

      while(true) {
         String var4 = var2 + (var3 == 0?"":"$" + var3);
         if(!this.isJavanameInScope(var4) && !SPECIALS.contains(var4) && !this.staticnames.contains(var4)) {
            this.javanames.add(var4);
            this.astele2javaname.put(var1, var4);
            return var4;
         }

         ++var3;
      }
   }

   public void setJavaName(Variable var1, String var2) {
      this.javanames.add(var2);
      this.astele2javaname.put(var1, var2);
   }

   private boolean isJavanameInScope(String var1) {
      for(JavaScope var2 = this; var2 != null; var2 = (JavaScope)var2.outerScope) {
         if(var2.javanames.contains(var1)) {
            return true;
         }
      }

      return false;
   }

   public String createConstantName(String var1) {
      var1 = toLegalJavaName(var1);
      int var2 = 0;

      while(true) {
         String var3 = var1 + (var2 == 0?"":"$" + var2);
         if(!this.isJavanameInScope(var3) && !SPECIALS.contains(var3) && !this.staticnames.contains(var3)) {
            this.javanames.add(var3);
            this.staticnames.add(var3);
            return var3;
         }

         ++var2;
      }
   }

   public static String toLegalJavaName(String var0) {
      String var1 = var0.replaceAll("[^\\w]", "_");
      if(var1.length() > 8) {
         var1 = var1.substring(0, 8);
      }

      if(var1.length() == 0 || !Character.isJavaIdentifierStart(var1.charAt(0))) {
         var1 = "_" + var1;
      }

      return var1;
   }

   private JavaScope initialize(Block var1, int var2) {
      JavaScope.NewScopeVisitor var3 = new JavaScope.NewScopeVisitor(var2);
      var1.accept(var3);
      this.nreturns = var3.nreturns;
      this.needsbinoptmp = var3.needsbinoptmp;
      this.usesvarargs = var3.usesvarargs;
      return this;
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      for(int var0 = 0; var0 < specials.length; ++var0) {
         SPECIALS.add(specials[var0]);
      }

      Field[] var4 = (class$org$luaj$vm2$lib$LibFunction == null?(class$org$luaj$vm2$lib$LibFunction = class$("org.luaj.vm2.lib.LibFunction")):class$org$luaj$vm2$lib$LibFunction).getFields();
      int var1 = 0;

      int var2;
      for(var2 = var4.length; var1 < var2; ++var1) {
         SPECIALS.add(var4[var1].getName());
      }

      Method[] var5 = (class$org$luaj$vm2$lib$LibFunction == null?(class$org$luaj$vm2$lib$LibFunction = class$("org.luaj.vm2.lib.LibFunction")):class$org$luaj$vm2$lib$LibFunction).getMethods();
      var2 = 0;

      for(int var3 = var5.length; var2 < var3; ++var2) {
         SPECIALS.add(var5[var2].getName());
      }

   }

   class NewScopeVisitor extends Visitor {

      int nreturns = 0;
      boolean needsbinoptmp = false;
      boolean usesvarargs = false;


      NewScopeVisitor(int var2) {
         this.nreturns = var2;
      }

      public void visit(FuncBody var1) {}

      public void visit(Stat.Return var1) {
         int var2 = var1.nreturns();
         this.nreturns = this.nreturns >= 0 && var2 >= 0?Math.max(var2, this.nreturns):-1;
         super.visit(var1);
      }

      public void visit(Exp.BinopExp var1) {
         switch(var1.op) {
         case 59:
         case 60:
            this.needsbinoptmp = true;
         default:
            super.visit(var1);
         }
      }

      public void visit(Exp.VarargsExp var1) {
         this.usesvarargs = true;
      }
   }
}
