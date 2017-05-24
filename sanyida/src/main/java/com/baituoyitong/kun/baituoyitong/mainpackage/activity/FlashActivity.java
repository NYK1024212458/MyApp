package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.view.CustomTimerView;

import java.io.IOException;
import java.io.InputStream;

public class FlashActivity extends AppCompatActivity {

    private static final String TAG =FlashActivity.class.getSimpleName() ;
    private  Context mContext ;
    private CustomTimerView cv_customtim_view;
    private ImageView iv_gg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        mContext = FlashActivity.this;
        //初始化
        initView();
        //刚开始就会进入倒计时
        cv_customtim_view.start();
        //设置监听
        cv_customtim_view.setCountDownTimerListener(new MyCountDownTimerListener());
        //设置点击事件
        cv_customtim_view.setcountTimerClickListener(new CustomTimerView.countTimerClickListener() {
            @Override
            public void onCountTimerClick() {
                cv_customtim_view.stop();
                Log.d(TAG, "onCountTimerClick: 倒计时点击事件是不是执行了!");
            }
        });
    }
    private void initView() {
        cv_customtim_view = (CustomTimerView) findViewById(R.id.cv_customtim_view);
        iv_gg = (ImageView) findViewById(R.id.iv_gg);
        //=获取资产目录解析图片 展示广告图片
        // 获取资产管理\
        AssetManager assets = mContext.getAssets();
        try {
            InputStream open = assets.open("ic_welcom.jpg");
            //解析为图片
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            //设置
            iv_gg.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MyCountDownTimerListener implements CustomTimerView.CountDownTimerListener {
        @Override
        public void onStartCount() {
            //开始倒计时
            cv_customtim_view.start();
        }

        @Override
        public void onFinishCount() {
            //进行跳转 关闭倒计时
            cv_customtim_view.stop();
            //跳转的操作
            Intent intent = new Intent(FlashActivity.this,LoginActivity.class);
            startActivity(intent);
            //销毁自己
            finish();


        }
    }
}
