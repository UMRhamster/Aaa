package com.cst.whut.aaa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    //进度条
    ProgressDialog progressDialog;
    //存储用户密码和自动登录
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图
        InitView();
        //用户登录信息
        boolean auto_login = sharedPreferences.getBoolean("auto_login",false);
        if(auto_login){
            startActivity(new Intent(MainActivity.this,ChatActivity.class));
            finish();
        }
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
//        progressBar.setVisibility(View.GONE);
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
                    //自动登录
                    if(checkBox.isChecked()){
                        editor.putBoolean("auto_login",true);
                        editor.putString("admin",login_admin.getText().toString());
                        editor.putString("password",login_pwd.getText().toString());
                    }else{
                        editor.putString("admin",login_admin.getText().toString());
                    }
                    editor.apply();
                    //
                    Toast.makeText(MainActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,ChatActivity.class));
                    finish();
                }else{
                    bool_btn = false;
                    Toast.makeText(MainActivity.this,"登陆失败！",Toast.LENGTH_SHORT).show();
                }
                progressDialog.cancel();
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
        //登录进度条
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登陆……");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        //存储用户登录信息
        checkBox = (CheckBox)findViewById(R.id.auto_login);
        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
                    if(login_admin.getText().toString().isEmpty() || login_pwd.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this,"请输入账号或密码！",Toast.LENGTH_SHORT).show();
                        bool_btn = false;
                        break;
                    }
                    progressDialog.show();   //登录进度显示
                    //开启线程
                    mHandler.post(mRunnable);
                }
                break;
            default:
                break;
        }
    }
}
