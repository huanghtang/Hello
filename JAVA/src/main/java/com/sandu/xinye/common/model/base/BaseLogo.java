package com.sandu.xinye.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLogo<M extends BaseLogo<M>> extends Model<M> implements IBean {

	public M setLogoId(java.lang.Integer logoId) {
		set("logoId", logoId);
		return (M)this;
	}
	
	public java.lang.Integer getLogoId() {
		return getInt("logoId");
	}

	public M setLogoKindId(java.lang.Integer logoKindId) {
		set("logoKindId", logoKindId);
		return (M)this;
	}
	
	public java.lang.Integer getLogoKindId() {
		return getInt("logoKindId");
	}

	public M setLogoChildKindId(java.lang.Integer logoChildKindId) {
		set("logoChildKindId", logoChildKindId);
		return (M)this;
	}
	
	public java.lang.Integer getLogoChildKindId() {
		return getInt("logoChildKindId");
	}

	public M setLogoImg(java.lang.String logoImg) {
		set("logoImg", logoImg);
		return (M)this;
	}
	
	public java.lang.String getLogoImg() {
		return getStr("logoImg");
	}

	public M setSysUserId(java.lang.Integer sysUserId) {
		set("sysUserId", sysUserId);
		return (M)this;
	}
	
	public java.lang.Integer getSysUserId() {
		return getInt("sysUserId");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

}
