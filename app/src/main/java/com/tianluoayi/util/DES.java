package com.tianluoayi.util;

import org.apaches.commons.codec.binary.Hex;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 *
 */
public class DES {

    public static final String DEFAULT_KEY = "FZMSTHwNxzbnbqTgRz";

    private final static String DES = "DES";

    // private final static String MODE = "DES/CBC/PKCS5Padding";

    private final static String MODE = "DES/ECB/PKCS5Padding";

    private DES() {

    }

    /**
     * 加密,使用默认秘钥
     *
     * @param input 加密前的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encode(String input) throws Exception {
        byte[] data = encrypt(DEFAULT_KEY, input);
        return Hex.encodeHexString(data);
    }

    /**
     * 加密
     *
     * @param key   密钥
     * @param input 加密前的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encode(String key, String input) throws Exception {
        byte[] data = encrypt(key, input);
        return Hex.encodeHexString(data);
    }

    /**
     * 加密
     *
     * @param key   密钥
     * @param input 加密前的字符串
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String key, String input) throws Exception {
        return doFinal(key, Cipher.ENCRYPT_MODE, input.getBytes());
    }

    /**
     * 解密
     *
     * @param key   密钥
     * @param input 解密前的字符串
     * @return encode方法返回的字符串
     * @throws Exception
     */
    public static String decode(String key, String input) throws Exception {
        byte[] data = Hex.decodeHex(input.toCharArray());
        return new String(decrypt(key, data));
    }

    /**
     * 解密
     *
     * @param input 解密前的字符串
     * @return encode方法返回的字符串
     * @throws Exception
     */
    public static String decode(String input) throws Exception {
        return decode(DEFAULT_KEY, input);
    }

    /**
     * 解密
     *
     * @param key   密钥
     * @param input encrypt方法返回的字节数组
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(String key, byte[] input) throws Exception {
        return doFinal(key, Cipher.DECRYPT_MODE, input);
    }

    /**
     * 执行加密解密操作
     *
     * @param key    密钥
     * @param opmode 操作类型：Cipher.ENCRYPT_MODE-加密，Cipher.DECRYPT_MODE-解密
     * @param input  加密解密前的字节数组
     * @return
     * @throws Exception
     */
    private static byte[] doFinal(String key, int opmode, byte[] input) throws Exception {
        key = key != null ? key : DEFAULT_KEY;
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(MODE);
        // 用密匙初始化Cipher对象
        // IvParameterSpec param = new IvParameterSpec(IV);
        // cipher.init(Cipher.DECRYPT_MODE, securekey, param, sr);
        cipher.init(opmode, securekey, sr);
        // 执行加密解密操作
        return cipher.doFinal(input);
    }

    public static void main(String[] args) throws Exception {
        String input = "测试";
        String key = DEFAULT_KEY;

        byte[] data = encrypt(key, input);
        System.out.println(new String(data));
        String output = encode(key, input);
        System.out.println(output);

        System.out.println(new String(decrypt(key, data)));
        System.out.println("breakline");
        // System.out.println(decode(key, "59262fb169f5748fd8e83f895b304675"));
        // System.out
        // .println(encode(DEFAULT_KEY, "600570YASD8" + "1505287602244" +
        // "a3d2c1d8-4d3e-45a5-84d0-816c6c3fec30"));
        // System.out.println(encode(Formater.makeToken(10801461, 10801461,
        // System.currentTimeMillis())));
        // System.out.println(encode(Formater.makeToken(122413221323212L, 10801461,
        // System.currentTimeMillis())).length());
        // System.out.println(decode(encode(Formater.makeToken(122413221323212L,
        // 10801461, System.currentTimeMillis()))));
        // System.out.println(Arrays.toString(Formater.parseToken(
        // decode(encode(Formater.makeToken(122413221323212L, 10801461,
        // System.currentTimeMillis()))))));
    }

}
