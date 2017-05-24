package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.activity
 * <p>
 * Created by ${kun} on 2017/5/2.
 */
public class DownloadSoundListActivity extends AppCompatActivity {
    private static final String TAG = DownloadSoundListActivity.class.getSimpleName();
    private static final String SAVEURL = "mnt/sdcard/sanyida-debug.apk";
    private TextView tv_show_downloadurl;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("上传apk");
        setContentView(R.layout.activity_downloadsound);
        initView();

    }

    private void initView() {
        tv_show_downloadurl = (TextView) findViewById(R.id.tv_show_downloadurl);
    }

    public void upload(View v){

        //  上传文件
        //第一步   根据创建的表设置相应的 bean


           // uri = new URI(SAVEURL);
            File file = new File(SAVEURL);

            final BmobFile bmobFile = new BmobFile(file);
            //上传
            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        // 表示的就是 成功
                        ToastUtils.showToast(getBaseContext(),"上传成功");
                        // 获取url
                        url = bmobFile.getUrl();
                        // 获取  上传的url   进行下载
                        String fileUrl = bmobFile.getFileUrl();
                        String filename = bmobFile.getFilename();
                        //
                        tv_show_downloadurl.setText(fileUrl+"======="+filename+"区别"+url);
                        Log.d(TAG, "done:  看看url"+fileUrl);

                    }else{
                        ToastUtils.showToast(getBaseContext(),e.toString());
                    }

                }
                @Override
                public void onProgress(Integer value) {
                    super.onProgress(value);
                }

                @Override
                public void onFinish() {
                   // 获取  上传的url   进行下载
                    String fileUrl = bmobFile.getFileUrl();
                    String filename = bmobFile.getFilename();
                    Log.d(TAG, "onFinish: fileUrl"+fileUrl);
                    Log.d(TAG, "onFinish: filename"+filename);
                    //
                    tv_show_downloadurl.setText(fileUrl+"======="+filename+"区别"+url);


                }
            });
    }
    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
