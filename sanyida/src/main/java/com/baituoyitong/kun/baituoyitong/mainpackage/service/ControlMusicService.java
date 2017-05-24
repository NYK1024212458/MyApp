package com.baituoyitong.kun.baituoyitong.mainpackage.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baituoyitong.kun.baituoyitong.mainpackage.activity.MusicDiscoveActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.service
 * <p>  主要是控制播放音乐的服务
 * Created by ${kun} on 2017/4/27.
 */

public class ControlMusicService extends Service {
    private static final String TAG = ControlMusicService.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> items;
    /**
     * 播放上一首的操作
     */
    public static final int PLAY_PRE = 662;
    /**
     * 播放下一首的操作
     */
    public static final int PLAY_NEXT = 540;


    //当前播放的音乐在集合中的索引
    private int mPosition = -1;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return  new MyBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //  从activity调用的startService
        items = (ArrayList<Song>) intent.getSerializableExtra("items");
        Log.d(TAG, "onStartCommand: 看看是不是将 iteam传递过来了" + items.size());

        return super.onStartCommand(intent, flags, startId);
    }

    public void playMusic(String path) {
        // 获取传递过来的位置

        Log.d(TAG, "playMusic: 感觉就是这个postiopn出错"  );


        if(mediaPlayer == null){
            Log.d(TAG, "playMusic:   怎么不走啊!");
            mediaPlayer = new MediaPlayer();
            //设置准备好的监听
            mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //说明一首歌已经播放完了 播放下一首
                    Log.d(TAG, "onCompletion: 歌曲播放完毕了" + "歌曲播放完毕,进行下一步的播放");
                    preNext(PLAY_NEXT);

                }
            });
        }else{
            //如果走到这里 说明是进行歌曲的切换 重置mediaplayer
            mediaPlayer.reset();
            Log.d(TAG, "playMusic: 切换歌曲");
        }
        //设置数据源
        try {
            mediaPlayer.setDataSource(path);
            //进行异步准备
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            //准备好之后开始播放音乐
            mediaPlayer.start();

            Intent intent = new Intent("com.baituoyitong");
            sendBroadcast(intent);


            //播放的同时要获取歌曲的最大进度 还有当前歌曲播放的进度
            updateSeekbar();

           /*
            //发通知到通知栏
            // sendDefaultNotification();
            sendBigCustomNotification();*/
        }
    }


    //更新进度条的进度
    private void updateSeekbar() {
        //[1]获取歌曲最大进度
        final int duration = mediaPlayer.getDuration();
        //[2]使用timer时时获取当前歌曲播放的进度
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //[2.1]获取歌曲当前播放的进度
                int currentPosition = mediaPlayer.getCurrentPosition();

                //[2.2]使用我们在mainActivity创建的handler 把当前总时长和当前播放进度 值传递到mainActivit类
                Message msg = Message.obtain();
                //[2.3]使用msg传递多个值  计算map集合
                Bundle bundle = new Bundle();
                //[2.4]使用bundle 传递多个数据
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                msg.setData(bundle);
                //[2.5]发消息 当下面这句话一执行  mainActivity的handleMessage方法就会执行
                MusicDiscoveActivity.handler.sendMessage(msg);

            }
        };
        //[3]1秒钟后 每隔1秒获取一次当前歌曲播放的进度
        timer.schedule(task, 1000, 1000);
        //[4]当歌曲播放完成把timer和task取消
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //当歌曲播放完成
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("哈哈哈 歌曲万事了");
                timer.cancel();
                task.cancel();
            }
        });


    }

    private boolean isPlay() {
       return mediaPlayer.isPlaying();
    }




    // 播放音乐
    public void pauseMusic() {
        if(mediaPlayer.isPlaying()){
            //处于播放状态 暂停音乐
            mediaPlayer.pause();
        }else{
            //处于暂停状态 播放音乐
            mediaPlayer.start();
        }
    }


    // 继续播放
    public void rePlayMusic() {
        System.out.println("音乐继续播放");
        mediaPlayer.start();

    }

    //实现播放指定的位置
    public void seekToPosition(int position) {

        mediaPlayer.seekTo(position);
    }


    public void preNext(int flag) {
        //
        Log.d(TAG, "preNext: 进入下一首歌曲的播放");
        if (flag == PLAY_NEXT) {
            //播放下一首
            mPosition = ++mPosition % items.size();
            Log.d(TAG, "preNext: 看看位置是不是出错了" + mPosition);
        } else if (flag == PLAY_PRE) {
            //播放上一首
            mPosition = mPosition == 0 ? items.size() - 1 : --mPosition;
        }
        String path = items.get(mPosition).path;
        Log.d(TAG, "preNext: 下一个首歌曲的地址是不是获取到了" + path);
        // 获取地址和传递位置
        playMusic(path);
    }


    //设置一个借口来暴露一些 控制音乐播放的方法
    public class MyBinder extends Binder implements IControlMusic {

        //调用播放音乐的方法


        @Override
        public void callPlayMusic(String path) {
            playMusic(path);
        }

        //调用暂停音乐的方法
        @Override
        public void callPauseMusic() {
            pauseMusic();
        }

        //调用继续播放
        @Override
        public void callRePlayMusic() {
            rePlayMusic();
        }

        //调用seekToPosition方法
        @Override
        public void callSeekToPosition(int position) {
            seekToPosition(position);
        }

        @Override
        public void callPreNext(int position) {
            preNext(position);
        }

        @Override
        public boolean callISPlay() {

            return isPlay();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
