package org.luaj.vm2.compiler;

import java.util.Hashtable;
import org.luaj.vm2.LocVars;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.InstructionPtr;
import org.luaj.vm2.compiler.IntPtr;
import org.luaj.vm2.compiler.LexState;
import org.luaj.vm2.compiler.LuaC;

public class FuncState extends LuaC {

   Prototype f;
   Hashtable htable;
   FuncState prev;
   LexState ls;
   LuaC L;
   FuncState.BlockCnt bl;
   int pc;
   int lasttarget;
   IntPtr jpc;
   int freereg;
   int nk;
   int np;
   short nlocvars;
   short nactvar;
   FuncState.upvaldesc[] upvalues = new FuncState.upvaldesc[60];
   short[] actvar = new short[200];


   InstructionPtr getcodePtr(LexState.expdesc var1) {
      return new InstructionPtr(this.f.code, var1.u.s.info);
   }

   int getcode(LexState.expdesc var1) {
      return this.f.code[var1.u.s.info];
   }

   int codeAsBx(int var1, int var2, int var3) {
      return this.codeABx(var1, var2, var3 + 131071);
   }

   void setmultret(LexState.expdesc var1) {
      this.setreturns(var1, -1);
   }

   LocVars getlocvar(int var1) {
      return this.f.locvars[this.actvar[var1]];
   }

   void checklimit(int var1, int var2, String var3) {
      if(var1 > var2) {
         this.errorlimit(var2, var3);
      }

   }

   void errorlimit(int var1, String var2) {
      String var3 = this.f.linedefined == 0?this.L.pushfstring("main function has more than " + var1 + " " + var2):this.L.pushfstring("function at line " + this.f.linedefined + " has more than " + var1 + " " + var2);
      this.ls.lexerror(var3, 0);
   }

   int indexupvalue(LuaString var1, LexState.expdesc var2) {
      for(int var3 = 0; var3 < this.f.nups; ++var3) {
         if(this.upvalues[var3].k == var2.k && this.upvalues[var3].info == var2.u.s.info) {
            _assert(this.f.upvalues[var3] == var1);
            return var3;
         }
      }

      this.checklimit(this.f.nups + 1, 60, "upvalues");
      if(this.f.upvalues == null || this.f.nups + 1 > this.f.upvalues.length) {
         this.f.upvalues = realloc(this.f.upvalues, this.f.nups * 2 + 1);
      }

      this.f.upvalues[this.f.nups] = var1;
      _assert(var2.k == 6 || var2.k == 7);
      this.upvalues[this.f.nups] = new FuncState.upvaldesc();
      this.upvalues[this.f.nups].k = (short)var2.k;
      this.upvalues[this.f.nups].info = (short)var2.u.s.info;
      return this.f.nups++;
   }

   int searchvar(LuaString var1) {
      for(int var2 = this.nactvar - 1; var2 >= 0; --var2) {
         if(var1 == this.getlocvar(var2).varname) {
            return var2;
         }
      }

      return -1;
   }

   void markupval(int var1) {
      FuncState.BlockCnt var2;
      for(var2 = this.bl; var2 != null && var2.nactvar > var1; var2 = var2.previous) {
         ;
      }

      if(var2 != null) {
         var2.upval = true;
      }

   }

   int singlevaraux(LuaString var1, LexState.expdesc var2, int var3) {
      int var4 = this.searchvar(var1);
      if(var4 >= 0) {
         var2.init(6, var4);
         if(var3 == 0) {
            this.markupval(var4);
         }

         return 6;
      } else if(this.prev == null) {
         var2.init(8, 255);
         return 8;
      } else if(this.prev.singlevaraux(var1, var2, 0) == 8) {
         return 8;
      } else {
         var2.u.s.info = this.indexupvalue(var1, var2);
         var2.k = 7;
         return 7;
      }
   }

