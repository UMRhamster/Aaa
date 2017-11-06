package com.cst.whut.aaa.DataProcess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 12421 on 2017/10/30.
 */

public class DBHelper extends SQLiteOpenHelper {
    //数据库名字
    private static final String DATABASE_NAME="chatrecord.db";
    //数据库版本号
    private static final int DATABASE_VERSION=1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_CHATRECORD="CREATE TABLE "
                +ChattingContext.TABLE_CR+"("
                +ChattingContext.KEY_ID_CR+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ChattingContext.KEY_CALLER_CR+" TEXT,"
                +ChattingContext.KEY_CONTENT_CR+" TEXT,"
                +ChattingContext.KEY_TIME_CR+" TEXT,"
                +ChattingContext.KEY_IMAGE+" BLOB,"
                +ChattingContext.KEY_MOTTO+" TEXT)";
        db.execSQL(CREATE_TABLE_CHATRECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ChattingContext.TABLE_CR);
        //再此创建表
        onCreate(db);
    }
}
