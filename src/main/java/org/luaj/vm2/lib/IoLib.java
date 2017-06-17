package org.luaj.vm2.lib;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.VarArgFunction;

public abstract class IoLib extends OneArgFunction {

   private IoLib.File infile = null;
   private IoLib.File outfile = null;
   private IoLib.File errfile = null;
   private static final LuaValue STDIN = valueOf("stdin");
   private static final LuaValue STDOUT = valueOf("stdout");
   private static final LuaValue STDERR = valueOf("stderr");
   private static final LuaValue FILE = valueOf("file");
   private static final LuaValue CLOSED_FILE = valueOf("closed file");
   private static final int IO_CLOSE = 0;
   private static final int IO_FLUSH = 1;
   private static final int IO_INPUT = 2;
   private static final int IO_LINES = 3;
   private static final int IO_OPEN = 4;
   private static final int IO_OUTPUT = 5;
   private static final int IO_POPEN = 6;
   private static final int IO_READ = 7;
   private static final int IO_TMPFILE = 8;
   private static final int IO_TYPE = 9;
   private static final int IO_WRITE = 10;
   private static final int FILE_CLOSE = 11;
   private static final int FILE_FLUSH = 12;
   private static final int FILE_LINES = 13;
   private static final int FILE_READ = 14;
   private static final int FILE_SEEK = 15;
   private static final int FILE_SETVBUF = 16;
   private static final int FILE_WRITE = 17;
   private static final int IO_INDEX = 18;
   private static final int LINES_ITER = 19;
   public static final String[] IO_NAMES = new String[]{"close", "flush", "input", "lines", "open", "output", "popen", "read", "tmpfile", "type", "write"};
   public static final String[] FILE_NAMES = new String[]{"close", "flush", "lines", "read", "seek", "setvbuf", "write"};
   LuaTable filemethods;
   static Class class$org$luaj$vm2$lib$IoLib$IoLibV;


   protected abstract IoLib.File wrapStdin() throws IOException;

   protected abstract IoLib.File wrapStdout() throws IOException;

   protected abstract IoLib.File openFile(String var1, boolean var2, boolean var3, boolean var4, boolean var5) throws IOException;

   protected abstract IoLib.File tmpFile() throws IOException;

   protected abstract IoLib.File openProgram(String var1, String var2) throws IOException;

   public LuaValue call(LuaValue var1) {
      LuaTable var2 = new LuaTable();
      this.bind(var2, class$org$luaj$vm2$lib$IoLib$IoLibV == null?(class$org$luaj$vm2$lib$IoLib$IoLibV = class$("org.luaj.vm2.lib.IoLib$IoLibV")):class$org$luaj$vm2$lib$IoLib$IoLibV, IO_NAMES);
      this.filemethods = new LuaTable();
      this.bind(this.filemethods, class$org$luaj$vm2$lib$IoLib$IoLibV == null?(class$org$luaj$vm2$lib$IoLib$IoLibV = class$("org.luaj.vm2.lib.IoLib$IoLibV")):class$org$luaj$vm2$lib$IoLib$IoLibV, FILE_NAMES, 11);
      LuaTable var3 = new LuaTable();
      this.bind(var3, class$org$luaj$vm2$lib$IoLib$IoLibV == null?(class$org$luaj$vm2$lib$IoLib$IoLibV = class$("org.luaj.vm2.lib.IoLib$IoLibV")):class$org$luaj$vm2$lib$IoLib$IoLibV, new String[]{"__index"}, 18);
      var2.setmetatable(var3);
      this.setLibInstance(var2);
      this.setLibInstance(this.filemethods);
      this.setLibInstance(var3);
      this.env.set("io", (LuaValue)var2);
      PackageLib.instance.LOADED.set("io", var2);
      return var2;
   }

