package com.blazesoft.workshopapp.newtork.url;

import com.google.gson.annotations.Expose;

public class Query {
    @Expose String key;
    @Expose String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public  String getQuery(){
        return key+"="+value;
    }
}
