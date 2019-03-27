package com.sandu.xinye.api.feedback;

import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class FeedbackController extends AppController {

	/**
	 * 
	 * @Title
	 * @Description 添加意见反馈
	 * @Param content 意见反馈内容
	 * @Param userId 登录人id
	 * @return
	 * @author tangziahu
	 * @time 2019年3月12日下午3:52:41
	 *
	 */
	public void addFeedback() {
		String content = getPara("content");
		RetKit ret = FeedbackService.me.addFeedBack(getUser().getUserId(), content);
		renderJson(ret);
	}
}
