package com.sandu.xinye.api.font;

import com.jfinal.aop.Clear;
import com.sandu.xinye.common.controller.AppController;
import com.sandu.xinye.common.kit.RetKit;

public class FontController extends AppController {
	
	/***
	 * 
	 * @Title 获取字体列表
	 * @Description
	 * @param text pageNumber 1
	 * @param text pageSize 10
	 * @return
	 *
	 */
	@Clear
	public void getFont(){
		RetKit ret = FontService.me.getFont();
		renderJson(ret);
	}
	
	@Clear
	public void downloadFont(){
		int fontId = getParaToInt("fontId");
		renderFile(FontService.me.downloadFile(fontId));
	}
	
}
