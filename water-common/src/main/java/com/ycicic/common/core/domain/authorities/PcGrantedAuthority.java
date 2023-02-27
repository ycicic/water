package com.ycicic.common.core.domain.authorities;

import org.springframework.security.core.GrantedAuthority;

/**
 * PC端用户权限
 *
 * @author ycicic
 */
public class PcGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -6015658485833339716L;

    public PcGrantedAuthority(String role) {
        this.role = role;
    }

    private final String role;

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof PcGrantedAuthority)) {
            return false;
        } else {
            PcGrantedAuthority pg = (PcGrantedAuthority) obj;
            return this.role.equals(pg.role);
        }
    }
}
