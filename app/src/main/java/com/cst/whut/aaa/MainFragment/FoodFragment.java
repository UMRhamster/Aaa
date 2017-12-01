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

public class FoodFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
