package com.baituoyitong.kun.baituoyitong.mainpackage.bean;

import cn.bmob.v3.BmobObject;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.bean
 * <p>
 * Created by ${kun} on 2017/4/25.
 */

public class ClientAdvice extends BmobObject{
    // 这个 类是用来上传客户的建议的类   支持bmob的一一对应
    //设置里面的类型\
    String txt_type;
    //建议的内容
    String  txt_content;

    //建议的联系人
    String txt_connect;

    public String getTxt_type() {
        return txt_type;
    }

    public void setTxt_type(String txt_type) {
        this.txt_type = txt_type;
    }

    public String getTxt_content() {
        return txt_content;
    }

    public void setTxt_content(String txt_content) {
        this.txt_content = txt_content;
    }

    public String getTxt_connect() {
        return txt_connect;
    }

    public void setTxt_connect(String txt_connect) {
        this.txt_connect = txt_connect;
    }
}
