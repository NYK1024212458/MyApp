package com.baituoyitong.kun.baituoyitong.mainpackage.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.kedaxunfeicontrol.DialogMscControl;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;

/**
 * com.baituoyitong.kun.baituoyitong.activity.fragment
 * <p>
 * Created by ${kun} on 2017/4/18.
 */

public class ChatFragment extends LazyFragment implements View.OnClickListener {

    private static final String TAG =ChatFragment.class.getSimpleName() ;
    private Button btn_statr_chant, btn_stop_chant;
    public DialogMscControl dialogMscControl;
    private View item_frist;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //开始打起第一个布局   ll_first_fragment

        //创建布局   打气
        item_frist = inflater.inflate(R.layout.item_frist, null);
        isPrepared = true;
        lazyLoad();
        return item_frist;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_statr_chant:
                Log.d(TAG, "onClick: "+"是不是错误的"+btn_statr_chant.toString());

                dialogMscControl.startDialogMsc();

            break;
            case R.id.btn_stop_chant:

                ToastUtils.showToast(getContext(),"是不是错误的"+btn_stop_chant.toString());
                dialogMscControl.stopTts();
                break;
        }

    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        // 开始一些初始化操作
        dialogMscControl = new DialogMscControl(getContext());
        // 获取按钮
        btn_statr_chant = (Button) item_frist.findViewById(R.id.btn_statr_chant);
        btn_stop_chant = (Button) item_frist.findViewById(R.id.btn_stop_chant);
        //设置点击事件
        btn_statr_chant.setOnClickListener(this);
        btn_stop_chant.setOnClickListener(this);
    }
}
