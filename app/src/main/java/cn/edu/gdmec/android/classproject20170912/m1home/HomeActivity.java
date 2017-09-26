package cn.edu.gdmec.android.classproject20170912.m1home;

import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.edu.gdmec.android.classproject20170912.R;
import cn.edu.gdmec.android.classproject20170912.m1home.adapter.HomeAdapter;
import cn.edu.gdmec.android.classproject20170912.m2theftguard.dialog.SetupPasswordDialog;

public class HomeActivity extends AppCompatActivity {
    private GridView gv_home;
    private long mExitTime;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        mSharedPreferences = getSharedPreferences("config",MODE_APPEND);
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(!isSetupPassword()){
                            //打开设置密码对话框
                            showSetupPwdDialog();
                        }else {
                            //打开输入密码对话框
                        }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis()-mExitTime)<2000){
                System.exit(0);
            }else{
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isSetupPassword(){
        String password = mSharedPreferences.getString("PhoneAntiThreftPWD",null);
        if(TextUtils.isEmpty(password)){
            return false;
        }else {
            return true;
        }
    }

    private void showSetupPwdDialog(){
        final SetupPasswordDialog setupPasswordDialog = new SetupPasswordDialog(HomeActivity.this);
        setupPasswordDialog.
    }

}
