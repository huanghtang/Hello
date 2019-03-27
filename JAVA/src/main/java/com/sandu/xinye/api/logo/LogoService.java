package com.sandu.xinye.api.logo;

import java.util.List;

import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Logo;
import com.sandu.xinye.common.model.LogoChildKind;
import com.sandu.xinye.common.model.LogoKind;

public class LogoService {

	public static final LogoService me = new LogoService();

	public RetKit getLogoList() {
		List<Logo> list = Logo.dao.find("select * from logo");
		for (Logo logo : list) {
			LogoKind kind = LogoKind.dao.findById(logo.getLogoKindId());
			LogoChildKind childKind = LogoChildKind.dao.findById(logo.getLogoChildKindId());
			String groupName = kind.getLogoKindName() + "/" + childKind.getLogoChildKindName();
			logo.put("groupName", groupName);
			logo.remove("logoKindId", "logoChildKindId", "sysUserId", "createTime");
		}
		return RetKit.ok("items", list);
	}

}
