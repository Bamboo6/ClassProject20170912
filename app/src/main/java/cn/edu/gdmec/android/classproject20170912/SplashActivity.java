package cn.edu.gdmec.android.classproject20170912;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.gdmec.android.classproject20170912.m1home.utils.MyUtils;

public class SplashActivity extends AppCompatActivity {
    TextView mTvVersion;
    String mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mVersion = MyUtils.getVersion(getApplicationContext());
        mTvVersion = (TextView) findViewById(R.id.tv_splash_version);

    }
}
