#sql("font.paginate")
	select * from font 
		where 1 = 1
		order by createTime desc
#end

#sql("machine.list")
	select machineId,machineName from machine
	where 1=1 
	#if(sk.notBlank(name))
		and machineName like #p(name)
	#end
#end