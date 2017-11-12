package cn.edu.gdmec.android.classproject20170912.m3communicationguard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lt on 2017/10/30.
 */

public class BlackNumberOpenHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "my_info";
    private static int VERSION = 1;

    private static BlackNumberOpenHelper instance = null;
    public static BlackNumberOpenHelper getInstance(Context context){
        if (instance == null){
            instance = new BlackNumberOpenHelper(context,DB_NAME,null,VERSION);
        }
        return instance;
    }


    public BlackNumberOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.DB_NAME = name;
        this.VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table blacknumber" +
                "(id integer primary key autoincrement," +
                "number varchar(20)," +
                "name varchar(255)," +
                "mode integer,"+
                "type varchar(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
