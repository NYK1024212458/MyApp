package com.baituoyitong.kun.baituoyitong.mainpackage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.MyUser;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.DialogFragmentHelper;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.IDialogResultListener;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.ToastUtils;

import java.util.Calendar;


import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;



/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.activity
 * <p>
 * Created by ${kun} on 2017/4/27.
 */

public class UpdatePersonInFoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtUserID;
    private TextView txtAccount,txtNickname;
    private DialogFragmentHelper dialogFragmentHelper;
    private String userid,desc;
    private RelativeLayout item_Nickname;
    private TextView lblNickname;
    private TextView txtMobile,txtEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("修改个人信息");
        setContentView(R.layout.activity_updateinfo);
        // 获取dialog
        dialogFragmentHelper = new DialogFragmentHelper();
        initView();
        initData();
    }

    private void initData() {
        // 获取里面的数据  之后就是更新里面的数据
        Intent intent = getIntent();
        // 获取里面的数据\
        userid = intent.getStringExtra("userid");
        desc = intent.getStringExtra("desc");
        String username = intent.getStringExtra("username");


        String mobilePhoneNumber1 = intent.getStringExtra("mobilePhoneNumber1");
        String email = intent.getStringExtra("email");

        //设置显示
        txtUserID.setText(userid);
        txtAccount.setText(username);
        txtNickname.setText(desc);
        txtMobile.setText(mobilePhoneNumber1);
        txtEmail.setText(email);


    }

    private void initView() {
        //  获取里面的控件  id展示
        txtUserID = (TextView) findViewById(R.id.txtUserID);

        //  展示的是登录的账户

        txtAccount = (TextView) findViewById(R.id.txtAccount);

        //
        item_Nickname = (RelativeLayout) findViewById(R.id.itemNickname);
        item_Nickname.setOnClickListener(this);

        //  呼气展示个性签名的
        txtNickname = (TextView) findViewById(R.id.txtNickname);


        findViewById(R.id.itemMobile).setOnClickListener(this);
        findViewById(R.id.itemEmail).setOnClickListener(this);
        findViewById(R.id.itemBirthday).setOnClickListener(this);
        findViewById(R.id.itemSex).setOnClickListener(this);


        txtMobile = (TextView) findViewById(R.id.txtMobile);
        txtEmail = (TextView) findViewById(R.id.txtEmail);




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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.itemNickname:
                //  设置个性签名的   都是dialog来完成的
                String titleInsert = "请输入个性签名";
                DialogFragmentHelper.showInsertDialog(getSupportFragmentManager(), titleInsert, new IDialogResultListener<String>() {
                    @Override
                    public void onDataResult(String result) {
                        if (result != null) {
                            //  获取result  进行跟新
                            final MyUser myUser = new MyUser();
                            myUser.setDesc(result);
                            myUser.update(userid, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        ToastUtils.showToast(getBaseContext(), "更新成功:" + myUser.getUpdatedAt());
                                    } else {
                                        ToastUtils.showToast(getBaseContext(), "更新失败：" + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            ToastUtils.showToast(getBaseContext(), "你没有输入任何东西");
                        }

                    }
                }, true);
                break;


            case R.id.itemMobile:
                //  设置手机号码的
                String itemMobile = "请输入电话号码";
                DialogFragmentHelper.showInsertDialog(getSupportFragmentManager(), itemMobile, new IDialogResultListener<String>() {
                    @Override
                    public void onDataResult(String result) {
                        if (result != null) {
                            //  获取result  进行跟新
                            final MyUser myUser1 = new MyUser();
                            myUser1.setMobilePhoneNumber(result);
                            myUser1.update(userid, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        ToastUtils.showToast(getBaseContext(), "更新成功:" + myUser1.getUpdatedAt());
                                    } else {
                                        ToastUtils.showToast(getBaseContext(), "更新失败：" + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            ToastUtils.showToast(getBaseContext(), "你没有输入任何东西");
                        }

                    }
                }, true);
                break;


            case R.id.itemEmail:
                //  设置邮箱的
                String itemEmail = "请输入邮箱";
                DialogFragmentHelper.showInsertDialog(getSupportFragmentManager(), itemEmail, new IDialogResultListener<String>() {
                    @Override
                    public void onDataResult(String result) {
                        if (result != null) {
                            //  获取result  进行跟新
                            final MyUser myUser2 = new MyUser();
                            myUser2.setEmail(result);
                            myUser2.update(userid, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        ToastUtils.showToast(getBaseContext(), "更新成功:" + myUser2.getUpdatedAt());
                                    } else {
                                        ToastUtils.showToast(getBaseContext(), "更新失败：" + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            ToastUtils.showToast(getBaseContext(), "你没有输入任何东西");
                        }

                    }
                }, true);
                break;


            case R.id.itemSex:

                final boolean[] isBoy = {false};
                //  设置性别的
                String titleList = "选择性别？";
                final String[] languanges = new String[]{"男", "女"};

                DialogFragmentHelper.showListDialog(getSupportFragmentManager(), titleList, languanges, new IDialogResultListener<Integer>() {
                    @Override
                    public void onDataResult(Integer result) {
                        String languange = languanges[result];
                        if (languange.equals("男")) {
                            // 你表示的男
                            isBoy[0] = true;

                        } else {
                            //nv
                            isBoy[0] =false;
                        }
                        // 开始跟新
                        final MyUser myUser = new MyUser();
                        myUser.setSex(isBoy[0]);
                        myUser.update(userid, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(getBaseContext(), "更新成功:" + myUser.getUpdatedAt());
                                } else {
                                    ToastUtils.showToast(getBaseContext(), "更新失败：" + e.getMessage());
                                }
                            }
                        });
                    }
                }, true);
                break;


            case R.id.itemBirthday:
                //  设置生日的
                String titleDate = "请选择日期";
                Calendar calendar = Calendar.getInstance();
                DialogFragmentHelper.showDateDialog(getSupportFragmentManager(), titleDate, calendar, new IDialogResultListener<Calendar>() {
                    @Override
                    public void onDataResult(Calendar result) {
                        ToastUtils.showToast(getBaseContext(), String.valueOf(result.getTime().getDate()));
                        //跟新生日
                        final MyUser myUser3 = new MyUser();
                        myUser3.setValue("brithady", String.valueOf(result.getTime().getDate()));
                        myUser3.update(userid, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.showToast(getBaseContext(), "更新成功:" + myUser3.getUpdatedAt());
                                } else {
                                    ToastUtils.showToast(getBaseContext(), "更新失败：" + e.getMessage());
                                }
                            }
                        });

                    }
                }, true);
                break;

        }


    }

}
