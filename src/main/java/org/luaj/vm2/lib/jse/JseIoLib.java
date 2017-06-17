package org.luaj.vm2.lib.jse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.lib.BaseLib;
import org.luaj.vm2.lib.IoLib;

public class JseIoLib extends IoLib {

   protected IoLib.File wrapStdin() throws IOException {
      return new JseIoLib.FileImpl(BaseLib.instance.STDIN, (JseIoLib.NamelessClass340839694)null);
   }

   protected IoLib.File wrapStdout() throws IOException {
      return new JseIoLib.FileImpl(BaseLib.instance.STDOUT, (JseIoLib.NamelessClass340839694)null);
   }

   protected IoLib.File openFile(String var1, boolean var2, boolean var3, boolean var4, boolean var5) throws IOException {
      RandomAccessFile var6 = new RandomAccessFile(var1, var2?"r":"rw");
      if(var3) {
         var6.seek(var6.length());
      } else if(!var2) {
         var6.setLength(0L);
      }

      return new JseIoLib.FileImpl(var6, (JseIoLib.NamelessClass340839694)null);
   }

   protected IoLib.File openProgram(String var1, String var2) throws IOException {
      Process var3 = Runtime.getRuntime().exec(var1);
      return "w".equals(var2)?new JseIoLib.FileImpl(var3.getOutputStream(), (JseIoLib.NamelessClass340839694)null):new JseIoLib.FileImpl(var3.getInputStream(), (JseIoLib.NamelessClass340839694)null);
   }

   protected IoLib.File tmpFile() throws IOException {
      File var1 = File.createTempFile(".luaj", "bin");
      var1.deleteOnExit();
      return new JseIoLib.FileImpl(new RandomAccessFile(var1, "rw"), (JseIoLib.NamelessClass340839694)null);
   }

   private static void notimplemented() {
      throw new LuaError("not implemented");
   }

   static void access$300() {
      notimplemented();
   }

   static class NamelessClass340839694 {
   }

   private final class FileImpl extends IoLib.File {

      private final RandomAccessFile file;
      private final InputStream is;
      private final OutputStream os;
      private boolean closed;
      private boolean nobuffer;


      private FileImpl(RandomAccessFile var2, InputStream var3, OutputStream var4) {
         super();
         this.closed = false;
         this.nobuffer = false;
         this.file = var2;
         this.is = (InputStream)(var3 != null?(var3.markSupported()?var3:new BufferedInputStream(var3)):null);
         this.os = var4;
      }

      private FileImpl(RandomAccessFile var2) {
         this(var2, (InputStream)null, (OutputStream)null);
      }

      private FileImpl(InputStream var2) {
         this((RandomAccessFile)null, var2, (OutputStream)null);
      }

      private FileImpl(OutputStream var2) {
         this((RandomAccessFile)null, (InputStream)null, var2);
      }

      public String tojstring() {
         return "file (" + this.hashCode() + ")";
      }

      public boolean isstdfile() {
         return this.file == null;
      }

      public void close() throws IOException {
         this.closed = true;
         if(this.file != null) {
            this.file.close();
         }

      }

      public void flush() throws IOException {
         if(this.os != null) {
            this.os.flush();
         }

      }

      public void write(LuaString var1) throws IOException {
         if(this.os != null) {
            this.os.write(var1.m_bytes, var1.m_offset, var1.m_length);
         } else if(this.file != null) {
            this.file.write(var1.m_bytes, var1.m_offset, var1.m_length);
         } else {
            JseIoLib.notimplemented();
         }

         if(this.nobuffer) {
            this.flush();
         }

      }

      public boolean isclosed() {
         return this.closed;
      }

      public int seek(String var1, int var2) throws IOException {
         if(this.file != null) {
            if("set".equals(var1)) {
               this.file.seek((long)var2);
            } else if("end".equals(var1)) {
               this.file.seek(this.file.length() + (long)var2);
            } else {
               this.file.seek(this.file.getFilePointer() + (long)var2);
            }

            return (int)this.file.getFilePointer();
         } else {
            JseIoLib.notimplemented();
            return 0;
         }
      }

      public void setvbuf(String var1, int var2) {
         this.nobuffer = "no".equals(var1);
      }

      public int remaining() throws IOException {
         return this.file != null?(int)(this.file.length() - this.file.getFilePointer()):-1;
      }

      public int peek() throws IOException {
         if(this.is != null) {
            this.is.mark(1);
            int var4 = this.is.read();
            this.is.reset();
            return var4;
         } else if(this.file != null) {
            long var1 = this.file.getFilePointer();
            int var3 = this.file.read();
            this.file.seek(var1);
            return var3;
         } else {
            JseIoLib.notimplemented();
            return 0;
         }
      }

      public int read() throws IOException {
         if(this.is != null) {
            return this.is.read();
         } else if(this.file != null) {
            return this.file.read();
         } else {
            JseIoLib.notimplemented();
            return 0;
         }
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         if(this.file != null) {
            return this.file.read(var1, var2, var3);
         } else if(this.is != null) {
            return this.is.read(var1, var2, var3);
         } else {
            JseIoLib.notimplemented();
            return var3;
         }
      }

      FileImpl(InputStream var2, JseIoLib.NamelessClass340839694 var3) {
         this(var2);
      }

      FileImpl(OutputStream var2, JseIoLib.NamelessClass340839694 var3) {
         this(var2);
      }

      FileImpl(RandomAccessFile var2, JseIoLib.NamelessClass340839694 var3) {
         this(var2);
      }
   }
}
