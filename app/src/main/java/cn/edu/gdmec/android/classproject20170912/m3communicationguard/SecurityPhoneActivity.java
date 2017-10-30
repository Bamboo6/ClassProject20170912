package cn.edu.gdmec.android.classproject20170912.m3communicationguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.classproject20170912.R;
import cn.edu.gdmec.android.classproject20170912.m3communicationguard.adapter.BlackContactAdapter;
import cn.edu.gdmec.android.classproject20170912.m3communicationguard.db.dao.BlackNumberDao;
import cn.edu.gdmec.android.classproject20170912.m3communicationguard.entity.BlackContactInfo;

public class SecurityPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    /** 有黑名单时，显示的帧布局 */
    private FrameLayout mHaveBlackNumber;
    /** 没有黑名单时，显示的帧布局 */
    private FrameLayout mNoBlackNumber;
    private BlackNumberDao dao;
    private ListView mListView;
    private int pagenumber = 0;
    //显示条目数
    private int pagesize = 15;
    private int totalNumber;
    private List<BlackContactInfo> pageBlackNumber = new ArrayList<BlackContactInfo>();
    private BlackContactAdapter adapter;

    private void fillData() {
        dao = new BlackNumberDao(SecurityPhoneActivity.this);
        totalNumber = dao.getTotalNumber();
        if (totalNumber == 0) {
            // 数据库中没有黑名单数据
            mHaveBlackNumber.setVisibility(View.GONE);
            mNoBlackNumber.setVisibility(View.VISIBLE);
        } else if (totalNumber > 0) {
            // 数据库中含有黑名单数据
            mHaveBlackNumber.setVisibility(View.VISIBLE);
            mNoBlackNumber.setVisibility(View.GONE);
            pagenumber = 0;
            if (pageBlackNumber.size() > 0) {
                pageBlackNumber.clear();
            }
            pageBlackNumber.addAll(dao.getPageBlackNumber(pagenumber, pagesize));
            if (adapter == null) {
                adapter = new BlackContactAdapter(pageBlackNumber,
                        SecurityPhoneActivity.this);
                adapter.setCallBack(new BlackContactAdapter.BlackConactCallBack() {
                    @Override
                    public void DataSizeChanged() {
                        fillData();
                    }
                });
                mListView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }

    }

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_purple));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("通讯卫士");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mHaveBlackNumber = (FrameLayout) findViewById(R.id.fl_haveblacknumber);
        mNoBlackNumber = (FrameLayout) findViewById(R.id.fl_noblacknumber);
        findViewById(R.id.btn_addblacknumber).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_blacknumbers);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {    //i是列表的滚动状态
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:  // 列表滑动后静止
                        //case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: //手指正拖着列表滑动
                        //case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 列表正自由滑动
                        // 获取最后一个可见条目
                        int lastVisiblePosition = mListView.getLastVisiblePosition();
                        // 如果当前条目是最后一个，则查询更多的数据
                        if (lastVisiblePosition == pageBlackNumber.size() - 1) {
                            pagenumber++;
                            if (pagenumber * pagesize >= totalNumber) {
                                Toast.makeText(SecurityPhoneActivity.this,
                                        "没有更多的数据了", Toast.LENGTH_LONG).show();
                            } else {
                                pageBlackNumber.addAll(dao.getPageBlackNumber(
                                        pagenumber, pagesize));
                                adapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //此代码须在setContentView前，否则报错。当Activity继承自AppCompatActivity时无用
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_security_phone);
        //隐藏标题栏应用名称
        getSupportActionBar().hide();
        initView();
        fillData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_addblacknumber:
                // 跳转至添加黑名单页面
                startActivity(new Intent(this, AddBlackNumberActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dao.getTotalNumber() > 0) {
            mHaveBlackNumber.setVisibility(View.VISIBLE);
            mNoBlackNumber.setVisibility(View.GONE);
        } else {
            mHaveBlackNumber.setVisibility(View.GONE);
            mNoBlackNumber.setVisibility(View.VISIBLE);
        }
        pagenumber = 0;
        pageBlackNumber.clear();
        pageBlackNumber
                .addAll(dao.getPageBlackNumber(pagenumber, pagesize));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
