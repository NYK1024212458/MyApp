package com.baituoyitong.kun.baituoyitong.mainpackage.bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.bean
 * <p>
 * Created by ${kun} on 2017/5/2.
 */

public class Apkurl extends BmobObject {
    //
    private BmobFile apk;//电影文件

    public BmobFile getApk() {
        return apk;
    }

    public void setApk(BmobFile apk) {
        this.apk = apk;
    }

    public Apkurl(BmobFile apk) {
        this.apk = apk;
    }

}
