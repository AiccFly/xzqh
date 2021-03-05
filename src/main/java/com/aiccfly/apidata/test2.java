package com.aiccfly.apidata;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class test2 {
 
	public static void main(String[] args) {
		String path="http://zwfw.moa.gov.cn:9000/zuul/application-moa-approval/contents/announcement";
		String token="8c953670-5f2d-4536-9946-dcd374203d6d";
		try {
			//创建一个URL对象
			URL url=new URL(path);
			//创建一个URLConnection连接对象
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			// 3, 设置提交类型
			conn.setRequestMethod("POST");
			conn.addRequestProperty("token", token);
			conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
			conn.setConnectTimeout(1000 * 5);//5秒
			conn.setReadTimeout(1000 * 60);//1分钟
			conn.setRequestProperty("connection","Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true);// 4, 设置允许写出数据,默认是不允许 false
			conn.setDoInput(true);// 当前的连接可以从服务器读取内容, 默认是true
			conn.connect();
			//创建输入流
			InputStream in=conn.getInputStream();
		//	int timeout = conn.getReadTimeout();
		//	conn.getPermission();
//			String s = conn.getResponseMessage();
		//	System.out.println(timeout);

			// 6, 获取响应的数据

			//创建一个输入流来
//			InputStream in=conn.getInputStream();
//			conn.getURL();
//			InputStream in = conn.getErrorStream();
//			conn.getPermission();
			//字节流——>字符流
			InputStreamReader isr = new InputStreamReader(in,"utf-8");
			//从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			StringBuffer sb=new StringBuffer();
			
			while((line=br.readLine())!=null) {
				sb.append(line);
            }
			in.close();
            System.out.println(sb);
		} catch (MalformedURLException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {


		}
	}

	public static String GetResponse(String Info,String token,String path) throws IOException {

// 1, 得到URL对象
		URL url = new URL(path);

// 2, 打开连接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

// 3, 设置提交类型
		conn.setRequestMethod("POST");
		conn.addRequestProperty("token", token);
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

// 4, 设置允许写出数据,默认是不允许 false
		conn.setDoOutput(true);
		conn.setDoInput(true);// 当前的连接可以从服务器读取内容, 默认是true

// 5, 获取向服务器写出数据的流
		OutputStream os = conn.getOutputStream();
// 参数是键值对 , 不以"?"开始
		os.write(Info.getBytes());

		os.flush();
// 6, 获取响应的数据
// 得到服务器写回的响应数据
		InputStream a = conn.getInputStream();
		InputStreamReader i = new InputStreamReader(a, "utf-8");
		BufferedReader br = new BufferedReader(i);
		String str = br.readLine();
		System.out.println(str);

		return str;
	}
 
}