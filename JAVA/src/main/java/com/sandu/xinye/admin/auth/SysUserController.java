package com.sandu.xinye.admin.auth;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.SysUser;

public class SysUserController extends AdminController {

	public void list() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		RetKit ret = SysUserService.me.list(pageNumber, pageSize, getParaToMap());
		renderJson(ret);
	}
	
	public void getRoleList(){
		RetKit ret = SysUserService.me.getRoleList();
		renderJson(ret);
	}
	
	public void save() {
		SysUser su = getBean(SysUser.class, "");
		RetKit ret = SysUserService.me.save(su, getAccount(), getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		SysUser su = getBean(SysUser.class,"");
		RetKit ret = SysUserService.me.update(su, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String id = getPara(0);
		RetKit ret = SysUserService.me.del(id,getAccount(),getIpAddress());
		renderJson(ret);
	}
}
