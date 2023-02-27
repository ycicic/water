package com.ycicic.common.core.domain.authorities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ycicic
 */
public class GrantedAuthorityConstants {

    public static List<PcGrantedAuthority> PC_GRANTED_AUTHORITY_LIST = new ArrayList<>();

    static {
        PC_GRANTED_AUTHORITY_LIST.add(new PcGrantedAuthority("ROLE_SYSTEM"));
        PC_GRANTED_AUTHORITY_LIST.add(new PcGrantedAuthority("ROLE_COMMON"));
    }

}
