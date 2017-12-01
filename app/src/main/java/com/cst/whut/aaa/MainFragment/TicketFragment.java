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
 * Created by 12421 on 2017/12/1.
 */

public class TicketFragment extends Fragment{
    private WebView webview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        webview = (WebView)view.findViewById(R.id.ticket);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("http://115.159.197.73:8087/prettyPrice");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.clearCache(true);
        webview.clearFormData();
    }
}
