package com.sandu.xinye.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.sandu.xinye.api.login.UserLoginService;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.constant.RetConstant;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.User;

public class AppUserInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller con = inv.getController();
		String sessionId = con.getHeader(Constant.APP_ACCESSTOKE);
		String platform = con.getHeader("platform");
		if (StrKit.isBlank(sessionId)) {
			con.renderJson(RetKit.fail(RetConstant.CODE_NO_LOGIN, "未登录！"));
			return;
		}
		User user = UserLoginService.me.getUserCacheWithSessionId(sessionId);
		if (user == null) {
			user = UserLoginService.me.loginWithSessionId(sessionId, getIpAddress(con.getRequest()),
					Integer.valueOf(platform));
		}
		if (user == null) {
			con.renderJson(RetKit.fail(RetConstant.CODE_LOGIN_EXPIRE, "Session已过期，请重新登录！"));
			return;
		}
		inv.invoke();

	}

	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-requested-For");
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (ip != null && ip.contains(",")) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}

		return ip;
	}

}
