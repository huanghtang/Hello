package com.sandu.xinye.admin.dataBase;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Banner;

public class BannerController extends AdminController {

	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret= BannerService.me.list(pageNumber, pageSize);
		renderJson(ret);
	}
	
	public void add(){
		Banner banner = getBean(Banner.class,"");
		RetKit ret = BannerService.me.add(banner, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		Banner banner = getBean(Banner.class,"");
		RetKit ret= BannerService.me.update(banner, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String id = getPara(0);
		RetKit ret = BannerService.me.del(id, getAccount(),getIpAddress());
		renderJson(ret);
	}
}
