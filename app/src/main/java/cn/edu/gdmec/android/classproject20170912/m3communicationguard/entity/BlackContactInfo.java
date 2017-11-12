package cn.edu.gdmec.android.classproject20170912.m3communicationguard.entity;

/**
 * Created by lt on 2017/10/30.
 */

public class BlackContactInfo {
    /*黑名单号码*/
    public String phoneNumber;
    /*黑名单联系人名称*/
    public String contactName;

    public String type;

    /*黑名单拦截模式
    1电话拦截
    2短信拦截
    3电话短信都拦截
     */
    public int mode;

    public String getModeString(int mode){
        switch (mode){
            case 1:
                return "电话拦截";
            case 2:
                return "短信拦截";
            case 3:
                return "电话、短信拦截";
        }
        return "";
    }
}
