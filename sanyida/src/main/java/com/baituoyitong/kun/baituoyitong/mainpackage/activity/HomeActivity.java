package com.baituoyitong.kun.baituoyitong.mainpackage.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.adapter.MyPagerAdapter;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.BluetoothController;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.DayNight;
import com.baituoyitong.kun.baituoyitong.mainpackage.eventbus.MessageEvent;
import com.baituoyitong.kun.baituoyitong.mainpackage.fragment.ChatFragment;
import com.baituoyitong.kun.baituoyitong.mainpackage.fragment.ShowChatFragment;
import com.baituoyitong.kun.baituoyitong.mainpackage.kedaxunfeicontrol.DialogMscControl;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.CommonSPHelper;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.DialogFragmentHelper;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;
import com.baituoyitong.kun.baituoyitong.mainpackage.view.CustomFAB;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;



/**
 * com.baituoyitong.kun.baituoyitong.activity
 * <p>
 * Created by ${kun} on 2017/4/10.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = HomeActivity.class.getSimpleName();
    private DrawerLayout my_drawlayout;
    private Context mContext;

    //// TODO: 2017/4/10        蓝牙判断和蓝牙的打开连接
    //// TODO: 2017/4/18        倒计时的点击进入的事件

    // 最新的思路
    private List<Fragment> fragmentList;
    private PagerSlidingTabStrip tabs;
    private ImageView iv_control_drawlayout;
    private List<Button> buttonList;
    public CommonSPHelper mDayNightHelper;
    private CustomFAB fab_main_customfab;
    private ChatFragment chatFragment;
    private ShowChatFragment showChatFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 初始化保存现在的是白天还是夜晚模式的sp
        initTheme();
        setContentView(R.layout.activity_main);
        mContext = HomeActivity.this;
        //初始化蓝牙
        initBluetooth();
        //初始化语音  展示初始化的进度
        initKeDaXunFei();
        //初始化布局
        initView();
        //设置沉浸式的状态栏
        initChenjinshi();
        // 最新的思路  就是一个viewpage嵌套 fragment来完成   各个操作是fragment中实现的
        initMainView();
        //将操作都在fragment里面
        initEvenbus();

        //设置浮动按钮的点击事件
        // initFAB();
    }

    private void initFAB() {

        fab_main_customfab = (CustomFAB) findViewById(R.id.fab_main_customfab);
        //  // TODO: 2017/4/27    要是  蓝牙连接状态没有连接或者是连接的uuid不是我们设置的 将不会显示   默认是不显示的
        if (true) {

            fab_main_customfab.setVisibility(View.VISIBLE);

            //可见的情况下设置点击事件  进行跳转到 发现页面

            fab_main_customfab.setOnClickListener(this);
        }

    }

    private void initEvenbus() {
        //注册
        EventBus.getDefault().register(this);
    }

    private void initMainView() {

        // 第一步就是  获取viewpage
        ViewPager vg_mian_communicate = (ViewPager) findViewById(R.id.vg_mian_communicate);
        // 创建fragment的集合  创建fragmnet的list
        fragmentList = new ArrayList<>();
        //创建fragment
        if (chatFragment == null) {
            chatFragment = new ChatFragment(mContext);
        }
        if (showChatFragment == null) {
            showChatFragment = new ShowChatFragment(mContext);
        }
        //添加到集合
        fragmentList.add(chatFragment);
        fragmentList.add(showChatFragment);
        //设置适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        vg_mian_communicate.setAdapter(myPagerAdapter);
        //设置展示的第二条目
        vg_mian_communicate.setCurrentItem(1);
        //设置每一tab的占用的宽度是一致的
        // 获取 tabs
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setViewPager(vg_mian_communicate);
    }

    private void initChenjinshi() {
        // TODO: 2017/4/17   沉浸式的实现

    }

    private void initView() {
        //初始化所有的布局
        my_drawlayout = (DrawerLayout) findViewById(R.id.my_drawlayout);
        // 获取控制侧滑菜单的按钮
        iv_control_drawlayout = (ImageView) findViewById(R.id.iv_control_drawlayout);
        //设置点击事件
        iv_control_drawlayout.setOnClickListener(this);
        // 获取分享的按钮
        findViewById(R.id.btn_share_zxing).setOnClickListener(this);

    }

    private boolean isOpenDrawlayout = true;

    private void initKeDaXunFei() {
        //初始化科大讯飞

        //测试的科大讯飞的 appid 58ec959e
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=58ed7c97");
    }

    private void initBluetooth() {
        //初始化蓝牙的连接 判断是否开启蓝牙
        BluetoothController bluetoothController = new BluetoothController(mContext);

        //判断是否支持蓝牙
        if (bluetoothController.isSupportBluetooth()) {
            //支持蓝牙设备  判断是不是打开蓝牙的状态
            if (bluetoothController.getBluetoothStatus()) {
                //蓝牙是打开的状态
                ToastUtils.showToast(mContext, "蓝牙已经打开");
            } else {
                //打开蓝牙的操作
                bluetoothController.turnOnBluetooth(HomeActivity.this, 1);
            }
        } else {
            DialogFragmentHelper.showTips(getSupportFragmentManager(), "你的设备不支持蓝牙设备");
            // 关闭页面
            finish();
        }
    }

    //打开蓝牙的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ToastUtils.showToast(HomeActivity.this, "蓝牙打开");
        } else {
            //表示客户点击了拒绝了   直接跳转到设置界面
            Intent intent = new Intent();
            //设置action
            intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
            //设置新的栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_control_drawlayout:
                //控制侧滑布局
                if (isOpenDrawlayout) {
                    //actionbar的左侧图标的点击事件处理   关联侧滑布局
                    my_drawlayout.openDrawer(Gravity.LEFT);
                    //// TODO: 2017/4/11   修改自定义toolbar的图标
                    //修改图标
                    iv_control_drawlayout.setBackgroundResource(R.drawable.menu);
                }
                break;
            case R.id.btn_share_zxing:
                //生成二维码,分享的页面

                //生成二维码
                Intent intent = new Intent(this, ShareZxingActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_main_customfab:
                //浮动按钮的点击事件
                Intent intent1 = new Intent(mContext, MusicDiscoveActivity.class);
                startActivity(intent1);
                break;
        }

    }

    //接收evetnbus
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.eventBusCode == 21) {
            LinearLayout ll_all = (LinearLayout) event.startThemeView;
            //呼气btn
            buttonList = event.buttonList;
            //设置动画是我们需要的
            showAnimation();
            //设置主题的颜色
            toggleThemeSetting();
            //刷新的界面
            refreshUI(ll_all);
            Log.d(TAG, "onMessageEvent: 看看主线程收到没有" + mDayNightHelper.isDay());
        }

    }

    private void initTheme() {
        mDayNightHelper = new CommonSPHelper(this);
        if (mDayNightHelper.isDay()) {
            setTheme(R.style.DayTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
    }

    /**
     * 展示一个切换动画
     */
    private void showAnimation() {

        //切换的动画  看看
        //获取顶级的 decoreView
        final View decorView = getWindow().getDecorView();
        // 获取截图
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            //  View
            final View view = new View(mContext);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            //属性动画

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }


    /**
     * 切换主题设置
     */
    private void toggleThemeSetting() {
        //获取此时是不是白天或者是夜间的模式
        if (mDayNightHelper.isDay()) {
            mDayNightHelper.setMode(DayNight.NIGHT);
            setTheme(R.style.NightTheme);
        } else {
            mDayNightHelper.setMode(DayNight.DAY);
            setTheme(R.style.DayTheme);
        }
    }

    /**
     * 刷新UI界面
     *
     * @param ll_all
     */
    private void refreshUI(LinearLayout ll_all) {
        TypedValue background = new TypedValue();//背景色
        TypedValue textColor = new TypedValue();//字体颜色
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.clockBackground, background, true);
        theme.resolveAttribute(R.attr.clockTextColor, textColor, true);
        Resources resources = getResources();
        // .(resources.getColor(textColor.resourceId));

        ll_all.setBackgroundResource(background.resourceId);


        //设置btn
        for (Button btn : buttonList) {
            btn.setBackgroundResource(background.resourceId);
        }
        //refreshStatusBar();
    }

    /**
     * 刷新 StatusBar
     */
    private void refreshStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  开始再次调用一下子
        if (chatFragment.dialogMscControl != null) {
            chatFragment.dialogMscControl.startDialogMsc();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);


    }


    @Override
    public void onBackPressed() {
    }

    private long exitTime = 0;


    public void ExitApp() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast(mContext, "在嗯一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    public void quit (View view){
        //停止 语义理解和tts播放  使用广播来进行
        chatFragment.dialogMscControl.stopTts();
        chatFragment.dialogMscControl.closeAll();

        ExitApp();

    }


}
