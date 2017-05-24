package com.kunstudy.communicatedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.kunstudy.communicatedemo.utils.FucUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

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
    private SpeechRecognizer mSpeechRecognizer;

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
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(MainActivity.this,null);

        //初始化语法，命令词（FucUtil在下面）
        mCloudGrammar = FucUtil.readFile(this,"Command.abnf","utf-8");
        mContent = new String(mCloudGrammar);

        //设置指定引擎类型
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE,"cloud");
        mSpeechRecognizer.setParameter(SpeechConstant.SUBJECT,"asr");
        ret = mSpeechRecognizer.buildGrammar(GRAMMAR_TYPE_ABNF,mContent,null);
        if (ret != ErrorCode.SUCCESS){
            Log.d(TAG,"语音语法构建失败，错误码："+ ret);

        }

    }

    private void initVView() {

    }


    public  void onlineControl(View view){
        //   在线命令词的测试


        // todo 难点在于   在线命令词的编写




    }
    public  void awakeControl(View view){
        //   唤醒测试


    }

}
