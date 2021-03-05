package com.aiccfly.apidata;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class test03 {
    public static void main(String[] args) {
//        httpRequest()
    }
    public static String httpRequest(String urlStr, String content, String requestMethod) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(requestMethod);
        connection.setUseCaches(false);
        connection.setConnectTimeout(1000 * 5);//5秒
        connection.setReadTimeout(1000 * 60);//1分钟
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Authorization","esmadmin");
        connection.connect();
        Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        if (content != null && !"".equals(content)) {
            writer.write(content);
        }
        writer.flush();
        writer.close();
        InputStream in = null;
        if (connection.getResponseCode() >= 400) {
            in = connection.getErrorStream();
        } else {
            in = connection.getInputStream();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            response.append(tmp);
        }
        if (in != null) {
            in.close();
        }
        bufferedReader.close();
        connection.disconnect();
        return response.toString();
    }
}
