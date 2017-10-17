package cn.edu.gdmec.android.classproject20170912.m2theftguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import cn.edu.gdmec.android.classproject20170912.R;

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

    public void startActivityAndFinishSelf(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            public boolean onFling(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){
                if (Math.abs(velocityX)<200){
                    Toast.makeText(getApplicationContext(),"无效动作，移动太慢",Toast.LENGTH_SHORT).show();
                    return true;
                }
                if ((e2.getRawX() - e1.getRawX())>200){
                    showPre();
                    overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
                    return true;
                }
                if ((e1.getRawX() - e2.getRawX())>200){
                    showNext();
                    overridePendingTransition(R.anim.next_in,R.anim.next_out);
                    return true;
                }
                return super.onFling(e1,e2,velocityX,velocityY);
            }
        });
    }
}