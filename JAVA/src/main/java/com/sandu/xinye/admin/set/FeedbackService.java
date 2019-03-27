package com.sandu.xinye.admin.set;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Feedback;
import com.sandu.xinye.common.model.SysUser;

public class FeedbackService {
	public static final FeedbackService me = new FeedbackService();
	
	public RetKit list(int pageNumber,int pageSize,Kv kv){
		String machineName = kv.getStr("machineName");
		if(StrKit.notBlank(machineName)){
			machineName = "%" + machineName + "%";
			kv.set("machineName",machineName);
		}
		SqlPara sqlPara = Db.getSqlPara("admin.feedback.paginate",kv);
		Page<Feedback> page = Feedback.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}
	
	public RetKit update(Feedback feedback,SysUser sysUser,String ip){
		boolean succ = feedback.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).update();
		if(feedback.getStatus() == 2){//2--未处理
			if(succ){
				String content = sysUser.getSysUserName()+"更新了id"+feedback.getFeedbackId()+"的意见反馈，状态更新为未处理";
				OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
			}
		}if(feedback.getStatus() == 1){//1--已处理
			if(succ){
				String content = sysUser.getSysUserName()+"更新了id"+feedback.getFeedbackId()+"的意见反馈，状态更新为已处理";
				OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
			}
		}
		
		return succ ? RetKit.ok().setOk("编辑成功") : RetKit.fail();
	}
}
