package com.sandu.xinye.common.kit;
import java.util.Random;
/**
 * 
    * @ClassName: 
    * @Description: 生成随机数字工具类
    * @author 小徐同学
    * @date 2017年1月16日
    *
 */
public class RandomKit {
	
	/**
	 * 
	* @Description: 获取随机数字
	* @Title: getRandomString 
	* @author 小徐同学
	* @param @param length
	* @param @return
	* @date 2017年1月22日
	* @throws
	 */
	public static String getRandomPsw(int length) { //length表示生成字符串的长度
	    String base = "0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   
}