package com.sandu.xinye.api.feedback;

import java.util.Date;

import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Feedback;

public class FeedbackService {
	
	public static final FeedbackService me = new FeedbackService();
	
	public RetKit addFeedBack(int userId ,String content){
		Feedback fb= new Feedback();
		boolean succ = fb.setUserId(userId).setContent(content).setCreateTime(new Date()).setStatus(Constant.NOTDONE_FEEDBACK).save();
		return succ ? RetKit.ok() : RetKit.fail();
	}
}
