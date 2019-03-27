package com.sandu.xinye.admin.dataBase;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.LogoKind;

public class MainKindController extends AdminController {
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret =MainKindService.me.list(pageNumber, pageSize,getParaToMap());
		renderJson(ret);
	}
	
	public void add(){
		LogoKind logoKind = getBean(LogoKind.class,"");
		RetKit ret = MainKindService.me.add(logoKind,getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		LogoKind logoKind = getBean(LogoKind.class,"");
		RetKit ret = MainKindService.me.update(logoKind,getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String id = getPara(0);
		RetKit ret = MainKindService.me.del(id,getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void getMainList(){
		renderJson(MainKindService.me.getKindList());
	}
}
