package com.sandu.xinye.common.routes;

import com.jfinal.config.Routes;
import com.sandu.xinye.admin.auth.RoleController;
import com.sandu.xinye.admin.auth.SysUserController;
import com.sandu.xinye.admin.dashboard.DashboardController;
import com.sandu.xinye.admin.dataBase.BannerController;
import com.sandu.xinye.admin.dataBase.ChildKindController;
import com.sandu.xinye.admin.dataBase.LogoController;
import com.sandu.xinye.admin.dataBase.MainKindController;
import com.sandu.xinye.admin.dataBase.UserController;
import com.sandu.xinye.admin.login.LoginController;
import com.sandu.xinye.admin.operate.OperationLogController;
import com.sandu.xinye.admin.set.AboutController;
import com.sandu.xinye.admin.set.FeedbackController;
import com.sandu.xinye.admin.set.FontController;
import com.sandu.xinye.admin.set.HelpController;
import com.sandu.xinye.admin.set.MachineController;
import com.sandu.xinye.admin.upload.UploadController;
import com.sandu.xinye.common.interceptor.SysAdminInterceptor;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		this.addInterceptor(new SysAdminInterceptor());
		// 登录
		this.add("/admin", LoginController.class);
		this.add("/admin/login", LoginController.class);
		//首页
		this.add("/admin/dashboard",DashboardController.class);
		// 上传
		this.add("/admin/upload",UploadController.class);
		// 基本信息
		this.add("/admin/database/mainkind", MainKindController.class);
		this.add("/admin/database/childkind", ChildKindController.class);
		this.add("/admin/database/logo",LogoController.class);
		this.add("/admin/database/user",UserController.class);
		this.add("/admin/database/banner",BannerController.class);
		//设置
		this.add("/admin/set/machine",MachineController.class);
		this.add("/admin/set/font",FontController.class);
		this.add("/admin/set/help",HelpController.class);
		this.add("/admin/set/feedback",FeedbackController.class);
		this.add("/admin/set/about",AboutController.class);
		//权限
		this.add("/admin/auth/account",SysUserController.class);
		this.add("/admin/auth/role",RoleController.class);
		//操作日志
		this.add("/admin/operate",OperationLogController.class);

	}


}
