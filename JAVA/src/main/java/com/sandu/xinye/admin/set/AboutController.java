package com.sandu.xinye.admin.set;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.About;

public class AboutController extends AdminController {

	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret = AboutService.me.list(pageSize, pageNumber);
		renderJson(ret);
	}
	
	public void update(){
		About about = getBean(About.class,"");
		RetKit ret = AboutService.me.update(about, getAccount());
		renderJson(ret);
	}
}
