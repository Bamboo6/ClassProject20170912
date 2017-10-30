package cn.edu.gdmec.android.classproject20170912.m1home;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
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
import cn.edu.gdmec.android.classproject20170912.m2theftguard.LostFindActivity;
import cn.edu.gdmec.android.classproject20170912.m2theftguard.dialog.InterPasswordDialog;
import cn.edu.gdmec.android.classproject20170912.m2theftguard.dialog.SetupPasswordDialog;
import cn.edu.gdmec.android.classproject20170912.m2theftguard.receiver.MyDeviceAdminReceiver;
import cn.edu.gdmec.android.classproject20170912.m2theftguard.utils.MD5Utils;
import cn.edu.gdmec.android.classproject20170912.m3communicationguard.SecurityPhoneActivity;

public class HomeActivity extends AppCompatActivity {
    private GridView gv_home;
    private long mExitTime;
//    存储手机防盗密码的sp
    private SharedPreferences mSharedPreferences;
    /*设备管理员*/
    private DevicePolicyManager policyManager;
    /*申请权限*/
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        mSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(isSetupPassword()){
                            //打开输入密码对话框
                            showInterPswdDialog();
                        }else {
                            //打开设置密码对话框
                            showSetupPswdDialog();
                        }
                        break;
                    case 1://通讯卫士
                        startActivity(SecurityPhoneActivity.class);
                        break;
                }
            }
        });
        //1、获取设备管理员
        policyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        //2、申请权限，MyDeviceAdminReciever继承来自DeviceAdminReceiver
        componentName = new ComponentName(this, MyDeviceAdminReceiver.class);
        //3、判断，如果没有权限则申请权限
        boolean active = policyManager.isAdminActive(componentName);
        if (!active){
            //没有管理员权限则获取管理员权限
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"获取管理员权限，用于远程锁屏和清除数据");
            startActivity(intent);
        }
    }

    public void startActivity(Class<?> cls){
        Intent intent = new Intent(HomeActivity.this,cls);
        startActivity(intent);
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


    private void showSetupPswdDialog(){
        final SetupPasswordDialog setupPasswordDialog = new SetupPasswordDialog(HomeActivity.this);
        setupPasswordDialog.setCallBack(new SetupPasswordDialog.MyCallBack(){
            @Override
            public void ok() {
                String firstPwsd = setupPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setupPasswordDialog.mAffirmET.getText().toString().trim();
                if(!TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd)){
                    if (firstPwsd.equals(affirmPwsd)){
                        //两次密码一致，储存密码
                        savePswd(affirmPwsd);
                        setupPasswordDialog.dismiss();
                        //显示输入密码对话框
                        showInterPswdDialog();
                    }else {
                        Toast.makeText(HomeActivity.this,"两次密码不一致！", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(HomeActivity.this,"密码不能为空！", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void cancel() {
                setupPasswordDialog.dismiss();
            }
        });
        setupPasswordDialog.setCancelable(true);
        setupPasswordDialog.show();
    }

/*
    弹出输入密码对话框，本方法需要完成“手机防盗模块”之后才能启用
*/
private void showInterPswdDialog(){
    final String password = getPassword();
    final InterPasswordDialog mInPswdDialog = new InterPasswordDialog(HomeActivity.this);
    mInPswdDialog.setCallBack(new InterPasswordDialog.MyCallBack(){
        @Override
        public void confirm() {
            if(TextUtils.isEmpty(mInPswdDialog.getPassword())){
                Toast.makeText(HomeActivity.this,"密码不能为空", Toast.LENGTH_LONG).show();
            }else if (password.equals(MD5Utils.encode(mInPswdDialog.getPassword()))){
                mInPswdDialog.dismiss();
                startActivity(LostFindActivity.class);
                Toast.makeText(HomeActivity.this,"允许进入手机防盗模块", Toast.LENGTH_LONG).show();
            }else{
                mInPswdDialog.dismiss();
                Toast.makeText(HomeActivity.this,"密码有误请重新输入", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void cancel() {
            mInPswdDialog.dismiss();
        }
    });
    mInPswdDialog.setCancelable(true);
    mInPswdDialog.show();
}

    /*
    保存密码，本方法需要完成“手机防盗模块”之后才能启用
    */
    private void savePswd(String affirmPwsd){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        //为防止用户隐私被泄露，因此需要加密密码
        edit.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwsd));
        edit.commit();
    }

    /*
    获取密码
    */
    private String getPassword(){
        String password = mSharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password)){
            return "";
        }
        return password;
    }


    //判断用户是否设置过手机防盗密码
    private boolean isSetupPassword(){
        String password = mSharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }


}