   void enterblock(FuncState.BlockCnt var1, boolean var2) {
      var1.breaklist.i = -1;
      var1.isbreakable = var2;
      var1.nactvar = this.nactvar;
      var1.upval = false;
      var1.previous = this.bl;
      this.bl = var1;
      _assert(this.freereg == this.nactvar);
   }

   void leaveblock() {
      FuncState.BlockCnt var1 = this.bl;
      this.bl = var1.previous;
      this.ls.removevars(var1.nactvar);
      if(var1.upval) {
         this.codeABC(35, var1.nactvar, 0, 0);
      }

      _assert(!var1.isbreakable || !var1.upval);
      _assert(var1.nactvar == this.nactvar);
      this.freereg = this.nactvar;
      this.patchtohere(var1.breaklist.i);
   }

   void closelistfield(LexState.ConsControl var1) {
      if(var1.v.k != 0) {
         this.exp2nextreg(var1.v);
         var1.v.k = 0;
         if(var1.tostore == 50) {
            this.setlist(var1.t.u.s.info, var1.na, var1.tostore);
            var1.tostore = 0;
         }

      }
   }

   boolean hasmultret(int var1) {
      return var1 == 13 || var1 == 14;
   }

   void lastlistfield(LexState.ConsControl var1) {
      if(var1.tostore != 0) {
         if(this.hasmultret(var1.v.k)) {
            this.setmultret(var1.v);
            this.setlist(var1.t.u.s.info, var1.na, -1);
            --var1.na;
         } else {
            if(var1.v.k != 0) {
               this.exp2nextreg(var1.v);
            }

            this.setlist(var1.t.u.s.info, var1.na, var1.tostore);
         }

      }
   }

   void nil(int var1, int var2) {
      if(this.pc > this.lasttarget) {
         if(this.pc == 0) {
            if(var1 >= this.nactvar) {
               return;
            }
         } else {
            InstructionPtr var3 = new InstructionPtr(this.f.code, this.pc - 1);
            if(GET_OPCODE(var3.get()) == 3) {
               int var4 = GETARG_A(var3.get());
               int var5 = GETARG_B(var3.get());
               if(var4 <= var1 && var1 <= var5 + 1) {
                  if(var1 + var2 - 1 > var5) {
                     SETARG_B(var3, var1 + var2 - 1);
                  }

                  return;
               }
            }
         }
      }

      this.codeABC(3, var1, var1 + var2 - 1, 0);
   }

   int jump() {
      int var1 = this.jpc.i;
      this.jpc.i = -1;
      IntPtr var2 = new IntPtr(this.codeAsBx(22, 0, -1));
      this.concat(var2, var1);
      return var2.i;
   }

   void ret(int var1, int var2) {
      this.codeABC(30, var1, var2 + 1, 0);
   }

   int condjump(int var1, int var2, int var3, int var4) {
      this.codeABC(var1, var2, var3, var4);
      return this.jump();
   }

   void fixjump(int var1, int var2) {
      InstructionPtr var3 = new InstructionPtr(this.f.code, var1);
      int var4 = var2 - (var1 + 1);
      _assert(var2 != -1);
      if(Math.abs(var4) > 131071) {
         this.ls.syntaxerror("control structure too long");
      }

      SETARG_sBx(var3, var4);
   }

   int getlabel() {
      this.lasttarget = this.pc;
      return this.pc;
   }

   int getjump(int var1) {
      int var2 = GETARG_sBx(this.f.code[var1]);
      return var2 == -1?-1:var1 + 1 + var2;
   }

   InstructionPtr getjumpcontrol(int var1) {
      InstructionPtr var2 = new InstructionPtr(this.f.code, var1);
      return var1 >= 1 && testTMode(GET_OPCODE(var2.code[var2.idx - 1]))?new InstructionPtr(var2.code, var2.idx - 1):var2;
   }

   boolean need_value(int var1) {
      while(var1 != -1) {
         int var2 = this.getjumpcontrol(var1).get();
         if(GET_OPCODE(var2) != 27) {
            return true;
         }

         var1 = this.getjump(var1);
      }

      return false;
   }

