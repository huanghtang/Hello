package com.sandu.xinye.api.about;

import com.jfinal.aop.Clear;
import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class AboutController extends AppController {

	/**
	 * 
	 * @Title
	 * @Description 获取关于公司信息
	 * @Param
	 * @return
	 * @time 2019年3月12日下午2:08:34
	 *
	 */
	@Clear
	public void getAbout(){
		RetKit ret = AboutService.me.getAbout();
		renderJson(ret);
	}
}
