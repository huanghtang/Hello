package com.sandu.xinye.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseFeedback<M extends BaseFeedback<M>> extends Model<M> implements IBean {

	public M setFeedbackId(java.lang.Integer feedbackId) {
		set("feedbackId", feedbackId);
		return (M)this;
	}
	
	public java.lang.Integer getFeedbackId() {
		return getInt("feedbackId");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setSysUserId(java.lang.Integer sysUserId) {
		set("sysUserId", sysUserId);
		return (M)this;
	}
	
	public java.lang.Integer getSysUserId() {
		return getInt("sysUserId");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setUserId(java.lang.Integer userId) {
		set("userId", userId);
		return (M)this;
	}
	
	public java.lang.Integer getUserId() {
		return getInt("userId");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

}