   boolean patchtestreg(int var1, int var2) {
      InstructionPtr var3 = this.getjumpcontrol(var1);
      if(GET_OPCODE(var3.get()) != 27) {
         return false;
      } else {
         if(var2 != 255 && var2 != GETARG_B(var3.get())) {
            SETARG_A(var3, var2);
         } else {
            var3.set(CREATE_ABC(26, GETARG_B(var3.get()), 0, Lua.GETARG_C(var3.get())));
         }

         return true;
      }
   }

   void removevalues(int var1) {
      while(var1 != -1) {
         this.patchtestreg(var1, 255);
         var1 = this.getjump(var1);
      }

   }

   void patchlistaux(int var1, int var2, int var3, int var4) {
      int var5;
      for(; var1 != -1; var1 = var5) {
         var5 = this.getjump(var1);
         if(this.patchtestreg(var1, var3)) {
            this.fixjump(var1, var2);
         } else {
            this.fixjump(var1, var4);
         }
      }

   }

   void dischargejpc() {
      this.patchlistaux(this.jpc.i, this.pc, 255, this.pc);
      this.jpc.i = -1;
   }

   void patchlist(int var1, int var2) {
      if(var2 == this.pc) {
         this.patchtohere(var1);
      } else {
         _assert(var2 < this.pc);
         this.patchlistaux(var1, var2, 255, var2);
      }

   }

   void patchtohere(int var1) {
      this.getlabel();
      this.concat(this.jpc, var1);
   }

   void concat(IntPtr var1, int var2) {
      if(var2 != -1) {
         if(var1.i == -1) {
            var1.i = var2;
         } else {
            int var3;
            int var4;
            for(var3 = var1.i; (var4 = this.getjump(var3)) != -1; var3 = var4) {
               ;
            }

            this.fixjump(var3, var2);
         }

      }
   }

   void checkstack(int var1) {
      int var2 = this.freereg + var1;
      if(var2 > this.f.maxstacksize) {
         if(var2 >= 250) {
            this.ls.syntaxerror("function or expression too complex");
         }

         this.f.maxstacksize = var2;
      }

   }

   void reserveregs(int var1) {
      this.checkstack(var1);
      this.freereg += var1;
   }

   void freereg(int var1) {
      if(!ISK(var1) && var1 >= this.nactvar) {
         --this.freereg;
         _assert(var1 == this.freereg);
      }

   }

   void freeexp(LexState.expdesc var1) {
      if(var1.k == 12) {
         this.freereg(var1.u.s.info);
      }

   }

   int addk(LuaValue var1) {
      int var2;
      if(this.htable.containsKey(var1)) {
         var2 = ((Integer)this.htable.get(var1)).intValue();
      } else {
         var2 = this.nk;
         this.htable.put(var1, new Integer(var2));
         Prototype var3 = this.f;
         if(var3.k == null || this.nk + 1 >= var3.k.length) {
            var3.k = realloc(var3.k, this.nk * 2 + 1);
         }

         var3.k[this.nk++] = var1;
      }

      return var2;
   }

   int stringK(LuaString var1) {
      return this.addk(var1);
   }

   int numberK(LuaValue var1) {
      if(var1 instanceof LuaDouble) {
         double var2 = ((LuaValue)var1).todouble();
         int var4 = (int)var2;
         if(var2 == (double)var4) {
            var1 = LuaInteger.valueOf(var4);
         }
      }

      return this.addk((LuaValue)var1);
   }

   int boolK(boolean var1) {
      return this.addk(var1?LuaValue.TRUE:LuaValue.FALSE);
   }

   int nilK() {
      return this.addk(LuaValue.NIL);
   }

   void setreturns(LexState.expdesc var1, int var2) {
      if(var1.k == 13) {
         SETARG_C(this.getcodePtr(var1), var2 + 1);
      } else if(var1.k == 14) {
         SETARG_B(this.getcodePtr(var1), var2 + 1);
         SETARG_A(this.getcodePtr(var1), this.freereg);
         this.reserveregs(1);
      }

   }

