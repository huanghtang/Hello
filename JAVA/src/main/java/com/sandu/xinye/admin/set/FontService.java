package com.sandu.xinye.admin.set;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Font;
import com.sandu.xinye.common.model.SysUser;

public class FontService {
	public static final FontService me = new FontService();
	
	public RetKit list(int pageSize,int pageNumber,Kv kv){
		String fontName = kv.getStr("fontName");
		if(StrKit.notBlank(fontName)){
			fontName = "%" +fontName+"%";
			kv.set("fontName",fontName);
		}
		SqlPara sqlPara = Db.getSqlPara("admin.font.paginate",kv);
		Page<Font> page = Font.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}

	
	public RetKit add(Font font,SysUser sysUser,String ip){
		boolean succ = font.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).save();
		if(succ){
			String content = sysUser.getSysUserName() + "添加了id为" +font.getFontId() +"的" +font.getFontName()+"字体";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return  succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit update(Font font,SysUser sysUser,String ip){
		boolean succ = font.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).update();
		if(succ){
			String content = sysUser.getSysUserName() + "编辑了id为" +font.getFontId() +"的" +font.getFontName()+"字体";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return  succ ? RetKit.ok() : RetKit.fail();
	}
	
	public RetKit del(String fontId,SysUser sysUser,String ip){
		Font font = Font.dao.findById(fontId);
		boolean succ = font.delete();
		if(succ){
			String content = sysUser.getSysUserName() + "删除了id为" +fontId +"的" +font.getFontName()+"字体";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}
	
}
