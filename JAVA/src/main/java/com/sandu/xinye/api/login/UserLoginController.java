package com.sandu.xinye.api.login;

import com.jfinal.aop.Clear;
import com.jfinal.kit.LogKit;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class UserLoginController extends AppController {

	@Clear
	public void register() {
		LogKit.info("用户注册接口开始--------------------");
		String phone = getPara("phone");
		String password = getPara("password");
		String captcha = getPara("captcha");
		RetKit ret = UserLoginService.me.register(phone, password, captcha, getIpAddress());
		renderJson(ret);
		LogKit.info("用户注册接口结束--------------------");
	}

	@Clear
	public void doLogin() {
		LogKit.info("用户账号登录接口开始--------------------");
		String phone = getPara("phone");
		String password = getPara("password");
		String platform = getHeader("platform");
		RetKit ret = UserLoginService.me.doLogin(phone, password, platform, getIpAddress());
		renderJson(ret);
		LogKit.info("用户账号登录接口开始--------------------");
	}

	public void logout() {
		LogKit.info("调用退出登录接口开始-------------------------->");
		boolean succ = UserLoginService.me.logout(getHeader(Constant.APP_ACCESSTOKE));
		renderJson(succ ? RetKit.ok() : RetKit.fail());
		LogKit.info("调用退出登录接口结束-------------------------->");
	}

	@Clear
	public void retSetPwd() {
		LogKit.info("调用忘记、重设密码接口开始-------------------------->" + getParaToMap().toJson());
		String phone = getPara("phone");
		String password = getPara("password");
		String checkPwd = getPara("checkPwd");
		RetKit ret = UserLoginService.me.retSetPwd(phone, password, checkPwd);
		renderJson(ret);
		LogKit.info("调用忘记、重设密码接口结束-------------------------->");
	}

	@Clear
	public void fasterLogin() {
		LogKit.info("调用第三方登录接口开始---------------");
		String openId = getPara("openId");
		Integer loginType = getParaToInt("loginType");
		String platform = getHeader("platform");
		String avatar = getPara("avatar");
		String nickName = getPara("nickName");
		String ip = getIpAddress();
		RetKit ret = UserLoginService.me.fasterLogin(openId, loginType, platform, avatar, nickName, ip);
		renderJson(ret);
		LogKit.info("调用第三方登录接口结束---------------");
	}

}
