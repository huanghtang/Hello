package com.sandu.xinye.api.login;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.sandu.xinye.common.constant.CacheConstant;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.User;
import com.sandu.xinye.common.model.UserSession;

public class UserLoginService {

	public static final UserLoginService me = new UserLoginService();

	public RetKit register(String phone, String password, String captcha, String ipAddress) {
		if (StrKit.isBlank(phone) || StrKit.isBlank(password) || StrKit.isBlank(captcha)) {
			return RetKit.fail("手机号、密码、验证码不能为空！");
		}
		phone = phone.trim();
		User user = User.dao.findFirst("select * from user where userPhone=?", phone);
		if (user != null) {
			return RetKit.fail("该手机号码已被注册！");
		}
		String realCaptcha = CacheKit.get(CacheConstant.CAPTCHA, phone);
		System.out.println(realCaptcha+"验证码");
		
		if (realCaptcha == null) {
			return RetKit.fail("验证码已过期，请重新发送验证码！");
		}
		if (!captcha.equals(realCaptcha)) {
			return RetKit.fail("验证码错误！");
		}
		String userNickName = phone;
		String userImg = "";
		String salt = HashKit.generateSaltForSha256();
		String hashPwd = HashKit.sha256(salt + password);
		User newUser = new User();
		boolean succ = newUser.setUserNickName(userNickName).setUserPhone(phone).setUserImg(userImg)
				.setUserPass(hashPwd).setRegisterType(User.REGISTER_TYPE_PHONE).setSalt(salt)
				.setStatus(User.STATUS_IS_REGISTER).setCreateTime(new Date()).save();
		if (succ) {
			CacheKit.remove(CacheConstant.CAPTCHA, phone);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

	public RetKit doLogin(String phone, String password, String platform, String ipAddress) {
		if (StrKit.isBlank(phone) || StrKit.isBlank(password)) {
			return RetKit.fail("手机号和密码不能为空！");
		}
		phone = phone.trim();
		User user = User.dao.findFirst("select * from user where userPhone=? ", phone);
		if (user == null) {
			return RetKit.fail("该手机号码未注册！");
		}
		String salt = user.getSalt();
		String hashPwd = HashKit.sha256(salt + password);
		if (hashPwd.equals(user.getUserPass())) {
			// 登录有效时间 7天(秒)
			long liveSeconds = 7 * 24 * 60 * 60;
			// 过期的时间戳
			long timeStamp = System.currentTimeMillis() + (liveSeconds * 1000);
			// 生成accessToken
			String accessToken = HashKit.sha1(StrKit.getRandomUUID());
			// 创建session
			UserSession session = new UserSession();
			session.setUserId(user.getUserId()).setSessionId(accessToken).setTimeStamp(timeStamp)
					.setCreateTime(new Date()).save();
			// 记录登录时间
			user.setLastLoginTime(new Date()).update();
			// 清楚账号的盐和密码，不暴露出来
			user.removeSensitiveInfo();
			CacheKit.put(CacheConstant.APP_USER, accessToken, user);
			// 创建登录日志
			createLoginLog(user.getUserId(), ipAddress, Integer.valueOf(platform));
			Kv kv = new Kv();
			kv = Kv.by("userId", user.getUserId()).set("userNickName", user.getUserNickName()).set("userPhone", phone)
					.set("userImg", user.getUserImg()).set(Constant.APP_ACCESSTOKE, accessToken);

			return RetKit.ok("data", kv).setMsg("登录成功");
		} else {
			return RetKit.fail("账号或密码错误");
		}
	}

	/**
	 * @Title: fasterLogin
	 * @Description:
	 * @param openId
	 * @param loginType
	 * @param platform
	 * @param avatar
	 * @param nickName
	 * @return
	 * @date 2019年3月15日 下午2:33:16
	 * @author liyingxiang
	 */
	public RetKit fasterLogin(String openId, Integer loginType, String platform, String avatar, String nickName,
			String ip) {
		if (StrKit.isBlank(openId) || StrKit.isBlank(platform) || loginType == null) {
			return RetKit.fail("openId,platform,loginType不能为空！");
		}
		if ((loginType != User.REGISTER_TYPE_QQ) || (loginType != User.REGISTER_TYPE_WX)) {
			return RetKit.fail("loginType只能传入0或者1！");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select * from user where ");
		if (loginType == User.REGISTER_TYPE_QQ) {
			sql.append(" qqId=? ");
		} else {
			sql.append(" wxId=? ");
		}
		sql.append(" limit 1");

		User checkUser = User.dao.findFirst(sql.toString(), openId);

		if (checkUser == null) {
			User user = new User();
			user.setUserNickName(nickName);
			user.setUserImg(avatar);
			if (loginType == User.REGISTER_TYPE_QQ) {
				user.setQqId(openId);
			} else {
				user.setWxId(openId);
			}
			user.setRegisterType(loginType);
			user.setStatus(User.STATUS_IS_REGISTER);
			user.setCreateTime(new Date());
			boolean succ = user.save();
			if (!succ) {
				return RetKit.fail("登录失败！数据库操作异常！");
			}
			checkUser = user;
		}
		// 登录有效时间 7天(秒)
		long liveSeconds = 7 * 24 * 60 * 60;
		// 过期的时间戳
		long timeStamp = System.currentTimeMillis() + (liveSeconds * 1000);
		// 生成accessToken
		String accessToken = HashKit.sha1(StrKit.getRandomUUID());
		// 创建session
		UserSession session = new UserSession();
		session.setUserId(checkUser.getUserId()).setSessionId(accessToken).setTimeStamp(timeStamp)
				.setCreateTime(new Date()).save();
		// 记录登录时间
		checkUser.setLastLoginTime(new Date()).update();
		// 清楚账号的盐和密码，不暴露出来
		checkUser.removeSensitiveInfo();
		//放进缓存
		CacheKit.put(CacheConstant.APP_USER, accessToken, checkUser);
		// 创建登录日志
		createLoginLog(checkUser.getUserId(), ip, Integer.valueOf(platform));
		Kv kv = new Kv();
		kv = Kv.by("userId", checkUser.getUserId()).set("userNickName", checkUser.getUserNickName())
				.set("userPhone", checkUser.getUserPhone() == null ? "" : checkUser.getUserPhone())
				.set("userImg", checkUser.getUserImg()).set(Constant.APP_ACCESSTOKE, accessToken);
		return RetKit.ok("data", kv).setMsg("登录成功");
	}

	/**
	 * @Title: logout
	 * @Description: 退出登录
	 * @param accessToken
	 * @return
	 * @date 2018年12月10日 下午6:36:51
	 * @author liyingxiang
	 */
	public boolean logout(String accessToken) {
		if (accessToken == null) {
			return false;
		}
		boolean succ = UserSession.dao.deleteById(accessToken);
		CacheKit.remove(CacheConstant.APP_USER, accessToken);
		return succ;
	}

	public User getUserCacheWithSessionId(String accessToken) {
		return CacheKit.get(CacheConstant.APP_USER, accessToken);
	}

	public RetKit retSetPwd(String phone, String password, String checkPwd) {
		if (StrKit.isBlank(phone)) {
			return RetKit.fail("请传入账号！");
		}
		User user = User.dao.findFirst("select * from user where userPhone=? limit 1", phone.trim());
		if (user == null) {
			return RetKit.fail("该手机号码未注册！");
		}
		if (!checkPwd.equals(password)) {
			return RetKit.fail("两次密码不一致！");
		}
		String salt = HashKit.generateSaltForSha256();
		String hashPwd = HashKit.sha256(salt + password);
		boolean succ = user.setSalt(salt).setUserPass(hashPwd).update();
		if (succ) {
			removeCacheAndSession(user.getUserId());
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

	private void createLoginLog(Integer userId, String ipAddress, Integer platform) {
		Record record = new Record();
		record.set("userId", userId).set("ip", ipAddress).set("platform", platform).set("loginTime", new Date());
		Db.save("user_login_log", record);
	}

	private void removeCacheAndSession(Integer userId) {
		List<UserSession> list = UserSession.dao.find("select * from user_session where userId=?", userId);
		for (UserSession cus : list) {
			CacheKit.remove(CacheConstant.APP_USER, cus.getSessionId());
		}
		Db.update("delete from user_session where userId=?", userId);
	}

	public User loginWithSessionId(String sessionId, String ipAddress, Integer platform) {
		UserSession session = UserSession.dao.findById(sessionId);
		if (session == null) {
			return null;
		}
		// 判断sessionId是否已过期，如果过期则删除
		if (session.isExpired()) {
			session.delete();
			return null;
		}

		User user = User.dao.findById(session.getUserId());
		if (user != null) {
			user.setLastLoginTime(new Date()).update();
			user.removeSensitiveInfo();
			user.put("sessionId", sessionId);
			CacheKit.put(CacheConstant.APP_USER, sessionId, user);
			createLoginLog(user.getUserId(), ipAddress, platform);
			return user;
		}

		return null;
	}

}
