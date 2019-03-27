package com.sandu.xinye.api.common;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.sandu.xinye.common.constant.CacheConstant;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.FeiGeMsgKit;
import com.sandu.xinye.common.kit.RandomKit;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.User;

public class CommonService {

	public static final CommonService me = new CommonService();

	public RetKit sendCaptcha(String phone, String type) {
		if (StrKit.isBlank(phone) || type == null || StrKit.isBlank(type)) {
			return RetKit.fail("参数不能为空！");
		}
		if (!type.equals(Constant.SEND_CAPTCHA_TYPE_REGISTER) && !type.equals(Constant.SEND_CAPTCHA_TYPE_FORGOT_PWD)) {
			return RetKit.fail("type参数有误！");
		}
		User cu = User.dao.findFirst("select * from user where userPhone=?", phone);
		if (type.equals(Constant.SEND_CAPTCHA_TYPE_REGISTER)) {
			if (cu != null) {
				return RetKit.fail("该手机号码被绑定账号！不能重复注册！");
			}
		}
		if (type.equals(Constant.SEND_CAPTCHA_TYPE_FORGOT_PWD)) {
			if (cu == null) {
				return RetKit.fail("该手机号码未注册！");
			}
		}
		String isExistCaptcha = CacheKit.get(CacheConstant.CAPTCHA, phone);
		if (isExistCaptcha != null) {
			return RetKit.fail("2分钟内不能重复发送验证码！");
		}
		// 发送验证码
		String captcha = RandomKit.getRandomPsw(4);
		boolean succ = new FeiGeMsgKit().send(phone, captcha);
		CacheKit.put(CacheConstant.CAPTCHA, phone, captcha);
		return succ ? RetKit.ok() : RetKit.fail();
	}

	public RetKit checkCaptcha(String phone, String captcha) {
		if (StrKit.isBlank(phone) || StrKit.isBlank(captcha)) {
			return RetKit.fail("手机号和验证码不能为空");
		}
		String realCaptcha = CacheKit.get(CacheConstant.CAPTCHA, phone);
		if (StrKit.isBlank(realCaptcha)) {
			return RetKit.fail("验证码为空，请重新发送验证码");
		}
		if (realCaptcha.equals(captcha)) {
			return RetKit.ok().setMsg("验证码正确！");
		} else {
			return RetKit.fail("验证失败！");
		}
	}

}
