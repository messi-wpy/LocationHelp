package com.example.messi_lp.qiniuyun.data;

import java.util.List;

public class AddUrl {


    /**
     * name : yy楼
     * url : ["string"]
     */

    private String name;
    private List<String> url;


    public AddUrl(String name,List<String> list){
        this.name=name;
        this.url=list;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
