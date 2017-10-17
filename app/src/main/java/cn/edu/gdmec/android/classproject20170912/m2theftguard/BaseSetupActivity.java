package cn.edu.gdmec.android.classproject20170912.m2theftguard;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Bamboo on 2017/10/12.
 */

public abstract class BaseSetupActivity extends AppCompatActivity{
    //手势识别类
    public SharedPreferences sp;
    private GestureDetector mGestureDetector;
    //抽象方法 显示前一屏的activity
    public abstract void showNext();
    //抽象方法 显示后一屏的activity
    public abstract void showPre();

    //用手势识别器去识别触控事件


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
