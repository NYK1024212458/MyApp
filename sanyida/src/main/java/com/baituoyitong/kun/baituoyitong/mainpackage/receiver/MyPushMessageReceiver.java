package com.baituoyitong.kun.baituoyitong.mainpackage.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.PushMsg;
import com.google.gson.Gson;

import cn.bmob.push.PushConstants;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.receiver
 * <p>
 * Created by ${kun} on 2017/5/3.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {

    private String msg;
    private  Context mContext;
    private PushMsg pushMsg;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            msg = intent.getStringExtra("msg");
            //进行格式化
            Gson gson = new Gson();
            pushMsg = gson.fromJson(msg, PushMsg.class);

            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            //  展示位通知栏   最后的操作就是   点击进入apk
           // sendBigCustomNotification();
            sendDefaultNotification(context);
            mContext=context;
        }
    }
    private void sendDefaultNotification(Context context){
        Notification.Builder builder = new Notification.Builder(context);
        //设置通知中的标题
        builder.setContentTitle("三易达信息来了!");
        //设置通知中的内容
        builder.setContentText(msg);
        builder.setTicker("服务端推送的消息展示:"+pushMsg.alert);
        //必须给通知设置图标 如果没设置图标 通知不会显示
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //如果setOngoing为true 这个通知不能被用户手动移除 需要应用设计相关的删除通知的方法
        //builder.setOngoing(true);
        //设置自动取消的通知 如果给通知设置了点击事件 点了之后通知会自动消失
        builder.setAutoCancel(true);
        Notification notification = builder.getNotification();
        //发送通知到通知栏 要使用NotificationManager
        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        //notify 方法两个参数 第一个参数 id用来区分不同的通知 可以通过这个id 调用cancel方法 移除通知
        //第二个参数 要发送到通知栏中的通知
        manager.notify(0,notification);
        //cancel方法 通过通知的id 把通知取消掉
        //manager.cancel(0);

        // 设置通知的点击事件

    }
   /* private void sendBigCustomNotification(){
        NotificationCompat.Builder builder =new NotificationCompat.Builder(getApplicationContext());
        //设置状态栏滚动显示的文字
        builder.setTicker(" 获取到的推送消息"+msg);
        //给通知设置小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);

        //设置自定义布局
        builder.setCustomContentView(getRemoteViews());
        if(Build.VERSION.SDK_INT>=16){
            //如果当前的sdk版本>=16 4.1之后的支持大布局的通知 100dp
            //大布局的通知 只有当当前通知在通知栏的第一条时会展示出来
            builder.setCustomBigContentView(getBigRemoteViews());
        }
        //设置为不可手动去除的通知
        builder.setOngoing(true);
        Notification notification = builder.getNotification();

        //发送通知到通知栏 要使用NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //notify 方法两个参数 第一个参数 id用来区分不同的通知 可以通过这个id 调用cancel方法 移除通知
        //第二个参数 要发送到通知栏中的通知
        manager.notify(0,notification);

    }
    private RemoteViews getRemoteViews(){
        RemoteViews views = new RemoteViews(getPackageName(),R.layout.notification_normal);
        //设置了歌曲的名字和艺术家名字
        views.setTextViewText(R.id.tv_notification_artist,items.get(position).artist);
        views.setTextViewText(R.id.tv_notification_title,items.get(position).title);

        //设置上一首下一首的点击事件
        views.setOnClickPendingIntent(R.id.iv_notification_next,getNextPendingIntent());
        views.setOnClickPendingIntent(R.id.iv_notification_pre,getPrePendingIntent());
        views.setOnClickPendingIntent(R.id.rl_notification,getActivityPendingIntent());
        return  views;
    }

    private RemoteViews getBigRemoteViews() {
        RemoteViews views = new RemoteViews(getPackageName(),R.layout.notification_big);
        //设置了歌曲的名字和艺术家名字
        views.setTextViewText(R.id.tv_notification_artist,items.get(position).artist);
        views.setTextViewText(R.id.tv_notification_title,items.get(position).title);
        //根据当前播放的状态 更新播放暂停图标
        if(player.isPlaying()){
            views.setImageViewResource(R.id.iv_notification_playPause,R.drawable.music_pause_selector);
        }else{
            views.setImageViewResource(R.id.iv_notification_playPause,R.drawable.music_play_selector);
        }

        //设置上一首下一首的点击事件
        views.setOnClickPendingIntent(R.id.iv_notification_next,getNextPendingIntent());
        views.setOnClickPendingIntent(R.id.iv_notification_pre,getPrePendingIntent());
        views.setOnClickPendingIntent(R.id.rl_notification,getActivityPendingIntent());
        views.setOnClickPendingIntent(R.id.iv_notification_playPause,getPlayPausePendingIntent());
        views.setOnClickPendingIntent(R.id.iv_notification_cancel,getCanelPendingIntent());
        return  views;
    }*/
}
