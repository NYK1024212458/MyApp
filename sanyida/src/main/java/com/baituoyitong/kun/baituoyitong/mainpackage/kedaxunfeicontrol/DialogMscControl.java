package com.baituoyitong.kun.baituoyitong.mainpackage.kedaxunfeicontrol;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;


import com.baituoyitong.kun.baituoyitong.R;

import com.baituoyitong.kun.baituoyitong.mainpackage.activity.LocalSoundListActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.WeatherRwsponseInfo;
import com.baituoyitong.kun.baituoyitong.mainpackage.eventbus.MessageEvent;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.FucUtil;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;
import com.google.gson.Gson;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * com.baituoyitong.kun.baituoyitong.activity.kedaxunfeicontrol
 * <p>
 * Created by ${kun} on 2017/4/11.
 */

public class DialogMscControl {

    //一个标识来判断是不是带一次交谈  默认是false
    static boolean isFristCommunicate = true;

    private Context mContext;
    //创建Tag的标识
    private static final String TAG = DialogMscControl.class.getSimpleName();
    private RecognizerDialog iatDialog;

    private List<String> clientSpeakerList;
    private List<WeatherRwsponseInfo> allMessageBody;
    private TtsControl ttsControl;
    private SpeechSynthesizer mTts;
    private MediaPlayer mediaPlayer;
    private SharedPreferences mSharedPreferences;
    private String grammarContent;


    public DialogMscControl(Context context) {
        this.mContext = context;

        // 获取客户输入的text
        clientSpeakerList = new ArrayList<>();
        allMessageBody = new ArrayList<>();
        ttsControl = new TtsControl(mContext);
        mSharedPreferences = context.getSharedPreferences(UnderstanderSettings.PREFER_NAME, Activity.MODE_PRIVATE);

       /* //  进行构建语法
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createRecognizer(mContext, null);
        grammarContent = FucUtil.readFile(mContext, "grammar_sample.abnf", "utf-8");
        // 设置引擎类型
        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        *//* 其中 "abnf" 指定语法类型为 ABNF,  grammarContent 为语法内容，grammarListener 为构建结果监听器*//*
        speechRecognizer.buildGrammar("abnf", grammarContent, new GrammarListener() {
            @Override
            public void onBuildFinish(String s, SpeechError speechError) {

                Log.d(TAG, "onBuildFinish: "+s);
            }
        });
*/

    }


