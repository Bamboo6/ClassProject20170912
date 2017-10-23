package cn.edu.gdmec.android.classproject20170912.m2theftguard.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

public class GPSLocationService extends Service {
    private LocationManager locationManager;
    private MyListenter myListenter;

    public GPSLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        myListenter = new MyListenter();
        //criteria 查询条件
        //true只返回可用的位置提供者
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//获取准确位置。
        criteria.setCostAllowed(true);//允许产生开销
        String name = locationManager.getBestProvider(criteria,true);
        //权限检查
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(name,0,0,myListenter);
    }

    private class MyListenter implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("accuracy:"+location.getAccuracy()+"\n");
            stringBuilder.append("speed"+location.getSpeed()+"\n");
            stringBuilder.append("Logitude"+location.getLongitude()+"\n");
            stringBuilder.append("Latitude"+location.getLatitude()+"\n");
            String result = stringBuilder.toString();
            SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
            String safenumber = sp.getString("safenumber","");
            //发送GPS坐标的短信
            SmsManager.getDefault().sendTextMessage(safenumber,null,result,null,null);
            stopSelf();
        }
        //当位置提供者，状态发生变化的时候调用的方法
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        //当某个位置提供者，可用的时候调用的方法
        @Override
        public void onProviderEnabled(String provider) {

        }
        //当某个位置提供者，不可用的时候调用的方法
        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(myListenter);
        myListenter = null;
    }
}
