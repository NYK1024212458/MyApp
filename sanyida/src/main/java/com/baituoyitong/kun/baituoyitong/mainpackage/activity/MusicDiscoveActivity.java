package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.Song;
import com.baituoyitong.kun.baituoyitong.mainpackage.fragment.MusicFragment;
import com.baituoyitong.kun.baituoyitong.mainpackage.service.ControlMusicService;

import java.util.List;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.activity
 * <p>
 * Created by ${kun} on 2017/4/27.
 */

public class MusicDiscoveActivity extends AppCompatActivity {
    private static final String TAG = MusicDiscoveActivity.class.getSimpleName();
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // 获取服务端传递过来的数据

        }
    } ;

    private Context mContext;
    private List<Song> musicData;
    private FrameLayout main_music_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("发现页面");
        setContentView(R.layout.activity_musicdiscove);
        mContext=MusicDiscoveActivity.this;



        //初始化操作
        initView();

    }
    private void initView() {

        //开始初始化fragment  替换
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_music_layout,new MusicFragment()).commit();

        // 获取seekbar
        LinearLayout playbar = (LinearLayout) findViewById(R.id.playbar);

        // 获取里面的控件



    }

    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
