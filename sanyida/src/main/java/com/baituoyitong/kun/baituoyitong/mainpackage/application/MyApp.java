package com.baituoyitong.kun.baituoyitong.mainpackage.application;

import android.app.Application;
import android.content.Context;

import com.baituoyitong.kun.baituoyitong.mainpackage.postpicture.BoxingFrescoLoader;
import com.baituoyitong.kun.baituoyitong.mainpackage.postpicture.BoxingUcrop;
import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobInstallation;

/**
 *
 *
 * Created by ${kun} on 2017/4/11.
 */

public  class MyApp extends Application {
    private Context mContext;
    //上传头像和切割头像的初始化的操作
    @Override
    public void onCreate() {
        super.onCreate();
        //选择头像 切割的设置
        IBoxingMediaLoader loader = new BoxingFrescoLoader(this);
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());

        //初始化Bmob的设置  配置appid和其他需要初始化的操作
        //  12532693200e1c7a2ff7169f934feb73
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("12532693200e1c7a2ff7169f934feb73")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
        //开启统计服务
       // Bmob.initialize(mContext,"12532693200e1c7a2ff7169f934feb73","Bmob");

        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);


        //初始化二维码
        ZXingLibrary.initDisplayOpinion(this);

        //初始化 内存泄漏
       /* if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
         LeakCanary.install(this);*/
        // Normal app init code...
    }




}
