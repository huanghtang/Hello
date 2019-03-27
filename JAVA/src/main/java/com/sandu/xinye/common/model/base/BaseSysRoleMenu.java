package com.sandu.xinye.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysRoleMenu<M extends BaseSysRoleMenu<M>> extends Model<M> implements IBean {

	public M setSysRoleMenuId(java.lang.Integer sysRoleMenuId) {
		set("sysRoleMenuId", sysRoleMenuId);
		return (M)this;
	}
	
	public java.lang.Integer getSysRoleMenuId() {
		return getInt("sysRoleMenuId");
	}

	public M setSysUserRoleId(java.lang.Integer sysUserRoleId) {
		set("sysUserRoleId", sysUserRoleId);
		return (M)this;
	}
	
	public java.lang.Integer getSysUserRoleId() {
		return getInt("sysUserRoleId");
	}

	public M setSysMenuId(java.lang.Integer sysMenuId) {
		set("sysMenuId", sysMenuId);
		return (M)this;
	}
	
	public java.lang.Integer getSysMenuId() {
		return getInt("sysMenuId");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

}