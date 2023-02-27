package com.ycicic.system.service;

import com.ycicic.system.entity.SysMenu;
import com.ycicic.system.vo.Router;

import java.util.List;

/**
 * @author ycicic
 */
public interface RouterService {

    List<Router> buildRouter(List<SysMenu> menus);

}
