package com.sandu.xinye.admin.dataBase;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Logo;
import com.sandu.xinye.common.model.LogoChildKind;
import com.sandu.xinye.common.model.LogoKind;
import com.sandu.xinye.common.model.SysUser;

public class LogoService {
	public static final LogoService me = new LogoService();
	
	public RetKit list(int pageNumber,int pageSize,Kv kv){
		SqlPara sqlPara = Db.getSqlPara("admin.logo.paginate",kv);
		Page<Logo> page= Logo.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}
	
	/**
	 * 添加logo
	 * 
	 * @param logo
	 */
	public RetKit add(Logo logo,SysUser sysUser,String ip ){
		boolean succ =logo.setSysUserId(sysUser.getSysUserId()).setCreateTime(new Date()).save();
		if(succ){
			LogoKind lk = LogoKind.dao.findById(logo.getLogoKindId());
			LogoChildKind lck = LogoChildKind.dao.findById(logo.getLogoChildKindId());
			String content = sysUser.getSysUserName() + "添加了" +lk.getLogoKindName() +lck.getLogoChildKindName()+"logo";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit update(Logo logo,SysUser sysUser,String ip){
		boolean succ =logo.setSysUserId(sysUser.getSysUserId()).setCreateTime(new Date()).update();
		if(succ){
			LogoKind lk = LogoKind.dao.findById(logo.getLogoKindId());
			LogoChildKind lck = LogoChildKind.dao.findById(logo.getLogoChildKindId());
			String content = sysUser.getSysUserName() + "编辑了" +lk.getLogoKindName() +lck.getLogoChildKindName()+"logo";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit del(String logoId,SysUser sysUser,String ip){
		Logo logo = Logo.dao.findById(logoId);
		boolean succ = logo.delete();
		if(succ){
			LogoKind lk = LogoKind.dao.findById(logo.getLogoKindId());
			LogoChildKind lck = LogoChildKind.dao.findById(logo.getLogoChildKindId());
			String content = sysUser.getSysUserName() + "删除了" +lk.getLogoKindName() +lck.getLogoChildKindName()+"logo";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
}
