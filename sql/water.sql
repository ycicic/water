create table sys_menu
(
    id          bigint                                 not null comment '菜单id'
        primary key,
    menu_name   varchar(50)                            not null comment '菜单名称',
    parent_id   bigint       default 0                 not null comment '父菜单ID',
    order_num   int          default 0                 not null comment '显示顺序',
    path        varchar(200) default ''                not null comment '路由地址',
    component   varchar(255)                           null comment '组件路径',
    query       varchar(255)                           null comment '路由参数',
    is_frame    tinyint      default 1                 not null comment '是否为外链（0是 1否）',
    is_cache    tinyint      default 0                 not null comment '是否缓存（0缓存 1不缓存）',
    menu_type   char         default ''                not null comment '菜单类型（M目录 C菜单 F按钮）',
    visible     tinyint      default 0                 not null comment '显示状态（0显示 1隐藏）',
    status      tinyint      default 0                 not null comment '菜单状态（0正常 1停用）',
    perms       varchar(100)                           null comment '权限标识',
    icon        varchar(100) default '#'               null comment '菜单图标',
    remark      text                                   null comment '备注',
    create_by   bigint                                 not null comment '创建者',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by   bigint                                 null comment '更新者',
    update_time datetime                               null comment '更新时间',
    deleted     tinyint      default 0                 not null comment '删除标识'
)
    comment '菜单权限表';

create index sys_menu_k
    on sys_menu (status, deleted);

INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1, '系统管理', 0, 1, 'system', null, '', 1, 0, 'M', 0, 0, '', 'Tools', '系统管理目录', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', 0, 0, 'system:user:list', 'UserFilled', '用户管理菜单', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', 0, 0, 'system:role:list', 'Avatar', '角色管理菜单', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', 0, 0, 'system:menu:list', 'Menu', '菜单管理菜单', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', 0, 0, 'system:user:query', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', 0, 0, 'system:user:add', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', 0, 0, 'system:user:edit', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', 0, 0, 'system:user:remove', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', 0, 0, 'system:user:export', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', 0, 0, 'system:user:import', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', 0, 0, 'system:user:resetPwd', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', 0, 0, 'system:role:query', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', 0, 0, 'system:role:add', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', 0, 0, 'system:role:edit', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', 0, 0, 'system:role:remove', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', 0, 0, 'system:role:export', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', 0, 0, 'system:menu:query', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', 0, 0, 'system:menu:add', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', 0, 0, 'system:menu:edit', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', 0, 0, 'system:menu:remove', '#', '', 1, '2023-02-09 13:32:59', 1, null, 0);

create table sys_role
(
    id          bigint                             not null comment '角色id'
        primary key,
    role_name   varchar(30)                        not null comment '角色名称',
    role_sort   int                                not null comment '显示顺序',
    status      tinyint                            not null comment '角色状态（0正常 1停用）',
    remark      text                               null comment '备注',
    create_by   bigint                             not null comment '创建者',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by   bigint                             null comment '更新者',
    update_time datetime                           null comment '更新时间',
    deleted     tinyint  default 0                 not null comment '删除标识'
)
    comment '角色信息表';

create index sys_role_k
    on sys_role (status, deleted);

INSERT INTO sys_role (id, role_name, role_sort, status, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1, '超级管理员', 1, 0, '', 1, '2023-02-28 13:56:13', 1, '2023-02-01 13:56:23', 0);
INSERT INTO sys_role (id, role_name, role_sort, status, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1630492856162779138, '管理员', 2, 0, '无角色、菜单管理权限', 1, '2023-02-28 16:59:25', 1, '2023-03-01 16:22:16', 0);


create table sys_role_menu
(
    role_id bigint not null comment '角色ID',
    menu_id bigint not null comment '菜单ID',
    primary key (role_id, menu_id)
)
    comment '角色和菜单关联表';

INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 100);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 101);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 102);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1000);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1001);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1002);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1004);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1005);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1006);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1007);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1630492856162779138, 1012);

create table sys_user
(
    id          bigint                                 not null comment '用户id'
        primary key,
    user_name   varchar(30)                            not null comment '用户账号',
    nick_name   varchar(30)                            not null comment '用户昵称',
    email       varchar(50)  default ''                null comment '用户邮箱',
    phone       varchar(11)  default ''                null comment '手机号码',
    gender      tinyint      default 0                 null comment '用户性别',
    avatar      varchar(100) default ''                null comment '头像地址',
    password    varchar(100) default ''                null comment '密码',
    status      tinyint      default 0                 null comment '帐号状态',
    remark      text                                   null comment '备注',
    create_by   bigint                                 not null comment '创建者',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by   bigint                                 null comment '更新者',
    update_time datetime                               null comment '更新时间',
    deleted     tinyint      default 0                 not null comment '删除标识'
)
    comment '用户信息表';

create index sys_user_k
    on sys_user (user_name, deleted);

INSERT INTO sys_user (id, user_name, nick_name, email, phone, gender, avatar, password, status, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1, 'admin', '管理员', 'water@qq.com', '15888888888', 0, '', '$2a$10$8m6jPakYDu0P02Y4tByjsOD35M3U7hM/SAu5RKYn3vl/zbwdKrm7i', 0, '', 1, '2023-02-08 16:55:58', 1, '2023-02-28 17:34:43', 0);
INSERT INTO sys_user (id, user_name, nick_name, email, phone, gender, avatar, password, status, remark, create_by, create_time, update_by, update_time, deleted) VALUES (1630842405716819970, 'test123', 'test123', '', '', 0, '', '$2a$10$D5iZK3s5z5.Dj7uS8NtAJOL8Om5uV6rpcf2U8f0PjCng2AQVrol1G', 0, '', 1, '2023-03-01 16:08:24', 1, '2023-03-01 16:08:24', 0);


create table sys_user_role
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    primary key (user_id, role_id)
)
    comment '用户和角色关联表';

INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1630492856162779138);
INSERT INTO sys_user_role (user_id, role_id) VALUES (1630842405716819970, 1630492856162779138);