   void setoneret(LexState.expdesc var1) {
      if(var1.k == 13) {
         var1.k = 12;
         var1.u.s.info = GETARG_A(this.getcode(var1));
      } else if(var1.k == 14) {
         SETARG_B(this.getcodePtr(var1), 2);
         var1.k = 11;
      }

   }

   void dischargevars(LexState.expdesc var1) {
      switch(var1.k) {
      case 6:
         var1.k = 12;
         break;
      case 7:
         var1.u.s.info = this.codeABC(4, 0, var1.u.s.info, 0);
         var1.k = 11;
         break;
      case 8:
         var1.u.s.info = this.codeABx(5, 0, var1.u.s.info);
         var1.k = 11;
         break;
      case 9:
         this.freereg(var1.u.s.aux);
         this.freereg(var1.u.s.info);
         var1.u.s.info = this.codeABC(6, 0, var1.u.s.info, var1.u.s.aux);
         var1.k = 11;
      case 10:
      case 11:
      case 12:
      default:
         break;
      case 13:
      case 14:
         this.setoneret(var1);
      }

   }

   int code_label(int var1, int var2, int var3) {
      this.getlabel();
      return this.codeABC(2, var1, var2, var3);
   }

   void discharge2reg(LexState.expdesc var1, int var2) {
      this.dischargevars(var1);
      switch(var1.k) {
      case 1:
         this.nil(var2, 1);
         break;
      case 2:
      case 3:
         this.codeABC(2, var2, var1.k == 2?1:0, 0);
         break;
      case 4:
         this.codeABx(1, var2, var1.u.s.info);
         break;
      case 5:
         this.codeABx(1, var2, this.numberK(var1.u.nval()));
         break;
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      default:
         _assert(var1.k == 0 || var1.k == 10);
         return;
      case 11:
         InstructionPtr var3 = this.getcodePtr(var1);
         SETARG_A(var3, var2);
         break;
      case 12:
         if(var2 != var1.u.s.info) {
            this.codeABC(0, var2, var1.u.s.info, 0);
         }
      }

      var1.u.s.info = var2;
      var1.k = 12;
   }

   void discharge2anyreg(LexState.expdesc var1) {
      if(var1.k != 12) {
         this.reserveregs(1);
         this.discharge2reg(var1, this.freereg - 1);
      }

   }

   void exp2reg(LexState.expdesc var1, int var2) {
      this.discharge2reg(var1, var2);
      if(var1.k == 10) {
         this.concat(var1.t, var1.u.s.info);
      }

      if(var1.hasjumps()) {
         int var4 = -1;
         int var5 = -1;
         if(this.need_value(var1.t.i) || this.need_value(var1.f.i)) {
            int var6 = var1.k == 10?-1:this.jump();
            var4 = this.code_label(var2, 0, 1);
            var5 = this.code_label(var2, 1, 0);
            this.patchtohere(var6);
         }

         int var3 = this.getlabel();
         this.patchlistaux(var1.f.i, var3, var2, var4);
         this.patchlistaux(var1.t.i, var3, var2, var5);
      }

      var1.f.i = var1.t.i = -1;
      var1.u.s.info = var2;
      var1.k = 12;
   }

   void exp2nextreg(LexState.expdesc var1) {
      this.dischargevars(var1);
      this.freeexp(var1);
      this.reserveregs(1);
      this.exp2reg(var1, this.freereg - 1);
   }

   int exp2anyreg(LexState.expdesc var1) {
      this.dischargevars(var1);
      if(var1.k == 12) {
         if(!var1.hasjumps()) {
            return var1.u.s.info;
         }

         if(var1.u.s.info >= this.nactvar) {
            this.exp2reg(var1, var1.u.s.info);
            return var1.u.s.info;
         }
      }

      this.exp2nextreg(var1);
      return var1.u.s.info;
   }

