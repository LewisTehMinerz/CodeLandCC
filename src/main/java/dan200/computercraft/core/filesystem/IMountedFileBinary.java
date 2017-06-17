package dan200.computercraft.core.filesystem;

import dan200.computercraft.core.filesystem.IMountedFile;
import java.io.IOException;

public interface IMountedFileBinary extends IMountedFile {

   int read() throws IOException;

   void write(int var1) throws IOException;

   void close() throws IOException;

   void flush() throws IOException;
}
