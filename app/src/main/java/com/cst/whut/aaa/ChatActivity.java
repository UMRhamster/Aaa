package com.cst.whut.aaa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cst.whut.aaa.MainFragment.ChattingFragment;
import com.cst.whut.aaa.MainFragment.FoodFragment;
import com.cst.whut.aaa.MainFragment.MapFragment;
import com.cst.whut.aaa.MainFragment.TicketFragment;


public class ChatActivity extends AppCompatActivity {
    //用户信息处理
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    //
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //侧边栏菜单 菜单栏
    private ChattingFragment chattingFragment;
    private MapFragment mapFragment;
    private FoodFragment foodFragment;
    private TicketFragment ticketFragment;
    //侧边栏菜单 头部
    private TextView drawer_id;
    //连续返回键退出程序
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InitView();
        ColorStateList csl = (ColorStateList)getResources().getColorStateList(R.color.naviview_itembg);
        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                switch (item.getItemId()){
                    case R.id.navi_message:
                        if(chattingFragment == null){
                            chattingFragment = new ChattingFragment();
                        }
                        transaction.replace(R.id.contentLayout,chattingFragment);
                        transaction.commit();
                        item.setChecked(true);
                        break;
                    case R.id.navi_map:
                        if(mapFragment == null){
                            mapFragment = new MapFragment();
                        }
                        transaction.replace(R.id.contentLayout,mapFragment);
                        transaction.commit();
                        item.setChecked(true);
                        break;
                    case R.id.navi_food:
                        if(foodFragment == null){
                            foodFragment = new FoodFragment();
                        }
                        transaction.replace(R.id.contentLayout,foodFragment);
                        transaction.commit();
                        Toast.makeText(ChatActivity.this,"测试碎片~",Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        break;
                    case R.id.navi_ticket:
                        if(ticketFragment == null){
                            ticketFragment = new TicketFragment();
                        }
                        transaction.replace(R.id.contentLayout,ticketFragment);
                        transaction.commit();
                        item.setChecked(true);
                        break;
                    case R.id.navi_logout:
                        editor.clear();
                        editor.apply();
                        item.setChecked(true);
                        startActivity(new Intent(ChatActivity.this,MainActivity.class));
                        finish();
                        break;
                    default:
                        item.setChecked(false);
                        item.setCheckable(false);
                }
                return true;
            }
        });
        setDefaultFragment();
    }

    public void InitView(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.naviview);
        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        View headlayout = navigationView.getHeaderView(0);//header位于0号元素
        drawer_id = (TextView)headlayout.findViewById(R.id.drawer_id);
        drawer_id.setText("ID:"+sharedPreferences.getString("admin","XXXXXX"));
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ChatActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ChatActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ChatActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ChatActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ChatActivity","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ChatActivity","onRestart");
    }

    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        chattingFragment = new ChattingFragment();
        transaction.replace(R.id.contentLayout,chattingFragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            if (keyCode == KeyEvent.KEYCODE_BACK){
                //监听到返回按钮点击事件
                if(mapFragment!=null&&mapFragment.mapSearch_lvStatus()){
                    return false;
                }else if(System.currentTimeMillis()-exitTime>2000){
                    Toast.makeText(ChatActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }else{
                    finish();
                    System.exit(0);
                }
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
