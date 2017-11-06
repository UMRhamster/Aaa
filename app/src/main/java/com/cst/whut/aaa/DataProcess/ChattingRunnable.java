package com.cst.whut.aaa.DataProcess;

/**
 * Created by 12421 on 2017/10/25.
 */

public class ChattingRunnable implements Runnable {
    private String string;
    public ChattingRunnable(String str){
        string = str;
    }
    @Override
    public void run() {

    }

    public String getString() {
        return string;
    }
}
