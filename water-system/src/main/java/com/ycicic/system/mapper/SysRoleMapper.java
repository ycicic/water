package com.ycicic.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycicic.system.entity.SysRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ycicic
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select distinct r.*\n" +
            "from sys_role r\n" +
            "         left join sys_user_role ur on ur.role_id = r.id\n" +
            "         left join sys_user u on u.id = ur.user_id\n" +
            "where r.deleted = 0 and ur.user_id = #{userId}")
    List<SysRole> selectRolePermissionByUser(Long userId);

}
