package com.snake.system.util;

import cn.hutool.core.util.StrUtil;
import io.github.yxsnake.pisces.web.core.exception.BizException;


/**
 * @author: snake
 * @create-time: 2024-06-29
 * @description: 账号密码相关工具
 * @version: 1.0
 */
public class PasswordUtil {

    /**
     * 得到默认密码
     * @param pwd
     * @return
     */
    public static String getEncryptPwd(String key,String pwd){
        if(StrUtil.isBlank(pwd) ){
            throw new BizException("密码不能为空");
        }
        return AesUtil.encryptHex(key,pwd);
    }

    /**
     * 校验密码正确性
     * @param key
     * @param dbPwd
     * @param inputPwd
     * @return
     */
    public static Boolean checkPwd(String key,String dbPwd,String inputPwd){
        String encryptPwd = getEncryptPwd(key, inputPwd);
        return dbPwd.equals(encryptPwd);
    }

    public static void main(String[] args) {
        String key = "RW0S6YkTQ4WFzXpbbhkBDg==";
        System.out.println(key);
        String content = "610001";
        String s = AesUtil.encryptHex(key, content);
        System.out.println(s);
        String s1 = AesUtil.decryptStr(key, s);
        System.out.println(s1);
    }
}
