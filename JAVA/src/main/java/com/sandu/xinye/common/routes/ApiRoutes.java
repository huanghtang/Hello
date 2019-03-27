package com.sandu.xinye.common.routes;

import com.jfinal.config.Routes;
import com.sandu.xinye.api.about.AboutController;
import com.sandu.xinye.api.banner.BannerApiController;
import com.sandu.xinye.api.common.CommonController;
import com.sandu.xinye.api.feedback.FeedbackController;
import com.sandu.xinye.api.font.FontController;
import com.sandu.xinye.api.help.HelpController;
import com.sandu.xinye.api.login.UserLoginController;
import com.sandu.xinye.api.logo.LogoController;
import com.sandu.xinye.api.machine.MachineController;
import com.sandu.xinye.common.interceptor.AppUserInterceptor;

public class ApiRoutes extends Routes {

	@Override
	public void config() {
		this.addInterceptor(new AppUserInterceptor());
		// 登录
		this.add("/api/login", UserLoginController.class);
		// 设备
		this.add("/api/device", MachineController.class);
		// banner
		this.add("/api/banner", BannerApiController.class);
		// logo
		this.add("/api/logo", LogoController.class);
		// 关于公司
		this.add("/api/about", AboutController.class);
		// 帮助中心
		this.add("/api/help", HelpController.class);
		// 字体
		this.add("/api/font", FontController.class);
		// 意见反馈
		this.add("/api/feedback", FeedbackController.class);
		// 通用
		this.add("/api/common", CommonController.class);
	}

}
