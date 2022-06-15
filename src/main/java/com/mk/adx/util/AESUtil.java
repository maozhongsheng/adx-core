package com.mk.adx.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *AES加密解密工具类
 *@author M-Y
 */
public class AESUtil {

    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    // private static final String KEY = "exwo0aln9sqgdtdn";  //测试key
    private static final String KEY = "1c944b7dbab49ec1"; //正式key
    private static final String KEY_MD5 = "MD5";
    public static final String AES = "AES";
    public static final String CBC_MODE = "AES/CBC/PKCS5Padding";
    private static MessageDigest md5Digest;

    static {
        try {
            md5Digest = MessageDigest.getInstance(KEY_MD5);
        } catch (NoSuchAlgorithmException e) {
            //
        }
    }

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param
     * @return
     */
    public static String encrypt(String data) {
        return doAES(data, KEY, Cipher.ENCRYPT_MODE);
    }
    /**
     * 解密
     *
     * @param data 待解密内容
     * @param
     * @return
     */
    public static String decrypt(String data) {
        return doAES(data, KEY, Cipher.DECRYPT_MODE);
    }

    /**
     * 加解密
     *
     * @param data
     * @param key
     * @param mode
     * @return
     */
    private static String doAES(String data, String key, int mode) {
        try {
            if (StringUtils.isBlank(data) || StringUtils.isBlank(key)) {
                return null;
            }
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            //true 加密内容 false 解密内容
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = parseHexStr2Byte(data);
            }
            SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(defaultCharset)), KEY_AES);//构造一个密钥
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            cipher.init(mode, keySpec);// 初始化
            byte[] result = cipher.doFinal(content);//加密或解密
            if (encrypt) {
                return parseByte2HexStr(result);
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    //此函数是将字符串每两个字符合并生成byte数组
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index  > hexString.length() - 1)
                return byteArray;
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        System.out.println(byteArray.length);
        return byteArray;
    }


    //此函数是pkcs7padding填充函数
    public static String pkcs7padding(String data) {
        int bs = 16;
        int padding = bs - (data.length() % bs);
        String padding_text = "";
        for (int i = 0; i < padding; i++) {
            padding_text += (char)padding;
        }
        return data+padding_text;
    }


    /**
     * CBC AES 加密算法
     *
     * @param content 加密内容 原文
     * @param password 加密密钥
     * @param ivData 偏移量
     * @return String 密文
     * @throws Exception
     */
    public static String encrypt(String content, String password, String ivData) throws
            Exception {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(password)) {
            return StringUtils.EMPTY;
        }
        Key key = new SecretKeySpec(password.getBytes(), AES);
        Cipher in = Cipher.getInstance(CBC_MODE);
        in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivData.getBytes()));
        return Base64.encodeBase64String(in.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * CBC AES 解密
     *
     * @param ciphertext 密文
     * @param password 解密密钥
     * @param ivData 偏移量
     * @return 原文
     * @throws Exception
     */
    public static String decrypt(String ciphertext, String password, String ivData)
            throws Exception {
        if (StringUtils.isBlank(ciphertext) || StringUtils.isBlank(password)) {
            return StringUtils.EMPTY;
        }
        Key key = new SecretKeySpec(password.getBytes(), AES);
        Cipher out = Cipher.getInstance(CBC_MODE);
        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivData.getBytes()));
        byte[] contentBytes = out.doFinal(Base64.decodeBase64(ciphertext));
        return new String(contentBytes, StandardCharsets.UTF_8);
    }

}