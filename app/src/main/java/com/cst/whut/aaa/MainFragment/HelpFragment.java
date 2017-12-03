package com.cst.whut.aaa.MainFragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cst.whut.aaa.R;

/**
 * Created by 12421 on 2017/12/3.
 */

public class HelpFragment extends Fragment implements View.OnClickListener{
    private TextView help_chat;
    private TextView help_chattx;
    private TextView help_map;
    private TextView help_maptx;
    private TextView help_food;
    private TextView help_foodtx;
    private TextView help_hotplace;
    private TextView help_hotplacetx;
    private TextView help_ticket;
    private TextView help_tickettx;
    //
    boolean isClicked_chat;
    boolean isClicked_map;
    boolean isClicked_food;
    boolean isClicked_hotplace;
    boolean isClicked_ticket;
    //toolbar菜单
    private ImageButton imageButton;
    private DrawerLayout drawerLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        help_chat = (TextView)view.findViewById(R.id.help_chat);
        help_chattx = (TextView)view.findViewById(R.id.help_chattx);
        help_map = (TextView)view.findViewById(R.id.help_map);
        help_maptx = (TextView)view.findViewById(R.id.help_maptx);
        help_food = (TextView)view.findViewById(R.id.help_food);
        help_foodtx = (TextView)view.findViewById(R.id.help_foodtx);
        help_hotplace = (TextView)view.findViewById(R.id.help_hotplace);
        help_hotplacetx = (TextView)view.findViewById(R.id.help_hotplacetx) ;
        help_ticket = (TextView)view.findViewById(R.id.help_ticket);
        help_tickettx = (TextView)view.findViewById(R.id.help_tickettx);
        drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        imageButton = (ImageButton)view.findViewById(R.id.help_toolbar_menu);

        isClicked_chat = false;
        isClicked_food = false;
        isClicked_hotplace = false;
        isClicked_map = false;
        isClicked_ticket = false;
        help_chat.setOnClickListener(this);
        help_map.setOnClickListener(this);
        help_food.setOnClickListener(this);
        help_hotplace.setOnClickListener(this);
        help_ticket.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.help_chat:
                if(!isClicked_chat){
                    help_chat.setBackgroundResource(R.drawable.help_txbg_pressed);
                    help_chat.setTextColor(Color.parseColor("#ffffff"));
                    help_chattx.setVisibility(View.VISIBLE);
                }else{
                    help_chat.setBackgroundResource(R.drawable.help_txbg_normal);
                    help_chat.setTextColor(Color.parseColor("#81CE47"));
                    help_chattx.setVisibility(View.GONE);
                }
                isClicked_chat = !isClicked_chat;
                break;
            case R.id.help_map:
                if(!isClicked_map){
                    help_map.setBackgroundResource(R.drawable.help_txbg_pressed);
                    help_map.setTextColor(Color.parseColor("#ffffff"));
                    help_maptx.setVisibility(View.VISIBLE);
                }else{
                    help_map.setBackgroundResource(R.drawable.help_txbg_normal);
                    help_map.setTextColor(Color.parseColor("#81CE47"));
                    help_maptx.setVisibility(View.GONE);
                }
                isClicked_map = !isClicked_map;
                break;
            case R.id.help_food:
                if(!isClicked_food){
                    help_food.setBackgroundResource(R.drawable.help_txbg_pressed);
                    help_food.setTextColor(Color.parseColor("#ffffff"));
                    help_foodtx.setVisibility(View.VISIBLE);
                }else{
                    help_food.setBackgroundResource(R.drawable.help_txbg_normal);
                    help_food.setTextColor(Color.parseColor("#81CE47"));
                    help_foodtx.setVisibility(View.GONE);
                }
                isClicked_food = !isClicked_food;
                break;
            case R.id.help_hotplace:
                if(!isClicked_hotplace){
                    help_hotplace.setBackgroundResource(R.drawable.help_txbg_pressed);
                    help_hotplace.setTextColor(Color.parseColor("#ffffff"));
                    help_hotplacetx.setVisibility(View.VISIBLE);
                }else {
                    help_hotplace.setBackgroundResource(R.drawable.help_txbg_normal);
                    help_hotplace.setTextColor(Color.parseColor("#81CE47"));
                    help_hotplacetx.setVisibility(View.GONE);
                }
                isClicked_hotplace = !isClicked_hotplace;
                break;
            case R.id.help_ticket:
                if(!isClicked_ticket){
                    help_ticket.setBackgroundResource(R.drawable.help_txbg_pressed);
                    help_ticket.setTextColor(Color.parseColor("#ffffff"));
                    help_tickettx.setVisibility(View.VISIBLE);
                }else{
                    help_ticket.setBackgroundResource(R.drawable.help_txbg_normal);
                    help_ticket.setTextColor(Color.parseColor("#81CE47"));
                    help_tickettx.setVisibility(View.GONE);
                }
                isClicked_ticket = !isClicked_ticket;
                break;
            case R.id.help_toolbar_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }
}
