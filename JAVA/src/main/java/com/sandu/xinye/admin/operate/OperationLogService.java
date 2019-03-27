package com.sandu.xinye.admin.operate;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.OperationLog;

public class OperationLogService {
	public static final OperationLogService me = new OperationLogService();
	
	public boolean saveOperationLog(Integer sysUserId,String ip, String content){
		OperationLog op = new OperationLog();
		boolean succ = op.setSysUserId(sysUserId).setIp(ip).setContent(content).setCreateTime(new Date()).save();
		return succ;
	}
	
	public RetKit page(int pageNumber ,int pageSize){
		SqlPara sqlPara = Db.getSqlPara("admin.operate.paginate");
		Page<OperationLog> page = OperationLog.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
		
	}
	
}
