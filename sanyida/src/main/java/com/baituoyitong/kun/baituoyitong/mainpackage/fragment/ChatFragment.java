package com.baituoyitong.kun.baituoyitong.mainpackage.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
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
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

/**
 * com.baituoyitong.kun.baituoyitong.activity.fragment
 * <p>
 * Created by ${kun} on 2017/4/18.
 */
@SuppressLint("ValidFragment")
public class ChatFragment extends LazyFragment implements View.OnClickListener , BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private Context mContext;
    private static final String TAG =ChatFragment.class.getSimpleName() ;
    private Button btn_statr_chant, btn_stop_chant;
    public DialogMscControl dialogMscControl;
    private View item_frist;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    public ChatFragment(Context context) {
        mContext=context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //开始打起第一个布局   ll_first_fragment

        //创建布局   打气
        item_frist = inflater.inflate(R.layout.item_frist, null);
        initView(item_frist);
        isPrepared = true;
        lazyLoad();
        return item_frist;
    }
    private SliderLayout mDemoSlider;
    private void initView(View item_frist) {
        //  初始化展示的  轮播图片
        mDemoSlider= (SliderLayout) item_frist.findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(mContext);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_statr_chant:
                Log.d(TAG, "onClick: "+"是不是错误的"+btn_statr_chant.toString());


                dialogMscControl.startDialogMsc();

            break;
            case R.id.btn_stop_chant:

              //  ToastUtils.showToast(getContext(),"是不是错误的"+btn_stop_chant.toString());
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

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStop() {
        //  停止轮播
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
