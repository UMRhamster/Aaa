package com.cst.whut.aaa;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cst.whut.aaa.DataProcess.DataProcess;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean bool_btn; //登录按钮标识
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private TextView register_tv;
    private Button login_btn;
    private EditText login_admin;
    private EditText login_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
        mHandlerThread = new HandlerThread("Login", 5);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        bool_btn = false;
        Log.d("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity","onRestart");
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                DataProcess dataProcess = new DataProcess();
                boolean bool = dataProcess.login(login_admin.getText().toString(),login_pwd.getText().toString());
                if(bool == true){
                    Toast.makeText(MainActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,ChatActivity.class));
                    onStop();
                }else{
                    bool_btn = false;
                    Toast.makeText(MainActivity.this,"登陆失败！",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private void InitView(){
        register_tv = (TextView) findViewById(R.id.login_register_tv);
        login_btn = (Button) findViewById(R.id.login_login_btn);
        login_admin = (EditText) findViewById(R.id.login_admin_et);
        login_pwd = (EditText) findViewById(R.id.login_password_et);
        register_tv.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        bool_btn = false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register_tv:
                Intent intent1 = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent1);
                onStop();
                break;
            case R.id.login_login_btn:
                if(!bool_btn){
                    bool_btn = true;
                    //开启线程
                    mHandler.post(mRunnable);
                }
                break;
            default:
                break;
        }
    }
}
