package com.sandu.xinye.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysUser<M extends BaseSysUser<M>> extends Model<M> implements IBean {

	public M setSysUserId(java.lang.Integer sysUserId) {
		set("sysUserId", sysUserId);
		return (M)this;
	}
	
	public java.lang.Integer getSysUserId() {
		return getInt("sysUserId");
	}

	public M setLinkManName(java.lang.String linkManName) {
		set("linkManName", linkManName);
		return (M)this;
	}
	
	public java.lang.String getLinkManName() {
		return getStr("linkManName");
	}

	public M setPhoneNumber(java.lang.String phoneNumber) {
		set("phoneNumber", phoneNumber);
		return (M)this;
	}
	
	public java.lang.String getPhoneNumber() {
		return getStr("phoneNumber");
	}

	public M setSysUserName(java.lang.String sysUserName) {
		set("sysUserName", sysUserName);
		return (M)this;
	}
	
	public java.lang.String getSysUserName() {
		return getStr("sysUserName");
	}

	public M setSysUserPass(java.lang.String sysUserPass) {
		set("sysUserPass", sysUserPass);
		return (M)this;
	}
	
	public java.lang.String getSysUserPass() {
		return getStr("sysUserPass");
	}

	public M setSalt(java.lang.String salt) {
		set("salt", salt);
		return (M)this;
	}
	
	public java.lang.String getSalt() {
		return getStr("salt");
	}

	public M setSysUserRoleId(java.lang.Integer sysUserRoleId) {
		set("sysUserRoleId", sysUserRoleId);
		return (M)this;
	}
	
	public java.lang.Integer getSysUserRoleId() {
		return getInt("sysUserRoleId");
	}

	public M setSysUserLoginTime(java.util.Date sysUserLoginTime) {
		set("sysUserLoginTime", sysUserLoginTime);
		return (M)this;
	}
	
	public java.util.Date getSysUserLoginTime() {
		return get("sysUserLoginTime");
	}

	public M setSysUserCreateTime(java.util.Date sysUserCreateTime) {
		set("sysUserCreateTime", sysUserCreateTime);
		return (M)this;
	}
	
	public java.util.Date getSysUserCreateTime() {
		return get("sysUserCreateTime");
	}

	public M setIsDel(java.lang.Integer isDel) {
		set("isDel", isDel);
		return (M)this;
	}
	
	public java.lang.Integer getIsDel() {
		return getInt("isDel");
	}

}
