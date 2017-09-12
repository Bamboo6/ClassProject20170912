package cn.edu.gdmec.android.classproject20170912.m1home.utils;

import android.app.Activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;

import cn.edu.gdmec.android.classproject20170912.m1home.entity.VersionEntity;

/**
 * Created by dream on 2017/9/12.
 */

public class VersionUpdateUntils {
    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;

    public VersionUpdateUntils(String mVersion, Activity context) {
        this.mVersion = mVersion;
        this.context = context;
    }

    public void getCloudVersion(){
    try {
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
        HttpGet httpGet = new HttpGet("");
        HttpResponse excute = httpClient.execute(httpGet);
        if (excute.getStatusLine().getStatusCode()==200){
            HttpEntity entity
        }
    }catch (IOException e){

    }
    }
}
