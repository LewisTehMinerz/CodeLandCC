package org.luaj.vm2.luajc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.bcel.generic.AASTORE;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ANEWARRAY;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.CompoundInstruction;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.IFEQ;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.luajc.ProtoInfo;

public class JavaBuilder {

   private static final String STR_VARARGS = (class$org$luaj$vm2$Varargs == null?(class$org$luaj$vm2$Varargs = class$("org.luaj.vm2.Varargs")):class$org$luaj$vm2$Varargs).getName();
   private static final String STR_LUAVALUE = (class$org$luaj$vm2$LuaValue == null?(class$org$luaj$vm2$LuaValue = class$("org.luaj.vm2.LuaValue")):class$org$luaj$vm2$LuaValue).getName();
   private static final String STR_LUASTRING = (class$org$luaj$vm2$LuaString == null?(class$org$luaj$vm2$LuaString = class$("org.luaj.vm2.LuaString")):class$org$luaj$vm2$LuaString).getName();
   private static final String STR_LUAINTEGER = (class$org$luaj$vm2$LuaInteger == null?(class$org$luaj$vm2$LuaInteger = class$("org.luaj.vm2.LuaInteger")):class$org$luaj$vm2$LuaInteger).getName();
   private static final String STR_LUADOUBLE = (class$org$luaj$vm2$LuaDouble == null?(class$org$luaj$vm2$LuaDouble = class$("org.luaj.vm2.LuaDouble")):class$org$luaj$vm2$LuaDouble).getName();
   private static final String STR_LUANUMBER = (class$org$luaj$vm2$LuaNumber == null?(class$org$luaj$vm2$LuaNumber = class$("org.luaj.vm2.LuaNumber")):class$org$luaj$vm2$LuaNumber).getName();
   private static final String STR_LUABOOLEAN = (class$org$luaj$vm2$LuaBoolean == null?(class$org$luaj$vm2$LuaBoolean = class$("org.luaj.vm2.LuaBoolean")):class$org$luaj$vm2$LuaBoolean).getName();
   private static final String STR_LUATABLE = (class$org$luaj$vm2$LuaTable == null?(class$org$luaj$vm2$LuaTable = class$("org.luaj.vm2.LuaTable")):class$org$luaj$vm2$LuaTable).getName();
   private static final String STR_BUFFER = (class$org$luaj$vm2$Buffer == null?(class$org$luaj$vm2$Buffer = class$("org.luaj.vm2.Buffer")):class$org$luaj$vm2$Buffer).getName();
   private static final String STR_STRING = (class$java$lang$String == null?(class$java$lang$String = class$("java.lang.String")):class$java$lang$String).getName();
   private static final ObjectType TYPE_VARARGS = new ObjectType(STR_VARARGS);
   private static final ObjectType TYPE_LUAVALUE = new ObjectType(STR_LUAVALUE);
   private static final ObjectType TYPE_LUASTRING = new ObjectType(STR_LUASTRING);
   private static final ObjectType TYPE_LUAINTEGER = new ObjectType(STR_LUAINTEGER);
   private static final ObjectType TYPE_LUADOUBLE = new ObjectType(STR_LUADOUBLE);
   private static final ObjectType TYPE_LUANUMBER = new ObjectType(STR_LUANUMBER);
   private static final ObjectType TYPE_LUABOOLEAN = new ObjectType(STR_LUABOOLEAN);
   private static final ObjectType TYPE_LUATABLE = new ObjectType(STR_LUATABLE);
   private static final ObjectType TYPE_BUFFER = new ObjectType(STR_BUFFER);
   private static final ArrayType TYPE_LOCALUPVALUE = new ArrayType(TYPE_LUAVALUE, 1);
   private static final ArrayType TYPE_CHARARRAY = new ArrayType(Type.CHAR, 1);
   private static final Class[] NO_INNER_CLASSES = new Class[0];
   private static final String STR_FUNCV = (class$org$luaj$vm2$lib$VarArgFunction == null?(class$org$luaj$vm2$lib$VarArgFunction = class$("org.luaj.vm2.lib.VarArgFunction")):class$org$luaj$vm2$lib$VarArgFunction).getName();
   private static final String STR_FUNC0 = (class$org$luaj$vm2$lib$ZeroArgFunction == null?(class$org$luaj$vm2$lib$ZeroArgFunction = class$("org.luaj.vm2.lib.ZeroArgFunction")):class$org$luaj$vm2$lib$ZeroArgFunction).getName();
   private static final String STR_FUNC1 = (class$org$luaj$vm2$lib$OneArgFunction == null?(class$org$luaj$vm2$lib$OneArgFunction = class$("org.luaj.vm2.lib.OneArgFunction")):class$org$luaj$vm2$lib$OneArgFunction).getName();
   private static final String STR_FUNC2 = (class$org$luaj$vm2$lib$TwoArgFunction == null?(class$org$luaj$vm2$lib$TwoArgFunction = class$("org.luaj.vm2.lib.TwoArgFunction")):class$org$luaj$vm2$lib$TwoArgFunction).getName();
   private static final String STR_FUNC3 = (class$org$luaj$vm2$lib$ThreeArgFunction == null?(class$org$luaj$vm2$lib$ThreeArgFunction = class$("org.luaj.vm2.lib.ThreeArgFunction")):class$org$luaj$vm2$lib$ThreeArgFunction).getName();
   private static final Type[] ARG_TYPES_NONE = new Type[0];
   private static final Type[] ARG_TYPES_INT = new Type[]{Type.INT};
   private static final Type[] ARG_TYPES_DOUBLE = new Type[]{Type.DOUBLE};
   private static final Type[] ARG_TYPES_STRING = new Type[]{Type.STRING};
   private static final Type[] ARG_TYPES_CHARARRAY = new Type[]{TYPE_CHARARRAY};
   private static final Type[] ARG_TYPES_VARARGS_INT = new Type[]{TYPE_VARARGS, Type.INT};
   private static final Type[] ARG_TYPES_INT_LUAVALUE = new Type[]{Type.INT, TYPE_LUAVALUE};
   private static final Type[] ARG_TYPES_INT_VARARGS = new Type[]{Type.INT, TYPE_VARARGS};
   private static final Type[] ARG_TYPES_LUAVALUE_VARARGS = new Type[]{TYPE_LUAVALUE, TYPE_VARARGS};
   private static final Type[] ARG_TYPES_LUAVALUE_LUAVALUE_VARARGS = new Type[]{TYPE_LUAVALUE, TYPE_LUAVALUE, TYPE_VARARGS};
   private static final Type[] ARG_TYPES_LUAVALUEARRAY = new Type[]{new ArrayType(TYPE_LUAVALUE, 1)};
   private static final Type[] ARG_TYPES_LUAVALUEARRAY_VARARGS = new Type[]{new ArrayType(TYPE_LUAVALUE, 1), TYPE_VARARGS};
   private static final Type[] ARG_TYPES_LUAVALUE_LUAVALUE_LUAVALUE = new Type[]{TYPE_LUAVALUE, TYPE_LUAVALUE, TYPE_LUAVALUE};
   private static final Type[] ARG_TYPES_VARARGS = new Type[]{TYPE_VARARGS};
   private static final Type[] ARG_TYPES_LUAVALUE_LUAVALUE = new Type[]{TYPE_LUAVALUE, TYPE_LUAVALUE};
   private static final Type[] ARG_TYPES_INT_INT = new Type[]{Type.INT, Type.INT};
   private static final Type[] ARG_TYPES_LUAVALUE = new Type[]{TYPE_LUAVALUE};
   private static final Type[] ARG_TYPES_BUFFER = new Type[]{TYPE_BUFFER};
   private static final String[] SUPER_NAME_N = new String[]{STR_FUNC0, STR_FUNC1, STR_FUNC2, STR_FUNC3, STR_FUNCV};
   private static final ObjectType[] RETURN_TYPE_N = new ObjectType[]{TYPE_LUAVALUE, TYPE_LUAVALUE, TYPE_LUAVALUE, TYPE_LUAVALUE, TYPE_VARARGS};
   private static final Type[][] ARG_TYPES_N = new Type[][]{ARG_TYPES_NONE, ARG_TYPES_LUAVALUE, ARG_TYPES_LUAVALUE_LUAVALUE, ARG_TYPES_LUAVALUE_LUAVALUE_LUAVALUE, ARG_TYPES_VARARGS};
   private static final String[][] ARG_NAMES_N = new String[][]{new String[0], {"arg"}, {"arg1", "arg2"}, {"arg1", "arg2", "arg3"}, {"args"}};
   private static final String[] METH_NAME_N = new String[]{"call", "call", "call", "call", "onInvoke"};
   private static final String PREFIX_CONSTANT = "k";
   private static final String PREFIX_UPVALUE = "u";
   private static final String PREFIX_PLAIN_SLOT = "s";
   private static final String PREFIX_UPVALUE_SLOT = "a";
   private static final String NAME_VARRESULT = "v";
   private final ProtoInfo pi;
   private final Prototype p;
   private final String classname;
   private final ClassGen cg;
   private final ConstantPoolGen cp;
   private final InstructionFactory factory;
   private final InstructionList init;
   private final InstructionList main;
   private final MethodGen mg;
   private int superclassType;
   private static int SUPERTYPE_VARARGS = 4;
   private final int[] targets;
   private final BranchInstruction[] branches;
   private final InstructionHandle[] branchDestHandles;
   private InstructionHandle beginningOfLuaInstruction;
   private LocalVariableGen varresult = null;
   private Map plainSlotVars = new HashMap();
   private Map upvalueSlotVars = new HashMap();
   private Map constants = new HashMap();
   public static final int BRANCH_GOTO = 1;
   public static final int BRANCH_IFNE = 2;
   public static final int BRANCH_IFEQ = 3;
   static Class class$org$luaj$vm2$Varargs;
   static Class class$org$luaj$vm2$LuaValue;
   static Class class$org$luaj$vm2$LuaString;
   static Class class$org$luaj$vm2$LuaInteger;
   static Class class$org$luaj$vm2$LuaDouble;
   static Class class$org$luaj$vm2$LuaNumber;
   static Class class$org$luaj$vm2$LuaBoolean;
   static Class class$org$luaj$vm2$LuaTable;
   static Class class$org$luaj$vm2$Buffer;
   static Class class$java$lang$String;
   static Class class$org$luaj$vm2$lib$VarArgFunction;
   static Class class$org$luaj$vm2$lib$ZeroArgFunction;
   static Class class$org$luaj$vm2$lib$OneArgFunction;
   static Class class$org$luaj$vm2$lib$TwoArgFunction;
   static Class class$org$luaj$vm2$lib$ThreeArgFunction;


