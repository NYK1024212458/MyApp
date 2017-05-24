package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.ClientAdvice;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.activity
 * <p>
 * Created by ${kun} on 2017/4/25.
 */

public class AdviceActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AdviceActivity.class.getSimpleName();
    private static final int MAX_LENGTH = 100;
    private Context mContext;

    private ImageButton btnBack;
    private EditText txtFeedback, txtContact;
    private TextView txtWordsTip;
    String[] single_list = {"功能意见", "性能意见", "用户体验", "其他建议"};
    private TextView txtFeedType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        mContext = AdviceActivity.this;
        //初始化的操作

        initView();
    }

    private void initView() {
        //  第一个就是回退的操作

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        // 获取显示反馈的类型和
        txtFeedType = (TextView) findViewById(R.id.txtFeedType);

        // 获取字数统计
        txtWordsTip = (TextView) findViewById(R.id.txtWordsTip);

        //反馈的类型选择
        findViewById(R.id.btnChooseType).setOnClickListener(this);

        // 获取意见呢的et  点击提交后获取信息
        txtFeedback = (EditText) findViewById(R.id.txtFeedback);
        //监听

        txtFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //改变了!
                String content = txtFeedback.getText().toString();
                txtWordsTip.setText(content.length() + "/"
                        + MAX_LENGTH);
                if (content.length()==MAX_LENGTH) {
                    // 提示 并且不能够再次输入
                    ToastUtils.showToast(mContext, "你的输入太多了!");
                    //禁止输入
                    txtFeedback.setEnabled(false);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //  获取联系方式
        txtContact = (EditText) findViewById(R.id.txtContact);
        // 获取提交按钮
        findViewById(R.id.btnSave).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnBack:
                // 回退按钮
                finish();
                break;
            case R.id.btnChooseType:
                //选择的类型  点击是一个单选按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("单选对话框");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setSingleChoiceItems(single_list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = single_list[which];
                        //设置显示的类型
                        txtFeedType.setText(str);
                        ToastUtils.showToast(mContext, "被点击了");
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.btnSave:
                //提交按钮   上传到服务端

                // 首先是获取   类型    获取类型
                String txt_Type = txtFeedType.getText().toString().trim();
                // 获取意见内容
                String txt_content = txtFeedback.getText().toString();
                //或联系人的联系方式
                String txt_connect = txtContact.getText().toString().trim();
                Log.d(TAG, "onClick: "+txt_connect+txt_content+txt_Type);

                //创建bena  设置上传  需要判断
                if (TextUtils.isEmpty(txt_Type)||TextUtils.isEmpty(txt_content)||TextUtils.isEmpty(txt_connect)){
                    ToastUtils.showToast(mContext, "请认真填写");
                }else{
                    ClientAdvice clientAdvice = new ClientAdvice();
                    clientAdvice.setTxt_connect(txt_connect);
                    clientAdvice.setTxt_content(txt_content);
                    clientAdvice.setTxt_type(txt_Type);
                    //保存到bmob服务器
                    clientAdvice.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                ToastUtils.showToast(mContext,"创建数据成功：" + s);
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                    ToastUtils.showToast(mContext, "上传成功,感谢你的建议");
                }
                break;
        }
    }
}
