package com.sandu.xinye.admin.set;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Machine;
import com.sandu.xinye.common.model.SysUser;

public class MachineService {

	public static final MachineService me = new MachineService();
	
	public RetKit list(int pageNumber,int pageSize,Kv kv){
		String machineName = kv.getStr("machineName");
		if(StrKit.notBlank(machineName)){
			machineName = "%" + machineName + "%";
			kv.set("machineName",machineName);
		}
		SqlPara sqlPara = Db.getSqlPara("admin.machine.paginate",kv);
		Page<Machine> page = Machine.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}
	
	public RetKit add(Machine machine,SysUser sysUser,String ip){
		boolean succ = machine.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).save();
		if(succ){
			String content = sysUser.getSysUserName() + "添加了id为" +machine.getMachineId() +"的" +machine.getMachineName()+"设备";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok().setOk("添加成功") : RetKit.fail();
	}
	
	public RetKit update(Machine machine,SysUser sysUser,String ip){
		boolean succ = machine.setCreateTime(new Date()).setSysUserId(sysUser.getSysUserId()).update();
		if(succ){
			String content = sysUser.getSysUserName() + "编辑了id为" +machine.getMachineId() +"的" +machine.getMachineName()+"设备";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok().setOk("编辑成功") : RetKit.fail();
	}
	
	public RetKit del(String machineId, SysUser sysUser,String ip){
		Machine machine = Machine.dao.findById(machineId);
		boolean succ = machine.delete();
		if(succ){
			String content = sysUser.getSysUserName() + "删除了id为" +machineId+"的" +machine.getMachineName()+"设备";
			OperationLogService.me.saveOperationLog(sysUser.getSysUserId(), ip, content);
		}
		return succ ? RetKit.ok().setOk("删除成功") : RetKit.fail();
	}
	
	public RetKit getMachineList(){
		List<Machine> list = Machine.dao.find("select * from machine order by machineId");
		return RetKit.ok("list",list);
	}
}
