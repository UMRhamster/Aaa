package com.cst.whut.aaa.MainFragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.cst.whut.aaa.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 12421 on 2017/12/3.
 */

public class HotPlaceFragment extends Fragment implements View.OnClickListener{
    //菜单
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    private WebView webview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragmet_hotplace, container, false);
        drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        imageButton = (ImageButton)view.findViewById(R.id.hotplace_toolbar_menu);
        imageButton.setOnClickListener(this);
        webview = (WebView)view.findViewById(R.id.hotpalce);
        webview.getSettings().setJavaScriptEnabled(true);
        //webview.getSettings().setUseWideViewPort(true);
        //webview.getSettings().setLoadWithOverviewMode(true);
        //Log.d("fdasdfsdf",webview.getSettings().getUserAgentString());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info",MODE_PRIVATE);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("http://115.159.197.73:8087/hotPlaces?username="+sharedPreferences.getString("admin",""));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.clearCache(true);
        webview.clearFormData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hotplace_toolbar_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }
}
