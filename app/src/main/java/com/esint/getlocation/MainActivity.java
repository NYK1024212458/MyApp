package com.esint.getlocation;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.esint.getlocation.bean.Point;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
/*
* 此项目是mvp书写的定位的功能,我们再次添加ble的功能
*   第二: 我们需要的是域名的填写
*
*
*
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "YODA";
    private Button btn_span, btn_close_span, btn_distance, btn_close_distance;
    private TextView show_span, show_distance;
    private GetLocationImpl getLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();

        // 1:创建clint的实列
        getLocation = new GetLocationImpl();


        // 注册eventbus 的
        EventBus.getDefault().register(this);

    }

    private void initView() {
        btn_span = (Button) findViewById(R.id.btn_span);
        btn_close_span = (Button) findViewById(R.id.btn_close_span);
        show_span = (TextView) findViewById(R.id.show_span);
        btn_distance = (Button) findViewById(R.id.btn_distance);
        btn_close_distance = (Button) findViewById(R.id.btn_close_distance);
        show_distance = (TextView) findViewById(R.id.show_distance);
    }

    private void initListener() {
        btn_span.setOnClickListener(this);
        btn_close_span.setOnClickListener(this);
        btn_distance.setOnClickListener(this);
        btn_close_distance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btn_span:
                //2:设置时间 必须设置在模式之前 单位 ms
                getLocation.setSpan(1000);
                //3：设置开启模式
                getLocation.start(this, IGetLocation.BY_SPAN);
//                //  4:    获取集合
                final List<Point> locationBySpan = getLocation.getLocationBySpan(MainActivity.this);

                int size = locationBySpan.size();

                Log.d(TAG, "onClick: 测试size的大小"+size);

                break;
            case R.id.btn_close_span:
                if (getLocation != null) {
                    //5:关闭
                    getLocation.stop();
                } else {
                    Log.d(TAG, " MASTER");
                }
                break;
            case R.id.btn_distance:
                //2:设置距离 单位 m
                getLocation.setDistance(2);
                //3：设置开启模式
                getLocation.start(this, IGetLocation.BY_DISTANCE);
                break;
            case R.id.btn_close_distance:
                //5:关闭
                getLocation.stop();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // eventbus的反注销
        EventBus.getDefault().unregister(this);

    }

    //  开始接收信息
@Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Message msg){
        if (msg.what==10112){
            int showsize = (int) msg.obj;
            show_span.setText("展示的大小"+showsize);
        }
    }
}
