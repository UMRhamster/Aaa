package com.cst.whut.aaa.MainFragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cst.whut.aaa.ChatActivity;
import com.cst.whut.aaa.DataProcess.BubbleAdapter;
import com.cst.whut.aaa.DataProcess.ChattingContext;
import com.cst.whut.aaa.DataProcess.ChattingRunnable;
import com.cst.whut.aaa.DataProcess.DataProcess;
import com.cst.whut.aaa.MainActivity;
import com.cst.whut.aaa.R;
import com.cst.whut.aaa.RegisterActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 12421 on 2017/10/22.
 */

public class ChattingFragment extends Fragment implements View.OnClickListener{
    private List<ChattingContext> chattingList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView chatting_sdbtn;
    private EditText chatting_et;
    BubbleAdapter bubbleAdapter;
    LinearLayoutManager layoutManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.chatting_recyclerview);
        chatting_sdbtn = (TextView)view.findViewById(R.id.chatting_send);
        chatting_et = (EditText) view.findViewById(R.id.chatting_et);
        chatting_sdbtn.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        bubbleAdapter = new BubbleAdapter(chattingList,getActivity());
        recyclerView.setAdapter(bubbleAdapter);
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
//                for(int i=0;i<chattingList.size();i++){
//                    Log.d("a",chattingList.get(i).getContext()+"   "+chattingList.get(i).getCaller());
//                }
                //线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataProcess dataProcess = new DataProcess();
                        try {
                            Thread.sleep((new Random().nextInt(2000%2001)+0));
                            Message message = Message.obtain();
                            message.obj = dataProcess.chatting(pmessage);
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            default:
                break;
               // ChattingContext chattingContext = new ChattingContext(pmessage,"people");


        }
    }
}
