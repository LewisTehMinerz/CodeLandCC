package org.luaj.vm2;

import org.luaj.vm2.LocVars;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;

public class Prototype {

   public LuaValue[] k;
   public int[] code;
   public Prototype[] p;
   public int[] lineinfo;
   public LocVars[] locvars;
   public LuaString[] upvalues;
   public LuaString source;
   public int nups;
   public int linedefined;
   public int lastlinedefined;
   public int numparams;
   public int is_vararg;
   public int maxstacksize;


   public String toString() {
      return this.source + ":" + this.linedefined + "-" + this.lastlinedefined;
   }

   public LuaString getlocalname(int var1, int var2) {
      for(int var3 = 0; var3 < this.locvars.length && this.locvars[var3].startpc <= var2; ++var3) {
         if(var2 < this.locvars[var3].endpc) {
            --var1;
            if(var1 == 0) {
               return this.locvars[var3].varname;
            }
         }
      }

      return null;
   }
}
