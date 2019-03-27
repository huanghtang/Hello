package com.sandu.xinye.admin.dashboard;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;

public class DashboardController extends AdminController {

	public void getProductData() {
		String beginDateStr = getPara("searchDate[0]");
		String endDateStr = getPara("searchDate[1]");
		Integer timeType = getParaToInt("timeType");
		RetKit ret = DashboardService.me.getProductKindData( beginDateStr,
				endDateStr, timeType);
		renderJson(ret);
	}
	
	public void getYesterdayData(){
		renderJson( DashboardService.me.getYesterdayData());
	}
	
	public void userPage(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret = DashboardService.me.getDataPage(pageNumber, pageSize);
		renderJson(ret);
	}
	
}
