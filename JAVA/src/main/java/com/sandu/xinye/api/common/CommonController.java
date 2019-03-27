package com.sandu.xinye.api.common;

import com.jfinal.aop.Clear;
import com.jfinal.kit.LogKit;
import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class CommonController extends AppController {

	@Clear
	public void sendCaptcha(){
		LogKit.info("调用发送验证码接口开始-----------------"+getParaToMap().toJson());
		String phone = getPara("phone");
		String type = getPara("type");
		RetKit ret = CommonService.me.sendCaptcha(phone,type);
		renderJson(ret);
		LogKit.info("调用发送验证码接口结束-----------------");
	}
	
	@Clear
	public void checkCaptcha(){
		String phone = getPara("phone");
		String captcha = getPara("captcha");
		RetKit ret = CommonService.me.checkCaptcha(phone,captcha);
		renderJson(ret);
	}
}
