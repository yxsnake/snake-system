package com.snake.system.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.util.Base64;

/**
 * @author: snake
 * @create-time: 2024-06-29
 * @description: aes工具类
 * @version: 1.0
 */
public class AesUtil {

    public static String encryptHex(String key,String content){
        byte[] bytes = Base64.getDecoder().decode(key);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, bytes);
        return aes.encryptHex(content);
    }

    public static String decryptStr(String key,String encryptHex){
        byte[] bytes = Base64.getDecoder().decode(key);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, bytes);
        return aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }
    public static String generateKey(){
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return Base64.getEncoder().encodeToString(key);
    }

}