    public void startDialogMsc() {
        //  判断是不是在开始

        //  第一次回话展示的文字  /*在这个开心的日子里，华夏幼星幼儿园向辛勤哺育小朋友们健康成长的父母，" +
       /* "以及默默奉献的园长和教师们致以衷心的感谢!我园内设外教英语，机器人，跆拳道，舞蹈，等许多艺术课程，" +
                "在即将到来的六一儿童节前向所有小朋友们说一句，六一儿童节快乐！下面让我们用热烈的掌声欢迎三易达机器人献上表演，可爱颂！*/

        if (isFristCommunicate) {
            //设置为true
            isFristCommunicate = false;

            String fristString = "大家好,我是三易达易休!";
            //是第一次回话

            MessageEvent messageEvent = new MessageEvent();
            messageEvent.clinetSpeakTest = fristString;
            messageEvent.eventBusCode = 1;
            EventBus.getDefault().post(messageEvent);
            ttsControl.textToLanguage(fristString);

        } else {

            //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
            iatDialog = new RecognizerDialog(mContext, null);
            //2.设置听写参数，同上节
            //2.设置accent、language等参数
            iatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            iatDialog.setParameter(SpeechConstant.ACCENT, "mandarin");


            //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
            iatDialog.setParameter("asr_sch", "1");
            iatDialog.setParameter("nlp_version", "2.0");


            //  设置
            // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
            iatDialog.setParameter(SpeechConstant.VAD_BOS,  "4000");

            // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
            iatDialog.setParameter(SpeechConstant.VAD_EOS, "1000");

            //3.设置回调接口
            RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
                @Override
                public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                    // parseToText(recognizerResult, isLast);
                    //看看语义理解的结果是什么东西
                    String resultString = recognizerResult.getResultString();
                    Log.d(TAG, "onResult: 这次是系统自带的对话框的语音交互+返回的直接是语义理解" + resultString);
                    ToastUtils.showToast(mContext, resultString);
                    //解析添加到bean上面
                    Gson gson = new Gson();
                    WeatherRwsponseInfo weatherRwsponseInfo = gson.fromJson(resultString, WeatherRwsponseInfo.class);
                    String s = weatherRwsponseInfo.text;

                    //  此处实现命令词的切换


                    Log.d(TAG, "onResult: s" + s);

                    //eventbus 实现数据的传递
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.eventBusCode = 1;
                    messageEvent.clinetSpeakTest = s;
                    EventBus.getDefault().post(messageEvent);

                    allMessageBody.add(weatherRwsponseInfo);
                    //添加到
                    clientSpeakerList.add(weatherRwsponseInfo.text);

                    //加一个判断  开始播放
                    switch (weatherRwsponseInfo.rc) {
                        case 0:
                            // 应答成功
                            switch (weatherRwsponseInfo.service) {
                                case "weather":
                                    //天气的返回
                                    String airQuality = weatherRwsponseInfo.data.result.get(0).airQuality;
                                    String tempRange = weatherRwsponseInfo.data.result.get(0).tempRange;
                                    String weather = weatherRwsponseInfo.data.result.get(0).weather;
                                    String wind = weatherRwsponseInfo.data.result.get(0).wind;
                                    String windLeve = weatherRwsponseInfo.data.result.get(0).windLevel + "";
                                    String pm25 = weatherRwsponseInfo.data.result.get(0).pm25 + "";


                                    //eventbus 实现数据的传递
                                    MessageEvent messageEvent1 = new MessageEvent();
                                    messageEvent1.eventBusCode = 2;
                                    messageEvent1.responseSpeakTest = airQuality + tempRange + weather + wind + "风力等级" + windLeve + "pm25" + pm25;
                                    EventBus.getDefault().post(messageEvent1);

                                    ttsControl.textToLanguage(airQuality + tempRange + weather + wind + "风力等级" + windLeve + "pm25" + pm25);
                                    break;
                                case "openQA":

                                    //eventbus 实现数据的传递
                                    MessageEvent messageEvenQA = new MessageEvent();
                                    messageEvenQA.eventBusCode = 2;
                                    messageEvenQA.responseSpeakTest = weatherRwsponseInfo.answer.text;
                                    EventBus.getDefault().post(messageEvenQA);

                                    //情绪的返回  自定义的   问候
                                    ttsControl.textToLanguage(weatherRwsponseInfo.answer.text);

                                    break;
                                case "chat":


                                    //eventbus 实现数据的传递
                                    MessageEvent messageEvenchat = new MessageEvent();
                                    messageEvenchat.eventBusCode = 2;
                                    messageEvenchat.responseSpeakTest = weatherRwsponseInfo.answer.text;
                                    EventBus.getDefault().post(messageEvenchat);

                                    //闲聊
                                    ttsControl.textToLanguage(weatherRwsponseInfo.answer.text);

                                    break;
                                case "telephone":
                                    if (weatherRwsponseInfo.operation.equals("CALL")) {
                                        //检查sim卡
                                        checkSim();

                                        //拨打电话的逻辑
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:" + weatherRwsponseInfo.semantic.slots.code + "");
                                        intent.setData(data);
                                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            // TODO: Consider calling
                                            //    ActivityCompat#requestPermissions
                                            // here to request the missing permissions, and then overriding
                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                            //                                          int[] grantResults)
                                            // to handle the case where the user grants the permission. See the documentation
                                            // for ActivityCompat#requestPermissions for more details.
                                            return;
                                        }
                                        mContext.startActivity(intent);
                                        Log.d(TAG, "onResult: " + "这个跳转为什么不走呢");
                                    }
                                    // passCall();
                                    break;

                                case "message":
                                    if (weatherRwsponseInfo.operation.equals("SEND")) {
                                        //发送短信

                                        send1(weatherRwsponseInfo.semantic.slots.code + "", weatherRwsponseInfo.semantic.slots.content);
                                    }

                                /*responseResultList.add("发短信需要坚持sim卡还没有做完");

                                //eventbus 实现数据的传递
                                MessageEvent messageEvenmessage = new MessageEvent();
                                messageEvenmessage.eventBusCode=2;
                                messageEvenmessage.responseSpeakTest ="发短信需要坚持sim卡还没有做完";
                                EventBus.getDefault().post(messageEvenmessage);

                                //发短信    判断有没有sim卡
                                ttsControl.textToLanguage("发短信需要坚持sim卡还没有做完");*/


                                    break;
                                case "pm25":
                                    //空气质量
                                    String pm = weatherRwsponseInfo.data.result.get(0).pm25 + "";
                                    String airQuality2 = weatherRwsponseInfo.data.result.get(0).airQuality;
                                    String publishDateTime = weatherRwsponseInfo.data.result.get(0).publishDateTime;


                                    //eventbus 实现数据的传递
                                    MessageEvent messageEvenpmt = new MessageEvent();
                                    messageEvenpmt.eventBusCode = 2;
                                    messageEvenpmt.responseSpeakTest = "airQuality2 + pm + publishDateTime";
                                    EventBus.getDefault().post(messageEvenpmt);


                                    ttsControl.textToLanguage(airQuality2 + pm + publishDateTime);
                                    break;
                                case "music":
                                    if (weatherRwsponseInfo.operation.equals("PLAY")) {
                                        Random random = new Random();
                                        int i = random.nextInt(10);

                                        WeatherRwsponseInfo.DataBean.ResultBean resultBean = weatherRwsponseInfo.data.result.get(i);

                                        //eventbus 实现数据的传递
                                        MessageEvent messageEvenmusic = new MessageEvent();
                                        messageEvenmusic.eventBusCode = 2;
                                        messageEvenmusic.responseSpeakTest = "开始播放音乐,请稍等";
                                        EventBus.getDefault().post(messageEvenmusic);

                                        // playMusic(resultBean.downloadUrl);

                                        //ceshi
                                        Intent intent = new Intent(mContext, LocalSoundListActivity.class);
                                        intent.putExtra("name", resultBean.name);
                                        intent.putExtra("singer", resultBean.singer);
                                        intent.putExtra("url", resultBean.downloadUrl);
                                        mContext.startActivity(intent);


                                        /*String s1 = weatherRwsponseInfo.getSemantic().toString();
                                        if (TextUtils.isEmpty(weatherRwsponseInfo.getSemantic().toString())) {
                                            WeatherRwsponseInfo.DataBean.ResultBean resultBean = weatherRwsponseInfo.data.result.get(0);
                                            //  直接跳转到  页面  传递过去 这个intent
                                            Intent intent = new Intent(mContext, LocalSoundListActivity.class);
                                            intent.putExtra("netmusic", (Serializable) resultBean);
                                            mContext.startActivity(intent);
                                        } else {*/
                                           /* if (TextUtils.isEmpty(weatherRwsponseInfo.semantic.slots.artist) | TextUtils.isEmpty(weatherRwsponseInfo.semantic.slots.song)) {
                                                //  获取更多的设备
                                                WeatherRwsponseInfo.MoreResultsBean moreResultsBean = weatherRwsponseInfo.getMoreResults().get(0);
                                                //eventbus 实现数据的传递
                                                MessageEvent messageEvenmusic = new MessageEvent();
                                                messageEvenmusic.eventBusCode = 2;
                                                messageEvenmusic.responseSpeakTest = moreResultsBean.answer.text;
                                                EventBus.getDefault().post(messageEvenmusic);
                                                //播放音乐      // TODO: 2017/4/12   还没有做完
                                                ttsControl.textToLanguage(moreResultsBean.answer.text);
                                            } else {
                                                // 播放音乐
                                                //  获取 需要的播放的音乐的  名称和作者
                                                // 艺术家
                                                String artist = weatherRwsponseInfo.semantic.slots.artist;
                                                // 歌曲的名称
                                                String songinfo = weatherRwsponseInfo.semantic.slots.song;
                                                //// TODO: 2017/4/27   封装一查询的类  进行跳转到播放音乐的界面
                                                //封装一个类  查询  手机上的音乐的文件
                                                List<Song> musicData = MusicUtil.getMusicData(mContext);
                                                for (int i = 0; i < musicData.size(); i++) {
                                                    Song song1 = musicData.get(i);
                                                    String song = song1.song;
                                                    if (song.equals(songinfo)) {
                                                        //查找到歌曲  进行播放
                                                        //  跳转到 音乐界面 开始播放  i
                                                        *//*Intent intent = new Intent(mContext, MusicDiscoveActivity.class);
                                                            intent.putExtra("查找到的位置",i);
                                                        mContext.startActivity(intent);*//*
                                                        final MediaPlayer mediaPlayer = new MediaPlayer();
                                                        try {
                                                            mediaPlayer.setDataSource(song1.path);
                                                            mediaPlayer.prepare();
                                                            mediaPlayer.start();
                                                            //监听是不是播放结束了
                                                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                @Override
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    //  表示播放完成
                                                                    //播放音乐完成
                                                                    ttsControl.textToLanguage("播放音乐结束");
                                                                }
                                                            });
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }

                                            }*/


                                        //}
                                        // 在封装一个类类来进行本地的歌曲的播放

                                    }


                                    break;
                                case "baike":
                                    //百科

                                    //eventbus 实现数据的传递
                                    MessageEvent messageEvenbaike = new MessageEvent();
                                    messageEvenbaike.eventBusCode = 2;
                                    messageEvenbaike.responseSpeakTest = weatherRwsponseInfo.answer.text;
                                    EventBus.getDefault().post(messageEvenbaike);

                                    //闲聊
                                    ttsControl.textToLanguage(weatherRwsponseInfo.answer.text);

                                    break;
                                case "cookbook":
                                    //菜谱
                                    String accessory = weatherRwsponseInfo.data.result.get(0).accessory;
                                    String ingredient = weatherRwsponseInfo.data.result.get(0).ingredient;

                                    //eventbus 实现数据的传递
                                    MessageEvent cookbook = new MessageEvent();
                                    cookbook.eventBusCode = 2;
                                    cookbook.responseSpeakTest = accessory + ingredient;
                                    EventBus.getDefault().post(cookbook);

                                    ttsControl.textToLanguage(accessory + ingredient);

                                    break;
                                case "fap":
                                    //社区问答
                                    //百科

                                    //eventbus 实现数据的传递
                                    MessageEvent fap = new MessageEvent();
                                    fap.eventBusCode = 2;
                                    fap.responseSpeakTest = weatherRwsponseInfo.answer.text;
                                    EventBus.getDefault().post(fap);

                                    //闲聊
                                    ttsControl.textToLanguage(weatherRwsponseInfo.answer.text);

                                    break;
                                case "calc":
                                    //计算


                                    //eventbus 实现数据的传递
                                    MessageEvent calc = new MessageEvent();
                                    calc.eventBusCode = 2;
                                    calc.responseSpeakTest = weatherRwsponseInfo.answer.text;
                                    EventBus.getDefault().post(calc);


                                    ttsControl.textToLanguage(weatherRwsponseInfo.answer.text);
                                    break;
                                default:

                                    //eventbus 实现数据的传递
                                    MessageEvent messageEvenother = new MessageEvent();
                                    messageEvenother.eventBusCode = 2;
                                    messageEvenother.responseSpeakTest = "其他服务平台,请重新输入";
                                    EventBus.getDefault().post(messageEvenother);


                                    //其他服务平台
                                    ttsControl.textToLanguage("其他服务平台,请重新输入");
                                    break;
                            }
                            break;
                        case 1:

                            //eventbus 实现数据的传递
                            MessageEvent messageEvent1 = new MessageEvent();
                            messageEvent1.eventBusCode = 2;
                            messageEvent1.responseSpeakTest = "无效请求,请重新输入";
                            EventBus.getDefault().post(messageEvent1);

                            //无效请求
                            ttsControl.textToLanguage("");
                            Log.d(TAG, "onResult: rc=1" + "无效请求,请重新输入");
                            break;
                        case 2:

                            //eventbus 实现数据的传递
                            MessageEvent messageEvent2 = new MessageEvent();
                            messageEvent2.eventBusCode = 2;
                            messageEvent2.responseSpeakTest = "";
                            EventBus.getDefault().post(messageEvent2);

                            //服务器内部错误
                            ttsControl.textToLanguage("服务器内部错误,请重新输入");
                            Log.d(TAG, "onResult: rc=2" + "");

                            break;
                        case 3:

                            //eventbus 实现数据的传递
                            MessageEvent messageEvent3 = new MessageEvent();
                            messageEvent3.eventBusCode = 2;
                            messageEvent3.responseSpeakTest = "";
                            EventBus.getDefault().post(messageEvent3);

                            //业务操作失败
                            ttsControl.textToLanguage("");

                            Log.d(TAG, "onResult: rc=3" + "业务操作失败,请重新输入");
                            break;
                        case 4:


                            //eventbus 实现数据的传递
                            MessageEvent messageEvent4 = new MessageEvent();
                            messageEvent4.eventBusCode = 2;
                            messageEvent4.responseSpeakTest = "";
                            EventBus.getDefault().post(messageEvent4);

                            //你的输入没有人可以理解,请重新输入
                            ttsControl.textToLanguage("");
                            Log.d(TAG, "onResult: rc=4" + "服务器错误,请重新输入");
                            break;
                    }

                }

