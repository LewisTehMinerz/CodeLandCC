package org.luaj.vm2.lua2java;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.ast.Block;
import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.ast.Exp;
import org.luaj.vm2.ast.FuncArgs;
import org.luaj.vm2.ast.FuncBody;
import org.luaj.vm2.ast.Name;
import org.luaj.vm2.ast.NameResolver;
import org.luaj.vm2.ast.ParList;
import org.luaj.vm2.ast.Stat;
import org.luaj.vm2.ast.TableConstructor;
import org.luaj.vm2.ast.TableField;
import org.luaj.vm2.ast.Variable;
import org.luaj.vm2.ast.Visitor;
import org.luaj.vm2.lua2java.JavaScope;

public class JavaCodeGen {

   final Chunk chunk;
   final String packagename;
   final String classname;
   Writer writer;


   public JavaCodeGen(Chunk var1, Writer var2, String var3, String var4) {
      this.chunk = var1;
      this.writer = var2;
      this.packagename = var3;
      this.classname = var4;
      var1.accept(new NameResolver());
      var1.accept(new JavaCodeGen.JavaClassWriterVisitor());
   }

   private static String quotedStringInitializer(LuaString var0) {
      byte[] var1 = var0.m_bytes;
      int var2 = var0.m_offset;
      int var3 = var0.m_length;
      StringBuffer var4 = new StringBuffer(var3 + 2);
      int var5;
      byte var6;
      if(!var0.isValidUtf8()) {
         var4.append("new byte[]{");

         for(var5 = 0; var5 < var3; ++var5) {
            if(var5 > 0) {
               var4.append(",");
            }

            var6 = var1[var2 + var5];
            switch(var6) {
            case 9:
               var4.append("\'\\t\'");
               break;
            case 10:
               var4.append("\'\\n\'");
               break;
            case 13:
               var4.append("\'\\r\'");
               break;
            case 92:
               var4.append("\'\\\\\'");
               break;
            default:
               if(var6 >= 32) {
                  var4.append('\'');
                  var4.append((char)var6);
                  var4.append('\'');
               } else {
                  var4.append(String.valueOf(var6));
               }
            }
         }

         var4.append("}");
         return var4.toString();
      } else {
         var4.append('\"');

         for(var5 = 0; var5 < var3; ++var5) {
            var6 = var1[var2 + var5];
            switch(var6) {
            case 8:
               var4.append("\\b");
               break;
            case 9:
               var4.append("\\t");
               break;
            case 10:
               var4.append("\\n");
               break;
            case 12:
               var4.append("\\f");
               break;
            case 13:
               var4.append("\\r");
               break;
            case 34:
               var4.append("\\\"");
               break;
            case 92:
               var4.append("\\\\");
               break;
            default:
               if(var6 >= 32) {
                  var4.append((char)var6);
               } else {
                  int var7 = 255 & var6;
                  if(var7 >= 192 && var5 + 1 < var3) {
                     if(var7 >= 224 && var5 + 2 < var3) {
                        var7 = (var7 & 15) << 12 | (63 & var1[var5 + 1]) << 6 | 63 & var1[var5 + 2];
                        var5 += 2;
                     } else {
                        int var10000 = (var7 & 31) << 6;
                        ++var5;
                        var7 = var10000 | 63 & var1[var5];
                     }
                  }

                  var4.append("\\u");
                  var4.append(Integer.toHexString(65536 + var7).substring(1));
               }
            }
         }

         var4.append('\"');
         return var4.toString();
      }
   }

   static String access$300(LuaString var0) {
      return quotedStringInitializer(var0);
   }

   class JavaClassWriterVisitor extends Visitor {

      JavaScope javascope = null;
      List constantDeclarations = new ArrayList();
      Map stringConstants = new HashMap();
      Map numberConstants = new HashMap();
      String indent = "";
      Map callerExpects = new HashMap();


      void addindent() {
         this.indent = this.indent + "   ";
      }

      void subindent() {
         this.indent = this.indent.substring(3);
      }

      void out(String var1) {
         try {
            JavaCodeGen.this.writer.write(var1);
         } catch (IOException var3) {
            throw new RuntimeException("write failed: " + var3, var3);
         }
      }

      void outi(String var1) {
         this.out(this.indent);
         this.out(var1);
      }

      void outl(String var1) {
         this.outi(var1);
         this.out("\n");
      }

      void outr(String var1) {
         this.out(var1);
         this.out("\n");
      }

      void outb(String var1) {
         this.outl(var1);
         this.addindent();
      }

      void oute(String var1) {
         this.subindent();
         this.outl(var1);
      }

      public void visit(Chunk var1) {
         if(JavaCodeGen.this.packagename != null) {
            this.outl("package " + JavaCodeGen.this.packagename + ";");
         }

         this.outl("import org.luaj.vm2.*;");
         this.outl("import org.luaj.vm2.lib.*;");
         this.outb("public class " + JavaCodeGen.this.classname + " extends VarArgFunction {");
         this.outl("public Varargs onInvoke(Varargs $arg) {");
         this.addindent();
         this.javascope = JavaScope.newJavaScope(var1);
         this.writeBodyBlock(var1.block);
         this.oute("}");
         int var2 = 0;

         for(int var3 = this.constantDeclarations.size(); var2 < var3; ++var2) {
            this.outl((String)this.constantDeclarations.get(var2));
         }

         this.subindent();
         this.outi("}");
      }

