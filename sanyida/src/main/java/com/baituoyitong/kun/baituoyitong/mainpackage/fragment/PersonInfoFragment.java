package com.baituoyitong.kun.baituoyitong.mainpackage.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.AdviceActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.ApkIntroduceActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.ContactActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.FlashActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.HomeActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.IntroduceActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.UpdatePersonInFoActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.MyUser;
import com.baituoyitong.kun.baituoyitong.mainpackage.eventbus.MessageEvent;

import com.baituoyitong.kun.baituoyitong.mainpackage.utils.CommonSPHelper;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.PackageUtils;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;
import com.baituoyitong.kun.baituoyitong.mainpackage.view.CustomRoundImageView;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.Bmob.getApplicationContext;
;

/**
 * com.baituoyitong.kun.baituoyitong.activity.fragment
 * <p>
 * Created by ${kun} on 2017/4/10.
 */

public class PersonInfoFragment extends Fragment implements View.OnClickListener {
    /**
     * 下载保存路径
     **/
    private static final String SAVEURL = "mnt/sdcard/sanyidasing.apk";
    private static final String mNewsUrl = "http://bmob-cdn-10945.b0.upaiyun.com/2017/05/04/c4613bcff61b41f8ac179496cd5f002e.apk";

    private Context mContext;