   void exp2val(LexState.expdesc var1) {
      if(var1.hasjumps()) {
         this.exp2anyreg(var1);
      } else {
         this.dischargevars(var1);
      }

   }

   int exp2RK(LexState.expdesc var1) {
      this.exp2val(var1);
      switch(var1.k) {
      case 1:
      case 2:
      case 3:
      case 5:
         if(this.nk <= 255) {
            var1.u.s.info = var1.k == 1?this.nilK():(var1.k == 5?this.numberK(var1.u.nval()):this.boolK(var1.k == 2));
            var1.k = 4;
            return RKASK(var1.u.s.info);
         }
         break;
      case 4:
         if(var1.u.s.info <= 255) {
            return RKASK(var1.u.s.info);
         }
      }

      return this.exp2anyreg(var1);
   }

   void storevar(LexState.expdesc var1, LexState.expdesc var2) {
      int var3;
      switch(var1.k) {
      case 6:
         this.freeexp(var2);
         this.exp2reg(var2, var1.u.s.info);
         return;
      case 7:
         var3 = this.exp2anyreg(var2);
         this.codeABC(8, var3, var1.u.s.info, 0);
         break;
      case 8:
         var3 = this.exp2anyreg(var2);
         this.codeABx(7, var3, var1.u.s.info);
         break;
      case 9:
         var3 = this.exp2RK(var2);
         this.codeABC(9, var1.u.s.info, var1.u.s.aux, var3);
         break;
      default:
         _assert(false);
      }

      this.freeexp(var2);
   }

   void self(LexState.expdesc var1, LexState.expdesc var2) {
      this.exp2anyreg(var1);
      this.freeexp(var1);
      int var3 = this.freereg;
      this.reserveregs(2);
      this.codeABC(11, var3, var1.u.s.info, this.exp2RK(var2));
      this.freeexp(var2);
      var1.u.s.info = var3;
      var1.k = 12;
   }

   void invertjump(LexState.expdesc var1) {
      InstructionPtr var2 = this.getjumpcontrol(var1.u.s.info);
      _assert(testTMode(GET_OPCODE(var2.get())) && GET_OPCODE(var2.get()) != 27 && Lua.GET_OPCODE(var2.get()) != 26);
      int var3 = GETARG_A(var2.get());
      int var4 = var3 != 0?0:1;
      SETARG_A(var2, var4);
   }

   int jumponcond(LexState.expdesc var1, int var2) {
      if(var1.k == 11) {
         int var3 = this.getcode(var1);
         if(GET_OPCODE(var3) == 19) {
            --this.pc;
            return this.condjump(26, GETARG_B(var3), 0, var2 != 0?0:1);
         }
      }

      this.discharge2anyreg(var1);
      this.freeexp(var1);
      return this.condjump(27, 255, var1.u.s.info, var2);
   }

   void goiftrue(LexState.expdesc var1) {
      this.dischargevars(var1);
      int var2;
      switch(var1.k) {
      case 2:
      case 4:
      case 5:
         var2 = -1;
         break;
      case 3:
         var2 = this.jump();
         break;
      case 6:
      case 7:
      case 8:
      case 9:
      default:
         var2 = this.jumponcond(var1, 0);
         break;
      case 10:
         this.invertjump(var1);
         var2 = var1.u.s.info;
      }

      this.concat(var1.f, var2);
      this.patchtohere(var1.t.i);
      var1.t.i = -1;
   }

   void goiffalse(LexState.expdesc var1) {
      this.dischargevars(var1);
      int var2;
      switch(var1.k) {
      case 1:
      case 3:
         var2 = -1;
         break;
      case 2:
         var2 = this.jump();
         break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      default:
         var2 = this.jumponcond(var1, 1);
         break;
      case 10:
         var2 = var1.u.s.info;
      }

      this.concat(var1.t, var2);
      this.patchtohere(var1.f.i);
      var1.f.i = -1;
   }

