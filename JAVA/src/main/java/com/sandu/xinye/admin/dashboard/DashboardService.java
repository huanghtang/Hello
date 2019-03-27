package com.sandu.xinye.admin.dashboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.common.kit.MyDateKit;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.EverydayData;
import com.sandu.xinye.common.model.User;
import com.xiaoleilu.hutool.date.DateUtil;

public class DashboardService {
	public static final DashboardService me = new DashboardService();

	/*
	 * 时间颗粒：1-按每日显示 2-按每周显示 3-按每月显示
	 */
	public static final int TIME_TYPE_DAY = 1;
	public static final int TIME_TYPE_WEEK = 2;
	public static final int TIME_TYPE_MONTH = 3;

	public RetKit getProductKindData(String beginDateStr, String endDateStr, Integer timeType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StrKit.isBlank(beginDateStr) || StrKit.isBlank(endDateStr)) {
			beginDateStr = sdf.format(new Date());
			endDateStr = sdf.format(new Date());
		}

		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = DateUtil.beginOfDay(sdf.parse(beginDateStr));
			endDate = DateUtil.endOfDay(sdf.parse(endDateStr));
		} catch (ParseException e) {
			LogKit.error("转换日期失败！");
		}
		Kv kv = Kv.by("beginDate", beginDate).set("endDate", endDate);

		if (timeType == TIME_TYPE_DAY) { // 按每日显示折线图
			long days = DateUtil.betweenDay(beginDate, endDate, true);
			System.out.println(days + "dddddddddddddays");
			// 创建视图
			SqlPara viewSqlPara = Db.getSqlPara("admin.days_view", Kv.by("days", days).set("endDate", endDate));
			Db.update(viewSqlPara);
			SqlPara sqlPara = Db.getSqlPara("admin.user.daysAnalysis", kv);
			List<Record> list = Db.find(sqlPara);
			return RetKit.ok("list", list);
			// return RetKit.ok();

		} else if (timeType == TIME_TYPE_WEEK) { // 按每周显示折线图

			long days = DateUtil.betweenDay(beginDate, endDate, true);
			// 创建视图
			SqlPara viewSqlPara = Db.getSqlPara("admin.days_view", Kv.by("days", days).set("endDate", endDate));
			Db.update(viewSqlPara);
			SqlPara sqlPara = Db.getSqlPara("admin.user.daysAnalysis", kv);
			List<Record> daysList = Db.find(sqlPara); // 按天显示的数据

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			long weeks = (days + 1) / 7; // 计算时间段共有多少周
			long remainder = (days + 1) % 7;// 剩下的余数
			if (weeks == 0) {
				String theDay = daysList.get(0).getStr("theDay") + "~"
						+ daysList.get(daysList.size() - 1).getStr("theDay");
				Integer userCounts = 0;
				for (int i = 0; i < daysList.size(); i++) {
					userCounts = userCounts + daysList.get(i).getInt("userCounts");

				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("theDay", theDay);
				map.put("userCounts", userCounts);
				list.add(map);
			} else {
				int index = 0;
				for (int i = 1; i <= weeks; i++) {
					String theDay = "";
					Integer userCounts = 0;
					for (int j = 0; j < 7; j++) {
						if (j == 0) {
							theDay = daysList.get(index).getStr("theDay") + "~";
						}
						if (j == 6) {
							theDay = theDay + daysList.get(index).getStr("theDay");
						}
						userCounts = userCounts + daysList.get(index).getInt("userCounts");
						index++;
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("theDay", theDay);
					map.put("userCounts", userCounts);
					list.add(map);
				}
				// 如果天数除以7的余数大于0
				if (remainder > 0) {
					String theDay = daysList.get(index).getStr("theDay") + "~"
							+ daysList.get(daysList.size() - 1).getStr("theDay");
					Integer userCounts = 0;
					for (int i = 0; i < remainder; i++) {
						userCounts = userCounts + daysList.get(index).getInt("userCounts");
						index++;
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("theDay", theDay);
					map.put("userCounts", userCounts);
					list.add(map);
				}
			}

			return RetKit.ok("list", list);

		} else if (timeType == TIME_TYPE_MONTH) { // 按每月显示折线图
			int months = MyDateKit.getMonths(beginDate, endDate);// 月份数
			// 创建视图
			SqlPara viewSqlPara = Db.getSqlPara("admin.months_view", Kv.by("months", months).set("endDate", endDate));
			Db.update(viewSqlPara);
			SqlPara sqlPara = Db.getSqlPara("admin.user.monthsAnalysis", kv.set("months", months + 1));
			List<Record> list = Db.find(sqlPara);
			// 删除视图
			Db.update("drop view user_months_view");
			return RetKit.ok("list", list);
		} else {
			return RetKit.fail("时间颗粒参数有误！");
		}
	}

	public RetKit getYesterdayData() {
		Calendar cal = Calendar.getInstance();
		Date beginOfDay = DateUtil.beginOfDay(cal.getTime());

		Record re = Db.findFirst("select count(userId) as historyRegisterCount from user");
		EverydayData yesterdayData = EverydayData.dao.findFirst("select * from everyday_data where createTime = ?",
				beginOfDay);
		if(yesterdayData == null){
			EverydayData nullData = new EverydayData();
			nullData.setLoginUserNumber(0).setNewUserNumber(0).setCreateTime(beginOfDay);
			nullData.put("historyRegisterCount", re.getInt("historyRegisterCount"));
			nullData.save();
			return RetKit.ok("yesterdayData", nullData);
		}
		yesterdayData.put("historyRegisterCount", re.getInt("historyRegisterCount"));
		System.out.println(yesterdayData);
		return RetKit.ok("yesterdayData", yesterdayData);
	}
	
	public RetKit getDataPage(int pageNumber,int pageSize){
		SqlPara sqlPara=Db.getSqlPara("admin.countUser.paginate");
		Page<User> page = User.dao.paginate(pageNumber, pageSize, sqlPara);
		return RetKit.ok("page",page);
	}
}
