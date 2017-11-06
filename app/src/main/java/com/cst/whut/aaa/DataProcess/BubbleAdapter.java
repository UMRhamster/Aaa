package com.cst.whut.aaa.DataProcess;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.cst.whut.aaa.MyUI.BubbleImageView;
import com.cst.whut.aaa.MyUI.BubbleTextVew;
import com.cst.whut.aaa.MyUI.SquareImageView;
import com.cst.whut.aaa.R;

/**
 * Created by 12421 on 2017/10/19.
 */

public class BubbleAdapter extends RecyclerView.Adapter<BubbleAdapter.ViewHolder> {
    private List<ChattingContext> mChattinglist;
    private Context context;

    public BubbleAdapter(List<ChattingContext> chattinglist,Context context){
        mChattinglist = chattinglist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listcell_chatting,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChattingContext chattingContext = mChattinglist.get(position);
        if(chattingContext.getCaller().equals("robot")){
            holder.squareImageView_r.setImageResource(R.drawable.iconrobot);
            holder.bubbleTextVew_r.setText(chattingContext.getContext());
            holder.squareImageView_r.setVisibility(View.VISIBLE);
            holder.bubbleTextVew_r.setVisibility(View.VISIBLE);
            holder.squareImageView_p.setImageResource(R.drawable.iconpeoople);
            holder.bubbleTextVew_p.setText(" ");
            holder.squareImageView_p.setVisibility(View.GONE);
            holder.bubbleTextVew_p.setVisibility(View.GONE);
        }else{
            holder.squareImageView_p.setImageResource(R.drawable.iconpeoople);
            holder.bubbleTextVew_p.setText(chattingContext.getContext());
            holder.squareImageView_p.setVisibility(View.VISIBLE);
            holder.bubbleTextVew_p.setVisibility(View.VISIBLE);
            holder.squareImageView_r.setImageResource(R.drawable.iconrobot);
            holder.bubbleTextVew_r.setText(" ");
            holder.squareImageView_r.setVisibility(View.GONE);
            holder.bubbleTextVew_r.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChattinglist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        SquareImageView squareImageView_p;
        SquareImageView squareImageView_r;
        BubbleTextVew bubbleTextVew_p;
        BubbleTextVew bubbleTextVew_r;

        public ViewHolder(View itemView) {
            super(itemView);
            squareImageView_p = (SquareImageView)itemView.findViewById(R.id.admin_image);
            squareImageView_r = (SquareImageView)itemView.findViewById(R.id.robot_image);
            bubbleTextVew_p = (BubbleTextVew)itemView.findViewById(R.id.admin_bubbletv);
            bubbleTextVew_r = (BubbleTextVew)itemView.findViewById(R.id.robot_bubbletv);
        }
    }
}

