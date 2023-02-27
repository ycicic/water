package com.ycicic.controller.common;

import com.ycicic.common.core.vo.CaptchaImage;
import com.ycicic.common.core.vo.Response;
import com.ycicic.framework.web.ValidateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycicic
 */
@Api(tags = "验证码接口")
@RestController
@RequestMapping("/common/validate")
public class ValidateCodeController {

    private ValidateCodeService validateCodeService;

    @Autowired
    public void setValidateCodeService(ValidateCodeService validateCodeService) {
        this.validateCodeService = validateCodeService;
    }

    /**
     * 生成验证码
     */
    @ApiOperation("生成图片验证码")
    @GetMapping("/captchaImage")
    public Response<CaptchaImage> captchaImage() {
        return Response.success(validateCodeService.getCaptchaImage());
    }

}
