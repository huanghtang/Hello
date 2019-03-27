package com.sandu.xinye.common.constant;

import com.jfinal.kit.PathKit;

public class Constant {
	// 平台后台的前端sessionId名称
	public static final String SYS_USER_SESSION_ID = "xinyeSysUserSessionId";

	// 平台app accessToken
	public static final String APP_ACCESSTOKE = "accessToken";

	/*
	 * 所有表数据 未删-0 1-已删
	 */
	public static final int IS_DEL = 1;
	public static final int NO_DEL = 0;

	public static final int QQ_STATUS = 0;
	public static final int WX_STATUS = 1;

	// 帮助类型 软件-1 硬件-2
	public static final int SOFT_HELP = 1;
	public static final int HARD_HELP = 2;

	// 处理状态 1-已处理 2-未处理
	public static final int DONE_FEEDBACK = 1;
	public static final int NOTDONE_FEEDBACK = 2;

	// 默认上传的临时文件夹绝对路径
	public static final String BASE_UPLOAD_PATH = PathKit.getWebRootPath() + "/upload/temp";
	// 相对路径
	public static final String UPLOAD_PATH = "/upload/temp";

	/*
	 * 发送验证码类型  1-注册  2-忘记密码 
	 */
	public static final String SEND_CAPTCHA_TYPE_REGISTER = "1";
	public static final String SEND_CAPTCHA_TYPE_FORGOT_PWD = "2";

}