      void writeBodyBlock(Block var1) {
         if(this.javascope.needsbinoptmp) {
            this.outl("LuaValue $b;");
         }

         super.visit(var1);
         if(!this.endsInReturn(var1)) {
            this.outl("return NONE;");
         }

      }

      public void visit(Block var1) {
         this.outb("{");
         super.visit(var1);
         this.oute("}");
      }

      private boolean endsInReturn(Block var1) {
         int var2 = var1.stats.size();
         if(var2 <= 0) {
            return false;
         } else {
            Stat var3 = (Stat)var1.stats.get(var2 - 1);
            if(!(var3 instanceof Stat.Return) && !(var3 instanceof Stat.Break)) {
               if(this.isInfiniteLoop(var3)) {
                  return true;
               } else if(!(var3 instanceof Stat.IfThenElse)) {
                  return false;
               } else {
                  Stat.IfThenElse var4 = (Stat.IfThenElse)var3;
                  if(var4.elseblock != null && this.endsInReturn(var4.ifblock) && this.endsInReturn(var4.elseblock)) {
                     if(var4.elseifblocks != null) {
                        int var5 = 0;

                        for(int var6 = var4.elseifblocks.size(); var5 < var6; ++var5) {
                           if(!this.endsInReturn((Block)var4.elseifblocks.get(var5))) {
                              return false;
                           }
                        }
                     }

                     return true;
                  } else {
                     return false;
                  }
               }
            } else {
               return true;
            }
         }
      }

      private boolean isInfiniteLoop(Stat var1) {
         return var1 instanceof Stat.WhileDo && "true".equals(this.evalBoolean(((Stat.WhileDo)var1).exp))?true:var1 instanceof Stat.RepeatUntil && "false".equals(this.evalBoolean(((Stat.RepeatUntil)var1).exp));
      }

      public void visit(Stat.Return var1) {
         int var2 = var1.nreturns();
         switch(var2) {
         case 0:
            this.outl("return NONE;");
            break;
         case 1:
            this.outl("return " + this.evalLuaValue((Exp)var1.values.get(0)) + ";");
            break;
         default:
            if(var1.values.size() == 1 && ((Exp)var1.values.get(0)).isfunccall()) {
               this.tailCall((Exp)var1.values.get(0));
            } else {
               this.outl("return " + this.evalListAsVarargs(var1.values) + ";");
            }
         }

      }

      public void visit(Exp.AnonFuncDef var1) {
         super.visit(var1);
      }

      public void visit(Stat.Assign var1) {
         this.multiAssign(var1.vars, var1.exps);
      }

      public void visit(Stat.LocalAssign var1) {
         List var2 = var1.names;
         List var3 = var1.values;
         int var4 = var2.size();
         int var5 = var3 != null?var3.size():0;
         boolean var6 = var5 > 0 && var5 < var4 && ((Exp)var3.get(var5 - 1)).isvarargexp();

         int var7;
         for(var7 = 0; var7 < var4 && var7 < (var6?var5 - 1:var5); ++var7) {
            if(!((Name)var2.get(var7)).variable.isConstant()) {
               this.singleLocalDeclareAssign((Name)var2.get(var7), this.evalLuaValue((Exp)var3.get(var7)));
            }
         }

         if(var6) {
            String var9 = this.javascope.getJavaName(this.tmpJavaVar("t").variable);
            this.outl("final Varargs " + var9 + " = " + this.evalVarargs((Exp)var3.get(var5 - 1)) + ";");

            for(int var8 = var5 - 1; var8 < var4; ++var8) {
               this.singleLocalDeclareAssign((Name)var2.get(var8), var9 + (var8 == var5 - 1?".arg1()":".arg(" + (var8 - var5 + 2) + ")"));
            }
         } else {
            for(var7 = var5; var7 < var4; ++var7) {
               if(!((Name)var2.get(var7)).variable.isConstant()) {
                  this.singleLocalDeclareAssign((Name)var2.get(var7), "NIL");
               }
            }
         }

         for(var7 = var4; var7 < var5; ++var7) {
            String var10 = this.javascope.getJavaName(this.tmpJavaVar("t").variable);
            this.outl("final Varargs " + var10 + " = " + this.evalVarargs((Exp)var3.get(var7)));
         }

      }

      private void multiAssign(List var1, List var2) {
         final boolean[] var3 = new boolean[]{false};
         if(var2.size() > 1) {
            (new Visitor() {
               public void visit(FuncBody var1) {}
               public void visit(Exp.FieldExp var1) {
                  var3[0] = true;
               }
               public void visit(Exp.FuncCall var1) {
                  var3[0] = true;
               }
               public void visit(Exp.IndexExp var1) {
                  var3[0] = true;
               }
               public void visit(Exp.MethodCall var1) {
                  var3[0] = true;
               }
               public void visit(Exp.NameExp var1) {
                  var3[0] = true;
               }
            }).visitExps(var2);
         }

         if(var3[0]) {
            this.tmpvarsMultiAssign(var1, var2);
         } else {
            this.directMultiAssign(var1, var2);
         }

      }

