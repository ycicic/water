package com.ycicic.common.core.domain;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author ycicic
 */
public interface BaseLoginUser extends UserDetails {

    Long getUserId();

    Long getExpireTime();

    void setLoginTime(Long loginTime);

    Long getLoginTime();

    void setExpireTime(Long expireTime);

    String getToken();
}
