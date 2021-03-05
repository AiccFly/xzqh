package com.aiccfly.pagedata;

//定义Area类进行接收
public class Area {
    private String code ;
    //行政编码
    private String name;
    //名称
    private int level;
    //行政级别 0:省/直辖市 1:地级市 2:县级市
    private String parentCode;
    //上一级的行政区划代码

    public Area() {
        super();
    }

    public Area(String code, String name, int level, String parentCode) {
        super();
        this.code = code;
        this.name = name;
        this.level = level;
        this.parentCode = parentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String outPutString(){
        return this.getCode()+" "+this.getName()+" "+this.getLevel()+" " +this.getParentCode();
    }
}



