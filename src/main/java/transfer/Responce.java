package transfer;

import stored.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * object that comes from server
 */
public class Responce implements Serializable {
    public final boolean isError;
    public final String message;
    public final List<CmdTemplate> arr;
    public final ArrayList<City> obj;

    public Responce(boolean isError, String message, List<CmdTemplate> arr) {
        this.isError = isError;
        this.message = message;
        this.arr = arr;
        this.obj = null;
    }
    public Responce(boolean isError, String message, List<CmdTemplate> arr, ArrayList<City> obj) {
        this.isError = isError;
        this.message = message;
        this.arr = null;
        this.obj = obj;
    }
}
