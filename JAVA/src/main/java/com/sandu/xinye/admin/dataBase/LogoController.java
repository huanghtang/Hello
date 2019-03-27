package com.sandu.xinye.admin.dataBase;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Logo;

public class LogoController extends AdminController {
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret= LogoService.me.list(pageNumber, pageSize,getParaToMap());
		renderJson(ret);
	}
	
	public void add(){
		Logo logo = getBean(Logo.class,"");
		RetKit ret = LogoService.me.add(logo, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		Logo logo = getBean(Logo.class,"");
		RetKit ret= LogoService.me.update(logo, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String logoId = getPara(0);
		RetKit ret = LogoService.me.del(logoId, getAccount(),getIpAddress());
		renderJson(ret);
	}
}
