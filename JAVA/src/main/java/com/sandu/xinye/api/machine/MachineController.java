package com.sandu.xinye.api.machine;

import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class MachineController extends AppController {

	/**
	 * @Title: getDeviveList
	 * @Description:  
	 * @date 2019年3月13日  上午10:52:24
	 * @author  liyingxiang
	 */
	public void getDeviveList(){
		String name = getPara("name");
		RetKit ret = MachineService.me.getDeviveList(name);
		renderJson(ret);
	}
}