   void codenot(LexState.expdesc var1) {
      this.dischargevars(var1);
      switch(var1.k) {
      case 1:
      case 3:
         var1.k = 2;
         break;
      case 2:
      case 4:
      case 5:
         var1.k = 3;
         break;
      case 6:
      case 7:
      case 8:
      case 9:
      default:
         _assert(false);
         break;
      case 10:
         this.invertjump(var1);
         break;
      case 11:
      case 12:
         this.discharge2anyreg(var1);
         this.freeexp(var1);
         var1.u.s.info = this.codeABC(19, 0, var1.u.s.info, 0);
         var1.k = 11;
      }

      int var2 = var1.f.i;
      var1.f.i = var1.t.i;
      var1.t.i = var2;
      this.removevalues(var1.f.i);
      this.removevalues(var1.t.i);
   }

   void indexed(LexState.expdesc var1, LexState.expdesc var2) {
      var1.u.s.aux = this.exp2RK(var2);
      var1.k = 9;
   }

   boolean constfolding(int var1, LexState.expdesc var2, LexState.expdesc var3) {
      if(var2.isnumeral() && var3.isnumeral()) {
         LuaValue var4 = var2.u.nval();
         LuaValue var5 = var3.u.nval();
         LuaValue var6;
         switch(var1) {
         case 12:
            var6 = var4.add(var5);
            break;
         case 13:
            var6 = var4.sub(var5);
            break;
         case 14:
            var6 = var4.mul(var5);
            break;
         case 15:
            var6 = var4.div(var5);
            break;
         case 16:
            var6 = var4.mod(var5);
            break;
         case 17:
            var6 = var4.pow(var5);
            break;
         case 18:
            var6 = var4.neg();
            break;
         case 19:
         default:
            _assert(false);
            var6 = null;
            break;
         case 20:
            return false;
         }

         if(Double.isNaN(var6.todouble())) {
            return false;
         } else {
            var2.u.setNval(var6);
            return true;
         }
      } else {
         return false;
      }
   }

   void codearith(int var1, LexState.expdesc var2, LexState.expdesc var3) {
      if(!this.constfolding(var1, var2, var3)) {
         int var4 = var1 != 18 && var1 != 20?this.exp2RK(var3):0;
         int var5 = this.exp2RK(var2);
         if(var5 > var4) {
            this.freeexp(var2);
            this.freeexp(var3);
         } else {
            this.freeexp(var3);
            this.freeexp(var2);
         }

         var2.u.s.info = this.codeABC(var1, 0, var5, var4);
         var2.k = 11;
      }
   }

   void codecomp(int var1, int var2, LexState.expdesc var3, LexState.expdesc var4) {
      int var5 = this.exp2RK(var3);
      int var6 = this.exp2RK(var4);
      this.freeexp(var4);
      this.freeexp(var3);
      if(var2 == 0 && var1 != 23) {
         int var7 = var5;
         var5 = var6;
         var6 = var7;
         var2 = 1;
      }

      var3.u.s.info = this.condjump(var1, var2, var5, var6);
      var3.k = 10;
   }

   void prefix(int var1, LexState.expdesc var2) {
      LexState.expdesc var3 = new LexState.expdesc();
      var3.init(5, 0);
      switch(var1) {
      case 0:
         if(var2.k == 4) {
            this.exp2anyreg(var2);
         }

         this.codearith(18, var2, var3);
         break;
      case 1:
         this.codenot(var2);
         break;
      case 2:
         this.exp2anyreg(var2);
         this.codearith(20, var2, var3);
         break;
      default:
         _assert(false);
      }

   }

