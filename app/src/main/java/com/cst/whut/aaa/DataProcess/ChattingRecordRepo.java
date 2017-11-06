package com.cst.whut.aaa.DataProcess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 12421 on 2017/10/30.
 */

public class ChattingRecordRepo {
    private DBHelper dbHelper;

    public ChattingRecordRepo(Context context){
        dbHelper = new DBHelper(context);
    }
    //增加
    public int insert(ChattingContext chattingContext){
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChattingContext.KEY_CALLER_CR,chattingContext.getCaller());
        values.put(ChattingContext.KEY_CONTENT_CR,chattingContext.getContext());
        values.put(ChattingContext.KEY_TIME_CR,chattingContext.getTime());
        //
        long record = db.insert(ChattingContext.TABLE_CR,null,values);
        db.close();
        return (int)record;
    }
    //删除
    public void delete(int chattingRecordID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(ChattingContext.TABLE_CR,ChattingContext.KEY_ID_CR+"=?",new String[]{String.valueOf(chattingRecordID)});
        db.close();
    }
    //修改(个人、机器人信息)
    public void update(ChattingContext chattingContext){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
    }
    //查询
    public ArrayList<ChattingContext> getRecord(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                ChattingContext.KEY_CALLER_CR+","+
                ChattingContext.KEY_CONTENT_CR+","+
                ChattingContext.KEY_TIME_CR + " FROM "+ ChattingContext.TABLE_CR;
        ArrayList<ChattingContext> list = new ArrayList<ChattingContext>();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                ChattingContext chattingContext = new ChattingContext();
                chattingContext.setCaller(cursor.getString(cursor.getColumnIndex(ChattingContext.KEY_CALLER_CR)));    //会话人
                chattingContext.setContext(cursor.getString(cursor.getColumnIndex(ChattingContext.KEY_CONTENT_CR)));  //内容
                chattingContext.setTime(cursor.getString(cursor.getColumnIndex(ChattingContext.KEY_TIME_CR)));        //时间
                list.add(chattingContext);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
