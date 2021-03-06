package com.cst.whut.aaa.MainFragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.cst.whut.aaa.DataProcess.BubbleAdapter;
import com.cst.whut.aaa.DataProcess.ChattingContext;
import com.cst.whut.aaa.DataProcess.DataProcess;
import com.cst.whut.aaa.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 12421 on 2017/10/22.
 */

public class ChattingFragment extends Fragment implements View.OnClickListener{
    //菜单
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    private List<ChattingContext> chattingList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView chatting_sdbtn;
    private EditText chatting_et;
    BubbleAdapter bubbleAdapter;
    LinearLayoutManager layoutManager;

    SharedPreferences sharedPreferences;
    String username;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);
        drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        imageButton = (ImageButton)view.findViewById(R.id.chat_toolbar_menu);
        imageButton.setOnClickListener(this);
        recyclerView = (RecyclerView)view.findViewById(R.id.chatting_recyclerview);
        chatting_sdbtn = (TextView)view.findViewById(R.id.chatting_send);
        chatting_et = (EditText) view.findViewById(R.id.chatting_et);
        chatting_sdbtn.setOnClickListener(this);

        sharedPreferences = getActivity().getSharedPreferences("user_info",MODE_PRIVATE);
        username = sharedPreferences.getString("user_info","");
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        bubbleAdapter = new BubbleAdapter(chattingList,getActivity());
        recyclerView.setAdapter(bubbleAdapter);
        //软键盘回车键监听
        chatting_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    final String  pmessage = chatting_et.getText().toString();
                    if(pmessage.equals("")){
                        Toast.makeText(getActivity(),"不能没有内容哦^_^",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    chattingList.add(new ChattingContext(pmessage,"people"));
                    bubbleAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(bubbleAdapter.getItemCount()-1);
                    chatting_et.setText("");
                    //线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataProcess dataProcess = new DataProcess();
                            try {
                                Message message = Message.obtain();
                                message.obj = dataProcess.chatting(pmessage,username);
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                return false;
            }
        });
        return view;
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            chattingList.add((ChattingContext)msg.obj);
            bubbleAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(bubbleAdapter.getItemCount()-1);
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chatting_send:
                final String  pmessage = chatting_et.getText().toString();
                if(pmessage.equals("")){
                    Toast.makeText(getActivity(),"不能没有内容哦^_^",Toast.LENGTH_SHORT).show();
                    return;
                }
                chattingList.add(new ChattingContext(pmessage,"people"));
                bubbleAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(bubbleAdapter.getItemCount()-1);
                chatting_et.setText("");
                //线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataProcess dataProcess = new DataProcess();
                        try {
                            Message message = Message.obtain();
                            message.obj = dataProcess.chatting(pmessage,username);
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.chat_toolbar_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                break;
               // ChattingContext chattingContext = new ChattingContext(pmessage,"people");


        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
