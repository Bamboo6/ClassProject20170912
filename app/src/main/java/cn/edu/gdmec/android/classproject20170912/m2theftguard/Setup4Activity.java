package cn.edu.gdmec.android.classproject20170912.m2theftguard;

import android.os.Bundle;
import android.widget.RadioButton;

import cn.edu.gdmec.android.classproject20170912.R;

public class Setup4Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_4);
        ((RadioButton) findViewById(R.id.rb_fourth)).setChecked(true);
    }
    @Override
    public void showNext(){
        startActivityAndFinishSelf(LostFindActivity.class);
    }

    @Override
    public void showPre(){
        startActivityAndFinishSelf(Setup3Activity.class);
    }
}