      private void directMultiAssign(List var1, List var2) {
         int var3 = var1.size();
         int var4 = var2 != null?var2.size():0;
         boolean var5 = var4 > 0 && var4 < var3 && ((Exp)var2.get(var4 - 1)).isvarargexp();

         int var6;
         for(var6 = 0; var6 < var3 && var6 < (var5?var4 - 1:var4); ++var6) {
            this.singleVarOrNameAssign(var1.get(var6), this.evalLuaValue((Exp)var2.get(var6)));
         }

         if(var5) {
            String var8 = this.javascope.getJavaName(this.tmpJavaVar("v").variable);
            this.outl("final Varargs " + var8 + " = " + this.evalVarargs((Exp)var2.get(var4 - 1)) + ";");

            for(int var7 = var4 - 1; var7 < var3; ++var7) {
               this.singleVarOrNameAssign(var1.get(var7), var8 + (var7 == var4 - 1?".arg1()":".arg(" + (var7 - var4 + 2) + ")"));
            }
         } else {
            for(var6 = var4; var6 < var3; ++var6) {
               this.singleVarOrNameAssign(var1.get(var6), "NIL");
            }
         }

         for(var6 = var3; var6 < var4; ++var6) {
            String var9 = this.javascope.getJavaName(this.tmpJavaVar("tmp").variable);
            this.outl("final Varargs " + var9 + " = " + this.evalVarargs((Exp)var2.get(var6)));
         }

      }

      private void tmpvarsMultiAssign(List var1, List var2) {
         int var3 = var1.size();
         int var4 = var2 != null?var2.size():0;
         boolean var5 = var4 > 0 && var4 < var3 && ((Exp)var2.get(var4 - 1)).isvarargexp();
         ArrayList var6 = new ArrayList();

         int var7;
         for(var7 = 0; var7 < var4; ++var7) {
            var6.add(this.javascope.getJavaName(this.tmpJavaVar("t").variable));
            if(var5 && var7 == var4 - 1) {
               this.outl("final Varargs " + var6.get(var7) + " = " + this.evalVarargs((Exp)var2.get(var7)) + ";");
            } else {
               this.outl("final LuaValue " + var6.get(var7) + " = " + this.evalLuaValue((Exp)var2.get(var7)) + ";");
            }
         }

         for(var7 = 0; var7 < var3; ++var7) {
            if(var7 < (var5?var4 - 1:var4)) {
               this.singleVarOrNameAssign(var1.get(var7), (String)var6.get(var7));
            } else if(var5) {
               this.singleVarOrNameAssign(var1.get(var7), var6.get(var4 - 1) + (var7 == var4 - 1?".arg1()":".arg(" + (var7 - var4 + 2) + ")"));
            } else {
               this.singleVarOrNameAssign(var1.get(var7), "NIL");
            }
         }

      }

      private void singleVarOrNameAssign(Object var1, final String var2) {
         Visitor var3 = new Visitor() {
            public void visit(Exp.FieldExp var1) {
               JavaClassWriterVisitor.this.outl(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".set(" + JavaClassWriterVisitor.this.evalStringConstant(var1.name.name) + "," + var2 + ");");
            }
            public void visit(Exp.IndexExp var1) {
               JavaClassWriterVisitor.this.outl(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".set(" + JavaClassWriterVisitor.this.evalLuaValue(var1.exp) + "," + var2 + ");");
            }
            public void visit(Exp.NameExp var1) {
               JavaClassWriterVisitor.this.singleAssign(var1.name, var2);
            }
         };
         if(var1 instanceof Exp.VarExp) {
            ((Exp.VarExp)var1).accept(var3);
         } else {
            if(!(var1 instanceof Name)) {
               throw new IllegalStateException("can\'t assign to " + var1.getClass());
            }

            this.singleAssign((Name)var1, var2);
         }

      }

      private void singleAssign(Name var1, String var2) {
         if(var1.variable.isLocal()) {
            if(var1.variable.isConstant()) {
               return;
            }

            this.outi("");
            this.singleReference(var1);
            this.outr(" = " + var2 + ";");
         } else {
            this.outl("env.set(" + this.evalStringConstant(var1.name) + "," + var2 + ");");
         }

      }

      private void singleReference(Name var1) {
         if(var1.variable.isLocal()) {
            if(var1.variable.isConstant()) {
               this.out(this.evalConstant(var1.variable.initialValue));
               return;
            }

            this.out(this.javascope.getJavaName(var1.variable));
            if(var1.variable.isupvalue && var1.variable.hasassignments) {
               this.out("[0]");
            }
         } else {
            this.out("env.get(" + this.evalStringConstant(var1.name) + ")");
         }

      }

      private void singleLocalDeclareAssign(Name var1, String var2) {
         this.singleLocalDeclareAssign(var1.variable, var2);
      }

      private void singleLocalDeclareAssign(Variable var1, String var2) {
         String var3 = this.javascope.getJavaName(var1);
         if(!var1.isConstant()) {
            if(var1.isupvalue && var1.hasassignments) {
               this.outl("final LuaValue[] " + var3 + " = {" + var2 + "};");
            } else if(var1.isupvalue) {
               this.outl("final LuaValue " + var3 + (var2 != null?" = " + var2:"") + ";");
            } else {
               this.outl((var1.hasassignments?"LuaValue ":"final LuaValue ") + var3 + (var2 != null?" = " + var2:"") + ";");
            }

         }
      }

      public void visit(Stat.Break var1) {
         this.outl("break;");
      }

      private Writer pushWriter() {
         Writer var1 = JavaCodeGen.this.writer;
         JavaCodeGen.this.writer = new CharArrayWriter();
         return var1;
      }

      private String popWriter(Writer var1) {
         Writer var2 = JavaCodeGen.this.writer;
         JavaCodeGen.this.writer = var1;
         return var2.toString();
      }

