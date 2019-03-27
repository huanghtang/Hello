package com.sandu.xinye.admin.set;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Help;

public class HelpController extends AdminController {
	
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret = HelpService.me.list(pageSize, pageNumber,getParaToMap());
		renderJson(ret);
	}
	
	public void add(){
		Help help = getBean(Help.class,"");
		RetKit ret = HelpService.me.add(help, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		Help help = getBean(Help.class,"");
		RetKit ret = HelpService.me.update(help, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String helpId = getPara(0);
		RetKit ret = HelpService.me.del(helpId, getAccount(),getIpAddress());
		renderJson(ret);
	}
}
