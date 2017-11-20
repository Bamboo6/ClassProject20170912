package cn.edu.gdmec.android.classproject20170912.m5virusscan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by killer on 2017/11/13.
 */

public class AntiVirusDao {
    private static Context context;
    private static String dbname;
    public AntiVirusDao(Context context){
        this.context = context;
        dbname = "/data/data/"+context.getPackageName()+"/files/antivirus.db";

    }
    public String checkVirus(String md5){
        String desc = null;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                dbname,null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select desc from datable where md5=?",
                new String[] { md5 });
        if(cursor.moveToNext()){
            desc = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return desc;
    }
    //获取病毒数据版本
    public String getVirusDbVersion(){
        String dbVersion = null;
        // 打开病毒数据库
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                dbname, null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select major||'.'||minor||'.'||build from version",null);
        if (cursor.moveToNext()) {
            dbVersion = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return dbVersion;
    }

}
