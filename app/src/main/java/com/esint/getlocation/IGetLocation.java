package com.esint.getlocation;

import android.content.Context;

import com.esint.getlocation.bean.Point;

import java.util.List;

/**
 * Created by esint12 on 2017/6/30.
 */

public interface IGetLocation {

    int MAX_NUM_IN_MEM = 30;
    int BY_SPAN = 0;
    int BY_DISTANCE = 1;

    /**
     * 以时间间隔获取经纬度的点调用此方法
     *
     * @param context
     * @param
     * @return
     */
    List<Point> getLocationBySpan(Context context);

    /**
     * 以两个点的距离获取经纬度的点的时候调用此方法
     *
     * @param context
     * @param
     * @return
     */
    List<Point> getLocationByDistance(Context context);


    void setSpan(int span);

    void setDistance(int distance);

    void start(Context context, int mode);

    void stop();

}
