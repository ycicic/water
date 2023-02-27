package com.ycicic.framework.web;

import com.ycicic.common.constants.CacheKeyConstants;
import com.ycicic.common.exception.ServiceException;
import com.ycicic.common.utils.redis.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ycicic
 */
@Service
public class ValidateService {

    @Resource
    private RedisCache redisCache;

    /**
     * 校验图形验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     */
    public void validateCaptcha(String code, String uuid) {
        String verifyKey = CacheKeyConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new ServiceException("验证码已失效");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new ServiceException("验证码错误");
        }
    }
}
