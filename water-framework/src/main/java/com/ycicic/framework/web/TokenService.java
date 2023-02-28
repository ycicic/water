package com.ycicic.framework.web;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ycicic.common.constants.CacheKeyConstants;
import com.ycicic.common.constants.Constants;
import com.ycicic.common.core.domain.BaseLoginUser;
import com.ycicic.common.utils.redis.RedisCache;
import com.ycicic.system.domain.SysLoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Token操作
 *
 * @author ycicic
 */
@Service
public class TokenService {

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;
    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;
    /**
     * 令牌有效期（分钟）
     */
    @Value("${token.expireTime}")
    private int expireTime;
    /**
     * 令牌刷新时间（分钟）
     */
    @Value("${token.refreshTime}")
    private int refreshTime;

    protected static final long MILLIS_SECOND = 1000L;

    protected static final long MILLIS_MINUTE = 60L * MILLIS_SECOND;

    @Resource
    private RedisCache redisCache;


    public String createToken(SysLoginUser sysLoginUser) {
        String token = UUID.randomUUID().toString().replace("-", "");
        sysLoginUser.setToken(token);

        refreshToken(sysLoginUser);

        Map<String, Object> claims = new HashMap<>(1);
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }


    public void refreshToken(BaseLoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    public void setLoginUser(BaseLoginUser loginUser) {
        if (Objects.nonNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    public BaseLoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                return redisCache.getCacheObject(userKey);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String getTokenKey(String uuid) {
        return CacheKeyConstants.SYS_LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 获取请求token
     *
     * @param request request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    public void verifyToken(BaseLoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= refreshTime * MILLIS_MINUTE) {
            refreshToken(loginUser);
        }
    }
}
