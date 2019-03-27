package com.sandu.xinye.admin.dataBase;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.LogoChildKind;

public class ChildKindController extends AdminController {
	
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret= ChildKindService.me.list(pageNumber, pageSize,getParaToMap());
		renderJson(ret);
	}
	
	public void add(){
		int logoKindId = getParaToInt("logoKindId");
		LogoChildKind childKind = getBean(LogoChildKind.class,"");
		RetKit ret = ChildKindService.me.add(logoKindId, childKind, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		LogoChildKind childKind = getBean(LogoChildKind.class,"");
		RetKit ret = ChildKindService.me.update(childKind, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String id = getPara(0);
		RetKit ret = ChildKindService.me.del(id, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void getChildList(){
		String mainId = getPara(0);
		RetKit ret = ChildKindService.me.getChildListByMainId(mainId);
		renderJson(ret);
	}
}
