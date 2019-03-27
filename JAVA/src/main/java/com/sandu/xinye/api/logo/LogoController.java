package com.sandu.xinye.api.logo;

import com.jfinal.aop.Clear;
import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class LogoController extends AppController {

	@Clear
	public void getLogoList() {
		RetKit ret = LogoService.me.getLogoList();
		renderJson(ret);
	}
}
