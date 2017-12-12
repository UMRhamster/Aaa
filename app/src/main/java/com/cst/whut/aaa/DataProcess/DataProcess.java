package com.cst.whut.aaa.DataProcess;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;


/**
 * Created by 12421 on 2017/10/15.
 */

public class DataProcess {

    HttpURLConnection connection =null;

    //登录/////////////////////////////////////////////////////////////////////////
    public boolean login (String loginName,String loginpwd) throws Exception{

        try {
            URL url = new URL("http://115.159.197.73:8087/login");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //计算md5
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(loginpwd.getBytes());
            //
            StringBuffer params = new StringBuffer();
            params.append("loginName").append("=").append(loginName).append("&")
                    .append("loginpwd").append("=").append(new BigInteger(1,md5.digest()).toString(16));  //密码为md5值
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
        }catch (Exception e) {
            return false;
        }
    }
    //注册/////////////////////////////////////////////////////////////////////////////////
    public boolean register (String registerName,String registerPwd) throws Exception{
        boolean bool = false;
        URL url = new URL("http://115.159.197.73:8087/signup");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream out = connection.getOutputStream();
        //计算md5
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(registerPwd.getBytes());
        //
        StringBuffer params = new StringBuffer();
        params.append("signUpName").append("=").append(registerName).append("&")
                .append("signUppwd").append("=").append(new BigInteger(1,md5.digest()).toString(16));
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
    public ChattingContext chatting(String userMessage,String userName) throws Exception{
        URL url = new URL("http://115.159.197.73:8087/index?username="+userName);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
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