                @Override
                public void onError(SpeechError speechError) {
                    //错误的话   打印日志
                    int errorCode = speechError.getErrorCode();
                    String errorDescription = speechError.getErrorDescription();

                    Log.d(TAG, "onError: 回话发生错误的错误编号和错误的描述" + errorCode + "错误,描述" + errorDescription);
                    if (speechError.getErrorCode() == 10118) {
                        //  此时表示的就是没有说话 我们再次调用
                        iatDialog.dismiss();

                        startDialogMsc();

                    }

                }
            };
            iatDialog.setCanceledOnTouchOutside(true);
            iatDialog.setListener(recognizerDialogListener);
            //设置不可以消失
            iatDialog.setCancelable(false);
            iatDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //  设置关闭
                    startDialogMsc();
                }
            });
            //4.开始听写
            iatDialog.show();


            //表示一次会话结束

        }
    }

    private void checkSim() {
        // 检查是不是具有SIM卡
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String simSer = tm.getSimSerialNumber();
        if (simSer == null || simSer.equals("")) {
            //Toast.makeText(this, "插入SIM卡才能开启此应用", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("插入SIM卡才能开启此应用");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            //  设置不可以点击取消


            builder.show();
        }
    }

    private void send1(String number, String message) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        sendIntent.putExtra("sms_body", message);
        mContext.startActivity(sendIntent);
    }

    private void passCall() {

        //eventbus 实现数据的传递
        MessageEvent messageEventelephone = new MessageEvent();
        messageEventelephone.eventBusCode = 2;
        messageEventelephone.responseSpeakTest = "打电话还没有做完,需要sim卡,还没有做完";
        EventBus.getDefault().post(messageEventelephone);

        //打电话   判断有没有sim卡  // TODO: 2017/4/12   还没有做完
        ttsControl.textToLanguage("打电话还没有做完,需要sim卡,还没有做完");
    }


    public void stopTts() {
        //加一个判断是不是空的
        mTts = ttsControl.mTtss;
        if (mTts != null) {
            if (mTts.isSpeaking()) {
                mTts.stopSpeaking();
            }
        }


    }


    public void closeAll() {
        if (ttsControl.mTtss != null) {
            if (ttsControl.mTtss.isSpeaking()) {
                ttsControl.mTtss.stopSpeaking();
                ttsControl.mTtss.destroy();
            }
        }
        if (null != iatDialog) {
            if (iatDialog.isShowing()) {
                // 退出时释放连接
                iatDialog.cancel();
                iatDialog.destroy();
            }


        }
    }

    //
    private onQuitAll mListener;


    //设置一个回调的接口来 监听是不是最后一次完成了!
    public void setOnQitAll(onQuitAll listener) {
        this.mListener = listener;


    }

    public interface onQuitAll {

        void onQuitAll();


    }

public RecognizerDialog getIatDialog(){
    if (null!=iatDialog){
      return  iatDialog;
    }
    return  null;
}

}
