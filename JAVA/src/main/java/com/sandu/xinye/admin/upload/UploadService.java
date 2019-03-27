package com.sandu.xinye.admin.upload;

import java.io.File;

import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.sandu.xinye.common.constant.Constant;
import com.sandu.xinye.common.kit.ImageKit;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.kit.VideoKit;

public class UploadService {
	public static final UploadService me = new UploadService();

	/**
	 * 上传图片
	 * 
	 * @param uf
	 * @return
	 */

	public RetKit uploadImg(UploadFile uf) {
		if (uf == null) {
			return RetKit.fail("上传图片不能为空");
		}
		try {
			if (!ImageKit.isImageExtName(uf.getFileName())) {
				return RetKit.fail("msg", "文件类型不正确，只支持类型：jpg、jpeg、png、bmp");
			}
			// 获得文件的后缀名
			String extName = ImageKit.getExtName(uf.getFileName());
			String newFileName = StrKit.getRandomUUID() + "." + extName;
			String newPath = Constant.BASE_UPLOAD_PATH + "/" + newFileName;

			File newFile = new File(newPath);
			if (newFile.exists()) {
				boolean res = newFile.delete();
				if (!res) {
					return RetKit.fail("转存图片失败!");
				}
			}
			boolean succ = uf.getFile().renameTo(newFile);
			return succ ? RetKit.ok("file", Kv.by("url", Constant.UPLOAD_PATH + "/" + newFileName))
					: RetKit.fail("msg", "上传文件失败！");
		} catch (Exception e) {
			LogKit.info(e.getMessage());
			return RetKit.fail("msg", e.getMessage());
		} finally {
			uf.getFile().delete();
		}
	}
	
	/**
	 * 上传字体
	 * 
	 * @param uf
	 * @return
	 */

	public RetKit uploadFont(UploadFile uf) {
		if (uf == null) {
			return RetKit.fail("上传字体不能为空");
		}
		try {
			// 获得文件的后缀名
			String extName = ImageKit.getExtName(uf.getFileName());
			String newFileName = StrKit.getRandomUUID() + "." + extName;
			String newPath = Constant.BASE_UPLOAD_PATH + "/" + newFileName;

			File newFile = new File(newPath);
//			if (newFile.exists()) {
//				boolean res = newFile.delete();
//				if (!res) {
//					return RetKit.fail("转存图片失败!");
//				}
//			}
			boolean succ = uf.getFile().renameTo(newFile);
			return succ ? RetKit.ok("file", Kv.by("url", Constant.UPLOAD_PATH + "/" + newFileName))
					: RetKit.fail("msg", "上传文件失败！");
		} catch (Exception e) {
			LogKit.info(e.getMessage());
			return RetKit.fail("msg", e.getMessage());
		} finally {
			uf.getFile().delete();
		}
	}
	
	public RetKit uploadVideo(UploadFile uf) {
		if (uf == null) {
			return RetKit.fail("上传视频不能为空！");
		}
		try {
			if (!VideoKit.isVideoExtName(uf.getFileName())) {
				return RetKit.fail("文件类型不正确，只支持类型：mp3，wma，wav，ogg，mp4，flv，avi，rm，rmvb，wmv，mpg等格式！");
			}
			// 获得文件的后缀名
			String extName = VideoKit.getExtName(uf.getFileName());
			String newFileName = StrKit.getRandomUUID() + "." + extName;
			String newPath = Constant.BASE_UPLOAD_PATH + "/" + newFileName;

			File newFile = new File(newPath);
			if (newFile.exists()) {
				boolean res = newFile.delete();
				if (!res) {
					return RetKit.fail("转存视频失败！");
				}
			}
			boolean succ = uf.getFile().renameTo(newFile);
			return succ ? RetKit.ok("file", Kv.by("url", Constant.UPLOAD_PATH + "/" + newFileName))
					: RetKit.fail("msg", "上传文件失败！");
		} catch (Exception e) {
			LogKit.info(e.getMessage());
			return RetKit.fail("msg", e.getMessage());
		} finally {
			uf.getFile().delete();
		}
	}
}
