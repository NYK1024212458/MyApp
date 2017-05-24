package com.baituoyitong.kun.baituoyitong.mainpackage.kedaxunfeicontrol;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.baituoyitong.kun.baituoyitong.mainpackage.eventbus.MessageEvent;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;


/**
 * com.baituoyitong.kun.baituoyitong.activity.kedaxunfeicontrol
 * <p>
 * Created by ${kun} on 2017/4/11.
 */

public class TtsControl {
    //创建Tag的标识
    private static  final String Tag = TtsControl.class.getSimpleName();
    private Context mContext;
    public SpeechSynthesizer mTtss;

    public TtsControl(Context context ) {
        //获取传递的上下文
        mContext=context;
    }

    //文本转化为语音的控制类
    public void textToLanguage(String strtext) {
        if (mTtss ==null){
            //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
            mTtss = SpeechSynthesizer.createSynthesizer(mContext, null);
        }

        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录13.2
        mTtss.setParameter(SpeechConstant.VOICE_NAME, "nannan");

        //设置语速
        mTtss.setParameter(SpeechConstant.SPEED, "50");
        //设置音量，范围 0~100
        mTtss.setParameter(SpeechConstant.VOLUME, "80");

        mTtss.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        // 保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        mTtss.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");


        //ToastUtils.showToast(mContext,"执行了保存音频的操作");
        Log.d(Tag, "textToLanguage: 执行了保存音频的操作");
        //3.开始合成
        SynthesizerListener mSynListener = new SynthesizerListener() {
            //会话结束回调接口，没有错误时，error为null
            public void onCompleted(SpeechError error) {


                //不就是完成了,发送消息给主线程
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.isTtsFinished = true;
                messageEvent.eventBusCode=3;
                EventBus.getDefault().post(messageEvent);
            }

            //缓冲进度回调
            //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            }

            //开始播放
            public void onSpeakBegin() {
            }

            //暂停播放
            public void onSpeakPaused() {
            }

            //播放进度回调
            //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
            }

            //恢复播放回调接口
            public void onSpeakResumed() {
            }

            //会话事件回调接口
            public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

            }
        };
        mTtss.startSpeaking(strtext, mSynListener);


    }
}
