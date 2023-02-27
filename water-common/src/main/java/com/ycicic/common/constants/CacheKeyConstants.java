package com.ycicic.common.constants;

/**
 * 缓存key常量
 *
 * @author ycicic
 */
public class CacheKeyConstants {
    /**
     * PC端登录用户 redis key
     */
    public static final String SYS_LOGIN_TOKEN_KEY = "sys_login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
}
