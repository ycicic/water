package com.ycicic.framework.web;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.ycicic.common.constants.CacheKeyConstants;
import com.ycicic.common.constants.Constants;
import com.ycicic.common.core.vo.CaptchaImage;
import com.ycicic.common.utils.redis.RedisCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ycicic
 */
@Slf4j
@Service
public class ValidateCodeService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource
    private RedisCache redisCache;

    @SneakyThrows
    public CaptchaImage getCaptchaImage() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String verifyKey = CacheKeyConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr, code;
        BufferedImage image;

        capStr = code = captchaProducer.createText();
        image = captchaProducer.createImage(capStr);

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        return new CaptchaImage(uuid, Base64.encode(os.toByteArray()));
    }

}
