package structure;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * The encryption utilities class. Title: Java AES Encryption Decryption Example
 *
 * @author Lokesh Gupta, Jimschenchen
 * @see <a href="https://howtodoinjava.com/security/java-aes-encryption-example/"></a>
 * @since 4.0
 * @version 4.0
 */
public class EncrytionUtils {
  /** The secret key for encryption */
  private static SecretKeySpec secretKey;

  /** The key list in byte */
  private static byte[] key;

  /**
   * Sets the secret key.
   *
   * @param myKey the secret key to set
   */
  public static void setKey(String myKey) {
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  /**
   * Encrypts the given string
   *
   * @param strToEncrypt the string to encrpyt
   * @param secret the secret key for encryption
   * @return the encrpyted string
   */
  public static String encrypt(String strToEncrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      // System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  /**
   * Decrypts the given string
   *
   * @param strToDecrypt the string to decrypt
   * @param secret the secret key for decryption
   * @return the decrypted string
   */
  public static String decrypt(String strToDecrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      // System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }
}
