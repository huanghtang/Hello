package com.sandu.xinye.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOperationLog<M extends BaseOperationLog<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setIp(java.lang.String ip) {
		set("ip", ip);
		return (M)this;
	}
	
	public java.lang.String getIp() {
		return getStr("ip");
	}

	public M setSysUserId(java.lang.Integer sysUserId) {
		set("sysUserId", sysUserId);
		return (M)this;
	}
	
	public java.lang.Integer getSysUserId() {
		return getInt("sysUserId");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

}