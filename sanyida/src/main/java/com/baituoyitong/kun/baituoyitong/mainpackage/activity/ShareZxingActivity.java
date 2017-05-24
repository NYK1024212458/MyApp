package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baituoyitong.kun.baituoyitong.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * com.baituoyitong.kun.baituoyitong.activity.activity
 * <p>
 * Created by ${kun} on 2017/4/20.
 */
public class ShareZxingActivity  extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_share_zxing;
    private ImageView iv_share_zxing1;
    private Button btn_share_zxing;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharezxing);
        //生成二维码
        getShowZxing();

        initview();
    }

    private void getShowZxing() {
        //生成二维码
        String textContent = "http://www.pvc123.com/b-syd20162017/";
        if (TextUtils.isEmpty(textContent)) {
            Toast.makeText(ShareZxingActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

    }

    private void initview() {
        // 获取展示二维码的控件

        iv_share_zxing1 = (ImageView) findViewById(R.id.iv_share_zxing);

        iv_share_zxing1.setImageBitmap(mBitmap);
        //设置集成第三方的控件
        btn_share_zxing = (Button) findViewById(R.id.btn_share_zxing);
      //  设置点击事件
        btn_share_zxing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.btn_share_zxing:
               //集成第三方的分享


            break;
        }

    }
}