      public String evalListAsVarargs(List var1) {
         int var2 = var1 != null?var1.size():0;
         switch(var2) {
         case 0:
            return "NONE";
         case 1:
            return this.evalVarargs((Exp)var1.get(0));
         case 2:
         case 3:
         default:
            Writer var3 = this.pushWriter();
            this.out(var2 > 3?"varargsOf(new LuaValue[] {":"varargsOf(");

            for(int var4 = 1; var4 < var2; ++var4) {
               this.out(this.evalLuaValue((Exp)var1.get(var4 - 1)) + ",");
            }

            if(var2 > 3) {
               this.out("},");
            }

            this.out(this.evalVarargs((Exp)var1.get(var2 - 1)) + ")");
            return this.popWriter(var3);
         }
      }

      public String evalLuaValue(Exp var1) {
         Writer var2 = this.pushWriter();
         this.callerExpects.put(var1, Integer.valueOf(1));
         var1.accept(this);
         return this.popWriter(var2);
      }

      public String evalVarargs(Exp var1) {
         Writer var2 = this.pushWriter();
         this.callerExpects.put(var1, Integer.valueOf(-1));
         var1.accept(this);
         return this.popWriter(var2);
      }

      public String evalBoolean(Exp var1) {
         Writer var2 = this.pushWriter();
         var1.accept(new Visitor() {
            public void visit(Exp.UnopExp var1) {
               switch(var1.op) {
               case 19:
                  String var2 = JavaClassWriterVisitor.this.evalBoolean(var1.rhs);
                  JavaClassWriterVisitor.this.out("true".equals(var2)?"false":("false".equals(var2)?"true":"(!" + var2 + ")"));
                  break;
               default:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
               }

            }
            public void visit(Exp.BinopExp var1) {
               switch(var1.op) {
               case 23:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".eq_b(" + JavaClassWriterVisitor.this.evalLuaValue(var1.rhs) + ")");
                  return;
               case 24:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".lt_b(" + JavaClassWriterVisitor.this.evalLuaValue(var1.rhs) + ")");
                  return;
               case 25:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".lteq_b(" + JavaClassWriterVisitor.this.evalLuaValue(var1.rhs) + ")");
                  return;
               case 59:
                  JavaClassWriterVisitor.this.out("(" + JavaClassWriterVisitor.this.evalBoolean(var1.lhs) + "||" + JavaClassWriterVisitor.this.evalBoolean(var1.rhs) + ")");
                  return;
               case 60:
                  JavaClassWriterVisitor.this.out("(" + JavaClassWriterVisitor.this.evalBoolean(var1.lhs) + "&&" + JavaClassWriterVisitor.this.evalBoolean(var1.rhs) + ")");
                  return;
               case 61:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".neq_b(" + JavaClassWriterVisitor.this.evalLuaValue(var1.rhs) + ")");
                  return;
               case 62:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".gteq_b(" + JavaClassWriterVisitor.this.evalLuaValue(var1.rhs) + ")");
                  return;
               case 63:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1.lhs) + ".gt_b(" + JavaClassWriterVisitor.this.evalLuaValue(var1.rhs) + ")");
                  return;
               default:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
               }
            }
            public void visit(Exp.Constant var1) {
               switch(var1.value.type()) {
               case 1:
                  JavaClassWriterVisitor.this.out(var1.value.toboolean()?"true":"false");
                  break;
               default:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
               }

            }
            public void visit(Exp.ParensExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalBoolean(var1.exp));
            }
            public void visit(Exp.VarargsExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
            }
            public void visit(Exp.FieldExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
            }
            public void visit(Exp.IndexExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
            }
            public void visit(Exp.NameExp var1) {
               if(var1.name.variable.isConstant()) {
                  JavaClassWriterVisitor.this.out(var1.name.variable.initialValue.toboolean()?"true":"false");
               } else {
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
               }
            }
            public void visit(Exp.FuncCall var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
            }
            public void visit(Exp.MethodCall var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
            }
            public void visit(TableConstructor var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".toboolean()");
            }
         });
         return this.popWriter(var2);
      }

      public String evalNumber(Exp var1) {
         Writer var2 = this.pushWriter();
         var1.accept(new Visitor() {
            public void visit(Exp.UnopExp var1) {
               switch(var1.op) {
               case 18:
                  JavaClassWriterVisitor.this.out("(-" + JavaClassWriterVisitor.this.evalNumber(var1.rhs) + ")");
                  break;
               case 20:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1.rhs) + ".length()");
                  break;
               default:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
               }

            }
            public void visit(Exp.BinopExp var1) {
               switch(var1.op) {
               case 12:
               case 13:
               case 14:
                  String var2 = var1.op == 12?"+":(var1.op == 13?"-":"*");
                  JavaClassWriterVisitor.this.out("(" + JavaClassWriterVisitor.this.evalNumber(var1.lhs) + var2 + JavaClassWriterVisitor.this.evalNumber(var1.rhs) + ")");
                  break;
               case 15:
                  JavaClassWriterVisitor.this.out("LuaDouble.ddiv_d(" + JavaClassWriterVisitor.this.evalNumber(var1.lhs) + "," + JavaClassWriterVisitor.this.evalNumber(var1.rhs) + ")");
                  break;
               case 16:
                  JavaClassWriterVisitor.this.out("LuaDouble.dmod_d(" + JavaClassWriterVisitor.this.evalNumber(var1.lhs) + "," + JavaClassWriterVisitor.this.evalNumber(var1.rhs) + ")");
                  break;
               case 17:
                  JavaClassWriterVisitor.this.out("MathLib.dpow_d(" + JavaClassWriterVisitor.this.evalNumber(var1.lhs) + "," + JavaClassWriterVisitor.this.evalNumber(var1.rhs) + ")");
                  break;
               default:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
               }

            }
            public void visit(Exp.Constant var1) {
               switch(var1.value.type()) {
               case 3:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalNumberLiteral(var1.value.checkdouble()));
                  break;
               default:
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
               }

            }
            public void visit(Exp.ParensExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalNumber(var1.exp));
            }
            public void visit(Exp.VarargsExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
            }
            public void visit(Exp.FieldExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
            }
            public void visit(Exp.IndexExp var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
            }
            public void visit(Exp.NameExp var1) {
               if(var1.name.variable.isConstant() && var1.name.variable.initialValue.isnumber()) {
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalNumberLiteral(var1.name.variable.initialValue.checkdouble()));
               } else {
                  JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
               }
            }
            public void visit(Exp.FuncCall var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
            }
            public void visit(Exp.MethodCall var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
            }
            public void visit(TableConstructor var1) {
               JavaClassWriterVisitor.this.out(JavaClassWriterVisitor.this.evalLuaValue(var1) + ".checkdouble()");
            }
         });
         return this.popWriter(var2);
      }

      public void visit(Stat.FuncCallStat var1) {
         this.outi("");
         var1.funccall.accept(this);
         this.outr(";");
      }

      public void visit(Exp.BinopExp var1) {
         switch(var1.op) {
         case 59:
         case 60:
            String var2 = var1.op == 60?"!":"";
            this.out("(" + var2 + "($b=" + this.evalLuaValue(var1.lhs) + ").toboolean()?$b:" + this.evalLuaValue(var1.rhs) + ")");
            return;
         default:
            switch(var1.op) {
            case 12:
               this.out("valueOf(" + this.evalNumber(var1.lhs) + "+" + this.evalNumber(var1.rhs) + ")");
               return;
            case 13:
               this.out("valueOf(" + this.evalNumber(var1.lhs) + "-" + this.evalNumber(var1.rhs) + ")");
               return;
            case 14:
               this.out("valueOf(" + this.evalNumber(var1.lhs) + "*" + this.evalNumber(var1.rhs) + ")");
               return;
            case 15:
               this.out("LuaDouble.ddiv(" + this.evalNumber(var1.lhs) + "," + this.evalNumber(var1.rhs) + ")");
               return;
            case 16:
               this.out("LuaDouble.dmod(" + this.evalNumber(var1.lhs) + "," + this.evalNumber(var1.rhs) + ")");
               return;
            case 17:
               this.out("MathLib.dpow(" + this.evalNumber(var1.lhs) + "," + this.evalNumber(var1.rhs) + ")");
               return;
            case 18:
            case 19:
            case 20:
            case 22:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            default:
               throw new IllegalStateException("unknown bin op:" + var1.op);
            case 21:
               if(this.isConcatExp(var1.rhs)) {
                  this.out(this.evalLuaValue(var1.lhs));
                  Exp var4 = var1.rhs;

                  String var3;
                  for(var3 = ""; this.isConcatExp(var4); var4 = ((Exp.BinopExp)var4).rhs) {
                     this.out(".concat(" + this.evalLuaValue(((Exp.BinopExp)var4).lhs));
                     var3 = var3 + ')';
                  }

                  this.out(".concat(" + this.evalLuaValue(var4) + ".buffer())");
                  this.out(var3);
                  this.out(".value()");
               } else {
                  this.out(this.evalLuaValue(var1.lhs) + ".concat(" + this.evalLuaValue(var1.rhs) + ")");
               }

               return;
            case 23:
               this.out(this.evalLuaValue(var1.lhs) + ".eq(" + this.evalLuaValue(var1.rhs) + ")");
               return;
            case 24:
               this.out(this.evalLuaValue(var1.lhs) + ".lt(" + this.evalLuaValue(var1.rhs) + ")");
               return;
            case 25:
               this.out(this.evalLuaValue(var1.lhs) + ".lteq(" + this.evalLuaValue(var1.rhs) + ")");
               return;
            case 61:
               this.out(this.evalLuaValue(var1.lhs) + ".neq(" + this.evalLuaValue(var1.rhs) + ")");
               return;
            case 62:
               this.out(this.evalLuaValue(var1.lhs) + ".gteq(" + this.evalLuaValue(var1.rhs) + ")");
               return;
            case 63:
               this.out(this.evalLuaValue(var1.lhs) + ".gt(" + this.evalLuaValue(var1.rhs) + ")");
            }
         }
      }

      private boolean isConcatExp(Exp var1) {
         return var1 instanceof Exp.BinopExp && ((Exp.BinopExp)var1).op == 21;
      }

      public void visit(Exp.UnopExp var1) {
         var1.rhs.accept(this);
         switch(var1.op) {
         case 18:
            this.out(".neg()");
            break;
         case 19:
            this.out(".not()");
            break;
         case 20:
            this.out(".len()");
         }

      }

      public void visit(Exp.Constant var1) {
         this.out(this.evalConstant(var1.value));
      }

      protected String evalConstant(LuaValue var1) {
         switch(var1.type()) {
         case 0:
            return "NIL";
         case 1:
            return var1.toboolean()?"TRUE":"FALSE";
         case 2:
         default:
            throw new IllegalStateException("unknown constant type: " + var1.typename());
         case 3:
            return this.evalNumberConstant(var1.todouble());
         case 4:
            return this.evalLuaStringConstant(var1.checkstring());
         }
      }

      private String evalStringConstant(String var1) {
         return this.evalLuaStringConstant(LuaValue.valueOf(var1));
      }

      private String evalLuaStringConstant(LuaString var1) {
         if(this.stringConstants.containsKey(var1)) {
            return (String)this.stringConstants.get(var1);
         } else {
            String var2 = JavaCodeGen.quotedStringInitializer(var1);
            String var3 = this.javascope.createConstantName(var1.tojstring());
            this.constantDeclarations.add("static final LuaValue " + var3 + " = valueOf(" + var2 + ");");
            this.stringConstants.put(var1, var3);
            return var3;
         }
      }

      private String evalNumberConstant(double var1) {
         if(var1 == 0.0D) {
            return "ZERO";
         } else if(var1 == -1.0D) {
            return "MINUSONE";
         } else if(var1 == 1.0D) {
            return "ONE";
         } else if(this.numberConstants.containsKey(Double.valueOf(var1))) {
            return (String)this.numberConstants.get(Double.valueOf(var1));
         } else {
            String var3 = this.evalNumberLiteral(var1);
            String var4 = this.javascope.createConstantName(var3);
            this.constantDeclarations.add("static final LuaValue " + var4 + " = valueOf(" + var3 + ");");
            this.numberConstants.put(Double.valueOf(var1), var4);
            return var4;
         }
      }

      private String evalNumberLiteral(double var1) {
         int var3 = (int)var1;
         String var4 = var1 == (double)var3?String.valueOf(var3):String.valueOf(var1);
         return var1 < 0.0D?"(" + var4 + ")":var4;
      }

      public void visit(Exp.FieldExp var1) {
         var1.lhs.accept(this);
         this.out(".get(" + this.evalStringConstant(var1.name.name) + ")");
      }

      public void visit(Exp.IndexExp var1) {
         var1.lhs.accept(this);
         this.out(".get(");
         var1.exp.accept(this);
         this.out(")");
      }

      public void visit(Exp.NameExp var1) {
         this.singleReference(var1.name);
      }

      public void visit(Exp.ParensExp var1) {
         if(var1.exp.isvarargexp()) {
            this.out(this.evalLuaValue(var1.exp));
         } else {
            var1.exp.accept(this);
         }

      }

      public void visit(Exp.VarargsExp var1) {
         int var2 = this.callerExpects.containsKey(var1)?((Integer)this.callerExpects.get(var1)).intValue():0;
         this.out(var2 == 1?"$arg.arg1()":"$arg");
      }

      public void visit(Exp.MethodCall var1) {
         List var2 = var1.args.exps;
         int var3 = var2 != null?var2.size():0;
         int var4 = this.callerExpects.containsKey(var1)?((Integer)this.callerExpects.get(var1)).intValue():0;
         if(var4 == -1) {
            var3 = -1;
         }

         this.out(this.evalLuaValue(var1.lhs));
         switch(var3) {
         case 0:
            this.out(".method(" + this.evalStringConstant(var1.name) + ")");
            break;
         case 1:
         case 2:
            this.out(".method(" + this.evalStringConstant(var1.name) + ",");
            var1.args.accept(this);
            this.out(")");
            break;
         default:
            this.out(".invokemethod(" + this.evalStringConstant(var1.name) + (var2 != null && var2.size() != 0?"," + this.evalListAsVarargs(var1.args.exps):"") + ")");
            if(var4 == 1) {
               this.out(".arg1()");
            }
         }

      }

      public void visit(Exp.FuncCall var1) {
         List var2 = var1.args.exps;
         int var3 = var2 != null?var2.size():0;
         if(var3 > 0 && ((Exp)var2.get(var3 - 1)).isvarargexp()) {
            var3 = -1;
         }

         int var4 = this.callerExpects.containsKey(var1)?((Integer)this.callerExpects.get(var1)).intValue():0;
         if(var4 == -1) {
            var3 = -1;
         }

         this.out(this.evalLuaValue(var1.lhs));
         switch(var3) {
         case 0:
         case 1:
         case 2:
         case 3:
            this.out(".call(");
            var1.args.accept(this);
            this.out(")");
            break;
         default:
            this.out(".invoke(" + (var2 != null && var2.size() != 0?this.evalListAsVarargs(var2):"") + ")");
            if(var4 == 1) {
               this.out(".arg1()");
            }
         }

      }

      public void tailCall(Exp var1) {
         if(var1 instanceof Exp.MethodCall) {
            Exp.MethodCall var2 = (Exp.MethodCall)var1;
            this.outl("return new TailcallVarargs(" + this.evalLuaValue(var2.lhs) + "," + this.evalStringConstant(var2.name) + "," + this.evalListAsVarargs(var2.args.exps) + ");");
         } else {
            if(!(var1 instanceof Exp.FuncCall)) {
               throw new IllegalArgumentException("can\'t tail call " + var1);
            }

            Exp.FuncCall var3 = (Exp.FuncCall)var1;
            this.outl("return new TailcallVarargs(" + this.evalLuaValue(var3.lhs) + "," + this.evalListAsVarargs(var3.args.exps) + ");");
         }

      }

      public void visit(FuncArgs var1) {
         if(var1.exps != null) {
            int var2 = var1.exps.size();
            if(var2 > 0) {
               for(int var3 = 1; var3 < var2; ++var3) {
                  this.out(this.evalLuaValue((Exp)var1.exps.get(var3 - 1)) + ",");
               }

               this.out(this.evalVarargs((Exp)var1.exps.get(var2 - 1)));
            }
         }

      }

      public void visit(FuncBody var1) {
         this.javascope = this.javascope.pushJavaScope(var1);
         int var2 = this.javascope.nreturns;
         int var3 = var1.parlist.names != null?var1.parlist.names.size():0;
         if(var2 >= 0 && var2 <= 1 && var3 <= 3 && !var1.parlist.isvararg) {
            switch(var3) {
            case 0:
               this.outr("new ZeroArgFunction(env) {");
               this.addindent();
               this.outb("public LuaValue call() {");
               break;
            case 1:
               this.outr("new OneArgFunction(env) {");
               this.addindent();
               this.outb("public LuaValue call(" + this.declareArg((Name)var1.parlist.names.get(0)) + ") {");
               this.assignArg((Name)var1.parlist.names.get(0));
               break;
            case 2:
               this.outr("new TwoArgFunction(env) {");
               this.addindent();
               this.outb("public LuaValue call(" + this.declareArg((Name)var1.parlist.names.get(0)) + "," + this.declareArg((Name)var1.parlist.names.get(1)) + ") {");
               this.assignArg((Name)var1.parlist.names.get(0));
               this.assignArg((Name)var1.parlist.names.get(1));
               break;
            case 3:
               this.outr("new ThreeArgFunction(env) {");
               this.addindent();
               this.outb("public LuaValue call(" + this.declareArg((Name)var1.parlist.names.get(0)) + "," + this.declareArg((Name)var1.parlist.names.get(1)) + "," + this.declareArg((Name)var1.parlist.names.get(2)) + ") {");
               this.assignArg((Name)var1.parlist.names.get(0));
               this.assignArg((Name)var1.parlist.names.get(1));
               this.assignArg((Name)var1.parlist.names.get(2));
            }
         } else {
            this.outr("new VarArgFunction(env) {");
            this.addindent();
            this.outb("public Varargs invoke(Varargs $arg) {");

            for(int var4 = 0; var4 < var3; ++var4) {
               Name var5 = (Name)var1.parlist.names.get(var4);
               String var6 = var4 > 0?"$arg.arg(" + (var4 + 1) + ")":"$arg.arg1()";
               this.singleLocalDeclareAssign(var5, var6);
            }

            if(var1.parlist.isvararg) {
               Variable var7 = var1.scope.find("arg");
               this.javascope.setJavaName(var7, "arg");
               if(var3 > 0) {
                  this.outl("$arg = $arg.subargs(" + (var3 + 1) + ");");
               }

               String var8 = this.javascope.usesvarargs?"NIL":"LuaValue.tableOf($arg,1)";
               this.singleLocalDeclareAssign(var7, var8);
            }
         }

         this.writeBodyBlock(var1.block);
         this.oute("}");
         this.subindent();
         this.outi("}");
         this.javascope = this.javascope.popJavaScope();
      }

      private String declareArg(Name var1) {
         String var2 = this.javascope.getJavaName(var1.variable);
         return "LuaValue " + var2 + (var1.variable.isupvalue?"$0":"");
      }

      private void assignArg(Name var1) {
         if(var1.variable.isupvalue) {
            String var2 = this.javascope.getJavaName(var1.variable);
            this.singleLocalDeclareAssign(var1, var2 + "$0");
         }

      }

      public void visit(Stat.FuncDef var1) {
         Writer var2 = this.pushWriter();
         var1.body.accept(this);
         String var3 = this.popWriter(var2);
         int var4 = var1.name.dots != null?var1.name.dots.size():0;
         boolean var5 = var1.name.method != null;
         if(var4 > 0 && !var5 && var1.name.name.variable.isLocal()) {
            this.singleAssign(var1.name.name, var3);
         } else if(var4 == 0 && !var5) {
            this.singleAssign(var1.name.name, var3);
         } else {
            this.singleReference(var1.name.name);

            for(int var6 = 0; var6 < var4 - 1 || var5 && var6 < var4; ++var6) {
               this.out(".get(" + this.evalStringConstant((String)var1.name.dots.get(var6)) + ")");
            }

            this.outr(".set(" + this.evalStringConstant(var5?var1.name.method:(String)var1.name.dots.get(var4)) + ", " + var3 + ");");
         }

      }

      public void visit(Stat.LocalFuncDef var1) {
         final Name var2 = var1.name;
         final boolean[] var3 = new boolean[]{false};
         var1.body.accept(new Visitor() {
            public void visit(Name var1) {
               if(var1.variable == var2.variable) {
                  var3[0] = true;
                  var1.variable.hasassignments = true;
               }

            }
         });
         Writer var4 = this.pushWriter();
         super.visit(var1);
         String var5 = this.popWriter(var4);
         if(var3[0]) {
            String var6 = this.javascope.getJavaName(var2.variable);
            this.outl("final LuaValue[] " + var6 + " = new LuaValue[1];");
            this.outl(var6 + "[0] = " + var5 + ";");
         } else {
            this.singleLocalDeclareAssign(var2, var5);
         }

      }

      public void visit(Stat.NumericFor var1) {
         String var2 = this.javascope.getJavaName(var1.name.variable);
         String var3 = var2 + "$0";
         this.outi("for ( double " + var3 + "=" + this.evalLuaValue(var1.initial) + ".checkdouble(), " + var2 + "$limit=" + this.evalLuaValue(var1.limit) + ".checkdouble()");
         if(var1.step == null) {
            this.outr("; " + var3 + "<=" + var2 + "$limit; ++" + var3 + " ) {");
         } else {
            this.out(", " + var2 + "$step=" + this.evalLuaValue(var1.step) + ".checkdouble()");
            this.out("; " + var2 + "$step>0? (" + var3 + "<=" + var2 + "$limit): (" + var3 + ">=" + var2 + "$limit);");
            this.outr(" " + var3 + "+=" + var2 + "$step ) {");
         }

         this.addindent();
         this.singleLocalDeclareAssign(var1.name, "valueOf(" + var3 + ")");
         super.visit(var1.block);
         this.oute("}");
      }

      private Name tmpJavaVar(String var1) {
         Name var2 = new Name(var1);
         var2.variable = this.javascope.define(var1);
         return var2;
      }

      public void visit(Stat.GenericFor var1) {
         Name var2 = this.tmpJavaVar("f");
         Name var3 = this.tmpJavaVar("s");
         Name var4 = this.tmpJavaVar("var");
         Name var5 = this.tmpJavaVar("v");
         String var6 = this.javascope.getJavaName(var2.variable);
         String var7 = this.javascope.getJavaName(var3.variable);
         String var8 = this.javascope.getJavaName(var4.variable);
         String var9 = this.javascope.getJavaName(var5.variable);
         this.outl("LuaValue " + var6 + "," + var7 + "," + var8 + ";");
         this.outl("Varargs " + var9 + ";");
         ArrayList var10 = new ArrayList();
         var10.add(var2);
         var10.add(var3);
         var10.add(var4);
         this.multiAssign(var10, var1.exps);
         this.outb("while (true) {");
         this.outl(var9 + " = " + var6 + ".invoke(varargsOf(" + var7 + "," + var8 + "));");
         this.outl("if ((" + var8 + "=" + var9 + ".arg1()).isnil()) break;");
         this.singleLocalDeclareAssign((Name)var1.names.get(0), var8);
         int var11 = 1;

         for(int var12 = var1.names.size(); var11 < var12; ++var11) {
            this.singleLocalDeclareAssign((Name)var1.names.get(var11), var9 + ".arg(" + (var11 + 1) + ")");
         }

         super.visit(var1.block);
         this.oute("}");
      }

      public void visit(ParList var1) {
         super.visit(var1);
      }

      public void visit(Stat.IfThenElse var1) {
         this.outb("if ( " + this.evalBoolean(var1.ifexp) + " ) {");
         super.visit(var1.ifblock);
         if(var1.elseifblocks != null) {
            int var2 = 0;

            for(int var3 = var1.elseifblocks.size(); var2 < var3; ++var2) {
               this.subindent();
               this.outl("} else if ( " + this.evalBoolean((Exp)var1.elseifexps.get(var2)) + " ) {");
               this.addindent();
               super.visit((Block)var1.elseifblocks.get(var2));
            }
         }

         if(var1.elseblock != null) {
            this.subindent();
            this.outl("} else {");
            this.addindent();
            super.visit(var1.elseblock);
         }

         this.oute("}");
      }

      public void visit(Stat.RepeatUntil var1) {
         this.outb("do {");
         super.visit(var1.block);
         this.oute("} while (!" + this.evalBoolean(var1.exp) + ");");
      }

      public void visit(TableConstructor var1) {
         int var2 = var1.fields != null?var1.fields.size():0;
         ArrayList var3 = new ArrayList();
         ArrayList var4 = new ArrayList();

         int var5;
         for(var5 = 0; var5 < var2; ++var5) {
            TableField var6 = (TableField)var1.fields.get(var5);
            (var6.name == null && var6.index == null?var4:var3).add(var6);
         }

         var5 = var3.size();
         int var11 = var4.size();
         this.out(var5 == 0 && var11 != 0?"LuaValue.listOf(":"LuaValue.tableOf(");
         if(var5 != 0) {
            this.out("new LuaValue[]{");
            int var7 = 0;

            for(int var8 = var3.size(); var7 < var8; ++var7) {
               TableField var9 = (TableField)var3.get(var7);
               if(var9.name != null) {
                  this.out(this.evalStringConstant(var9.name) + ",");
               } else {
                  this.out(this.evalLuaValue(var9.index) + ",");
               }

               this.out(this.evalLuaValue(var9.rhs) + ",");
            }

            this.out("}");
         }

         if(var11 != 0) {
            this.out((var5 != 0?",":"") + "new LuaValue[]{");
            Exp var12 = ((TableField)var4.get(var11 - 1)).rhs;
            boolean var13 = var12.isvarargexp();
            int var14 = 0;

            for(int var10 = var13?var11 - 1:var11; var14 < var10; ++var14) {
               this.out(this.evalLuaValue(((TableField)var4.get(var14)).rhs) + ",");
            }

            this.out(var13?"}, " + this.evalVarargs(var12):"}");
         }

         this.out(")");
      }

      public void visit(Stat.WhileDo var1) {
         this.outb("while (" + this.evalBoolean(var1.exp) + ") {");
         super.visit(var1.block);
         this.oute("}");
      }

      public void visitExps(List var1) {
         super.visitExps(var1);
      }

      public void visitNames(List var1) {
         super.visitNames(var1);
      }

      public void visitVars(List var1) {
         super.visitVars(var1);
      }
   }
}
