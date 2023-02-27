package com.ycicic.framework.security.filter;

import com.ycicic.common.core.domain.BaseLoginUser;
import com.ycicic.framework.security.authentication.SysPasswordAuthenticationToken;
import com.ycicic.common.utils.SecurityUtils;
import com.ycicic.framework.web.TokenService;
import com.ycicic.system.domain.SysLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author ycicic
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        BaseLoginUser loginUser = tokenService.getLoginUser(request);
        if (Objects.nonNull(loginUser) && Objects.isNull(SecurityUtils.getAuthentication())) {
            if (loginUser instanceof SysLoginUser) {
                SysLoginUser sysLoginUser = (SysLoginUser) loginUser;
                SysPasswordAuthenticationToken authenticationToken = new SysPasswordAuthenticationToken(sysLoginUser, null, sysLoginUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                tokenService.verifyToken(loginUser);
            }
        }
        filterChain.doFilter(request, response);
    }
}
