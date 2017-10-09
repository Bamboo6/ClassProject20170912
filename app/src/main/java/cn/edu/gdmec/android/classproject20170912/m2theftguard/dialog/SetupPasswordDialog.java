package cn.edu.gdmec.android.classproject20170912.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.android.classproject20170912.R;

/**
 * Created by LT on 2017/9/26.
 */

public class SetupPasswordDialog extends Dialog implements View.OnClickListener {
    private TextView mTitleTv;
    public EditText mFirstPWDET;
    public EditText mAffirmET;
    private MyCallBack myCallBack;

    public SetupPasswordDialog(@NonNull Context context) {
        super(context,R.style.dialog_custom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.setup_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                myCallBack.ok();
                break;
            case R.id.btn_cancle:
                myCallBack.cancel();
                break;

        }
    }

    public void initView(){
        mTitleTv = findViewById(R.id.tv_setuppwd_title);
        mFirstPWDET = findViewById(R.id.et_firstpwd);
        mAffirmET = findViewById(R.id.et_affirm_password);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancle).setOnClickListener(this);
        /*Button btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        Button btnCancle = findViewById(R.id.btn_cancle);
        btnCancle.setOnClickListener(this);*/
    }

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            mTitleTv.setText(title);
        }
    }

    public void setCallBack(MyCallBack myCallBack){
        this.myCallBack = myCallBack;
    }

    public interface MyCallBack{
        void ok();
        void cancel();
    }
}
