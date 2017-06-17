package dan200.computercraft.core.filesystem;

import dan200.computercraft.core.filesystem.IMountedFile;
import java.io.IOException;

public interface IMountedFileNormal extends IMountedFile {

   String readLine() throws IOException;

   void write(String var1, int var2, int var3, boolean var4) throws IOException;

   void close() throws IOException;

   void flush() throws IOException;
}
