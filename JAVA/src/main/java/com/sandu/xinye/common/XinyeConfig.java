package com.sandu.xinye.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.sandu.xinye.common.model._MappingKit;
import com.sandu.xinye.common.routes.AdminRoutes;
import com.sandu.xinye.common.routes.ApiRoutes;

public class XinyeConfig extends JFinalConfig {

	private static Prop p = loadConfig();

	private static Prop loadConfig() {
		return PropKit.use("dev_config.txt");
	}

	// 此方法用来配置JFinal常量值
	@Override
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		me.setDevMode(p.getBoolean("devMode", false));
		// 最大上传文件大小：30M
		me.setMaxPostSize(1024 * 1024 * 30);
		me.setBaseUploadPath("upload/temp");
		me.setError404View("/admin_index.html");
		me.setJsonDatePattern("yyyy-MM-dd HH:mm:ss");
	}

	// 此方法用来配置Template Engine
	@Override
	public void configEngine(Engine me) {
		
	}

	// 此方法用来配置JFinal的Handler
	@Override
	public void configHandler(Handlers me) {
		
	}

	// 配置全局拦截器
	@Override
	public void configInterceptor(Interceptors me) {
		
	}

	// 配置插件，如数据库连接池等
	@Override
	public void configPlugin(Plugins me) {
		// 配置数据库连接池
		DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
		// orm映射 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(p.getBoolean("devMode"));
		arp.setDialect(new MysqlDialect());
		
		arp.setBaseSqlTemplatePath(PathKit.getRootClassPath() + "/sql");
		arp.addSqlTemplate("xinye.sql");
		
		arp.getSqlKit().getEngine().addSharedObject("sk", new com.jfinal.kit.StrKit());
		_MappingKit.mapping(arp);
		me.add(druidPlugin);
		me.add(arp);
		//缓存
		me.add(new EhCachePlugin());
		// 任务定时调度
		me.add(new Cron4jPlugin(p));
		//orm
	}

	//配置路由
	@Override
	public void configRoute(Routes me) {
		me.add(new AdminRoutes());
		
		//app端路由
		me.add(new ApiRoutes());
	}

	@Override
	public void afterJFinalStart() {
		//Every.ess();//测试每天统计注册用户和登录人数
		
	}

	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}

}
