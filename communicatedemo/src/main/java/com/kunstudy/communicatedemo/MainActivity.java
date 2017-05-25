package com.kunstudy.communicatedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.kunstudy.communicatedemo.utils.FucUtil;
import com.kunstudy.communicatedemo.utils.JsonParser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 请勿在“=”与 appid 之间添加任务空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59228aa6");

        //  初始化控件
        initVView();

        // 初始化科大讯飞
        initKeDaXunFei();

    }
    //语音听写UI初始化
    // private RecognizerDialog mRecognizerDialog;
    //创建SpeechRecognizer,语音识别对象
    private SpeechRecognizer speechRecognizer;

    //缓存
    private SharedPreferences mSharedPreferences;
    //云端语法文件
    private String mCloudGrammar = null;

    private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
    private static final String GRAMMAR_TYPE_ABNF = "abnf";
    private static final String GRAMMAR_TYPE_BNF = "bnf";

    //引擎类型
    private String mEngineType = null;
    //语法，词典临时变量
    String mContent;
    //函数调用返回值
    int ret = 0 ;
    private void initKeDaXunFei() {

        //语音识别引擎类型为云端
        mEngineType = SpeechConstant.TYPE_CLOUD;

        //创建SpeechRecognizedr对象,并初始化,注意不要导入错误的SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createRecognizer(MainActivity.this,null);


        //语法构建 获取的是资源目录下面的文件的构建
        mCloudGrammar = FucUtil.readFile(this,"grammar_sample.abnf","utf-8");

        mContent = new String(mCloudGrammar);

        //设置指定引擎类型
        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE,"cloud");

        // 设置文本编码格式
        speechRecognizer.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");




        speechRecognizer.buildGrammar(GRAMMAR_TYPE_ABNF, mContent, new GrammarListener() {
            @Override
            public void onBuildFinish(String s, SpeechError speechError) {

                Log.d(TAG, "onBuildFinish: "+s);



            }
        });






    }

    private void initVView() {
        mToast= Toast.makeText(this,"",Toast.LENGTH_SHORT);

    }


    public  void onlineControl(View view){
        //   在线命令词的测试


        // todo 难点在于   在线命令词的编写
        ret = speechRecognizer.startListening(mRecognizerListener);
        //  0   标识 的就是成功的
        if (ret != ErrorCode.SUCCESS) {
            Log.d(TAG,"识别失败,错误码: " + ret);
        }



    }
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {

        }

        @Override
        public void onResult(final RecognizerResult result, boolean isLast) {
            if (null != result && !TextUtils.isEmpty(result.getResultString())) {
                Log.d(TAG, "recognizer result：" + result.getResultString());
                String text = "";
                //s设置解析的类型

                    text = JsonParser.parseGrammarResult(result.getResultString(), mEngineType);

                Log.d(TAG, "onResult: text"+text);
            } else {
                Log.d(TAG, "recognizer result : null");
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            showTip("onError Code："	+ error.getErrorCode());
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    };

    public  void awakeControl(View view){
        //   唤醒测试


    }
    private void showTip(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }
}
