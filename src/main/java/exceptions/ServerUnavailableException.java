package exceptions;

import java.io.IOException;

/**
 * when server is unreachable
 */
public class ServerUnavailableException extends IOException {
    public ServerUnavailableException(String msg){
        super(msg);
    }
}