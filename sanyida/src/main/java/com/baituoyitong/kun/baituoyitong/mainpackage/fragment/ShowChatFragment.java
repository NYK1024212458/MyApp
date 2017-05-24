package com.baituoyitong.kun.baituoyitong.mainpackage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.adapter.ChatMsgViewAdapter;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.ChatMsgEntity;
import com.baituoyitong.kun.baituoyitong.mainpackage.eventbus.MessageEvent;
import com.baituoyitong.kun.baituoyitong.mainpackage.kedaxunfeicontrol.DialogMscControl;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * com.baituoyitong.kun.baituoyitong.activity.fragment
 * <p>
 * Created by ${kun} on 2017/4/18.
 */

public class ShowChatFragment  extends Fragment implements View.OnClickListener {
    private Context mContext;
    public static final String TAG = ShowChatFragment.class.getSimpleName();
    private ListView lv_show_message;
    private ChatMsgViewAdapter mAdapter;
    private List<ChatMsgEntity> chatLists;
    private boolean isTtsFinished;
    private DialogMscControl dialogMscControl;
    private EditText et_search_content;
    private ImageView btnSearchCancel;
    private Button btnSearch;
    private ChatMsgViewAdapter mAdapter1;
    private ImageView btnSearchCancel1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化eventbus
        EventBus.getDefault().register(this);
        //chushihua
        chatLists = new ArrayList<>();
        dialogMscControl = new DialogMscControl(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        //开始打起第二个布局   ll_second_fragment
        //创建布局   打气
        View item_second = inflater.inflate(R.layout.item_second, null);

        // 获取listview
        lv_show_message = (ListView) item_second.findViewById(R.id.lv_show_message);
        //获取搜索的界面
        initSearch(item_second);

        return item_second;
    }

    private void initSearch(View item_second) {
        // 初始化
        //这里就是keysearch
        et_search_content = (EditText) item_second.findViewById(R.id.et_search_content);
        btnSearchCancel = (ImageView) item_second.findViewById(R.id.btnSearchCancel);
        //初始化  搜索按钮
        btnSearch = (Button) item_second.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        //初始化 删除的文字
        btnSearchCancel1 = (ImageView) item_second.findViewById(R.id.btnSearchCancel);
        btnSearchCancel1.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //接收事件   并且在此设置适配器


    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        switch (event.eventBusCode) {

            case 1:
                //接受 客户的说话信息
                String clinetSpeakTest = event.clinetSpeakTest;
                Log.d(TAG, "clinetSpeakTest: " + clinetSpeakTest);
                ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
                chatMsgEntity.setMsgType(false);
                chatMsgEntity.setName("客户");
                chatMsgEntity.setDate(getSystemCurrentTimeOne());
                chatMsgEntity.setText(clinetSpeakTest);
                chatLists.add(chatMsgEntity);
                mAdapter1 = new ChatMsgViewAdapter(getContext(), chatLists);
                lv_show_message.setAdapter(mAdapter1);
                break;
            case 2:
                //是语义理解的回应的消息
                String responseSpeakTest = event.responseSpeakTest;
                Log.d(TAG, "responseSpeakTest: " + responseSpeakTest);
                ChatMsgEntity chatMsgEntity1 = new ChatMsgEntity();
                chatMsgEntity1.setMsgType(true);
                chatMsgEntity1.setName("易休");
                chatMsgEntity1.setDate(getSystemCurrentTimeOne());
                chatMsgEntity1.setText(responseSpeakTest);
                chatLists.add(chatMsgEntity1);

                //  刷新适配器

                mAdapter1.notifyDataSetChanged();
                break;
            case 3:
                //表示一次的tts回话结束了!
                isTtsFinished = event.isTtsFinished;
                Log.d(TAG, "onMessageEvent: 测试是不是一次tts结束" + isTtsFinished);


                //  刷新适配器

                mAdapter1.notifyDataSetChanged();

                Log.d(TAG, "onMessageEvent: 看看 适配器是不是执行了");
                Log.d(TAG, "onMessageEvent: 看看 集合数据"+chatLists.size());

                //再次调用
                if (isTtsFinished) {
                    dialogMscControl.startDialogMsc();
                }
                break;

        }
        Log.d(TAG, "onMessageEvent:测试" + chatLists.size() + "");
    }

    private String getSystemCurrentTimeOne() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        // 格式化时间\
        String str = formatter.format(curDate);
        //ToastUtils.showToast(getContext(), str);
        return str;
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSearchCancel:
                // 点击之后清除文字
                et_search_content.setText("");
                break;
            case R.id.btnSearch:
                //  开始搜索
                String trim = et_search_content.getText()
                        .toString().trim();
                if (!TextUtils.isEmpty(trim)){
                    //获取集合  循环
                    for (int i= 0 ; i<chatLists.size();i++){
                        ChatMsgEntity chatMsgEntity = chatLists.get(i);
                        // 获取实体
                        String text = chatMsgEntity.getText();
                        if (text.contains(trim)){
                            //找到了
                            lv_show_message.smoothScrollToPosition(i);
                            //跳出
                            return;
                        }
                    }
                    ToastUtils.showToast(mContext,"没搜到相应的关键词,请重新输入");
                }else{
                    ToastUtils.showToast(mContext,"没有输入任何关键词,请重新输入");
                }
                break;
        }
    }

    //设置一个方法来监听软键盘

}
