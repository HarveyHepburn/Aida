package Aida;

import java.security.Key;
import javax.crypto.Cipher;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
    static String loginCode = "7PustRbz1WYdMsszl7N4thz7ROxiecG9O4WIy4XaKTsFWQmwZuvSI2M2Y7jnChJv";

    private static final String ALGO = "AES";
    private static byte[] keyValue;

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */

    public static String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encVal);
    }

    static void setKeyValue(String keyV) {
        keyValue = ("EVOLI" + keyV).getBytes();
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }

    private static String generateVeryfyString(String password, String encriptString) throws Exception {
        setKeyValue(password);
        return encrypt(encriptString + password);
    }

    public static void main(String[] args) throws Exception {
        /*
         * Use this line to generate your password.
         * Notice: your password length must be 11.
         * And then copy it from terminal as value of the loginCode in Encrypter class.
         * */
        System.out.println(generateVeryfyString("NewPassword", "Anything you want encrypt as a login code"));
    }
}