package org.meteorite.com.base;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA算法，实现数据的加密解密。
 */
@Slf4j
public class RSAUtil {
    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 生成密钥对
     *
     * @param filePath 生成密钥的路径
     * @return
     */
    public static Map<String, String> generateKeyPair(String filePath) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 密钥位数  
            keyPairGen.initialize(1024);
            // 密钥对  
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥  
            PublicKey publicKey = keyPair.getPublic();
            // 私钥  
            PrivateKey privateKey = keyPair.getPrivate();
            //得到公钥字符串  
            String publicKeyString = getKeyString(publicKey);
            //得到私钥字符串  
            String privateKeyString = getKeyString(privateKey);
            //将密钥对写入到文件  
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
            //将生成的密钥对返回  
            Map<String, String> map = new HashMap<String, String>();
            map.put("publicKey", publicKeyString);
            map.put("privateKey", privateKeyString);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 生成密钥对
     *
     * @return
     */
    public static Map<String, String> generateKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 密钥位数  
            keyPairGen.initialize(1024);
            // 密钥对  
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥  
            PublicKey publicKey = keyPair.getPublic();
            // 私钥  
            PrivateKey privateKey = keyPair.getPrivate();
            //得到公钥字符串  
            String publicKeyString = getKeyString(publicKey);
            //得到私钥字符串  
            String privateKeyString = getKeyString(privateKey);

            //将生成的密钥对返回  
            Map<String, String> map = new HashMap<String, String>();
            map.put("publicKey", publicKeyString);
            map.put("privateKey", privateKeyString);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到密钥字符串（经过base64编码）
     *
     * @return
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    /**
     * 使用公钥对明文进行加密，返回BASE64编码的字符串
     *
     * @param publicKey
     * @param plainText
     * @return
     */
    public static String encrypt(PublicKey publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return (new BASE64Encoder()).encode(enBytes);
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使用keystore对明文进行加密
     *
     * @param publicKeystore 公钥文件路径
     * @param plainText      明文
     * @return
     */
    public static String fileEncrypt(String publicKeystore, String plainText) {
        try {
            FileReader fr = new FileReader(publicKeystore);
            BufferedReader br = new BufferedReader(fr);
            String publicKeyString = "";
            String str;
            while ((str = br.readLine()) != null) {
                publicKeyString += str;
            }
            br.close();
            fr.close();
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return (new BASE64Encoder()).encode(enBytes);
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使用公钥对明文进行加密
     *
     * @param publicKey 公钥
     * @param plainText 明文
     * @return
     */
    public static String encrypt(String publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return (new BASE64Encoder()).encode(enBytes);
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使用私钥对明文密文进行解密
     *
     * @param privateKey
     * @param enStr
     * @return
     */
    public static String decrypt(PrivateKey privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使用私钥对密文进行解密
     *
     * @param privateKey 私钥
     * @param enStr      密文
     * @return
     */
    public static synchronized String decrypt(String privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使用keystore对密文进行解密
     *
     * @param privateKeystore 私钥路径
     * @param enStr           密文
     * @return
     */
    public static String fileDecrypt(String privateKeystore, String enStr) {
        try {
            FileReader fr = new FileReader(privateKeystore);
            BufferedReader br = new BufferedReader(fr);
            String privateKeyString = "";
            String str;
            while ((str = br.readLine()) != null) {
                privateKeyString += str;
            }
            br.close();
            fr.close();
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyString));
            byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbsAhtq82pNlz7G16LuHiyLmbMVn1+AqzHBqVzSZ9cFZSrNqZdT+ZFMOSK/LoXIPbyawRtHkAkQW1t8bDPueoy95W1xF7x2VijUp4YpL5bdNrKScWj+GcvhiV1JhELQy7tAb5W25sNaj6DdnuQ5I3JFe7MlTeqp2JKlnVoBCOlQQIDAQAB";

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJuwCG2rzak2XPsbXou4eLIuZsxW\n" +
                "fX4CrMcGpXNJn1wVlKs2pl1P5kUw5Ir8uhcg9vJrBG0eQCRBbW3xsM+56jL3lbXEXvHZWKNSnhik\n" +
                "vlt02spJxaP4Zy+GJXUmEQtDLu0Bvlbbmw1qPoN2e5DkjckV7syVN6qnYkqWdWgEI6VBAgMBAAEC\n" +
                "gYAPhGgSpkEFUInL7VprCqPc/or4atZvLM0TuTHcX8YmY3BB8Fx8iG4nD0x4HeBeVcbHOqtiRNWX\n" +
                "x32kq6Y3zgvth4tntLVpMcGYX+6lbNRLDgSXs9Gdp89yYJBCgZl45emjLNejpfaRPkiR7+aDOPlI\n" +
                "TF/aXLeBTGJRvHun+KABEQJBAP7HU9QEKBOOdIDiCncWgWIqdvmrqoyObsLFr15gv/ytQp+JNAOH\n" +
                "RayJtmP3kHg/XuLcbVp0Re5t436HfJcAAIcCQQCcbxkJtQLeSvYqqOBsS4VUEwViPBd27xW69k4Y\n" +
                "HF18BXa1z4g1lIRMSvmTXDegBP+IgPEoMQ0YIIce7ebee4X3AkBhmtVHjQwZaeLCGVavBsUsaV5J\n" +
                "CfX9gPd30Kn9ew0x7OJwIez2SRVtIxjntUj4eDaOrKmMFK1RyXF04MzfQFXzAkBRJ3WWypgNWFgy\n" +
                "s1+R7u/hOOjvGHuX0Nq2HndPHNAGuhLmqR5hpYWoyrCFGS8mTdF/MF1rW18OqDlQ+1xtCSnrAkEA\n" +
                "vxwkCUrThXVIXGrNSe7774K7CCd7R6bKbHDF6YpnGjLnYG/WEGOz9nSkp5xnwUAVCpF/tv9yYWjJ\n" +
                "TZ74rdH1QQ==";


//    	Map<String,String> pars = generateKeyPair();
//    	String publicKey = pars.get("publicKey");
        System.out.println("公钥:"+publicKey);

//    	String privateKey = pars.get("privateKey");
        System.out.println("私钥:"+privateKey);

        String plainText = "13570438496";
        String miwen = RSAUtil.encrypt(publicKey, plainText);
        System.out.println("加密后："+miwen);

//        miwen = "hhHiEFW8X6PL2LphkcXPoFIEVZRlNgvbKOh1XNVVwtbAo1vqp9S0tgfJfbRE7vOycyEKVeKm5G3Z\r\n" +
//                "NneABQ/9LyMnJNnOB4yJEhH9XdM67eaO5RwogRZH4CLZQNbtOIFBzD8OLe/jlWkOQG8DaulcCA+K\r\n" +
//                "0RpgGIK5mkbUkuMvQtc=";
        System.out.println("解密后："+ RSAUtil.decrypt(privateKey, miwen));
    }
}