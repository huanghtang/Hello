package com.sandu.xinye.admin.dataBase;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;

public class UserController extends AdminController {
	
	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret = UserService.me.list(pageNumber, pageSize,getParaToMap());
		renderJson(ret);
	}
}
