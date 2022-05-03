package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class Converter {
    public static <T> ByteBuffer convertToBB(T something) {
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);){

            oos.writeObject(something);
            oos.flush();
            buffer.put(baos.toByteArray());
            buffer.flip();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return buffer;
    }
}
