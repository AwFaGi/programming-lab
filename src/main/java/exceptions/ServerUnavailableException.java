package exceptions;

import java.io.IOException;

public class ServerUnavailableException extends IOException {
    public ServerUnavailableException(String msg){
        super(msg);
    }
}