package com.sandu.xinye.admin.upload;

import com.jfinal.upload.UploadFile;
import com.sandu.xinye.common.controller.AdminController;
import com.sandu.xinye.common.kit.RetKit;

public class UploadController extends AdminController {
	
	public void uploadImg(){
		UploadFile uf = getFile("file");
		RetKit ret = UploadService.me.uploadImg(uf);
		renderJson(ret);
	}
	
	public void uploadFont(){
		UploadFile uf = getFile("file");
		RetKit ret = UploadService.me.uploadFont(uf);
		renderJson(ret);
	}
	
	public void uploadVideo(){
		UploadFile uf = getFile("file");
		RetKit ret = UploadService.me.uploadVideo(uf);
		renderJson(ret);
	}
}
