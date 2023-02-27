package com.ycicic.framework.security.provider;

import com.ycicic.common.exception.ServiceException;
import com.ycicic.framework.security.authentication.SysPasswordAuthenticationToken;
import com.ycicic.framework.web.SysPermissionService;
import com.ycicic.framework.web.SysUserPasswordService;
import com.ycicic.system.domain.SysLoginUser;
import com.ycicic.system.entity.SysUser;
import com.ycicic.system.enums.UserStatusEnum;
import com.ycicic.system.service.SysUserService;
import com.ycicic.system.vo.SysUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ycicic
 */
@Slf4j
@Component
public class SysPasswordLoginProvider implements AuthenticationProvider {

    private SysUserService sysUserService;

    private SysUserPasswordService passwordService;

    private SysPermissionService permissionService;

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setPasswordService(SysUserPasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Autowired
    public void setPermissionService(SysPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        log.debug("密码验证登录 -> 用户名:{}", userName);
        SysUser sysUser = sysUserService.getByUserName(userName);
        if (Objects.isNull(sysUser)) {
            throw new ServiceException("用户：" + userName + " 不存在");
        } else if (UserStatusEnum.DEACTIVATE.equals(sysUser.getStatus())) {
            throw new ServiceException("用户：" + userName + " 已停用");
        }
        passwordService.validate(sysUser);
        SysLoginUser loginUser = createLoginUser(sysUser);
        return new SysPasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SysPasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private SysLoginUser createLoginUser(SysUser user) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        BeanUtils.copyProperties(user, sysUserInfo);
        return new SysLoginUser(user.getId(), sysUserInfo, permissionService.getMenuPermission(sysUserInfo));
    }
}
