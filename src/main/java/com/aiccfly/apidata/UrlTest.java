package com.aiccfly.apidata;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
 
 
public class UrlTest {
 
public static void main(String[] args) throws Exception {
		
		/**用到的架包有：
			json-lib-2.4-jdk15.jar
			commons-lang-2.6.jar
			ezmorph-1.0.6.jar
			commons-logging-1.1.1.jar
			commons-collections-3.2.1.jar
			commons-beanutils-1.8.3.jar
		 */
		
		//外接口路径
//		 String urlStr="http://m.weather.com.cn/data/101230101.html";
		 String urlStr="http://zwfw.moa.gov.cn:9000/zuul/application-moa-approval/contents/announcement";
		 //链接URL
		 URL url=new URL(urlStr);
		 //返回结果集
		 StringBuffer document = new StringBuffer();
		 //创建链接
		URLConnection conn = url.openConnection();

//	获取响应
/*	InputStream is =null;
	if(conn.getReadTimeout()==200){
		is = conn.getInputStream();
	}else{
		is = conn.get();
	}*/
		//读取返回结果集



		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));

		String line = null;
		while ((line = reader.readLine()) != null){
			  document.append(line);
		}	
			reader.close();
		System.out.println(document);	
		JSONObject json = JSONObject.fromObject(document.toString());
		//查看json格式
		System.out.println(json.toString());
		//获取json中某个对象
		JSONObject obj = (JSONObject) json.get("weatherinfo");
		System.out.println(obj.toString());
		//获取对象中某个属性的值
		String val = obj.getString("city");
		System.out.println(val);
	}
	
}