   void infix(int var1, LexState.expdesc var2) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
         if(!var2.isnumeral()) {
            this.exp2RK(var2);
         }
         break;
      case 6:
         this.exp2nextreg(var2);
         break;
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      default:
         this.exp2RK(var2);
         break;
      case 13:
         this.goiftrue(var2);
         break;
      case 14:
         this.goiffalse(var2);
      }

   }

   void posfix(int var1, LexState.expdesc var2, LexState.expdesc var3) {
      switch(var1) {
      case 0:
         this.codearith(12, var2, var3);
         break;
      case 1:
         this.codearith(13, var2, var3);
         break;
      case 2:
         this.codearith(14, var2, var3);
         break;
      case 3:
         this.codearith(15, var2, var3);
         break;
      case 4:
         this.codearith(16, var2, var3);
         break;
      case 5:
         this.codearith(17, var2, var3);
         break;
      case 6:
         this.exp2val(var3);
         if(var3.k == 11 && GET_OPCODE(this.getcode(var3)) == 21) {
            _assert(var2.u.s.info == GETARG_B(this.getcode(var3)) - 1);
            this.freeexp(var2);
            SETARG_B(this.getcodePtr(var3), var2.u.s.info);
            var2.k = 11;
            var2.u.s.info = var3.u.s.info;
         } else {
            this.exp2nextreg(var3);
            this.codearith(21, var2, var3);
         }
         break;
      case 7:
         this.codecomp(23, 0, var2, var3);
         break;
      case 8:
         this.codecomp(23, 1, var2, var3);
         break;
      case 9:
         this.codecomp(24, 1, var2, var3);
         break;
      case 10:
         this.codecomp(25, 1, var2, var3);
         break;
      case 11:
         this.codecomp(24, 0, var2, var3);
         break;
      case 12:
         this.codecomp(25, 0, var2, var3);
         break;
      case 13:
         _assert(var2.t.i == -1);
         this.dischargevars(var3);
         this.concat(var3.f, var2.f.i);
         var2.setvalue(var3);
         break;
      case 14:
         _assert(var2.f.i == -1);
         this.dischargevars(var3);
         this.concat(var3.t, var2.t.i);
         var2.setvalue(var3);
         break;
      default:
         _assert(false);
      }

   }

   void fixline(int var1) {
      this.f.lineinfo[this.pc - 1] = var1;
   }

   int code(int var1, int var2) {
      Prototype var3 = this.f;
      this.dischargejpc();
      if(var3.code == null || this.pc + 1 > var3.code.length) {
         var3.code = LuaC.realloc(var3.code, this.pc * 2 + 1);
      }

      var3.code[this.pc] = var1;
      if(var3.lineinfo == null || this.pc + 1 > var3.lineinfo.length) {
         var3.lineinfo = LuaC.realloc(var3.lineinfo, this.pc * 2 + 1);
      }

      var3.lineinfo[this.pc] = var2;
      return this.pc++;
   }

   int codeABC(int var1, int var2, int var3, int var4) {
      _assert(getOpMode(var1) == 0);
      _assert(getBMode(var1) != 0 || var3 == 0);
      _assert(getCMode(var1) != 0 || var4 == 0);
      return this.code(CREATE_ABC(var1, var2, var3, var4), this.ls.lastline);
   }

   int codeABx(int var1, int var2, int var3) {
      _assert(getOpMode(var1) == 1 || getOpMode(var1) == 2);
      _assert(getCMode(var1) == 0);
      return this.code(CREATE_ABx(var1, var2, var3), this.ls.lastline);
   }

   void setlist(int var1, int var2, int var3) {
      int var4 = (var2 - 1) / 50 + 1;
      int var5 = var3 == -1?0:var3;
      _assert(var3 != 0);
      if(var4 <= 511) {
         this.codeABC(34, var1, var5, var4);
      } else {
         this.codeABC(34, var1, var5, 0);
         this.code(var4, this.ls.lastline);
      }

      this.freereg = var1 + 1;
   }

   class upvaldesc {

      short k;
      short info;


   }

   static class BlockCnt {

      FuncState.BlockCnt previous;
      IntPtr breaklist = new IntPtr();
      short nactvar;
      boolean upval;
      boolean isbreakable;


   }
}
