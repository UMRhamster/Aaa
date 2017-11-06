package com.cst.whut.aaa.MainFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cst.whut.aaa.R;

/**
 * Created by 12421 on 2017/10/29.
 */

public class TestFragment extends Fragment {
    private WebView webview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        webview = (WebView)view.findViewById(R.id.map);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("http://www.cnpromise.cn:8087/myPosition");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.clearCache(true);
        webview.clearFormData();
    }
}
