package com.sandu.xinye.admin.set;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Help;
import com.sandu.xinye.common.model.SysUser;

public class HelpService {
	public static final HelpService me =new HelpService();
	
	public RetKit list(int pageSize,int pageNumber,Kv kv){
		String helpName = kv.getStr("helpName");
		if(StrKit.notBlank(helpName)){
			helpName = "%"+helpName+"%";
			kv.set("helpName",helpName);
		}
		SqlPara sqlPara = Db.getSqlPara("admin.help.paginate",kv);
		Page<Help> page =Help.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}
	
	public RetKit add(Help help,SysUser sysUser,String ip){
		boolean succ = help.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).save();
		if(succ){
			String content = sysUser.getSysUserName() + "添加了id为" +help.getHelpId() +"的" +help.getHelpName()+"帮助中心";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit update(Help help,SysUser sysUser,String ip){
		boolean succ = help.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).update();
		if(succ){
			String content = sysUser.getSysUserName() + "编辑了id为" +help.getHelpId() +"的" +help.getHelpName()+"帮助中心";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit del(String helpId,SysUser sysUser,String ip){
		Help help = Help.dao.findById(helpId);
		boolean succ = help.delete();
		if(succ){
			String content = sysUser.getSysUserName() + "删除了id为" +helpId +"的" +help.getHelpName()+"帮助中心";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
}
