package com.sandu.xinye.api.help;

import com.jfinal.aop.Clear;
import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class HelpController extends AppController {
	


	/***
	 * 
	 * @Title
	 * @Description
	 * @Param helpKind   帮助类型  软件-1 硬件-2
	 * @Param machineId  设备id
	 * @return
	 * @time 2019年3月12日下午12:00:17
	 *
	 */
	@Clear
	public void getHelp(){
		int helpKind = getParaToInt("helpKind");
		int machineId= getParaToInt("machineId");
		RetKit ret = HelpService.me.getHelp(machineId, helpKind);
		renderJson(ret);
	}
}
