package com.github.ShinChven.lib.CommonLib.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ShinChven on 15/4/29.
 */
public class AESEncryptUtil {


    public static final String LOG_TAG = "AES_ENCRYPTOR";

    /**
     * 加密
     *
     * @param sSrc 源
     * @param sKey key（长度为16位）
     * @return 加密以后的字符串
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            LogUtil.i(LOG_TAG, "AES_KEY 不能为null");
            return null;
        }
        if (sKey.length() != 16) {
            LogUtil.i(LOG_TAG, "AES_KEY 长度应该为16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //                                  算法/模式/补码方式
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        // 使用Base64将byte array 中的数据转成String。
        // 在流（比如网络）的另一端作为字符串接收以后还能使用Base64转换回来
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**
     * 解密
     *
     * @param sSrc 加密以后的字符串
     * @param sKey key（长度为16位）
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            if (sKey == null) {
                LogUtil.i(LOG_TAG, "AES_KEY 不能为null");
                return null;
            }
            if (sKey.length() != 16) {
                LogUtil.i(LOG_TAG, "AES_KEY 长度应该为16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //                                  算法/模式/补码方式
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 使用用base64将保存在String中的数据转换成byte array
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);
            try {
                byte[] originalData = cipher.doFinal(encrypted1);
                String originalString = new String(originalData, "utf-8");
                return originalString;
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
                return null;
            }
        } catch (Exception ex) {
            LogUtil.printStackTrace(ex);
            return null;
        }
    }
}
