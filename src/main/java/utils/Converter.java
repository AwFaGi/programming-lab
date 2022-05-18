package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String computeMD5hash(String something) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(something.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        return bigInt.toString(16);
    }
}