   private void setLibInstance(LuaTable var1) {
      LuaValue[] var2 = var1.keys();
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         ((IoLib.IoLibV)var1.get(var2[var3])).iolib = this;
      }

   }

   private IoLib.File input() {
      return this.infile != null?this.infile:(this.infile = this.ioopenfile("-", "r"));
   }

   public Varargs _io_flush() throws IOException {
      checkopen(this.output());
      this.outfile.flush();
      return LuaValue.TRUE;
   }

   public Varargs _io_tmpfile() throws IOException {
      return this.tmpFile();
   }

   public Varargs _io_close(LuaValue var1) throws IOException {
      IoLib.File var2 = var1.isnil()?this.output():checkfile(var1);
      checkopen(var2);
      return ioclose(var2);
   }

   public Varargs _io_input(LuaValue var1) {
      this.infile = var1.isnil()?this.input():(var1.isstring()?this.ioopenfile(var1.checkjstring(), "r"):checkfile(var1));
      return this.infile;
   }

   public Varargs _io_output(LuaValue var1) {
      this.outfile = var1.isnil()?this.output():(var1.isstring()?this.ioopenfile(var1.checkjstring(), "w"):checkfile(var1));
      return this.outfile;
   }

   public Varargs _io_type(LuaValue var1) {
      IoLib.File var2 = optfile(var1);
      return var2 != null?(var2.isclosed()?CLOSED_FILE:FILE):NIL;
   }

   public Varargs _io_popen(String var1, String var2) throws IOException {
      return this.openProgram(var1, var2);
   }

   public Varargs _io_open(String var1, String var2) throws IOException {
      return this.rawopenfile(var1, var2);
   }

   public Varargs _io_lines(String var1) {
      this.infile = var1 == null?this.input():this.ioopenfile(var1, "r");
      checkopen(this.infile);
      return this.lines(this.infile);
   }

   public Varargs _io_read(Varargs var1) throws IOException {
      checkopen(this.input());
      return this.ioread(this.infile, var1);
   }

   public Varargs _io_write(Varargs var1) throws IOException {
      checkopen(this.output());
      return iowrite(this.outfile, var1);
   }

   public Varargs _file_close(LuaValue var1) throws IOException {
      return ioclose(checkfile(var1));
   }

   public Varargs _file_flush(LuaValue var1) throws IOException {
      checkfile(var1).flush();
      return LuaValue.TRUE;
   }

   public Varargs _file_setvbuf(LuaValue var1, String var2, int var3) {
      checkfile(var1).setvbuf(var2, var3);
      return LuaValue.TRUE;
   }

   public Varargs _file_lines(LuaValue var1) {
      return this.lines(checkfile(var1));
   }

   public Varargs _file_read(LuaValue var1, Varargs var2) throws IOException {
      return this.ioread(checkfile(var1), var2);
   }

   public Varargs _file_seek(LuaValue var1, String var2, int var3) throws IOException {
      return valueOf(checkfile(var1).seek(var2, var3));
   }

   public Varargs _file_write(LuaValue var1, Varargs var2) throws IOException {
      return iowrite(checkfile(var1), var2);
   }

   public Varargs _io_index(LuaValue var1) {
      return (Varargs)(var1.equals(STDOUT)?this.output():(var1.equals(STDIN)?this.input():(var1.equals(STDERR)?this.errput():NIL)));
   }

   public Varargs _lines_iter(LuaValue var1) throws IOException {
      return freadline(checkfile(var1));
   }

   private IoLib.File output() {
      return this.outfile != null?this.outfile:(this.outfile = this.ioopenfile("-", "w"));
   }

   private IoLib.File errput() {
      return this.errfile != null?this.errfile:(this.errfile = this.ioopenfile("-", "w"));
   }

   private IoLib.File ioopenfile(String var1, String var2) {
      try {
         return this.rawopenfile(var1, var2);
      } catch (Exception var4) {
         error("io error: " + var4.getMessage());
         return null;
      }
   }

   private static Varargs ioclose(IoLib.File var0) throws IOException {
      if(var0.isstdfile()) {
         return errorresult("cannot close standard file");
      } else {
         var0.close();
         return successresult();
      }
   }

   private static Varargs successresult() {
      return LuaValue.TRUE;
   }

   private static Varargs errorresult(Exception var0) {
      String var1 = var0.getMessage();
      return errorresult("io error: " + (var1 != null?var1:var0.toString()));
   }

   private static Varargs errorresult(String var0) {
      return varargsOf(NIL, valueOf(var0));
   }

   private Varargs lines(IoLib.File var1) {
      try {
         return new IoLib.IoLibV(var1, "lnext", 19, this);
      } catch (Exception var3) {
         return error("lines: " + var3);
      }
   }

   private static Varargs iowrite(IoLib.File var0, Varargs var1) throws IOException {
      int var2 = 1;

      for(int var3 = var1.narg(); var2 <= var3; ++var2) {
         var0.write(var1.checkstring(var2));
      }

      return LuaValue.TRUE;
   }

   private Varargs ioread(IoLib.File var1, Varargs var2) throws IOException {
      int var4 = var2.narg();
      LuaValue[] var5 = new LuaValue[var4];
      int var3 = 0;

      while(true) {
         if(var3 < var4) {
            LuaValue var7;
            LuaValue var6;
            label32:
            switch((var6 = var2.arg(var3 + 1)).type()) {
            case 3:
               var7 = freadbytes(var1, var6.toint());
               break;
            case 4:
               LuaString var8 = var6.checkstring();
               if(var8.m_length == 2 && var8.m_bytes[var8.m_offset] == 42) {
                  switch(var8.m_bytes[var8.m_offset + 1]) {
                  case 97:
                     var7 = freadall(var1);
                     break label32;
                  case 108:
                     var7 = freadline(var1);
                     break label32;
                  case 110:
                     var7 = freadnumber(var1);
                     break label32;
                  }
               }
            default:
               return argerror(var3 + 1, "(invalid format)");
            }

            if(!(var5[var3++] = var7).isnil()) {
               continue;
            }
         }

         return (Varargs)(var3 == 0?NIL:varargsOf(var5, 0, var3));
      }
   }

   private static IoLib.File checkfile(LuaValue var0) {
      IoLib.File var1 = optfile(var0);
      if(var1 == null) {
         argerror(1, "file");
      }

      checkopen(var1);
      return var1;
   }

   private static IoLib.File optfile(LuaValue var0) {
      return var0 instanceof IoLib.File?(IoLib.File)var0:null;
   }

   private static IoLib.File checkopen(IoLib.File var0) {
      if(var0.isclosed()) {
         error("attempt to use a closed file");
      }

      return var0;
   }

   private IoLib.File rawopenfile(String var1, String var2) throws IOException {
      boolean var3 = "-".equals(var1);
      boolean var4 = var2.startsWith("r");
      if(var3) {
         return var4?this.wrapStdin():this.wrapStdout();
      } else {
         boolean var5 = var2.startsWith("a");
         boolean var6 = var2.indexOf("+") > 0;
         boolean var7 = var2.endsWith("b");
         return this.openFile(var1, var4, var5, var6, var7);
      }
   }

   public static LuaValue freadbytes(IoLib.File var0, int var1) throws IOException {
      byte[] var2 = new byte[var1];
      int var3;
      return (LuaValue)((var3 = var0.read(var2, 0, var2.length)) < 0?NIL:LuaString.valueOf(var2, 0, var3));
   }

   public static LuaValue freaduntil(IoLib.File var0, boolean var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();

      int var3;
      try {
         if(var1) {
            while((var3 = var0.read()) > 0) {
               switch(var3) {
               case 10:
                  return (LuaValue)(var3 < 0 && var2.size() == 0?NIL:LuaString.valueOf(var2.toByteArray()));
               case 13:
                  break;
               default:
                  var2.write(var3);
               }
            }
         } else {
            while((var3 = var0.read()) > 0) {
               var2.write(var3);
            }
         }
      } catch (EOFException var5) {
         var3 = -1;
      }

      return (LuaValue)(var3 < 0 && var2.size() == 0?NIL:LuaString.valueOf(var2.toByteArray()));
   }

   public static LuaValue freadline(IoLib.File var0) throws IOException {
      return freaduntil(var0, true);
   }

   public static LuaValue freadall(IoLib.File var0) throws IOException {
      int var1 = var0.remaining();
      return var1 >= 0?freadbytes(var0, var1):freaduntil(var0, false);
   }

   public static LuaValue freadnumber(IoLib.File var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      freadchars(var0, " \t\r\n", (ByteArrayOutputStream)null);
      freadchars(var0, "-+", var1);
      freadchars(var0, "0123456789", var1);
      freadchars(var0, ".", var1);
      freadchars(var0, "0123456789", var1);
      String var2 = var1.toString();
      return (LuaValue)(var2.length() > 0?valueOf(Double.parseDouble(var2)):NIL);
   }

   private static void freadchars(IoLib.File var0, String var1, ByteArrayOutputStream var2) throws IOException {
      while(true) {
         int var3 = var0.peek();
         if(var1.indexOf(var3) < 0) {
            return;
         }

         var0.read();
         if(var2 != null) {
            var2.write(var3);
         }
      }
   }

   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static Varargs access$000(Exception var0) {
      return errorresult(var0);
   }


   protected abstract class File extends LuaValue {

      public abstract void write(LuaString var1) throws IOException;

      public abstract void flush() throws IOException;

      public abstract boolean isstdfile();

      public abstract void close() throws IOException;

      public abstract boolean isclosed();

      public abstract int seek(String var1, int var2) throws IOException;

      public abstract void setvbuf(String var1, int var2);

      public abstract int remaining() throws IOException;

      public abstract int peek() throws IOException, EOFException;

      public abstract int read() throws IOException, EOFException;

      public abstract int read(byte[] var1, int var2, int var3) throws IOException;

      public LuaValue get(LuaValue var1) {
         return IoLib.this.filemethods.get(var1);
      }

      public int type() {
         return 7;
      }

      public String typename() {
         return "userdata";
      }

      public String tojstring() {
         return "file: " + Integer.toHexString(this.hashCode());
      }
   }

   static final class IoLibV extends VarArgFunction {

      public IoLib iolib;


      public IoLibV() {}

      public IoLibV(LuaValue var1, String var2, int var3, IoLib var4) {
         this.env = var1;
         this.name = var2;
         this.opcode = var3;
         this.iolib = var4;
      }

      public Varargs invoke(Varargs var1) {
         try {
            switch(this.opcode) {
            case 0:
               return this.iolib._io_close(var1.arg1());
            case 1:
               return this.iolib._io_flush();
            case 2:
               return this.iolib._io_input(var1.arg1());
            case 3:
               return this.iolib._io_lines(var1.isvalue(1)?var1.checkjstring(1):null);
            case 4:
               return this.iolib._io_open(var1.checkjstring(1), var1.optjstring(2, "r"));
            case 5:
               return this.iolib._io_output(var1.arg1());
            case 6:
               return this.iolib._io_popen(var1.checkjstring(1), var1.optjstring(2, "r"));
            case 7:
               return this.iolib._io_read(var1);
            case 8:
               return this.iolib._io_tmpfile();
            case 9:
               return this.iolib._io_type(var1.arg1());
            case 10:
               return this.iolib._io_write(var1);
            case 11:
               return this.iolib._file_close(var1.arg1());
            case 12:
               return this.iolib._file_flush(var1.arg1());
            case 13:
               return this.iolib._file_lines(var1.arg1());
            case 14:
               return this.iolib._file_read(var1.arg1(), var1.subargs(2));
            case 15:
               return this.iolib._file_seek(var1.arg1(), var1.optjstring(2, "cur"), var1.optint(3, 0));
            case 16:
               return this.iolib._file_setvbuf(var1.arg1(), var1.checkjstring(2), var1.optint(3, 1024));
            case 17:
               return this.iolib._file_write(var1.arg1(), var1.subargs(2));
            case 18:
               return this.iolib._io_index(var1.arg(2));
            case 19:
               return this.iolib._lines_iter(this.env);
            }
         } catch (IOException var3) {
            return IoLib.errorresult((Exception)var3);
         }

         return NONE;
      }
   }
}
