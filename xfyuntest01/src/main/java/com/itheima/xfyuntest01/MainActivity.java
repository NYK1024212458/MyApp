package com.itheima.xfyuntest01;

import android.annotation.TargetApi;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.itheima.xfyuntest01.bean.ResultInfo;
import com.itheima.xfyuntest01.bean.VoiceInfo;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView show_listview;
    private List<String> stList, reList;
    private Toast mToast;
    private ListView lv_show_result_textview;
    private SpeechUnderstander understander;
    private Button btn_complex;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不要标题栏
        setContentView(R.layout.activity_main);
        //初始化toast  是對toast的封裝
        mToast = Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);
        //初始化
        // 将“12345678”替换成您申请的 APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与 appid 之间添加任务空字符或者转义符
        //57f75ccc            58e138bc
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=58e138bc");

        //展示文字到listview
        show_listview = (ListView) findViewById(R.id.lv_show_speak_lauage);
        //创建一个集合来存放每一次的语音转化的文字
        stList = new ArrayList<>();
        //
        lv_show_result_textview = (ListView) findViewById(R.id.lv_show_result_textview);
        //
        reList = new ArrayList<String>();



        //  整合初始化
        initAll();
    }
      // 整合的功能的初始化工作

    private void initAll() {
        // 获取btn
        btn_complex = (Button) findViewById(R.id.btn_complex);
        // 获取展示的listview
        ListView lv_complex_dialogue = (ListView) findViewById(R.id.lv_complex_dialogue);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //把文字转换成语言
    public void say(View view) {
        //  提取的方法   最后会构造 textToLanguage();
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(this, null);
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置发音人 mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速 mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        // 保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        //Environment.getExternalStorageDirectory()
        //getFilesDir
        //getExternalFilesDir()
        File file = new File("xunfei", "/hecheng2017.wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, String.valueOf(file));
        Toast.makeText(this, "  最上面执行了保存音频的操作", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "textToLanguage:最上面 执行了保存音频的操作");
        Log.d(TAG, "textToLanguage: 保存的路径"+"/tts2017.wav");
        //3.开始合成
        //获取输入的文字
        text = (EditText) findViewById(R.id.et_saing);
        String say_text = text.getText().toString().trim();

        mTts.startSpeaking(say_text, mSynListener);
    }

    private void textToLanguage(String strtext) {
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(this, null);
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "vixx");
        //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        // 保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");


        Toast.makeText(this, "执行了保存音频的操作", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "textToLanguage: 执行了保存音频的操作");
        //3.开始合成
        //获取输入的文字
       //* EditText text = (EditText) findViewById(R.id.et_saing);
        String say_text = text.getText().toString().trim();

        mTts.startSpeaking(strtext, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
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


    // 开始录音  和结束录音
    private Boolean isSpeak = true;


    //主要的实现

    //把语言转换成文字
    public void listen(View view) {

        Toast.makeText(getBaseContext(), "开始说话", Toast.LENGTH_SHORT).show();
        //开始录音
        if (isSpeak) {

            //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
            SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null);
            //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
            mIat.setParameter(SpeechConstant.DOMAIN, "iat");
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
            //3.开始听写
            mIat.startListening(mRecoListener);

            isSpeak = false;
        }
    }

    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
        //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        //关于解析Json的代码可参见Demo中JsonParser类；
        // isLast等于true时会话结束。
        StringBuilder sb = new StringBuilder();
        public void onResult(RecognizerResult results, boolean isLast) {
//            Log.d(TAG, "result:" + results.getResultString ());
            VoiceInfo info = new Gson().fromJson(results.getResultString(), VoiceInfo.class);
            if (isLast) {
                //此时说话结束   友好提示
                Toast.makeText(getBaseContext(), "说话结束", Toast.LENGTH_SHORT).show();
                String content = info.ws.get(0).cw.get(0).w;
                sb.append(content);
                Log.d(TAG, "result:" + sb.toString());
                stList.add(sb.toString());

                Log.d(TAG, "onResult: " + stList.size());

                //设置适配器

                Log.d(TAG, "onResult: 是不是还没有执行到这里");

                show_listview.setAdapter(new MyAdapter(stList, MainActivity.this));

                //开始语言理解之 文本理解
            } else {
                //拼接内容
                List<VoiceInfo.WS> ws = info.ws;
                for (VoiceInfo.WS w : ws) {
                    String content = w.cw.get(0).w;
                    sb.append(content);
                }

            }

        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            //打印错误码描述
            Log.d(TAG, "error:" + error.getPlainDescription(true));
        }

        //开始录音
        public void onBeginOfSpeech() {
        }

        //volume音量值0~30，data音频数据
        public void onVolumeChanged(int volume, byte[] data) {

        }
        //结束录音

        public void onEndOfSpeech() {
            if (!isSpeak) {
                //
                Toast.makeText(getBaseContext(), "结束录音", Toast.LENGTH_SHORT).show();

            }
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };


    //显示对话框的一个按键

    public void dialogListen(View view) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, mInitListener);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //结果
        mDialog.setParameter("asr_sch", "1");
        mDialog.setParameter("nlp_version", "2.0");

        //3.设置回调接口
        mDialog.setListener(mRecognizerDialogListener);
        //4.显示dialog，接收语音输入
        mDialog.show();

    }

    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int i) {

        }
    };

    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        StringBuilder sb = new StringBuilder();

        public void onResult(RecognizerResult results, boolean isLast) {
//            Log.d(TAG, "result:" + results.getResultString ());
            VoiceInfo info = new Gson().fromJson(results.getResultString(), VoiceInfo.class);
            if (isLast) {
                String content = info.ws.get(0).cw.get(0).w;
                sb.append(content);
                Log.d(TAG, "result:+对话框" + sb.toString());
            } else {
                //拼接内容
                List<VoiceInfo.WS> ws = info.ws;
                for (VoiceInfo.WS w : ws) {
                    String content = w.cw.get(0).w;
                    sb.append(content);
                    Log.d(TAG, "result:" + sb.toString());
                    stList.add(sb.toString());

                    Log.d(TAG, "onResult: " + stList.size());
                }
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };



    //语义理解
    int ret = 0;// 函数调用返回值

    public void cclick3(View v) {
        //语义理解集成


        //1.创建語音语义理解对象
        understander = SpeechUnderstander.createUnderstander(MainActivity.this, null);
        //2.设置参数，语义场景配置请登录http://osp.voicecloud.cn/
        understander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        /**
         * 服务器为不同的应用领域，定制了不同的听写匹配引擎，使用对应的领域能获取更 高的匹配率。
         应用领域用于听写和语音语义服务。当前支持的应用领域有：
         短信和日常用语：iat (默认)
         视频：video
         地图：poi
         音乐：music
         */
        understander.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置支持的方言    默认支持的mandarin
        understander.setParameter(SpeechConstant.ACCENT, "mandarin ");

        //3.开始语义理解
        understander.startUnderstanding(mUnderstanderListener);
        if (understander.isUnderstanding()) {// 开始前检查状态
            understander.stopUnderstanding();
            showTip("停止录音");
        } else {
            ret = understander.startUnderstanding(mUnderstanderListener);
            if (ret != 0) {
                showTip("语义理解失败,错误码:" + ret);
            } else {
                showTip("请开始说话");
            }
        }
    }

    // XmlParser为结果解析类，见SpeechDemo
    private SpeechUnderstanderListener mUnderstanderListener = new SpeechUnderstanderListener() {
        public void onResult(UnderstanderResult result) {
            String text = result.getResultString();

            Log.d(TAG, "onResult: 這是使用語音理解的  返回值" + text);
            //解析gson


        }

        public void onError(SpeechError error) {
            //会话发生错误回调接口
            showTip("回话发生错误");
            Log.d(TAG, "onError: 语音语义理解 错误" + error);
            if (error.getErrorCode() == 10118) {

                // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
                understander.setParameter(SpeechConstant.VAD_BOS, "5000");
                // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音0~10000
                understander.setParameter(SpeechConstant.VAD_EOS, "1800");

            }
        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            //修改声音的大小

        }

        //开始录音
        public void onBeginOfSpeech() {
            showTip("开始录音");
            Log.d(TAG, ": onBeginOfSpeech");


        }

        //结束录音
        public void onEndOfSpeech() {
            showTip("结束录音");
            Log.d(TAG, ": onEndOfSpeech");

        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };


    //文本语义理解
    public void cclick4(View v) {
        Log.d(TAG, "cclick4: " + "点击事件是不是知悉");


        //创建文本语义理解对象
        TextUnderstander mTextUnderstander = TextUnderstander.createTextUnderstander(this, null);
        //开始语义理解
        String s = stList.get(0);
        Log.d(TAG, "cclick4: " + s);
        mTextUnderstander.understandText(s, searchListener);

        //初始化监听器


    }

    TextUnderstanderListener searchListener = new TextUnderstanderListener() {
        //语义结果回调
        public void onResult(UnderstanderResult recognizerResult) {

            Log.d(TAG, "onResult: " + recognizerResult.toString());
            String string = recognizerResult.getResultString();
            Log.d(TAG, "onResult: 文本语义理解的返回值" + string);

            // 语义理解 之文本理解的返回值进行解析
            Log.d(TAG, "onResult: 解析的结果是" + string);
            //开始将解析的  string解析后开始播放  并添加到机器人回答的listview上面
            ResultInfo.MoreResultsBean moreResultsBean = new Gson().fromJson(string, ResultInfo.MoreResultsBean.class);
            ResultInfo.MoreResultsBean.AnswerBeanX answer = moreResultsBean.answer;
            String text = answer.text;
            Log.d(TAG, "onResult: 最后解析的结果是" + text);
            //开始播放
            textToLanguage(text);
            //展示到右侧
            //加一个判断
            if (text != null) {
                reList.add(text);
            }
            //展示的就是机器语义解析后的结果
            lv_show_result_textview.setAdapter(new MyShowAdapter(reList, MainActivity.this));
        }

        //语义错误回调
        public void onError(SpeechError error) {
            Log.d(TAG, "cclick4: " + "错误" + error.toString());

        }
    };

    //实际上就是对   吐司的封裝
    private void showTip(final String s) {
        mToast.setText(s);
        mToast.show();

    }




    //  对上面的功能进行整合  并实现复杂的listview来展示对话

}
