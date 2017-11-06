package cn.edu.gdmec.android.classproject20170912.m4appmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by lt on 2017/11/6.
 */

public class AppInfo {
    /*应用程序包名*/
    public String packageName;
    /*应用程序图标*/
    public Drawable icon;
    /*应用程序名称*/
    public String appName;
    /*应用程序路径*/
    public String apkPath;
    /*应用程序大小*/
    public long appSize;
    /*是否手机存储*/
    public boolean isInRoom;
    /*是否用户应用*/
    public boolean isUserApp;
    /*是否选中，默认都为false*/
    public boolean isSelected = false;

    /*拿到app位置字符串*/
    public String getAppLocation(boolean isInRoom){
        if (isInRoom){
            return "手机内存";
        } else {
            return "外部存储";
        }
    }
}
