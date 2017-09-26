package cn.edu.gdmec.android.classproject20170912.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.android.classproject20170912.R;

/**
 * Created by LT on 2017/9/26.
 */

public class InterPasswordDialog extends Dialog implements View.OnClickListener {
    private TextView mTitleTV;
    private EditText mInterET;
    private Button mOKBTN;
    private Button mCancelBTN;
    private MyCallBack myCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.inter_password_dialog);
        super.onCreate(savedInstanceState);
    }

    public InterPasswordDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View view) {

    }

    private void initView(){
        mTitleTV = findViewById(R.id.tv_interpwd_title);
    }

    public interface MyCallBack{
        void confirm();
        void cancel();
    }

}
