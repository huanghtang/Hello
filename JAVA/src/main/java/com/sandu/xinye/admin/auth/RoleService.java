package com.sandu.xinye.admin.auth;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Menu;
import com.sandu.xinye.common.model.SysRoleMenu;
import com.sandu.xinye.common.model.SysUser;
import com.sandu.xinye.common.model.SysUserRole;

public class RoleService {
	public static final RoleService me = new RoleService();

	public RetKit getRoleList() {
		List<SysUserRole> list = SysUserRole.dao.find("select * from sys_user_role order by sysUserRoleId desc");
		for (SysUserRole role : list) {
			role.put("menus", findMenuIdsByRoleId(role.getSysUserRoleId()));
		}
		return RetKit.ok("list", list);
	}

	/**
	 * @Title: findMenuIdsByRoleId
	 * @Description: 通过角色id查看该角色所有的菜单id
	 * @param roleId
	 * @return
	 * @date 2019年2月20日 下午1:43:04
	 */
	private Object[] findMenuIdsByRoleId(Integer roleId) {
		List<Menu> menuList = new ArrayList<>();
		// 如果是超管
		if (roleId == 0) {
			menuList = Menu.dao.find("select menuId from menu where status=?  order by level,sort", Menu.STATUS_OK);
		} else {
			menuList = Menu.dao.find(
					"select menuId from menu where status=? and menuId in (select sysMenuId from sys_role_menu where sysUserRoleId=?) order by level,sort",
					Menu.STATUS_OK, roleId);
		}
		List<Integer> menuIdList = new ArrayList<>();
		for (Menu menu : menuList) {
			menuIdList.add(menu.getMenuId());
		}
		return menuIdList.toArray();
	}

	public List<Kv> findMenus() {
		List<Menu> menuList = Menu.dao.find("select * from menu where status = ? order by level,sort", Menu.STATUS_OK);
		return getChildrenMenu(menuList, 0);
	}

	/**
	 * @Title: getChildrenMenu
	 * @Description: 递归获取权限菜单列表
	 * @param menuList
	 * @param parentId
	 * @return
	 * @date 2019年2月18日 下午6:56:04
	 */
	public List<Kv> getChildrenMenu(List<Menu> menuList, int parentId) {
		List<Kv> kvList = new ArrayList<>();
		for (Menu menu : menuList) {
			if (parentId == 0 && menu.getIsParent() && menu.getParentId() == parentId) { // 没有父id，而且是父节点
				Kv kv = new Kv();
				kv.set("id", menu.getMenuId());
				kv.set("label", menu.getName());
				kv.set("children", getChildrenMenu(menuList, menu.getMenuId()));
				kvList.add(kv);
			} else if (parentId > 0) { // 有父id的
				Kv kv = new Kv();
				if (menu.getParentId() == parentId) {
					kv.set("id", menu.getMenuId());
					kv.set("label", menu.getName());
					if (menu.getIsParent()) {
						kv.set("children", getChildrenMenu(menuList, menu.getMenuId()));
					}
					kvList.add(kv);
				}
			}
		}
		return kvList;
	}

	/**
	 * @Title: save
	 * @Description: 添加权限组
	 * @param menus
	 * @param name
	 * @param account
	 * @param ip
	 * @return
	 */
	public RetKit save(List<String> menus, String name, SysUser account, String ip) {
		if (menus.isEmpty()) {
			return RetKit.fail("请选择权限菜单！");
		}
		boolean succ = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				SysUserRole role = new SysUserRole().setName(name).setCreateTime(new Date()).setUpdateTime(new Date());
				boolean succ = role.save();
				if (!succ) {
					return false;
				}
				if(succ){
					String content = account.getSysUserName()+"添加了名为"+name+"的权限组";
					OperationLogService.me.saveOperationLog(account.getSysUserId(), ip, content);
						
				}
				List<SysRoleMenu> list = new ArrayList<>();
				for (String menuId : menus) {
					SysRoleMenu roleMenu = new SysRoleMenu().setSysMenuId(Integer.valueOf(menuId))
							.setSysUserRoleId(role.getSysUserRoleId()).setCreateTime(new Date());
					list.add(roleMenu);
				}
				Db.batchSave(list, list.size());
				return true;
			}
		});

		return succ ? RetKit.ok() : RetKit.fail();

	}
	
	public RetKit update(List<String> menus, SysUserRole role, SysUser account, String ip) {
		if (role.getSysUserRoleId() == null) {
			return RetKit.fail("参数错误！");
		}
		if (role.getSysUserRoleId() == 0) {
			return RetKit.fail("不能修改超级管理员权限！");
		}
		if (isExistName(role.getName(), role.getSysUserRoleId())) {
			return RetKit.fail("权限组名称已存在");
		}
		if (menus.isEmpty()) {
			return RetKit.fail("请选择权限菜单！");
		}
		boolean succ = role.setUpdateTime(new Date()).update();
		// 先删除再插入
		deleteRoleMenuByRoleId(role.getSysUserRoleId());
		if(succ){
			String content = account.getSysUserName()+"添加了名为"+role.getName()+"的权限组";
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ip, content);
				
		}		
		List<SysRoleMenu> list = new ArrayList<>();
		for (String menuId : menus) {
			SysRoleMenu roleMenu = new SysRoleMenu().setSysMenuId(Integer.parseInt(menuId)).
					setSysUserRoleId(role.getSysUserRoleId()).setCreateTime(new Date());
			list.add(roleMenu);
		}
		Db.batchSave(list, list.size());


		return succ ? RetKit.ok() : RetKit.fail();
	}
	
	private boolean deleteRoleMenuByRoleId(long id) {
		return Db.update("delete from sys_role_menu where sysUserRoleId = ?", id) > 1;
	}
	
	public boolean isExistName(String name) {
		return Db.queryInt("select sysUserRoleId from sys_user_role where name = ? limit 1", name.trim()) != null;
	}
	
	public boolean isExistName(String name, Integer id) {
		return Db.queryInt("select sysUserRoleId from sys_user_role where name = ? and sysUserRoleId != ? limit 1",
				name.trim(), id) != null;
	}
	
	public boolean isExistAccountRole(Integer id){
		return Db.queryInt("select sysUserId from sys_user where sysUserRoleId = ? and isDel=? limit 1",id,Constant.NO_DEL) != null;
	}

	public Object[] findMenuNamesByRoleId(Integer roleId) {
		List<Menu> menuList = new ArrayList<>();
		// 如果是超管
		if (roleId == 0) {
			menuList = Menu.dao.find("select name from menu where status = ? order by level,sort", Menu.STATUS_OK);
		} else {
			menuList = Menu.dao.find(
					"select name from menu where status = ? and menuId in (select sysMenuId from sys_role_menu where sysUserRoleId = ? ) order by level,sort",
					Menu.STATUS_OK, roleId);
		}
		System.out.println("dfa:"+menuList);
		return menuList.toArray();
	}

	public List<SysUserRole> roleList() {
		List<SysUserRole> list = SysUserRole.dao.find("select * from sys_user_role order by sysUserRoleId desc");
		return list;
	}
	
	public RetKit remove(Integer id,SysUser account, String ip){
		if(isExistAccountRole(id)){
			return RetKit.fail("该权限组被使用中，不能删除！");
		}
		deleteRoleMenuByRoleId(id);
		SysUserRole role = SysUserRole.dao.findById(id);
		boolean succ = role.delete();
		if(succ){
			String content = account.getSysUserName()+"删除了名为"+role.getName()+"的权限组";
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ip, content);
				
		}	
		return succ ? RetKit.ok() : RetKit.fail();
	}
}
