package com.sandu.xinye.admin.login;

import java.util.Date;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.sandu.xinye.admin.auth.RoleService;
import com.sandu.xinye.common.constant.CacheConstant;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.SysUser;
import com.sandu.xinye.common.model.SysUserSession;

public class LoginService {

	public static final LoginService me = new LoginService();

	public RetKit doLogin(String username, String password, String loginIp) {
		if (StrKit.isBlank(username) || StrKit.isBlank(password)) {
			return RetKit.fail("用户名或密码不能为空");
		}
		username = username.trim();
		password = password.trim();

		SysUser sysUser = SysUser.dao.findFirst("select * from sys_user where sysUserName = ? and isDel=? ", username,
				Constant.NO_DEL);
		if (sysUser == null) {
			return RetKit.fail("用户名不存在");
		}

		String salt = sysUser.getSalt();
		String hashPwd = HashKit.sha256(salt + password);
		// 密码正确
		if (hashPwd.equals(sysUser.getSysUserPass())) {

			// 过期时间为7天
			long liveSeconds = 7 * 24 * 60 * 60;
			// timestamp 用于设置 session 的过期时间点，需要转换成毫秒
			long timeStamp = System.currentTimeMillis() + (liveSeconds * 1000);
			// 生成sessionId
			String sessionId = HashKit.sha256(StrKit.getRandomUUID());

			//
			SysUserSession session = new SysUserSession();
			session.setSessionId(sessionId);
			session.setSysUserId(sysUser.getSysUserId());
			session.setTimeStamp(timeStamp);
			session.setCreateTime(new Date());
			session.save();

			// 更新登录时间
			sysUser.setSysUserLoginTime(new Date()).update();
			sysUser.put("sessionId", sessionId);
			// 获得该登录账号的用户权限菜单
			sysUser.put("menus", RoleService.me.findMenuNamesByRoleId(sysUser.getSysUserRoleId()));
			// 把登录用户放进缓存
			CacheKit.put(CacheConstant.SYS_USER, sessionId, sysUser);
			// 创建登录日志
			createLoginLog(sysUser.getSysUserId(), loginIp);
			return RetKit.ok("sysUser", sysUser);
		} else {
			return RetKit.fail("用户名或密码错误");
		}
	}

	/**
	 * 通过sessionId,从缓存中获取后台用户的数据
	 * 
	 * @param sessionId
	 * @return
	 */
	public SysUser getSysUserCacheWithSessionId(String sessionId) {
		return CacheKit.get(CacheConstant.SYS_USER, sessionId);
	}

	/**
	 * 通过未过期的sessionId登录
	 * 
	 * @param sessionId
	 * @param loginIp
	 * @return
	 */
	public SysUser loginWithSessionId(String sessionId, String loginIp) {
		SysUserSession session = SysUserSession.dao.findById(sessionId);
		if (session == null) {
			return null;
		}
		// 判断sessionId是否已过期，如果过期则删除
		if (session.isExpired()) {
			session.delete();
			return null;
		}

		SysUser user = SysUser.dao.findById(session.getSysUserId());
		if (user.getIsDel().equals(Constant.NO_DEL)) {
			user.setSysUserLoginTime(new Date()).update();
			// user.removeSensitiveInfo();
			// 菜单数据
			user.put("menus", RoleService.me.findMenuNamesByRoleId(user.getSysUserRoleId()));
			// user.put("btns",
			// RoleService.me.findBtnListByRoleId(user.getSysUserRoleId()));
			user.put("sessionId", sessionId);
			CacheKit.put(CacheConstant.SYS_USER, sessionId, user);
			createLoginLog(user.getSysUserId(), loginIp);
			return user;
		}
		return null;
	}

	/**
	 * 创建登录日志
	 * 
	 * @param sysUserId
	 * @param loginIp
	 */
	private void createLoginLog(Integer sysUserId, String loginIp) {
		Record record = new Record().set("sysUserId", sysUserId).set("ip", loginIp).set("loginTime", new Date());
		Db.save("sys_user_login_log", record);
	}

	public boolean logOut(String sessionId) {
		boolean succ = true;
		if (StrKit.notBlank(sessionId)) {
			// 先删除数据库中的session
			succ = SysUserSession.dao.deleteById(sessionId);
			// 再移除缓存
			CacheKit.remove(CacheConstant.SYS_USER, sessionId);
			// SysUser sy = CacheKit.get(CacheConstant.SYS_USER, sessionId);
			// System.out.println(sy + "logOUtttttttttttttttttttt");
		}
		return succ;
	}
}
