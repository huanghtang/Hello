package com.sandu.xinye.common.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.NotAction;
import com.sandu.xinye.admin.login.LoginService;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.model.SysUser;

public class AdminController extends BaseController {
	
	@Clear
	public void index(){
		render("/admin_index.html");
	}
	
	@NotAction
	public SysUser getAccount(){
		return LoginService.me.getSysUserCacheWithSessionId(getSessionId());
	}
	
	@NotAction
	public String getSessionId(){
		return getCookie(Constant.SYS_USER_SESSION_ID);
	}
}
