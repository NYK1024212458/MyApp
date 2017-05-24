package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.baituoyitong.kun.baituoyitong.R;

/**
 * com.baituoyitong.kun.baituoyitong.activity.activity
 * <p>
 * Created by ${kun} on 2017/4/25.
 */
public class ApkIntroduceActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apkintroduce);
        initview();
    }

    private void initview() {
        //获取btn_back
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  关闭当前的页面
                finish();
            }
        });

    }
}
