package com.esint.getlocation;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.model.LatLng;
import com.esint.getlocation.bean.Point;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.mapapi.utils.DistanceUtil.getDistance;


/**
 * Created by esint12 on 2017/6/30.
 * arrayList集合会导致oom
 * 解决 方法 ： 定义集合的大小 num = 默认值
 * 当size（） >= num 的时候
 * 把集合以json的方式存入到 sp或者本地
 */

public class GetLocationImpl implements IGetLocation {
    public static final String TAG = "YODA";
    public LocationManager mLocationManager = null;

    private int mSpan = 1000, mDistance = 1;

    List<Point> listBySpan = new ArrayList<>();
    List<Point> listByDitance = new ArrayList<>();
    Point prePoint;


    @Override
    public List<Point> getLocationBySpan(Context context) {
        return listBySpan;
    }

    @Override
    public List<Point> getLocationByDistance(Context context) {
        return listByDitance;
    }

    @Override
    public void setSpan(int span) {
        mSpan = span;
    }

    @Override
    public void setDistance(int distance) {
        mDistance = distance;
    }

        int chuangdidesize;
    @Override
    public void start(Context context, final int mode) {
        mLocationManager = new LocationManager(context);
        mLocationManager.setLocationOption(mLocationManager.getDIYScanSpanOption(mSpan));
        mLocationManager.registerListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation == null) return;

                if (bdLocation.getLatitude() == 0 && bdLocation.getLongitude() == 0) return;

                Point point = new Point();

                point.setLat(bdLocation.getLatitude());
                Log.d(TAG, bdLocation.getLatitude() + "getLatitude");
                point.setLng(bdLocation.getLongitude());
                Log.d(TAG, bdLocation.getLongitude() + "getLongitude");
                if (mode == BY_SPAN) {
                    listBySpan.add(point);
                    Log.d(TAG, listBySpan.size() + "size");

                    // 开始发送
                    Message message= new Message();
                    message.what=10112;
                    chuangdidesize=listBySpan.size();
                    message.obj=chuangdidesize;
                    EventBus.getDefault().post(message);
                    //  发送的结束  主页面接收

                } else {
                    if (prePoint == null) {
                        prePoint = point;
                        listByDitance.add(point);
                    } else {

                        if (matchDistanceCondition(mDistance, prePoint, point)) {

                            listByDitance.add(point);

                        }
                    }
                }
            }

        });
        mLocationManager.start();
    }

    @Override
    public void stop() {

        if (mLocationManager != null) {
            if (listBySpan.size() != 0) {
                Log.d(TAG, "listBySpan.size() = " + listBySpan.size());
            }
            if (listByDitance.size() != 0) {
                Log.d(TAG, "listByDitance.size() = " + listByDitance.size());
            }
            mLocationManager.stop();

        }

    }


    private boolean matchDistanceCondition(int mDistantce, Point point1, Point point2) {
        LatLng lastLatLng = new LatLng(point1.getLat(), point1.getLng());
        LatLng currentLatLng = new LatLng(point2.getLat(), point2.getLng());
//        double distantce = DistanceUtil.getDistance(lastLatLng, currentLatLng);
        double distance = getDistance(lastLatLng, currentLatLng);
        Log.d(TAG,"distance = " +distance);
        return distance > mDistantce;
    }

}
