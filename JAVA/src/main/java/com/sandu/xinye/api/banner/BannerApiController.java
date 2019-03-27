package com.sandu.xinye.api.banner;

import com.jfinal.aop.Clear;
import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;

public class BannerApiController extends AdminController {

	@Clear
	public void getBannerlist() {
		RetKit ret = BannerApiService.me.list();
		renderJson(ret);
	}
}
