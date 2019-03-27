package com.sandu.xinye.admin.auth;


import java.util.List;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.SysUserRole;

public class RoleController extends AdminController {

	public void getRoleList() {
		RetKit ret = RoleService.me.getRoleList();
		renderJson(ret);
	}
	
	public void getMenuList(){
		renderJson(RetKit.ok("list", RoleService.me.findMenus()));
	}
	
	public void save() {
		List<String> menus = getParaToList("menus");
		String name = getPara("name");
		RetKit ret = RoleService.me.save(menus, name, getAccount(), getIpAddress());
		renderJson(ret);
	}
	
	public void update() {
		List<String> menus = getParaToList("menus");
		SysUserRole role = getBean(SysUserRole.class, "");
		RetKit ret = RoleService.me.update(menus, role, getAccount(), getIpAddress());
		renderJson(ret);
	}

	public void remove() {
		Integer id = getParaToInt();
		renderJson(RoleService.me.remove(id, getAccount(), getIpAddress()));
	}
}
