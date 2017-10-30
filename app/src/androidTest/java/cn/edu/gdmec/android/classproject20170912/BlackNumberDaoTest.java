package cn.edu.gdmec.android.classproject20170912;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Random;

import cn.edu.gdmec.android.classproject20170912.m3communicationguard.db.dao.BlackNumberDao;
import cn.edu.gdmec.android.classproject20170912.m3communicationguard.entity.BlackContactInfo;

/**
 * Created by lt on 2017/10/30.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 19)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlackNumberDaoTest {
    private Context context;
    private BlackNumberDao dao;
    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
        dao = new BlackNumberDao(context);
    }

    @Test
    public void t1Add() throws Exception{

        Random random = new Random(8979);
        for (long i = 1; i < 30; i++) {
            BlackContactInfo info = new BlackContactInfo();
            info.phoneNumber = 13500000000l + i + "";
            info.contactName = "zhangsan" + i;
            info.mode = random.nextInt(3) + 1;
            dao.add(info);
        }
    }

    /**
     * 测试刪除
     *
     * @throws Exception
     */
    @Test
    public void t2Delete() throws Exception {
        BlackNumberDao dao = new BlackNumberDao(context);

        BlackContactInfo info = new BlackContactInfo();
        for (long i = 1; i < 5; i++) {
            info.phoneNumber = 13500000000l + i + "";
            dao.detele(info);
        }
    }

    /**
     * 测试分页查询
     *
     * @throws Exception
     */
    @Test
    public void t3GetPageBlackNumber() throws Exception {
        BlackNumberDao dao = new BlackNumberDao(context);
        List<BlackContactInfo> list = dao.getPageBlackNumber(2, 5);
        for (int i = 0; i < list.size(); i++) {
            Log.i("TestBlackNumberDao", list.get(i).phoneNumber);
        }
    }

    /**
     * 测试根据号码查询黑名单信息
     *
     * @throws Exception
     */
    @Test
    public void t4GetBlackContactMode() throws Exception {
        BlackNumberDao dao = new BlackNumberDao(context);
        int mode = dao.getBlackContactMode(13500000008l + "");
        Log.i("TestBlackNumberDao", mode + "");
    }

    /**
     * 测试数据总条目
     *
     * @throws Exception
     */
    @Test
    public void t5GetTotalNumber() throws Exception {
        BlackNumberDao dao = new BlackNumberDao(context);
        int total = dao.getTotalNumber();
        Log.i("TestBlackNumberDao", "数据总条目：  " + total);
    }

    /**
     * 测试号码是否在数据库中
     *
     * @throws Exception
     */
    @Test
    public void t6IsNumberExist() throws Exception {
        BlackNumberDao dao = new BlackNumberDao(context);
        boolean isExist = dao.IsNumberExist(13500000008l + "");
        if (isExist) {
            Log.i("TestBlackNumberDao", "该号码在数据库中");
        } else {
            Log.i("TestBlackNumberDao", "该号码不在数据库中");
        }
    }
}