    private static final String TAG = PersonInfoFragment.class.getSimpleName();
    private static final int REQUEST_CODE = 1024;
    private static final int COMPRESS_REQUEST_CODE = 2048;
    private CustomRoundImageView navigation_user_head_iv;
    private LinearLayout ll_all;
    private Button btn_loginout;
    private List<Button> btn_list;
    private LinearLayout navi_frament_problem;
    private LinearLayout fragment_navi_checkupdate, navi_frament_brief_introduction, navi_frament_advice, navi_frament_contact, navi_frament_message;
    private ProgressDialog progressDialog;
    private TextView personinfo_tv_showname,personinfo_tv_showrobot,personinfo_tv_showid,personinfo_tv_showtelephone;
    private ImageButton personinfo_ib_update;
    private CommonSPHelper commonSPHelper;
    private String userid;
    private String username;
    private String userid1;
    private String desc;
    private String mobilePhoneNumber1;
    private String email;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取activity\
        HomeActivity activity = (HomeActivity) getActivity();
        mContext = activity;
        //创建集合
        btn_list = new ArrayList<>();
        // 初始化sp
        commonSPHelper = new CommonSPHelper(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personinfo, container, false);
        initViewPersoninfo(rootView);
        initView(rootView);
        initDta();
        return rootView;
    }

    private void initDta() {

        // 获取数据  进行修改数据
        //获取  id
        userid = commonSPHelper.getUserid();
        personinfo_tv_showid.setText("用户id:"+ userid);
        //查询
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.getObject(userid, new QueryListener<MyUser>() {

            @Override
            public void done(MyUser object, BmobException e) {
                if(e==null){
                    //获得年龄的信息
                    object.getAge();
                    personinfo_tv_showtelephone.setText("用户号码:"+object.getMobilePhoneNumber());
                    //获得数据的昵称

                    mobilePhoneNumber1 = object.getMobilePhoneNumber();

                    username = object.getUsername();
                    personinfo_tv_showname.setText( "用户名称:"+object.getUsername());
                    //获得
                    desc = object.getDesc();
                    personinfo_tv_showrobot.setText("个性描述:"+object.getDesc());

                    email = object.getEmail();


                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }

    private void initViewPersoninfo(View rootView) {
        //初始化
        //  头像的控件
        CustomRoundImageView navigation_user_head_iv = (CustomRoundImageView) rootView.findViewById(R.id.navigation_user_head_iv);

        //
        personinfo_tv_showname = (TextView) rootView.findViewById(R.id.personinfo_tv_showname);
        personinfo_tv_showrobot = (TextView) rootView.findViewById(R.id.personinfo_tv_showrobot);
        personinfo_tv_showid = (TextView) rootView.findViewById(R.id.personinfo_tv_showid);
        personinfo_tv_showtelephone = (TextView) rootView.findViewById(R.id.personinfo_tv_showtelephone);

         rootView.findViewById(R.id.personinfo_ib_update).setOnClickListener(this);


    }

    private void initView(View rootView) {
        rootView.findViewById(R.id.fragment_navi_checkupdate).setOnClickListener(this);

        // 获取头像的图片设置上传图片来设置头像的显示操作
        navigation_user_head_iv = (CustomRoundImageView) rootView.findViewById(R.id.navigation_user_head_iv);
        navigation_user_head_iv.setOnClickListener(this);


        // 获取换肤的布局
        rootView.findViewById(R.id.navi_frament_day_night).setOnClickListener(this);
        ll_all = (LinearLayout) rootView.findViewById(R.id.ll_all);

        // 获取注销的账号的按钮
        btn_loginout = (Button) rootView.findViewById(R.id.btn_loginout);
        //
        btn_list.add(btn_loginout);

        //获取使用说明的布局
        navi_frament_problem = (LinearLayout) rootView.findViewById(R.id.navi_frament_problem);
        //设置点击事件
        navi_frament_problem.setOnClickListener(this);

        // 获取版本跟新的设置 整体布局设置点击事件
        fragment_navi_checkupdate = (LinearLayout) rootView.findViewById(R.id.fragment_navi_checkupdate);
        fragment_navi_checkupdate.setOnClickListener(this);

        //公司简介
        navi_frament_brief_introduction = (LinearLayout) rootView.findViewById(R.id.navi_frament_brief_introduction);
        navi_frament_brief_introduction.setOnClickListener(this);

        //意见反馈
        navi_frament_advice = (LinearLayout) rootView.findViewById(R.id.navi_frament_advice);
        navi_frament_advice.setOnClickListener(this);

        //  联系我们

        navi_frament_contact = (LinearLayout) rootView.findViewById(R.id.navi_frament_contact);
        navi_frament_contact.setOnClickListener(this);


        //注销操作

        btn_loginout = (Button) rootView.findViewById(R.id.btn_loginout);
        btn_loginout.setOnClickListener(this);

        //动态的设置给textview
        TextView tv_show_versionName = (TextView) rootView.findViewById(R.id.tv_show_versionName);
        //设置
        tv_show_versionName.setText("版本号:" + PackageUtils.getVersionNme(mContext));


        //  最后一个就是   消息推送
        //  联系我们

        navi_frament_message = (LinearLayout) rootView.findViewById(R.id.navi_frament_message);
        navi_frament_message.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fragment_navi_checkupdate:
                //检查更新的操作   使用工具类
                int clientVersionCode = PackageUtils.getVersionCode(mContext);

                String clientVersionNme = PackageUtils.getVersionNme(mContext);
                //  网络请求 获取  网络上的versionname


                //进行判断  和完咯上的进行比较
                if (true){




                /*
                 * 伪代码:  如果  获取解析的json包括的   vername   大于  本地的
                 * clientVersionNme<  获取的到的数据
                 *   展示对话框
                 *
                 */


                    //模拟展示对话框

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("更新最新的版本");
                    builder.setIcon(R.mipmap.ic_launcher);
                    //设置  更新和取消的操作
                    builder.setPositiveButton("立即下载更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //  此处的逻辑就是   获取到的url  进行下载  并且显示进度条
                            downLoadApk();


                           // ToastUtils.showToast(mContext, "服务器限制 ");

                        }
                    });
                    builder.setNegativeButton("暂时不安装", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //提醒
                            ToastUtils.showToast(mContext, "你取消了版本更新");

                        }
                    });
                    builder.show();
                }else{
                    ToastUtils.showToast(mContext,"已经是最新了");
                }

                break;
            case R.id.navigation_user_head_iv:
                // 点击之后开始上跳转  切割图片
                //获取路径
                String cachePath = BoxingFileHelper.getCacheDir(mContext);
                Log.d(TAG, "onClick: 获取到的路劲地址" + cachePath);
                if (TextUtils.isEmpty(cachePath)) {

                    Toast.makeText(mContext, R.string.boxing_storage_deny, Toast.LENGTH_SHORT).show();

                    return;
                }
                //创建uri的操作

                Uri destUri = new Uri.Builder()
                        .scheme("file")
                        .appendPath(cachePath)
                        .appendPath(String.format(Locale.US, "%s.jpg", System.currentTimeMillis()))
                        .build();
                Log.d(TAG, "destUri: 获取到destUri是什么,没看懂" + destUri.toString());

                //设置参数
                BoxingConfig singleCropImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).withCropOption(new BoxingCropOption(destUri));

                //  boxingActivity是启动缩略图界面, 依赖boxing-impl.

                Boxing.of(singleCropImgConfig).withIntent(mContext, BoxingActivity.class).start(this, REQUEST_CODE);
                break;
            case R.id.btn_loginout:
                //注销按钮
                // 获取用户id  之后就是删除这个用户  关闭页面 退出程序
                userid1 = commonSPHelper.getUserid();
                //删除数据
                final MyUser p2 = new MyUser();
                p2.setObjectId(userid1);
                p2.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            ToastUtils.showToast(mContext,"删除成功:"+p2.getUpdatedAt());
                        }else{
                            ToastUtils.showToast(mContext,"删除失败：" + e.getMessage());
                        }
                    }

                });
                getActivity().finish();

                break;


            case R.id.navi_frament_day_night:
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.eventBusCode = 21;
                messageEvent.startThemeView = ll_all;
                messageEvent.buttonList = btn_list;
                EventBus.getDefault().post(messageEvent);
                break;
            case R.id.navi_frament_problem:
                //使用说明的点击事件
                Intent intent = new Intent(mContext, ApkIntroduceActivity.class);
                mContext.startActivity(intent);
                break;


            case R.id.navi_frament_brief_introduction:
                //公司简介
                Intent intent2 = new Intent(mContext, IntroduceActivity.class);
                mContext.startActivity(intent2);

                break;
            case R.id.navi_frament_advice:
                //公司简介
                Intent intent3 = new Intent(mContext, AdviceActivity.class);
                mContext.startActivity(intent3);
                break;


            case R.id.navi_frament_contact:
                // 联系我们的页面

                Intent intent4 = new Intent(mContext, ContactActivity.class);
                mContext.startActivity(intent4);
                break;

            case R.id.navi_frament_message:
                // 消息推送页面


                break;
            case R.id.personinfo_ib_update:
                //  修改个人信息  跳转  获取  修改个人的信息
                Intent intentPerson = new Intent(mContext,UpdatePersonInFoActivity.class);
                //  传递的就是用户得等和用户的登录名称  是不能修改的
                intentPerson.putExtra("userid", userid);
                intentPerson.putExtra("username",username);
                intentPerson.putExtra("desc",desc);
                intentPerson.putExtra("mobilePhoneNumber1",mobilePhoneNumber1);
                intentPerson.putExtra("email",email);
                startActivity(intentPerson);
                break;
        }
    }

    //网络请求来进行   apk的下载
    protected void downLoadApk() {
        //判断SD卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //展示对话框

            showProgressDialog();

            //从服务器下载,联网操作
            HttpUtils httpUtils = new HttpUtils();
            //url : 下载路径
            //问题:1.设置下载的路径;2.操作SD卡权限;3.确定SD卡是否挂载;4.生成一个2.0apk存放到服务器进行下载操作
            //target : 保存路径
            //callback : 请求回调函数

            httpUtils.download(mNewsUrl, SAVEURL, new RequestCallBack<File>() {

                @Override
                public void onSuccess(ResponseInfo<File> arg0) {
                    // 下载完毕的逻辑就是  调用系统进行软件的安装
                    installAPK();
                    //隐藏进度条对话框
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(HttpException arg0, String arg1) {

                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    progressDialog.setMax((int) total);//设置最大进度
                    progressDialog.setProgress((int) current);//设置当前进度
                }
            });
        }else{
            Toast.makeText(mContext, "没有发现可用的SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 弹出进度条对话框
     */
    private void showProgressDialog() {

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条的样式,STYLE_HORIZONTAL:水平样式
        progressDialog.setCancelable(false);//设置是否可以点击空白处消失
        progressDialog.show();//显示操作
    }

    /**
     * 4.安装最新版本的apk
     */
    protected void installAPK() {
        //通过隐式意图实现跳转到系统的安装界面
        /**
         *  <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="content" /> content:// 内容提供者
         <data android:scheme="file" />
         <data android:mimeType="application/vnd.android.package-archive" />
         </intent-filter>
         */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //setData和setType使用会相互覆盖
            /*intent.setData(Uri.fromFile(new File(SAVEURL)));
            intent.setType("application/vnd.android.package-archive");*/
        intent.setDataAndType(Uri.fromFile(new File(SAVEURL)), "application/vnd.android.package-archive");
        //startActivity(intent);
        //当前跳转的界面退出的时候,会调用之前界面的onActivityResult方法
        startActivityForResult(intent, 0);
    }


    //获取数据
    private String path2;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            // 安装最新的apk完成  跳转到首页
            enterFlash();


        }
        if (resultCode == RESULT_OK) {
            ArrayList<BaseMedia> medias1 = Boxing.getResult(data);
            BaseMedia imageMedia1 = medias1.get(0);
            //转化为图片的形式
            if (imageMedia1 instanceof ImageMedia) {
                path2 = ((ImageMedia) imageMedia1).getThumbnailPath();
                Log.d(TAG, "onActivityResult:  看看path2 " + path2);
            } else {
                path2 = imageMedia1.getPath();
                Log.d(TAG, "onActivityResult:  看看  else的path2 " + path2);
            }
            //参数说明   第一个就是要设置的哪一个控件   获取到要设置图片的控件    最后的两个参数是指宽和长
            BoxingMediaLoader.getInstance().displayThumbnail(navigation_user_head_iv, path2, 62, 62);
            Log.d(TAG, "onActivityResult:  看看  最后走没有走 " + imageMedia1.toString());

            //获取设置的新的头像保存到  网络上
            Drawable drawable = navigation_user_head_iv.getDrawable();

            //  先转化为  bitmap    之后转为 png 的格式
            BitmapDrawable bd = (BitmapDrawable) drawable;
            //  转化为 bitmap

            Bitmap bm = bd.getBitmap();
            //将这个保存到本地
            //File file = new File()

            saveBitmap2file(bm,"userpic");



            // 保存到 user上面

           /* private void upload() {

                File f = new File(Environment.getExternalStorageDirectory()+"/image/test.jpg");

                final BmobFile file = new BmobFile(f);

                file.upload(mContext, new UploadFileListener() {


                    @Override
                    public void done(BmobException e) {

                    }

                    public void onSuccess() {


                        String url = file.getUrl();

                        Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();

                    }


                    public void onFailure(int arg0, String arg1) {



                        Toast.makeText(mContext, "上传失败"+arg1, Toast.LENGTH_SHORT).show();

                    }

                });*/

            




        }

    }

    private void enterFlash() {
        //进入flash页面
        Intent intent = new Intent(mContext, FlashActivity.class);
        startActivity(intent);
    }


    //将bitmap转化为 file文件


    static boolean saveBitmap2file(Bitmap bmp,String filename){
        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" + filename);
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }
}
