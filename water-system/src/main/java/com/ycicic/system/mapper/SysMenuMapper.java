package com.ycicic.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycicic.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ycicic
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select distinct m.perms\n" +
            "from sys_menu m\n" +
            "         left join sys_role_menu rm on m.id = rm.menu_id\n" +
            "         left join sys_user_role ur on rm.role_id = ur.role_id\n" +
            "         left join sys_role r on r.id = ur.role_id\n" +
            "where m.status = 0\n" +
            "  and r.status = 0\n" +
            "  and m.deleted = 0\n" +
            "  and ur.user_id = #{userId}")
    List<String> selectMenuPermsByUser(@Param("userId") Long userId);

    @Select("select distinct m.id,\n" +
            "                m.parent_id,\n" +
            "                m.menu_name,\n" +
            "                m.path,\n" +
            "                m.component,\n" +
            "                m.`query`,\n" +
            "                m.visible,\n" +
            "                m.status,\n" +
            "                ifnull(m.perms, '''') as perms,\n" +
            "                m.is_frame,\n" +
            "                m.is_cache,\n" +
            "                m.menu_type,\n" +
            "                m.icon,\n" +
            "                m.order_num,\n" +
            "                m.create_time\n" +
            "from sys_menu m\n" +
            "where m.menu_type in ('M', 'C')\n" +
            "  and m.status = 0\n" +
            "  and m.deleted = 0\n" +
            "order by m.parent_id, m.order_num")
    List<SysMenu> selectMenuTreeAll();

    @Select("select distinct m.id,\n" +
            "                m.parent_id,\n" +
            "                m.menu_name,\n" +
            "                m.path,\n" +
            "                m.component,\n" +
            "                m.`query`,\n" +
            "                m.visible,\n" +
            "                m.status,\n" +
            "                ifnull(m.perms, '') as perms,\n" +
            "                m.is_frame,\n" +
            "                m.is_cache,\n" +
            "                m.menu_type,\n" +
            "                m.icon,\n" +
            "                m.order_num,\n" +
            "                m.create_time\n" +
            "from sys_menu m\n" +
            "         left join sys_role_menu rm on m.id = rm.menu_id\n" +
            "         left join sys_user_role ur on rm.role_id = ur.role_id\n" +
            "         left join sys_role ro on ur.role_id = ro.id\n" +
            "         left join sys_user u on ur.user_id = u.id\n" +
            "where u.id = #{userId} \n" +
            "  and m.menu_type in ('M', 'C')\n" +
            "  and m.status = 0\n" +
            "  and m.deleted = 0\n" +
            "  and ro.status = 0\n" +
            "order by m.parent_id, m.order_num")
    List<SysMenu> selectMenuTreeByUser(@Param("userId") Long userId);

    @Select("select m.id\n" +
            "from sys_menu m\n" +
            "         left join sys_role_menu rm on m.id = rm.menu_id\n" +
            "where rm.role_id = #{roleId}\n" +
            "  and m.id not in (select m.parent_id\n" +
            "                   from sys_menu m\n" +
            "                            inner join sys_role_menu rm on m.id = rm.menu_id and rm.role_id = #{roleId})\n" +
            "order by m.parent_id, m.order_num")
    List<Long> queryIdListByRoleId(@Param("roleId") Long roleId);

    @Select("select distinct m.id,\n" +
            "                m.parent_id,\n" +
            "                m.menu_name,\n" +
            "                m.path,\n" +
            "                m.component,\n" +
            "                m.`query`,\n" +
            "                m.visible,\n" +
            "                m.status,\n" +
            "                ifnull(m.perms, '''') as perms,\n" +
            "                m.is_frame,\n" +
            "                m.is_cache,\n" +
            "                m.menu_type,\n" +
            "                m.icon,\n" +
            "                m.order_num,\n" +
            "                m.create_time\n" +
            "from sys_menu m\n" +
            "${ew.customSqlSegment}\n" +
            "and m.deleted = 0\n" +
            "order by m.parent_id, m.order_num")
    List<SysMenu> selectMenuList(@Param("ew") Wrapper<SysMenu> wrapper);

    @Select("select distinct m.id,\n" +
            "                m.parent_id,\n" +
            "                m.menu_name,\n" +
            "                m.path,\n" +
            "                m.component,\n" +
            "                m.`query`,\n" +
            "                m.visible,\n" +
            "                m.status,\n" +
            "                ifnull(m.perms, '') as perms,\n" +
            "                m.is_frame,\n" +
            "                m.is_cache,\n" +
            "                m.menu_type,\n" +
            "                m.icon,\n" +
            "                m.order_num,\n" +
            "                m.create_time\n" +
            "from sys_menu m\n" +
            "         left join sys_role_menu rm on m.id = rm.menu_id\n" +
            "         left join sys_user_role ur on rm.role_id = ur.role_id\n" +
            "         left join sys_role ro on ur.role_id = ro.id\n" +
            "         left join sys_user u on ur.user_id = u.id\n" +
            "  ${ew.customSqlSegment}\n" +
            "  and u.id = #{userId} \n" +
            "  and m.deleted = 0\n" +
            "  and ro.status = 0\n" +
            "order by m.parent_id, m.order_num")
    List<SysMenu> selectMenuListByUser(@Param("userId") Long userId, @Param("ew") Wrapper<SysMenu> wrapper);
}