   public JavaBuilder(ProtoInfo var1, String var2, String var3) {
      this.pi = var1;
      this.p = var1.prototype;
      this.classname = var2;
      this.superclassType = this.p.numparams;
      if(this.p.is_vararg != 0 || this.superclassType >= SUPERTYPE_VARARGS) {
         this.superclassType = SUPERTYPE_VARARGS;
      }

      int var4 = 0;

      for(int var5 = this.p.code.length; var4 < var5; ++var4) {
         int var6 = this.p.code[var4];
         int var7 = Lua.GET_OPCODE(var6);
         if(var7 == 29 || var7 == 30 && (Lua.GETARG_B(var6) < 1 || Lua.GETARG_B(var6) > 2)) {
            this.superclassType = SUPERTYPE_VARARGS;
            break;
         }
      }

      this.cg = new ClassGen(var2, SUPER_NAME_N[this.superclassType], var3, 33, (String[])null);
      this.cp = this.cg.getConstantPool();
      this.factory = new InstructionFactory(this.cg);
      this.init = new InstructionList();
      this.main = new InstructionList();

      for(var4 = 0; var4 < this.p.nups; ++var4) {
         boolean var8 = var1.isReadWriteUpvalue(var1.upvals[var4]);
         Object var9 = var8?TYPE_LOCALUPVALUE:TYPE_LUAVALUE;
         FieldGen var10 = new FieldGen(0, (Type)var9, upvalueName(var4), this.cp);
         this.cg.addField(var10.getField());
      }

      this.mg = new MethodGen(17, RETURN_TYPE_N[this.superclassType], ARG_TYPES_N[this.superclassType], ARG_NAMES_N[this.superclassType], METH_NAME_N[this.superclassType], STR_LUAVALUE, this.main, this.cp);
      this.initializeSlots();
      var4 = this.p.code.length;
      this.targets = new int[var4];
      this.branches = new BranchInstruction[var4];
      this.branchDestHandles = new InstructionHandle[var4];
   }

