package exceptions;

import java.io.IOException;

public class ReadWriteException extends IOException {
    public ReadWriteException(String action, String filename) {
        super(String.format("Something's wrong on %s '%s'", action, filename));
    }
    public ReadWriteException(String action, String filename, String msg) {
        super(String.format("Something's wrong on %s '%s': %s", action, filename, msg));
    }
}
