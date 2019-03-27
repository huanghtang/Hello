package com.sandu.xinye.api.about;

import java.util.List;

import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Help;

public class AboutService {
	public static final AboutService me = new AboutService();
	
	
	public RetKit getAbout(){
		List<Help> list = Help.dao.find("select * from about order by createTime desc");
		return RetKit.ok("list",list);
	}
}
