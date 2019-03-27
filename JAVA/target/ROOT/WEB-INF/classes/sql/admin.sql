#sql("mainkind.paginate")
select a.logoKindId,a.logoKindName,a.createTime,b.sysUserName from logo_kind a
	left join sys_user b on a.sysUserId = b.sysUserId
	where 1 = 1
	#if(sk.notBlank(logoKindName))
		and a.logoKindName like #p(logoKindName)
	#end
	order by a.createTime desc
#end

#sql("childkind.paginate")
select a.logoKindId,a.logoChildKindId,a.logoChildKindName,a.createTime,b.sysUserName,c.logoKindName from logo_child_kind a
left join sys_user b on a.sysUserId = b.sysUserId
left join logo_kind c on c.logoKindId = a.logoKindId
	where 1 = 1
	#if(sk.notBlank(logoChildKindName))
		and a.logoChildKindName like #p(logoChildKindName)
	#end
	#if(sk.notBlank(logoKindId))
		and a.logoKindId = #p(logoKindId)
	#end
	order by a.createTime desc
#end

#sql("logo.paginate")
SELECT a.*,b.logoKindName,c.logoChildKindName,d.sysUserName FROM logo a 
LEFT JOIN logo_kind b on b.logoKindId = a.logoKindId
LEFT JOIN logo_child_kind c on c.logoChildKindId = a.logoChildKindId
LEFT JOIN sys_user d on a.sysUserId = d.sysUserId
where 1 = 1
#if(sk.notBlank(logoKindId))
		and a.logoKindId = #p(logoKindId)
	#end
	#if(sk.notBlank(logoChildKindId))
		and a.logoChildKindId = #p(logoChildKindId)
	#end
order by a.createTime desc
#end

#sql("user.paginate")
SELECT * FROM user
where 1 = 1
#if(sk.notBlank(registerType))
		and registerType = #p(registerType)
	#end
	#if(sk.notBlank(status))
		and status = #p(status)
	#end
		#if(sk.notBlank(userPhone))
		and userPhone = #p(userPhone)
	#end
order by createTime desc
#end

#sql("machine.paginate")
select a.*,b.sysUserName from machine a
	left join sys_user b on a.sysUserId = b.sysUserId
	where 1 = 1
	#if(sk.notBlank(machineName))
		and a.machineName like #p(machineName)
	#end
	order by a.createTime desc
#end

#sql("font.paginate")
select a.*,b.sysUserName from font a
	left join sys_user b on a.sysUserId = b.sysUserId
	where 1 = 1
	#if(sk.notBlank(fontName))
		and a.fontName like #p(fontName)
	#end
	order by a.createTime desc
#end

#sql("help.paginate")
select a.*,b.sysUserName,c.machineName from help a
	left join sys_user b on a.sysUserId = b.sysUserId
	left join machine c on a.machineId = c.machineId
	where 1 = 1
	#if(sk.notBlank(helpName))
		and a.helpName like #p(helpName)
	#end
		#if(sk.notBlank(helpKind))
		and a.helpKind like #p(helpKind)
	#end
		#if(sk.notBlank(machineId))
		and a.machineId like #p(machineId)
	#end
	order by a.createTime desc
#end

#sql("feedback.paginate")
select a.*,b.sysUserName from feedback a
	left join sys_user b on a.sysUserId = b.sysUserId
	where 1 = 1
	#if(sk.notBlank(statue))
		and a.statue like #p(statue)
	#end
	order by a.createTime desc
#end

#sql("about.paginate")
select a.*,b.sysUserName from about a
	left join sys_user b on a.sysUserId = b.sysUserId
	where 1 = 1
	order by a.createTime desc
#end

#sql("sysUser.paginate")
	select a.phoneNumber,a.linkManName,a.sysUserId,a.sysUserName,a.sysUserRoleId,a.sysUserLoginTime,a.sysUserCreateTime,a.isDel,IFNULL(b.name,"系统管理员") as roleName
	from sys_user a
	left join sys_user_role b on a.sysUserRoleId = b.sysUserRoleId
	where a.isDel = 0
	order by a.sysUserId desc
#end	

#sql("operate.paginate")
	select a.*,b.sysUserName
	from operation_log a
	LEFT JOIN sys_user b on a.sysUserId = b.sysUserId
	ORDER BY a.createTime desc
#end	

----------------------------------------注册登录分析--------------------------------------------
#sql("days_view")
	CREATE OR REPLACE
	VIEW `user_days_view` AS
	    SELECT DATE_FORMAT(#p(endDate), '%Y-%m-%d') AS theDay
	    #for(x = 0; x <= days; x++)
	    	UNION SELECT DATE_FORMAT((#p(endDate) - INTERVAL #(x) DAY), '%Y-%m-%d') AS theDay
	    #end
#end

#sql("months_view")
	CREATE OR REPLACE
	VIEW `user_months_view` AS
	    SELECT DATE_FORMAT(#p(endDate), '%Y-%m') AS theDay
	    #for(x = 0; x <= months; x++)
	    	UNION SELECT DATE_FORMAT((#p(endDate) - INTERVAL #(x) MONTH), '%Y-%m') AS theDay
	    #end
#end

#sql("user.daysAnalysis")
	select v.theDay,IFNULL(w.userCounts,0) userCounts  from user_days_view v
	 left join
	(
		select count(a.userId) as userCounts
		,DATE_FORMAT(a.createTime,"%Y-%m-%d") as theDay
		from user a
		where  a.createTime BETWEEN #p(beginDate) AND #p(endDate)
		GROUP BY theDay
	) w
	on v.theDay = w.theDay
	order by v.theDay
#end

#sql("user.monthsAnalysis")
	select v.theDay,IFNULL(w.userCounts,0) userCounts  from user_months_view v
	 left join
	(
		select count(a.userId) as userCounts
		,DATE_FORMAT(a.createTime,"%Y-%m") as theDay
		from user a
		where  DATE_FORMAT(a.createTime,"%Y-%m") > DATE_FORMAT(date_sub(#p(endDate), interval #p(months) MONTH),'%Y-%m')
and a.createTime BETWEEN #p(beginDate) AND #p(endDate)
		GROUP BY theDay
	) w
	on v.theDay = w.theDay
	order by v.theDay
#end

#sql("countUser.paginate")
SELECT  * ,DATE_FORMAT(createTime,'%Y-%m-%d') as time from everyday_data ORDER BY time desc
#end