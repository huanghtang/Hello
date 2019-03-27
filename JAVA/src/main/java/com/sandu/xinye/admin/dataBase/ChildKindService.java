package com.sandu.xinye.admin.dataBase;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Logo;
import com.sandu.xinye.common.model.LogoChildKind;
import com.sandu.xinye.common.model.SysUser;

public class ChildKindService {
	public static final ChildKindService me = new ChildKindService();

	public RetKit list(int pageNumber, int pageSize, Kv kv) {
		String logoChildKindName = kv.getStr("logoChildKindName");
		if (StrKit.notBlank(logoChildKindName)) {
			logoChildKindName = "%" + logoChildKindName + "%";
			kv.set("logoChildKindName", logoChildKindName);
		}
		SqlPara sqlPara = Db.getSqlPara("admin.childkind.paginate", kv);
		Page<LogoChildKind> page = LogoChildKind.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page", page);
	}

	public RetKit add(int logoKindId, LogoChildKind childKind, SysUser sysUser,String ip) {
		LogoChildKind exist = LogoChildKind.dao.findFirst("select * from logo_child_kind where logoChildKindName = ? and logoKindId = ?",childKind.getLogoChildKindName(),logoKindId);
		if(exist != null){
			return RetKit.fail("子类已存在");
		}
		childKind.setLogoKindId(logoKindId).setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId());
		boolean succ = childKind.save();
		if(succ){
			String content = sysUser.getSysUserName() + "添加了id为" +childKind.getLogoKindId() +"的" +childKind.getLogoChildKindName()+"子类";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit update(LogoChildKind childKind,SysUser sysUser,String ip){
		LogoChildKind exist = LogoChildKind.dao.findFirst("select * from logo_child_kind where logoChildKindName = ?",childKind.getLogoChildKindName());
		if(exist != null){
			return RetKit.fail("子类已存在");
		}
		boolean succ = childKind.setSysUserId(sysUser.getSysUserId()).update();
		if(succ){
			String content = sysUser.getSysUserName() + "添加了id为" +childKind.getLogoKindId() +"的" +childKind.getLogoChildKindName()+"主类";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit del(String id,SysUser sysUser,String ip){
		Logo logoExist=Logo.dao.findFirst("select * from logo where logoChildKindId = ?",id);
		if(logoExist != null){
			return RetKit.fail("存在使用中的logo,请优先删除logo");
		}
		LogoChildKind childKind = LogoChildKind.dao.findById(id);
		boolean succ = childKind.delete();
		if(succ){
			String content = sysUser.getSysUserName() + "添加了id为" +id +"的" +childKind.getLogoChildKindName()+"子类";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	/**
	 * 获取子类列表
	 */
	public RetKit getChildListByMainId(String mainId){
		List<LogoChildKind> list = LogoChildKind.dao.find("select * from logo_child_kind where logoKindId = ?",mainId);
		return RetKit.ok("list",list);
	}
}
