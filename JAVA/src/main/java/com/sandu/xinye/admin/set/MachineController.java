package com.sandu.xinye.admin.set;

import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Machine;

public class MachineController extends AdminController {

	public void list(){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		RetKit ret = MachineService.me.list(pageNumber, pageSize,getParaToMap());
		renderJson(ret);
	}
	
	public void add(){
		Machine machine = getBean(Machine.class,"");
		RetKit ret = MachineService.me.add(machine, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void update(){
		Machine machine = getBean(Machine.class,"");
		RetKit ret = MachineService.me.update(machine, getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void del(){
		String machineId = getPara(0);
		RetKit ret = MachineService.me.del(machineId,getAccount(),getIpAddress());
		renderJson(ret);
	}
	
	public void getMachineList(){
		RetKit ret = MachineService.me.getMachineList();
		renderJson(ret);
	}
}
