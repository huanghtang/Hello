package com.sandu.xinye.admin.dataBase;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.User;

public class UserService {
	
	public static final UserService me = new UserService();
	
	public RetKit list(int pageNumber,int pageSize,Kv kv){
		String userPhone = kv.getStr("userPhone");
		kv.set("userPhone",userPhone);
		SqlPara sqlPara = Db.getSqlPara("admin.user.paginate",kv);
		Page<User> page = User.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}
}
