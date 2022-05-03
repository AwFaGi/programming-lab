package transfer;

import java.io.Serializable;
import java.util.List;

public class Responce implements Serializable {
    public final boolean isError;
    public final String message;
    public final List<CmdTemplate> arr;

    public Responce(boolean isError, String message, List<CmdTemplate> arr) {
        this.isError = isError;
        this.message = message;
        this.arr = arr;
    }
}
