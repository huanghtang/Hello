package com.sandu.xinye.common.kit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 飞鸽传书短信验证码工具类
 * @author liyingxiang
 * @date  2018/8/1
 */
public class FeiGeMsgKit {
	
	private static final Logger logger = Logger.getLogger(FeiGeMsgKit.class);

	//用于把线程设置为停止状态，由于Post方法里需要跳到其他线程（http请求）进行异步请求，等待请求完成之后取到回调结果，再执行后面的代码。
	private CountDownLatch latch;
	private boolean result = false;
		
	/**
	 * @方法描述: 发送验证码
	 * @param phone  手机号码
	 * @param captcha  验证码
	 * @return  true 发送成功，  false发送失败
	 */
	public boolean send(String phone,String code){
		logger.info("发送验证码开始----------------"+phone);
		result=false;
		latch=new CountDownLatch(1);
		try {
			  List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			  formparams.add(new BasicNameValuePair("Account","13798970929"));
			    formparams.add(new BasicNameValuePair("Pwd","f109876138e7d6cd97b4dfb8b"));
			    formparams.add(new BasicNameValuePair("Content","您的注册验证码是："+code+"，请不要把验证码泄露给其他人，如非本人请忽略。"));
			    formparams.add(new BasicNameValuePair("Mobile",phone));
			    formparams.add(new BasicNameValuePair("SignId","105174"));
			   Post(formparams);
			   //等待结果
			   latch.await();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("发送验证码结束----------------"+phone);
		return result;
	}
		
	/**
	 * @方法描述:Post 验证码
	 * @param formparams
	 * @throws Exception
	 */
	private void Post(List<NameValuePair> formparams) throws Exception{
		CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
	 	httpClient.start();
	 	
	 	HttpPost requestPost=new HttpPost("http://api.feige.ee/SmsService/Send");
	 	
	    requestPost.setEntity(new UrlEncodedFormEntity(formparams,"utf-8"));

		httpClient.execute(requestPost, new FutureCallback<HttpResponse>() {
			
			//如果执行这个方法，证明中途取消了发送
			public void cancelled() {
				result=false;
				//取消等待，继续执行后面的程序
				latch.countDown();
			}

			public void completed(HttpResponse arg0) {
				try {
					InputStream stram= arg0.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(stram));
					String jsonStr = reader.readLine();
					JSONObject json = JSONObject.parseObject(jsonStr);
					String code = json.get("Code").toString();
					if(code.equals("0")){
						logger.info("发送验证码成功！");
						result=true;
					}
				} catch (UnsupportedOperationException e) {
					logger.error(e.getMessage());
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally{
					//无论结果如何，最后都要取消等待，继续执行后面的程序
					latch.countDown();
				}
			}

			//如果执行这个方法，证明发送失败
			public void failed(Exception e) {
				logger.error(e.getMessage());
				result=false;
				//取消等待，继续执行后面的程序
				latch.countDown();
			}
			
		}).get();
	}
	
}
