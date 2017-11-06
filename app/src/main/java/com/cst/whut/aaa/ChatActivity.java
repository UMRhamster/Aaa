package com.cst.whut.aaa;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.cst.whut.aaa.MainFragment.ChattingFragment;
import com.cst.whut.aaa.MainFragment.TestFragment;
import com.cst.whut.aaa.MainFragment.MapFragment;


public class ChatActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ChattingFragment chattingFragment;
    private MapFragment mapFragment;
    private TestFragment testFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.naviview);

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
                        break;
                    case R.id.navi_map:
                        if(mapFragment == null){
                            mapFragment = new MapFragment();
                        }
                        transaction.replace(R.id.contentLayout,mapFragment);
                        transaction.commit();
                        break;
                    case R.id.navi_food:
                        if(testFragment == null){
                            testFragment = new TestFragment();
                        }
                        transaction.replace(R.id.contentLayout,testFragment);
                        transaction.commit();
                        Toast.makeText(ChatActivity.this,"测试碎片~",Toast.LENGTH_SHORT).show();
                        break;
                }
                item.setChecked(true);
                return true;
            }
        });
        setDefaultFragment();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
