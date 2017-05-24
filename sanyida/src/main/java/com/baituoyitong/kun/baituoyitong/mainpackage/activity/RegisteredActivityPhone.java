package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.MyUser;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.activity
 * <p>
 * Created by ${kun} on 2017/4/26.
 */
public class RegisteredActivityPhone  extends AppCompatActivity implements View.OnClickListener{
    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pass;
    private EditText et_password;

    private EditText et_email;
    private Button btnRegistered,btn_sendverifyno;
    //性别
    private boolean isGender = true;
    private EditText txt_verifyNo;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("电话注册");
        setContentView(R.layout.activity_registeredphone);

        initView();
    }
    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);

        btnRegistered = (Button) findViewById(R.id.btnRegistered);
        btnRegistered.setOnClickListener(this);
        // 发送验证码按钮
        btn_sendverifyno = (Button) findViewById(R.id.btn_sendverifyno);
        btn_sendverifyno.setOnClickListener(this);
        //  输入验证码的按钮
        txt_verifyNo = (EditText) findViewById(R.id.txt_VerifyNo);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendverifyno:
                //非空的判断


                //  点击后显示  倒计时的按钮



                //设置点击后显示的文字


                // 点击发送验证码
                BmobSMS.requestSMSCode(email,"nyk", new QueryListener<Integer>() {

                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        if(ex==null){//验证码发送成功
                            Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                            ToastUtils.showToast(RegisteredActivityPhone.this,smsId+"");
                        }
                    }
                });
                break;
            case R.id.btnRegistered:
                //不能点击的判断


                //获取到输入框的值
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                email = et_email.getText().toString().trim();

                // 获取验证码
                String txt_verify = txt_verifyNo.getText().toString().trim();


                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(pass) &
                        !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(email)&!TextUtils.isEmpty(txt_verify)) {

                    //判断两次输入的密码是否一致
                    if (pass.equals(password)) {

                        //先把性别判断一下
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.rb_boy) {
                                    isGender = true;
                                } else if (checkedId == R.id.rb_girl) {
                                    isGender = false;
                                }
                            }
                        });

                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc = getString(R.string.text_nothing);
                        }
                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setMobilePhoneNumber(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);
                        user.setDesc(desc);

                        //判断 短信验证码
                        user.signOrLogin(txt_verify, new SaveListener<MyUser>() {

                            @Override
                            public void done(MyUser user,BmobException e) {
                                if(e==null){

                                    ToastUtils.showToast(RegisteredActivityPhone.this,"注册成功");
                                    Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId());
                                }else{
                                    ToastUtils.showToast(RegisteredActivityPhone.this,"失败:" + e.getMessage());
                                }

                            }
                        });

                    } else {
                        Toast.makeText(this, R.string.text_two_input_not_consistent, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.text_tost_empty), Toast.LENGTH_SHORT).show();
                }

                break;
        }
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
