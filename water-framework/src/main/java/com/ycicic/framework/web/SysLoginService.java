package com.ycicic.framework.web;

import com.ycicic.framework.security.authentication.SysPasswordAuthenticationToken;
import com.ycicic.framework.security.context.AuthenticationContextHolder;
import com.ycicic.system.domain.SysLoginUser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ycicic
 */
@Service
public class SysLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    private ValidateService validateService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setValidateService(ValidateService validateService) {
        this.validateService = validateService;
    }

    @SneakyThrows
    public String login(String username, String password, String code, String uuid) {
        validateService.validateCaptcha(code, uuid);

        Authentication authentication;
        SysPasswordAuthenticationToken authenticationToken = new SysPasswordAuthenticationToken(username, password);
        AuthenticationContextHolder.setContext(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);

        SysLoginUser sysLoginUser = (SysLoginUser) authentication.getPrincipal();
        return tokenService.createToken(sysLoginUser);
    }

}
