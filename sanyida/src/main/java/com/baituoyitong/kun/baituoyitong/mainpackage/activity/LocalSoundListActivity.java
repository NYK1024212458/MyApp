package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.adapter.MyAllMusicAdapter;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.MyUser;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.Song;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.WeatherRwsponseInfo;
import com.baituoyitong.kun.baituoyitong.mainpackage.kedaxunfeicontrol.DialogMscControl;
import com.baituoyitong.kun.baituoyitong.mainpackage.service.ControlMusicService;
import com.baituoyitong.kun.baituoyitong.mainpackage.service.IControlMusic;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.MusicUtil;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.activity
 * <p>
 * Created by ${kun} on 2017/4/28.
 */

public class LocalSoundListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LocalSoundListActivity.class.getSimpleName();
    private MyServiceConnection conn;
    private ListView lvSoundList;
    private List<Song> musicData;
    private MyAllMusicAdapter myAllMusicAdapter;
    private IControlMusic myservice;
    private View showMusicRootView;
    private String singer;
    private String name;
    private String url;
    private TextView tv_sound_name, tv_sound_author;
    private ImageView btnPlayPause1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("本地音乐");
        setContentView(R.layout.activity_localsound_list);

        // 绑定服务  并且传递数据
        Intent intent = new Intent(this, ControlMusicService.class);
        intent.putExtra("items", (Serializable) musicData);
        conn = new MyServiceConnection();
        bindService(intent, conn, BIND_AUTO_CREATE);

        initData();


        initView();
    }

    private void initData() {
        // 获取数据
        musicData = MusicUtil.getMusicData(this);

        //  获取传递过来的数据
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        singer = intent.getStringExtra("singer");
        url = intent.getStringExtra("url");

    }

    private void initView() {
        // 获取listview
        lvSoundList = (ListView) findViewById(R.id.lvSoundList);


        //  获取展示播放设置的view
        showMusicRootView = findViewById(R.id.playbar);
        initPlayMusicView(showMusicRootView);

    }

    private void initPlayMusicView(View showMusicRootView) {
        //  获取里面的 播放音乐的按钮  和传递过来的数据结合
        tv_sound_name = (TextView) showMusicRootView.findViewById(R.id.tv_sound_name);
        tv_sound_author = (TextView) showMusicRootView.findViewById(R.id.tv_sound_author);

        tv_sound_name.setText(name);
        tv_sound_author.setText(singer);
        //  获取开始或者是暂停的按钮
        btnPlayPause1 = (ImageView) showMusicRootView.findViewById(R.id.btnPlayPause);
        btnPlayPause1.setOnClickListener(this);

        if (myservice == null) {
            playMusic(url);
        }else{
            myservice.callPlayMusic(url);
        }

        //设置适配器
        myAllMusicAdapter = new MyAllMusicAdapter(musicData);
        lvSoundList.setAdapter(myAllMusicAdapter);

        //设置点击事件
        lvSoundList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  开启服务    传递给服务 postion   调用服务方法开启音乐

                // 获取去的是播放的地址
                String path = musicData.get(position).path;
                myservice.callPlayMusic(path);
                //设置显示的

                tv_sound_name.setText(musicData.get(position).song);
                tv_sound_author.setText(musicData.get(position).singer);

            }
        });
    }

    private void playMusic(String downloadUrl) {
        if (downloadUrl != null) {

            //  准备
            if (mediaPlayer != null) {

                mediaPlayer.reset();
            } else {
                mediaPlayer = new MediaPlayer();
            }
            try {
                mediaPlayer.setDataSource(downloadUrl);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //播放音乐结束
                        mediaPlayer.reset();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void puseMusic() {
        mediaPlayer.pause();
    }

    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        //
                        btnPlayPause1.setImageResource(R.mipmap.pause);

                    } else {
                        //替换图标
                        btnPlayPause1.setImageResource(R.mipmap.play);
                       mediaPlayer.start();

                    }
                    puseMusic();
                } else {
                    ToastUtils.showToast(this, "请先开始播放音乐");
                }

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlayPause:
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        //
                        btnPlayPause1.setImageResource(R.mipmap.pause);

                    } else {
                        //替换图标
                        btnPlayPause1.setImageResource(R.mipmap.play);

                    }
                    puseMusic();
                } else {
                    ToastUtils.showToast(this, "请先开始播放音乐");
                }
                break;
        }

    }

    public class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {


            myservice = (IControlMusic) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解绑
        unbindService(conn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
        mediaPlayer.release();

    }


}
