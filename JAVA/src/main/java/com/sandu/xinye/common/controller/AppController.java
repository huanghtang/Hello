package com.sandu.xinye.common.controller;

import com.jfinal.core.NotAction;
import com.sandu.xinye.api.login.UserLoginService;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.model.User;

public class AppController extends BaseController {

	@NotAction
	public User getUser(){
		return UserLoginService.me.getUserCacheWithSessionId(getSessionId());
	}
	
	@NotAction
	public String getSessionId(){
		return getHeader(Constant.APP_ACCESSTOKE);
	}
}
