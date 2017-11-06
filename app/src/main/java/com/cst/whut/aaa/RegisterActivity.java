package com.cst.whut.aaa;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cst.whut.aaa.DataProcess.DataProcess;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private Button register_register;
    private EditText register_admin;
    private EditText register_password;
    private EditText register_cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitView();
        mHandlerThread = new HandlerThread("Signup", 5);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                DataProcess dataProcess = new DataProcess();
                boolean bool = dataProcess.register(register_admin.getText().toString(),register_password.getText().toString());
                if(bool == true){
                    Toast.makeText(RegisterActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"用户名已存在!",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void InitView(){
        register_register = (Button) findViewById(R.id.register_register_btn);
        register_admin = (EditText) findViewById(R.id.register_admin_et);
        register_password = (EditText) findViewById(R.id.register_password_et);
        register_cpassword = (EditText) findViewById(R.id.register_cpassword_et);
        register_register.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_register_btn:
                if(register_password.getText().toString().equals(register_cpassword.getText().toString())){
                    //开启线程
                    mHandler.post(mRunnable);
                }else{
                    Toast.makeText(RegisterActivity.this,"两次密码不一致!",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }
}
