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
import com.sandu.xinye.common.model.LogoChildKind;
import com.sandu.xinye.common.model.LogoKind;
import com.sandu.xinye.common.model.SysUser;

public class MainKindService {
	public static final MainKindService me = new MainKindService();

	public RetKit list(int pageNumber, int pageSize, Kv kv) {
		String logoKindName = kv.getStr("logoKindName");
		if (StrKit.notBlank(logoKindName)) {
			logoKindName = "%" + logoKindName + "%";
			kv.set("logoKindName", logoKindName);
		}
		SqlPara sqlPara = Db.getSqlPara("admin.mainkind.paginate", kv);
		Page<LogoKind> page = LogoKind.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page", page);
	}

	public RetKit add(LogoKind logoKind, SysUser sysUser,String ip) {
		LogoKind exist = LogoKind.dao.findFirst("select * from logo_kind where logoKindName = ?",logoKind.getLogoKindName());
		if(exist != null){
			return RetKit.fail("主类已存在");
		}
		boolean succ = logoKind.setSysUserId(sysUser.getSysUserId()).setCreateTime(new Date()).save();
		if(succ){
			String content = sysUser.getSysUserName() + "添加了id为" +logoKind.getLogoKindId() +"的" +logoKind.getLogoKindName()+"主类";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail("添加失败");
	}

	public RetKit update(LogoKind logoKind, SysUser sysUser,String ip) {
		LogoKind exist = LogoKind.dao.findFirst("select * from logo_kind where logoKindName = ?",logoKind.getLogoKindName());
		if(exist != null){
			return RetKit.fail("主类已存在");
		}
		boolean succ = logoKind.setSysUserId(sysUser.getSysUserId()).update();
		if(succ){
			String content = sysUser.getSysUserName() + "更新了id为" +logoKind.getLogoKindId() +"的" +logoKind.getLogoKindName()+"主类";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

	public RetKit del(String id, SysUser sysUser,String ip) {
		LogoChildKind existChild = LogoChildKind.dao.findFirst("select * from logo_child_kind where logoKindId = ?",id);
		if(existChild != null){
			return RetKit.fail("存在使用中的子类，请优先删除子类");
		}
		LogoKind logoKind = LogoKind.dao.findById(id);
		boolean succ = logoKind.delete();
		if(succ){
			String content = sysUser.getSysUserName() + "删除了id为" +id +"的" +logoKind.getLogoKindName()+"主类";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok("删除成功") : RetKit.fail("删除失败！");
	}

	/**
	 * 获取logo主类的列表
	 */
	public RetKit getKindList() {
		List<LogoKind> list = LogoKind.dao.find("select logoKindId,logoKindName from logo_kind order by logoKindId");
		return RetKit.ok("list", list);
	}
}
