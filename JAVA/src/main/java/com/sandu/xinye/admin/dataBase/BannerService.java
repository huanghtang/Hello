package com.sandu.xinye.admin.dataBase;

import java.util.Date;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.sandu.xinye.admin.operate.OperationLogService;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Banner;
import com.sandu.xinye.common.model.SysUser;

public class BannerService {

	public static final BannerService me = new BannerService();

	public RetKit list(int pageNumber, int pageSize) {
		Page<Banner> page = Banner.dao.paginate(pageNumber, pageSize, "select * ", " from banner order by id desc");
		return RetKit.ok("page", page);
	}

	public RetKit add(Banner banner, SysUser account, String ipAddress) {
		boolean succ = banner.setCreateTime(new Date()).save();
		if (succ) {
			String content = account.getSysUserName() + "添加了banner，id为:" + banner.getId();
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ipAddress, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

	public RetKit update(Banner banner, SysUser account, String ipAddress) {
		boolean succ = banner.update();
		if (succ) {
			String content = account.getSysUserName() + "更新了banner，id为:" + banner.getId();
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ipAddress, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

	public RetKit del(String id, SysUser account, String ipAddress) {
		if (StrKit.isBlank(id)) {
			return RetKit.fail("id不能为空！");
		}
		Banner banner = Banner.dao.findById(id);
		if (banner == null) {
			return RetKit.fail("id有误！");
		}
		boolean succ = banner.update();
		if (succ) {
			String content = account.getSysUserName() + "更新了banner，id为:" + banner.getId();
			OperationLogService.me.saveOperationLog(account.getSysUserId(), ipAddress, content);
		}
		return succ ? RetKit.ok() : RetKit.fail();
	}

}
