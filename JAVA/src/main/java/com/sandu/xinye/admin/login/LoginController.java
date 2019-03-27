package com.sandu.xinye.admin.login;

import com.jfinal.aop.Clear;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.constant.RetConstant;
import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.SysUser;

public class LoginController extends AdminController {
	@Clear
	public void doLogin() {
		String username = getPara("username");
		String password = getPara("password");
		String loginIp = getIpAddress();
		renderJson(LoginService.me.doLogin(username, password,loginIp));
	}	
	
	public void loginBySessionId(){
		String sessionId = getCookie(Constant.SYS_USER_SESSION_ID);
		SysUser user = LoginService.me.getSysUserCacheWithSessionId(sessionId);
		if(user == null){
			user = LoginService.me.loginWithSessionId(sessionId, getIpAddress());
		}
		if(user == null){
			renderJson(RetKit.fail(RetConstant.CODE_LOGIN_EXPIRE,"Session已过期，请重新登录！"));
		} else {
			renderJson(RetKit.ok("sysUser", user));
		}
	}
	
	public void logOut(){
		String sessionId = getCookie(Constant.SYS_USER_SESSION_ID);
		boolean succ = LoginService.me.logOut(sessionId);
		renderJson(succ ? RetKit.ok() : RetKit.fail());
	}
}
