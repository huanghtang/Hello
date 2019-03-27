package com.sandu.xinye.common.kit;

import com.jfinal.kit.StrKit;

/**
 * 验证上传的文件是否为视频或音频，此类仍不完善，只是应急使用
 * @author liyingxiang
 * @date 2018年2月12日
 */
public class VideoKit {
	
	private final static String[] videoExts = new String[]{"mp3","wma","wav","ogg","mp4", "flv", "avi", "rm", "rmvb", "wmv","mpg"};
	
	
	public static final boolean notVideoExtName(String fileName) {
		return ! isVideoExtName(fileName);
	}
	
	/**
	 * 通过文件扩展名，判断是否为支持的video文件，支持则返回 true，否则返回 false
	 */
	public static boolean isVideoExtName(String fileName) {
		if (StrKit.isBlank(fileName)) {
			return false;
		}
		fileName = fileName.trim().toLowerCase();
		String ext = getExtName(fileName);
		if (ext != null) {
			for (String s : videoExts) {
				if (s.equals(ext)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * 获得文件.后面的扩展名
	 */
	public static String getExtName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index != -1 && (index + 1) < fileName.length()) {
			return fileName.substring(index + 1);
		} else {
			return null;
		}
	}
}
