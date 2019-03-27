package com.sandu.xinye.api.banner;

import java.util.List;

import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Banner;

public class BannerApiService {

	public static final BannerApiService me = new BannerApiService();

	public RetKit list() {
		List<Banner> list = Banner.dao.find("select * from banner order by id desc");
		return RetKit.ok("list", list);
	}

}
