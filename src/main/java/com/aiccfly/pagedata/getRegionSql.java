package com.aiccfly.pagedata;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.*;


public class getRegionSql {
    @Test
    public void getRegionSql () throws Exception {

        BufferedWriter writer = null;
        //获取文件路径。 如果文件不存在，则新建一个
        String path = "E:\\MyWork\\Code\\xzqh\\village.json";
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//test--test
//        String url = "http://www.mca.gov.cn/article/sj/xzqh/2019/201901-06/201904301706.html";
//        String url = "http://www.mca.gov.cn//article/sj/xzqh/2020/202006/202008310601.shtml";
        String url = "http://files2.mca.gov.cn/cws/201307/20130711193420826.htm";

        int count = 0;//记录条数
//获取链接中的数据
        Document doc = Jsoup.connect(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0 Win64 x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xmlq=0.9,image/webp,image/apng,*/*q=0.8,application/signed-exchangev=b3")
                .maxBodySize(0)
                .timeout(100000)
                .get();
//获取表格
        Elements trs = doc.select("tr");

        // 格式化json字符串
//        String jsonData= JSON.toJSONString(要存的数据);
//写入
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8"));
        try {
            for (Element tr : trs ) {
                Elements tds = tr.select("td");

                if (tds.size() > 3) {
                    String regionCode = tds.get(1).text();
                    String regionArea = tds.get(2).text();
                    String parentCode = "";

                    if (validCode(regionCode)) {
    /*                    int leveType = 2;
                        parentCode = regionCode.substring(0,2) + "0000";
                        if (!regionCode.endsWith("00")) {
                            leveType = 3;
                            parentCode = regionCode.substring(0,4) + "00";
                        }
                        if (regionCode.endsWith("0000")) {
                            leveType = 1;
                            parentCode = "000000";
                        }
                        String content = String.format(
                               "insert into region_code(code, name, level, parent_code )" +
                                " values (%s, '%s', %s, %s );" +
                                System.getProperty("line.separator"), regionCode, regionArea, leveType, parentCode);
    */
                        count++;
                        String content = String.format("{\"code\":\"%s\",\"name\":\"%s\"}", regionCode, regionArea);
                        if (count!=1) {//把第一条过滤掉
                            if (count != 2)
                                writer.write(","+content);
                            else writer.write("["+content);//以“ [ ” 开头
                        }
                    }
                }
            }
            writer.write("]");//结尾
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();//关闭输出流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("总数量为：" + (count-1));//打印输出总条数
    }

    private boolean validCode(String regionCode) {
        if(regionCode.isEmpty()) return false;
        else return true;
    }
}
