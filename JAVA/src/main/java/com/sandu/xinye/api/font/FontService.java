package com.sandu.xinye.api.font;

import java.io.File;
import java.util.List;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sandu.xinye.common.kit.RetKit;
import com.sandu.xinye.common.model.Font;

public class FontService {
	public static final FontService me = new FontService();

	public RetKit getFont() {
		SqlPara sqlPara = Db.getSqlPara("app.font.paginate");
		List<Font> page = Font.dao.find(sqlPara);
		return RetKit.ok("list", page);
	}

	public File downloadFile(int fontId) {
		Font font = Font.dao.findById(fontId);
		if (font == null) {
			return null;
		}
		String filePath = PathKit.getWebRootPath() + font.getFontUrl();
		File file = new File(filePath);
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}

}
