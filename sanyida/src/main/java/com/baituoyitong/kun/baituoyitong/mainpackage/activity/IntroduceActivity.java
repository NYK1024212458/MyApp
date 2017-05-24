package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baituoyitong.kun.baituoyitong.R;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.activity
 * <p>
 * Created by ${kun} on 2017/4/24.
 */

public class IntroduceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
       initView();
    }

    private void initView() {
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