   public void initializeSlots() {
      boolean var1 = false;
      this.createUpvalues(-1, 0, this.p.maxstacksize);
      int var3;
      if(this.superclassType == SUPERTYPE_VARARGS) {
         for(var3 = 0; var3 < this.p.numparams; ++var3) {
            if(this.pi.isInitialValueUsed(var3)) {
               this.append((Instruction)(new ALOAD(1)));
               this.append((CompoundInstruction)(new PUSH(this.cp, var3 + 1)));
               this.append((Instruction)this.factory.createInvoke(STR_VARARGS, "arg", TYPE_LUAVALUE, ARG_TYPES_INT, (short)182));
               this.storeLocal(-1, var3);
            }
         }

         boolean var2 = (this.p.is_vararg & 4) != 0;
         if(var2) {
            this.append((Instruction)(new ALOAD(1)));
            this.append((CompoundInstruction)(new PUSH(this.cp, 1 + this.p.numparams)));
            this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "tableOf", TYPE_LUATABLE, ARG_TYPES_VARARGS_INT, (short)184));
            this.storeLocal(-1, var3++);
         } else if(this.p.numparams > 0) {
            this.append((Instruction)(new ALOAD(1)));
            this.append((CompoundInstruction)(new PUSH(this.cp, 1 + this.p.numparams)));
            this.append((Instruction)this.factory.createInvoke(STR_VARARGS, "subargs", TYPE_VARARGS, ARG_TYPES_INT, (short)182));
            this.append((Instruction)(new ASTORE(1)));
         }
      } else {
         for(var3 = 0; var3 < this.p.numparams; ++var3) {
            this.plainSlotVars.put(Integer.valueOf(var3), Integer.valueOf(1 + var3));
            if(this.pi.isUpvalueCreate(-1, var3)) {
               this.append((Instruction)(new ALOAD(1 + var3)));
               this.storeLocal(-1, var3);
            }
         }
      }

      for(; var3 < this.p.maxstacksize; ++var3) {
         if(this.pi.isInitialValueUsed(var3)) {
            this.loadNil();
            this.storeLocal(-1, var3);
         }
      }

   }

   public byte[] completeClass() {
      if(!this.init.isEmpty()) {
         MethodGen var1 = new MethodGen(8, Type.VOID, ARG_TYPES_NONE, new String[0], "<clinit>", this.cg.getClassName(), this.init, this.cg.getConstantPool());
         this.init.append(InstructionConstants.RETURN);
         var1.setMaxStack();
         this.cg.addMethod(var1.getMethod());
         this.init.dispose();
      }

      this.cg.addEmptyConstructor(1);
      this.resolveBranches();
      this.mg.setMaxStack();
      this.cg.addMethod(this.mg.getMethod());
      this.main.dispose();

      try {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         this.cg.getJavaClass().dump(var3);
         return var3.toByteArray();
      } catch (IOException var2) {
         throw new RuntimeException("JavaClass.dump() threw " + var2);
      }
   }

   public void dup() {
      this.append((Instruction)InstructionConstants.DUP);
   }

   public void pop() {
      this.append((Instruction)InstructionConstants.POP);
   }

   public void loadNil() {
      this.append((Instruction)this.factory.createFieldAccess(STR_LUAVALUE, "NIL", TYPE_LUAVALUE, (short)178));
   }

   public void loadNone() {
      this.append((Instruction)this.factory.createFieldAccess(STR_LUAVALUE, "NONE", TYPE_LUAVALUE, (short)178));
   }

   public void loadBoolean(boolean var1) {
      String var2 = var1?"TRUE":"FALSE";
      this.append((Instruction)this.factory.createFieldAccess(STR_LUAVALUE, var2, TYPE_LUABOOLEAN, (short)178));
   }

   private int findSlot(int var1, Map var2, String var3, Type var4) {
      Integer var5 = Integer.valueOf(var1);
      if(var2.containsKey(var5)) {
         return ((Integer)var2.get(var5)).intValue();
      } else {
         String var6 = var3 + var1;
         LocalVariableGen var7 = this.mg.addLocalVariable(var6, var4, (InstructionHandle)null, (InstructionHandle)null);
         int var8 = var7.getIndex();
         var2.put(var5, Integer.valueOf(var8));
         return var8;
      }
   }

   private int findSlotIndex(int var1, boolean var2) {
      return var2?this.findSlot(var1, this.upvalueSlotVars, "a", TYPE_LOCALUPVALUE):this.findSlot(var1, this.plainSlotVars, "s", TYPE_LUAVALUE);
   }

   public void loadLocal(int var1, int var2) {
      boolean var3 = this.pi.isUpvalueRefer(var1, var2);
      int var4 = this.findSlotIndex(var2, var3);
      this.append((Instruction)(new ALOAD(var4)));
      if(var3) {
         this.append((CompoundInstruction)(new PUSH(this.cp, 0)));
         this.append((Instruction)InstructionConstants.AALOAD);
      }

   }

   public void storeLocal(int var1, int var2) {
      boolean var3 = this.pi.isUpvalueAssign(var1, var2);
      int var4 = this.findSlotIndex(var2, var3);
      if(var3) {
         boolean var5 = this.pi.isUpvalueCreate(var1, var2);
         if(var5) {
            this.append((Instruction)this.factory.createInvoke(this.classname, "newupe", TYPE_LOCALUPVALUE, ARG_TYPES_NONE, (short)184));
            this.append((Instruction)InstructionConstants.DUP);
            this.append((Instruction)(new ASTORE(var4)));
         } else {
            this.append((Instruction)(new ALOAD(var4)));
         }

         this.append((Instruction)InstructionConstants.SWAP);
         this.append((CompoundInstruction)(new PUSH(this.cp, 0)));
         this.append((Instruction)InstructionConstants.SWAP);
         this.append((Instruction)InstructionConstants.AASTORE);
      } else {
         this.append((Instruction)(new ASTORE(var4)));
      }

   }

   public void createUpvalues(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2 + var4;
         boolean var6 = this.pi.isUpvalueCreate(var1, var5);
         if(var6) {
            int var7 = this.findSlotIndex(var5, true);
            this.append((Instruction)this.factory.createInvoke(this.classname, "newupn", TYPE_LOCALUPVALUE, ARG_TYPES_NONE, (short)184));
            this.append((Instruction)(new ASTORE(var7)));
         }
      }

   }

   public void convertToUpvalue(int var1, int var2) {
      boolean var3 = this.pi.isUpvalueAssign(var1, var2);
      if(var3) {
         int var4 = this.findSlotIndex(var2, false);
         this.append((Instruction)(new ALOAD(var4)));
         this.append((Instruction)this.factory.createInvoke(this.classname, "newupl", TYPE_LOCALUPVALUE, ARG_TYPES_LUAVALUE, (short)184));
         int var5 = this.findSlotIndex(var2, true);
         this.append((Instruction)(new ASTORE(var5)));
      }

   }

   private static String upvalueName(int var0) {
      return "u" + var0;
   }

   public void loadUpvalue(int var1) {
      boolean var2 = this.pi.isReadWriteUpvalue(this.pi.upvals[var1]);
      this.append((Instruction)InstructionConstants.THIS);
      if(var2) {
         this.append((Instruction)this.factory.createFieldAccess(this.classname, upvalueName(var1), TYPE_LOCALUPVALUE, (short)180));
         this.append((CompoundInstruction)(new PUSH(this.cp, 0)));
         this.append((Instruction)InstructionConstants.AALOAD);
      } else {
         this.append((Instruction)this.factory.createFieldAccess(this.classname, upvalueName(var1), TYPE_LUAVALUE, (short)180));
      }

   }

   public void storeUpvalue(int var1, int var2, int var3) {
      boolean var4 = this.pi.isReadWriteUpvalue(this.pi.upvals[var2]);
      this.append((Instruction)InstructionConstants.THIS);
      if(var4) {
         this.append((Instruction)this.factory.createFieldAccess(this.classname, upvalueName(var2), TYPE_LOCALUPVALUE, (short)180));
         this.append((CompoundInstruction)(new PUSH(this.cp, 0)));
         this.loadLocal(var1, var3);
         this.append((Instruction)InstructionConstants.AASTORE);
      } else {
         this.loadLocal(var1, var3);
         this.append((Instruction)this.factory.createFieldAccess(this.classname, upvalueName(var2), TYPE_LUAVALUE, (short)181));
      }

   }

   public void newTable(int var1, int var2) {
      this.append((CompoundInstruction)(new PUSH(this.cp, var1)));
      this.append((CompoundInstruction)(new PUSH(this.cp, var2)));
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "tableOf", TYPE_LUATABLE, ARG_TYPES_INT_INT, (short)184));
   }

   public void loadEnv() {
      this.append((Instruction)InstructionConstants.THIS);
      this.append((Instruction)this.factory.createFieldAccess(this.classname, "env", TYPE_LUAVALUE, (short)180));
   }

   public void loadVarargs() {
      this.append((Instruction)(new ALOAD(1)));
   }

   public void loadVarargs(int var1) {
      this.loadVarargs();
      this.arg(var1);
   }

   public void arg(int var1) {
      if(var1 == 1) {
         this.append((Instruction)this.factory.createInvoke(STR_VARARGS, "arg1", TYPE_LUAVALUE, ARG_TYPES_NONE, (short)182));
      } else {
         this.append((CompoundInstruction)(new PUSH(this.cp, var1)));
         this.append((Instruction)this.factory.createInvoke(STR_VARARGS, "arg", TYPE_LUAVALUE, ARG_TYPES_INT, (short)182));
      }

   }

   private int getVarresultIndex() {
      if(this.varresult == null) {
         this.varresult = this.mg.addLocalVariable("v", TYPE_VARARGS, (InstructionHandle)null, (InstructionHandle)null);
      }

      return this.varresult.getIndex();
   }

   public void loadVarresult() {
      this.append((Instruction)(new ALOAD(this.getVarresultIndex())));
   }

   public void storeVarresult() {
      this.append((Instruction)(new ASTORE(this.getVarresultIndex())));
   }

   public void subargs(int var1) {
      this.append((CompoundInstruction)(new PUSH(this.cp, var1)));
      this.append((Instruction)this.factory.createInvoke(STR_VARARGS, "subargs", TYPE_VARARGS, ARG_TYPES_INT, (short)182));
   }

   public void getTable() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "get", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short)182));
   }

   public void setTable() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "set", Type.VOID, ARG_TYPES_LUAVALUE_LUAVALUE, (short)182));
   }

   public void unaryop(int var1) {
      String var2;
      switch(var1) {
      case 18:
      default:
         var2 = "neg";
         break;
      case 19:
         var2 = "not";
         break;
      case 20:
         var2 = "len";
      }

      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, var2, TYPE_LUAVALUE, Type.NO_ARGS, (short)182));
   }

   public void binaryop(int var1) {
      String var2;
      switch(var1) {
      case 12:
      default:
         var2 = "add";
         break;
      case 13:
         var2 = "sub";
         break;
      case 14:
         var2 = "mul";
         break;
      case 15:
         var2 = "div";
         break;
      case 16:
         var2 = "mod";
         break;
      case 17:
         var2 = "pow";
      }

      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, var2, TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short)182));
   }

   public void compareop(int var1) {
      String var2;
      switch(var1) {
      case 23:
      default:
         var2 = "eq_b";
         break;
      case 24:
         var2 = "lt_b";
         break;
      case 25:
         var2 = "lteq_b";
      }

      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, var2, Type.BOOLEAN, ARG_TYPES_LUAVALUE, (short)182));
   }

   public void areturn() {
      this.append((Instruction)InstructionConstants.ARETURN);
   }

   public void toBoolean() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "toboolean", Type.BOOLEAN, Type.NO_ARGS, (short)182));
   }

   public void tostring() {
      this.append((Instruction)this.factory.createInvoke(STR_BUFFER, "tostring", TYPE_LUASTRING, Type.NO_ARGS, (short)182));
   }

   public void isNil() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "isnil", Type.BOOLEAN, Type.NO_ARGS, (short)182));
   }

   public void testForLoop() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "testfor_b", Type.BOOLEAN, ARG_TYPES_LUAVALUE_LUAVALUE, (short)182));
   }

   public void loadArrayArgs(int var1, int var2, int var3) {
      this.append((CompoundInstruction)(new PUSH(this.cp, var3)));
      this.append((Instruction)(new ANEWARRAY(this.cp.addClass(STR_LUAVALUE))));

      for(int var4 = 0; var4 < var3; ++var4) {
         this.append((Instruction)InstructionConstants.DUP);
         this.append((CompoundInstruction)(new PUSH(this.cp, var4)));
         this.loadLocal(var1, var2++);
         this.append((Instruction)(new AASTORE()));
      }

   }

   public void newVarargs(int var1, int var2, int var3) {
      switch(var3) {
      case 0:
         this.loadNone();
         break;
      case 1:
         this.loadLocal(var1, var2);
         break;
      case 2:
         this.loadLocal(var1, var2);
         this.loadLocal(var1, var2 + 1);
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUE_VARARGS, (short)184));
         break;
      case 3:
         this.loadLocal(var1, var2);
         this.loadLocal(var1, var2 + 1);
         this.loadLocal(var1, var2 + 2);
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUE_LUAVALUE_VARARGS, (short)184));
         break;
      default:
         this.loadArrayArgs(var1, var2, var3);
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUEARRAY, (short)184));
      }

   }

   public void newVarargsVarresult(int var1, int var2, int var3) {
      this.loadArrayArgs(var1, var2, var3);
      this.loadVarresult();
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUEARRAY_VARARGS, (short)184));
   }

   public void call(int var1) {
      switch(var1) {
      case 0:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_NONE, (short)182));
         break;
      case 1:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short)182));
         break;
      case 2:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE_LUAVALUE, (short)182));
         break;
      case 3:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE_LUAVALUE_LUAVALUE, (short)182));
         break;
      default:
         throw new IllegalArgumentException("can\'t call with " + var1 + " args");
      }

   }

   public void newTailcallVarargs() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "tailcallOf", TYPE_VARARGS, ARG_TYPES_LUAVALUE_VARARGS, (short)184));
   }

   public void invoke(int var1) {
      switch(var1) {
      case -1:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_VARARGS, (short)182));
         break;
      case 0:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_NONE, (short)182));
         break;
      case 1:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_VARARGS, (short)182));
         break;
      case 2:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_LUAVALUE_VARARGS, (short)182));
         break;
      case 3:
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_LUAVALUE_LUAVALUE_VARARGS, (short)182));
         break;
      default:
         throw new IllegalArgumentException("can\'t invoke with " + var1 + " args");
      }

   }

   public void closureCreate(String var1) {
      this.append((Instruction)this.factory.createNew(new ObjectType(var1)));
      this.append((Instruction)InstructionConstants.DUP);
      this.append((Instruction)this.factory.createInvoke(var1, "<init>", Type.VOID, Type.NO_ARGS, (short)183));
      this.append((Instruction)InstructionConstants.DUP);
      this.loadEnv();
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "setfenv", Type.VOID, ARG_TYPES_LUAVALUE, (short)182));
   }

   public void closureInitUpvalueFromUpvalue(String var1, int var2, int var3) {
      boolean var4 = this.pi.isReadWriteUpvalue(this.pi.upvals[var3]);
      Object var5 = var4?TYPE_LOCALUPVALUE:TYPE_LUAVALUE;
      String var6 = upvalueName(var3);
      String var7 = upvalueName(var2);
      this.append((Instruction)InstructionConstants.THIS);
      this.append((Instruction)this.factory.createFieldAccess(this.classname, var6, (Type)var5, (short)180));
      this.append((Instruction)this.factory.createFieldAccess(var1, var7, (Type)var5, (short)181));
   }

   public void closureInitUpvalueFromLocal(String var1, int var2, int var3, int var4) {
      boolean var5 = this.pi.isReadWriteUpvalue(this.pi.vars[var4][var3].upvalue);
      Object var6 = var5?TYPE_LOCALUPVALUE:TYPE_LUAVALUE;
      String var7 = upvalueName(var2);
      int var8 = this.findSlotIndex(var4, var5);
      this.append((Instruction)(new ALOAD(var8)));
      this.append((Instruction)this.factory.createFieldAccess(var1, var7, (Type)var6, (short)181));
   }

   public void loadConstant(LuaValue var1) {
      switch(var1.type()) {
      case 0:
         this.loadNil();
         break;
      case 1:
         this.loadBoolean(var1.toboolean());
         break;
      case 2:
      default:
         throw new IllegalArgumentException("bad constant type: " + var1.type());
      case 3:
      case 4:
         String var2 = (String)this.constants.get(var1);
         if(var2 == null) {
            var2 = var1.type() == 3?(var1.isinttype()?this.createLuaIntegerField(var1.checkint()):this.createLuaDoubleField(var1.checkdouble())):this.createLuaStringField(var1.checkstring());
            this.constants.put(var1, var2);
         }

         this.append((Instruction)this.factory.createGetStatic(this.classname, var2, TYPE_LUAVALUE));
      }

   }

   private String createLuaIntegerField(int var1) {
      String var2 = "k" + this.constants.size();
      FieldGen var3 = new FieldGen(24, TYPE_LUAVALUE, var2, this.cp);
      this.cg.addField(var3.getField());
      this.init.append(new PUSH(this.cp, var1));
      this.init.append(this.factory.createInvoke(STR_LUAVALUE, "valueOf", TYPE_LUAINTEGER, ARG_TYPES_INT, (short)184));
      this.init.append(this.factory.createPutStatic(this.classname, var2, TYPE_LUAVALUE));
      return var2;
   }

   private String createLuaDoubleField(double var1) {
      String var3 = "k" + this.constants.size();
      FieldGen var4 = new FieldGen(24, TYPE_LUAVALUE, var3, this.cp);
      this.cg.addField(var4.getField());
      this.init.append(new PUSH(this.cp, var1));
      this.init.append(this.factory.createInvoke(STR_LUAVALUE, "valueOf", TYPE_LUANUMBER, ARG_TYPES_DOUBLE, (short)184));
      this.init.append(this.factory.createPutStatic(this.classname, var3, TYPE_LUAVALUE));
      return var3;
   }

   private String createLuaStringField(LuaString var1) {
      String var2 = "k" + this.constants.size();
      FieldGen var3 = new FieldGen(24, TYPE_LUAVALUE, var2, this.cp);
      this.cg.addField(var3.getField());
      LuaString var4 = var1.checkstring();
      if(var4.isValidUtf8()) {
         this.init.append(new PUSH(this.cp, var1.tojstring()));
         this.init.append(this.factory.createInvoke(STR_LUASTRING, "valueOf", TYPE_LUASTRING, ARG_TYPES_STRING, (short)184));
      } else {
         char[] var5 = new char[var4.m_length];

         for(int var6 = 0; var6 < var4.m_length; ++var6) {
            var5[var6] = (char)(255 & var4.m_bytes[var4.m_offset + var6]);
         }

         this.init.append(new PUSH(this.cp, new String(var5)));
         this.init.append(this.factory.createInvoke(STR_STRING, "toCharArray", TYPE_CHARARRAY, Type.NO_ARGS, (short)182));
         this.init.append(this.factory.createInvoke(STR_LUASTRING, "valueOf", TYPE_LUASTRING, ARG_TYPES_CHARARRAY, (short)184));
      }

      this.init.append(this.factory.createPutStatic(this.classname, var2, TYPE_LUAVALUE));
      return var2;
   }

   public void addBranch(int var1, int var2, int var3) {
      switch(var2) {
      case 1:
      default:
         this.branches[var1] = new GOTO((InstructionHandle)null);
         break;
      case 2:
         this.branches[var1] = new IFNE((InstructionHandle)null);
         break;
      case 3:
         this.branches[var1] = new IFEQ((InstructionHandle)null);
      }

      this.targets[var1] = var3;
      this.append(this.branches[var1]);
   }

   private void append(Instruction var1) {
      this.conditionalSetBeginningOfLua(this.main.append(var1));
   }

   private void append(CompoundInstruction var1) {
      this.conditionalSetBeginningOfLua(this.main.append(var1));
   }

   private void append(BranchInstruction var1) {
      this.conditionalSetBeginningOfLua(this.main.append(var1));
   }

   private void conditionalSetBeginningOfLua(InstructionHandle var1) {
      if(this.beginningOfLuaInstruction == null) {
         this.beginningOfLuaInstruction = var1;
      }

   }

   public void onEndOfLuaInstruction(int var1) {
      this.branchDestHandles[var1] = this.beginningOfLuaInstruction;
      this.beginningOfLuaInstruction = null;
   }

   private void resolveBranches() {
      int var1 = this.p.code.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         if(this.branches[var2] != null) {
            int var3;
            for(var3 = this.targets[var2]; var3 < this.branchDestHandles.length && this.branchDestHandles[var3] == null; ++var3) {
               ;
            }

            if(var3 >= this.branchDestHandles.length) {
               throw new IllegalArgumentException("no target at or after " + this.targets[var2] + " op=" + Lua.GET_OPCODE(this.p.code[this.targets[var2]]));
            }

            this.branches[var2].setTarget(this.branchDestHandles[var3]);
         }
      }

   }

   public void setlistStack(int var1, int var2, int var3, int var4) {
      for(int var5 = 0; var5 < var4; ++var5) {
         this.dup();
         this.append((CompoundInstruction)(new PUSH(this.cp, var3 + var5)));
         this.loadLocal(var1, var2 + var5);
         this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "rawset", Type.VOID, ARG_TYPES_INT_LUAVALUE, (short)182));
      }

   }

   public void setlistVarargs(int var1, int var2) {
      this.append((CompoundInstruction)(new PUSH(this.cp, var1)));
      this.loadVarresult();
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "rawsetlist", Type.VOID, ARG_TYPES_INT_VARARGS, (short)182));
   }

   public void concatvalue() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "concat", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short)182));
   }

   public void concatbuffer() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "concat", TYPE_BUFFER, ARG_TYPES_BUFFER, (short)182));
   }

   public void tobuffer() {
      this.append((Instruction)this.factory.createInvoke(STR_LUAVALUE, "buffer", TYPE_BUFFER, Type.NO_ARGS, (short)182));
   }

   public void tovalue() {
      this.append((Instruction)this.factory.createInvoke(STR_BUFFER, "value", TYPE_LUAVALUE, Type.NO_ARGS, (short)182));
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

}
