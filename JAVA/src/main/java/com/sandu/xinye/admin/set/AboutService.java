package com.sandu.xinye.admin.set;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.About;
import com.sandu.xinye.common.model.Help;
import com.sandu.xinye.common.model.SysUser;

public class AboutService {
	public static final AboutService me = new AboutService();
	
	public RetKit list(int pageSize,int pageNumber){
		SqlPara sqlPara = Db.getSqlPara("admin.about.paginate");
		Page<Help> page =Help.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}
	
	public RetKit update(About about,SysUser sysUser){
		boolean succ = about.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).update();
		return succ ? RetKit.ok() : RetKit.fail();
	}
}
