package cn.edu.gdmec.android.classproject20170912.m4appmanager.utils;

import android.content.Context;

/**
 * Created by lt on 2017/11/6.
 */

public class DensityUtil {
    /*dip转换像素px*/
    public static int dip2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) dpValue;
    }

    /*像素px转换为dip*/
    public static int px2dip(Context context, float pxValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) pxValue;
    }
}
