package com.sandu.xinye.admin.operate;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;

public class OperationLogController extends AdminController {

	public void page(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize =getParaToInt("pageSize",10);
		RetKit ret = OperationLogService.me.page(pageNumber, pageSize);
		renderJson(ret);
	}
}
