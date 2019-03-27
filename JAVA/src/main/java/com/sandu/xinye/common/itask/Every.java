package com.sandu.xinye.common.itask;

import java.util.Calendar;
import java.util.Date;

import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sandu.xinye.common.model.EverydayData;
import com.xiaoleilu.hutool.date.DateUtil;

public class Every {
	
	public static  void ess(){
		LogKit.info("统计每日用户数据开始———————————————————————————————————————");
		//每天凌晨0点统计前一天的数据
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date beginOfDay = DateUtil.beginOfDay(cal.getTime());
		Date endOfDay = DateUtil.endOfDay(cal.getTime());
		Record re1 = Db.findFirst("select COUNT(userId) as newUserNumber from user where createTime BETWEEN  ? and ?",beginOfDay,endOfDay);
		 //前一天注册人数
		Record re2 = Db.findFirst("select COUNT(userId) as loginUserNumber from user where lastLoginTime BETWEEN  ? and ?",beginOfDay,endOfDay);
		//前一天登录人数
		EverydayData every = new EverydayData();
		every.setNewUserNumber(re1.getInt("newUserNumber")).setLoginUserNumber(re2.getInt("loginUserNumber")).setCreateTime(endOfDay).save();
		System.out.println("beginOfDay"+beginOfDay+"endOfDay"+endOfDay+"size");
		LogKit.info("每日更新产品价格结束-----------------------------");
	}
	
}
