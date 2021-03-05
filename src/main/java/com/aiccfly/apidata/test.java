package com.aiccfly.apidata;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
 
public class test {
 
	public static void main(String[] args) {
		String path="http://zwfw.moa.gov.cn:9000/zuul/application-moa-approval/contents/announcement";
		try {
			//创建一个URL对象
			URL url=new URL(path);
			//创建一个URLConnection连接对象
			URLConnection conn=url.openConnection();


			//创建一个输入流来接收网页
			InputStream in=conn.getInputStream();
			//字节流——>字符流
			InputStreamReader isr = new InputStreamReader(in);
			//从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			StringBuffer sb=new StringBuffer();
			
			while ((line=br.readLine())!=null) {
				sb.append(line);
            }
			
            System.out.println(sb);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		InputStream a = conn
				.getInputStream();
		InputStreamReader i = new InputStreamReader(a, "utf-8");
		BufferedReader br = new BufferedReader(i);
		String str = br.readLine();
		System.out.println(str);

		return str;
	}
 
}