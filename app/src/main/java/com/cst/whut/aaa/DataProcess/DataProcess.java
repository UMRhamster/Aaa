package com.cst.whut.aaa.DataProcess;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by 12421 on 2017/10/15.
 */

public class DataProcess {

    HttpURLConnection connection =null;

    //登录/////////////////////////////////////////////////////////////////////////
    public boolean login (String loginName,String loginpwd) throws Exception{

        URL url = new URL("http://www.cnpromise.cn:8087/login");
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        //
        StringBuffer params = new StringBuffer();
        params.append("loginName").append("=").append(loginName).append("&")
                .append("loginpwd").append("=").append(loginpwd);
        byte[] bytes = params.toString().getBytes("UTF-8");
        //
        //
        OutputStream out = connection.getOutputStream();
        out.write(bytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null){
            response.append(line);
        }
        if(connection!=null){
            connection.disconnect();
        }
        return parseJson(response.toString());
    }
    //注册/////////////////////////////////////////////////////////////////////////////////
    public boolean register (String registerName,String registerPwd) throws Exception{
        boolean bool = false;
        URL url = new URL("http://www.cnpromise.cn:8087/signup");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream out = connection.getOutputStream();
        //
        StringBuffer params = new StringBuffer();
        params.append("signUpName").append("=").append(registerName).append("&")
                .append("signUppwd").append("=").append(registerPwd);
        byte[] bytes = params.toString().getBytes("UTF-8");
        //
        out.write(bytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null){
            response.append(line);
        }
        if (connection != null) {
            connection.disconnect();
        }
        return parseJson(response.toString());
    }
    //聊天/////////////////////////////////////////////////////////////////////////////
    public ChattingContext chatting(String userMessage) throws Exception{
        URL url = new URL("http://www.cnpromise.cn:8087/hello");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream out = connection.getOutputStream();
        //
        StringBuffer params = new StringBuffer();
        params.append("userMessage").append("=").append(userMessage);
        byte[] bytes = params.toString().getBytes("UTF-8");
        //
        out.write(bytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null){
            response.append(line);
        }
        JSONObject jsonObject = new JSONObject(response.toString());
        String text = jsonObject.getString("robot_text");

        return new ChattingContext(text,"robot");
    }
//    public void chat(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://www.cnpromise.cn:8087/hello/")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .client(new OkHttpClient())
//                .build();
//    }
//    public interface ChatService{
//        //@HTTP(method = "POST",path = "";
//    }
    //解析JSON/////////////////////////////////////////////////////
    private Boolean parseJson(String jsonData)throws Exception{
        JSONObject jsonObject = new JSONObject(jsonData);
        Boolean permission = jsonObject.getBoolean("permission");
        return permission;
    }

}
