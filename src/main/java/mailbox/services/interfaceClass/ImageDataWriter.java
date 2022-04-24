package mailbox.services.interfaceClass;

import java.io.File;
import java.io.IOException;

public interface ImageDataWriter {
    void copyData(File source, File destination) throws IOException;
}
