package com.baituoyitong.kun.baituoyitong.mainpackage.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * com.baituoyitong.kun.baituoyitong.activity.service
 * 这个服务主要是在app的运行的过程中来检查 蓝牙的状态  是否是连接的和蓝牙的打开状态
 *
 * Created by ${kun} on 2017/4/19.
 */

public class CheckBluetoothService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    //我的思路就是 里面写一些方法  我们是使用timer来设置一个死循环来 进行 连接的检测



}
