package com.baituoyitong.kun.baituoyitong.mainpackage.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * com.baituoyitong.kun.sanyida.eventbus
 * <p>
 * Created by ${kun} on 2017/4/13.
 */

public  class MessageEvent {
    //声明一个String
    public String clinetSpeakTest;
    public String responseSpeakTest;
    //区别是哪一个回应
    public int eventBusCode;


    // tts的一次播放是不是完成了!

    public  boolean isTtsFinished ;


    //传递listview
    public ListView mListview;

    //
    public View startThemeView;
    //设置集合
    public List<Button> buttonList;


}
