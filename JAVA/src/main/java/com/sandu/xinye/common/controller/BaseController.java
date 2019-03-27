package com.sandu.xinye.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;


public class BaseController extends Controller{

	/**
	 * 用于批量接参，重复参数名只取第一个值
	 */
	@NotAction
	public Kv getParaToMap() {
		Kv kv = new Kv();
		Map<String, String[]> praaMap = getParaMap();
		for (Map.Entry<String, String[]> entry : praaMap.entrySet()) {
			kv.set(entry.getKey(), entry.getValue()[0]);
		}
		return kv;
	}

	/**
	 * 用于批量接数组参数 
	 * 例如： extCoverList[0]=/upload/temp/1.jpg  
	 * 		extCoverList[1]=/upload/temp/1.jpg
	 * @param paraName : 参数名称
	 * @param times:循环次数
	 */
	
	@NotAction
	public List<String> getParaToList(String paraName,int times) {
		times = times > 0 ? times : 1;
		List<String> list = new ArrayList<>();
		for (int i = 0; i < times; i++){
			String value = getPara(paraName+"["+i+"]");
			if (StrKit.notBlank(value)) {
				list.add(value);
			}else{
				break;
			}
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	@NotAction
	public List<String> getParaToList(String paraName) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++){
			String value = getPara(paraName+"["+i+"]");
			if (StrKit.notBlank(value)) {
				list.add(value);
			}else{
				break;
			}
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 用于批量接二维数组参数 
	 * 例如： extCoverList[0][name]:35.jpg
			extCoverList[0][url]:/upload/temp/35.jpg
			extCoverList[1][name]:14.jpg
			extCoverList[1][url]:/upload/temp/14.jpg
	 */
	@NotAction
	public List<String> getParaArrToList(String arrName,String paraName) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++){
			String value = getPara(arrName+"["+i+"]"+"["+paraName+"]");
			if (value != null) {
				list.add(value);
			}else{
				break;
			}
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 用于批量接二维数组参数 
	 * 例如： extCoverList[0][name]:35.jpg
			extCoverList[0][url]:/upload/temp/35.jpg
			extCoverList[1][name]:14.jpg
			extCoverList[1][url]:/upload/temp/14.jpg
	 */
	@NotAction
	public List<String> getParaArrToList(String arrName,String paraName,int times) {
		times = times > 0 ? times : 1;
		List<String> list = new ArrayList<>();
		for (int i = 0; i < times; i++){
			String value = getPara(arrName+"["+i+"]"+"["+paraName+"]");
			if (StrKit.notBlank(value)) {
				list.add(value);
			}else{
				break;
			}
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

	/**
	 * 获取访问IP
	 */
	@NotAction
    public String getIpAddress() {
    	HttpServletRequest request  = getRequest();
        String ip = request.getHeader("X-requested-For");
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.contains(",")) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        return ip;
    }
}
