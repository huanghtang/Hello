package com.sandu.xinye.api.machine;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Machine;

public class MachineService {

	public static final MachineService me = new MachineService();

	/**
	 * @Title: getDeviveList
	 * @Description:
	 * @param name
	 * @return
	 * @date 2019年3月13日 上午10:53:38
	 * @author liyingxiang
	 */
	public RetKit getDeviveList(String name) {
		if (StrKit.notBlank(name)) {
			name = "%" + name + "%";
		}
		LogKit.info("设备名：" + name);
		SqlPara sqlPara = Db.getSqlPara("app.machine.list", Kv.by("name", name));
		List<Machine> list = Machine.dao.find(sqlPara);
		return RetKit.ok("list", list);
	}
}
