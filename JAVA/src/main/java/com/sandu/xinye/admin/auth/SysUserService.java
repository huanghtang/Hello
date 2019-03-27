package com.sandu.xinye.admin.auth;

import java.util.Date;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.SysUser;

public class SysUserService {
	public static final SysUserService me = new SysUserService();

	/**
	 * @Title: list
	 * @Description: 平台用户列表
	 * @param pageNumber
	 * @param pageSize
	 * @param kv
	 * @return
	 */
	public RetKit list(int pageNumber, int pageSize, Kv kv) {
		String sysUserName = kv.getStr("sysUserName");
		if (StrKit.notBlank(sysUserName)) {
			sysUserName = "%" + sysUserName + "%";
			kv.set("sysUserName", sysUserName);
		}
		SqlPara sqlPara = Db.getSqlPara("admin.sysUser.paginate", kv);
		Page<SysUser> page = SysUser.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page", page);
	}

	/**
	 * @deprecated: 获得角色列表
	 */
	public RetKit getRoleList() {
		return RetKit.ok("list", RoleService.me.roleList());
	}

	/**
	 * @Title: save
	 * @Description: 添加平台用户
	 * @param su
	 * @param account
	 * @param ipAddress
	 * @return
	 */
	public RetKit save(SysUser su, SysUser account, String ip) {
		if (su == null || su.getSysUserRoleId() <= 0) {
			return RetKit.fail("非法参数");
		}
		String salt = HashKit.generateSaltForSha256();
		String hashPwd = HashKit.sha256(salt + su.getSysUserPass());
		boolean succ = su.setSalt(salt).setSysUserPass(hashPwd).setSysUserCreateTime(new Date())
				.setIsDel(Constant.NO_DEL).setSysUserCreateTime(new Date()).save();
		
		if(succ){
			String content = account.getSysUserName()+"添加了名为"+su.getSysUserName()+"的平台人员";
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ip, content);
				
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

	public RetKit update(SysUser su, SysUser account,String ip) {
		if (su.getSysUserRoleId() == 0) {
			return RetKit.fail("超级管理员账号不允许更改");
		}
		boolean succ = su.update();
		if(succ){
			String content = account.getSysUserName()+"编辑了名为"+su.getSysUserName()+"的平台人员";
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ip, content);
				
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

	public RetKit del(String sysUserId,SysUser account,String ip) {
		if (StrKit.isBlank(sysUserId) || sysUserId == null) {
			return RetKit.fail("参数不能为空");
		}
		SysUser su = SysUser.dao.findById(sysUserId);
		if (su == null) {
			return RetKit.fail("找不到数据");
		}
		if (su.getSysUserRoleId() == 0) {
			return RetKit.fail("不能删除管理员");
		}
		boolean succ = su.setIsDel(Constant.IS_DEL).update();
		if(succ){
			String content = account.getSysUserName()+"删除了名为"+su.getSysUserName()+"的平台人员";
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ip, content);
				
		}
		return succ ? RetKit.ok() : RetKit.fail();

	}
}
