package com.sandu.xinye.admin.set;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Font;

public class FontController extends AdminController {
	
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret = FontService.me.list(pageSize, pageNumber,getParaToMap());
		renderJson(ret);
	}
	
	public void add(){
		Font font = getBean(Font.class,"");
		RetKit ret = FontService.me.add(font, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		Font font = getBean(Font.class,"");
		RetKit ret = FontService.me.update(font, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String fontId = getPara(0);
		RetKit ret = FontService.me.del(fontId, getAccount(),getIpAddress());
		renderJson(ret);
	}
}
