package com.ycicic.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycicic.system.entity.SysRole;
import com.ycicic.system.mapper.SysRoleMapper;
import com.ycicic.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ycicic
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public Set<String> selectRolePermissionByUser(Long userId) {
        List<SysRole> list = baseMapper.selectRolePermissionByUser(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : list) {
            if (Objects.nonNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

}
