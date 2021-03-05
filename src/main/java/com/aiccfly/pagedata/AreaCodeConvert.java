package com.aiccfly.pagedata;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
//import java.lang.String;

public class AreaCodeConvert {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        // 到政府网站复制列表，保存到txt中，我找到的2020年的
        // http://www.mca.gov.cn/article/sj/xzqh/2020/2020/202003301019.html
        File f = new File("D:\\areaCode.txt");
        // 注意转码
        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f), "GBK"));
        String l = null;
        Map map = Maps.newLinkedHashMap();
        while ((l = r.readLine()) != null) {
            boolean isProvince = false, isCity = false, isRegion = false;
            // 左代码，右名称
            String[] split = StringUtils.split(l);
            String code = split[0];
            String name = split[1];
            // 代码规律：省级的都是xx0000,市级都是xxyy00
            isProvince = "0000".equals(StringUtils.substring(code, 2, 6));
            if (!isProvince) {
                isCity = "00".equals(StringUtils.substring(code, 4, 6));
            }
            if (!isCity) {
                isRegion = true;
            }
            if (isProvince) {
                // 存省级
                Map pMap = Maps.newLinkedHashMap();
                map.put(StringUtils.substring(code, 0, 2), pMap);
                pMap.put("code", code);
                pMap.put("name", name);
            } else if (isCity) {
                // 市级存到升级的children中
                Map pMap = (Map) map.get(StringUtils.substring(code, 0, 2));
                Map cMap = (Map) pMap.get("children");
                if (cMap == null) {
                    cMap = Maps.newLinkedHashMap();
                    pMap.put("children", cMap);
                }
                Map ccMap = Maps.newLinkedHashMap();
                cMap.put(StringUtils.substring(code, 0, 4), ccMap);
                ccMap.put("code", code);
                ccMap.put("pCode", StringUtils.substring(code, 0, 2) + "0000");
                ccMap.put("name", name);
            } else if (isRegion) {
                // 区级存到市级的children中
                Map pMap = (Map) map.get(StringUtils.substring(code, 0, 2));
                Map cMap = (Map) pMap.get("children");
                // 直辖市和特别行政区的处理
                if (cMap == null) {
                    cMap = Maps.newLinkedHashMap();
                    Map ccMap = Maps.newLinkedHashMap();
                    ccMap.put("code", StringUtils.substring(String.valueOf(pMap.get("code")), 0, 3) + "100");
                    ccMap.put("pCode", pMap.get("code"));
                    ccMap.put("name", pMap.get("name"));
                    cMap.put(String.valueOf(ccMap.get("code")), ccMap);
                    pMap.put("children", cMap);
                }
                Map ccMap = (Map)cMap.get(StringUtils.substring(code, 0, 4));
                // 坑爹的情况是有些是县级市，有些没有对应市的县，不过根据列表发现只是找到上一个就好了
                if (ccMap == null) {
                    List cList = new ArrayList(cMap.entrySet());
                    ccMap = (Map)cList.get(cList.size()-1);
                    //.getValue();
                }
                List rList = (List) ccMap.get("children");
                if (rList == null) {
                    rList = Lists.newArrayList();
                    ccMap.put("children", rList);
                }
                Map rMap = Maps.newLinkedHashMap();
                rMap.put("code", code);
                rMap.put("pCode", StringUtils.substring(code, 0, 4) + "00");
                rMap.put("name", name);
                rList.add(rMap);
            }
        }
        // Map不好看，转成List格式的
        List result = new ArrayList((Collection)map.values());
//        for (Object o : result) {
//        }
        for(Object o : result) {
            Map m = (Map)o;
            Map c = (Map)m.get("children");
            if (!Objects.isNull(c) && !c.isEmpty()) {// 台湾、香港、澳门就一个地方
                m.put("children", new ArrayList((Collection
                        ) c.values()));
            }
        }
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.setSerializationInclusion(Include.NON_DEFAULT);
        jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        System.out.println(jsonMapper.writeValueAsString(result));
        // 关闭，也懒得写try...catch了
        r.close();
    }
}
