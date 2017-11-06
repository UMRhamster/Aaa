package com.cst.whut.aaa.DataProcess;

import java.sql.Blob;

/**
 * Created by 12421 on 2017/10/19.
 */

public class ChattingContext {
    //聊天记录表名
    public static final String TABLE_CR = "ChattingRecords";
    //聊天记录表的各域
    public static final String KEY_ID_CR = "id";;
    public static final String KEY_CALLER_CR = "caller";
    public static final String KEY_CONTENT_CR = "content";
    public static final String KEY_TIME_CR = "time";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_MOTTO = "motto";
    //属性
    public String context;
    public String caller;
    public String time;

    public  ChattingContext(String context,String caller){
        this.context = context;
        this.caller = caller;
    }

    public ChattingContext(){}

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCaller() {
        return caller;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
