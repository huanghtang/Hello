package com.sandu.xinye.api.help;

import java.util.List;

import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Help;

public class HelpService {

	public static final HelpService me = new HelpService();


	/**
	 * @Title: getHelp
	 * @Description:  
	 * @param machineId
	 * @param helpKind
	 * @return
	 * @date 2019年3月13日  上午10:53:42
	 * @author  liyingxiang
	 */
	public RetKit getHelp(int machineId, int helpKind) {
		if (helpKind == Constant.SOFT_HELP) {
			List<Help> list = Help.dao.find("select * from help where machineId = ? and helpKind = ?", machineId,
					helpKind);
			return RetKit.ok("list", list);
		}
		List<Help> list = Help.dao.find("select * from help where machineId = ? and helpKind = ?", machineId, helpKind);
		return RetKit.ok("list", list);
	}

}
