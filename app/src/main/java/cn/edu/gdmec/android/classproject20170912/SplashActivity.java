package cn.edu.gdmec.android.classproject20170912;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.gdmec.android.classproject20170912.m1home.utils.MyUtils;
import cn.edu.gdmec.android.classproject20170912.m1home.utils.VersionUpdateUntils;

public class SplashActivity extends AppCompatActivity {
    private TextView mTvVersion;
    private String mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mVersion = MyUtils.getVersion(getApplicationContext());
        mTvVersion = (TextView) findViewById(R.id.tv_splash_version);
        mTvVersion.setText("版本号："+mVersion);
        final VersionUpdateUntils versionUpdateUntils = new VersionUpdateUntils(mVersion,SplashActivity.this);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        versionUpdateUntils.getCloudVersion();
                    }
                }.start();
    }
}
