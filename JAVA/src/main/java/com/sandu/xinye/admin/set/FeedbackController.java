package com.sandu.xinye.admin.set;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Feedback;

public class FeedbackController extends AdminController {

	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret = FeedbackService.me.list(pageNumber, pageSize,getParaToMap());
		renderJson(ret);
	}
	
	public void update(){
		Feedback feedback = getBean(Feedback.class,"");
		RetKit ret = FeedbackService.me.update(feedback, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
}
