package com.baituoyitong.kun.baituoyitong.mainpackage.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.baituoyitong.kun.baituoyitong.mainpackage.bean.DayNight;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.SpeakMode;


/**
 * 是一个sp的帮助类
 *
 */

public class CommonSPHelper {



    private final static String FILE_NAME = "settings";
    private final static String MODE = "day_night_mode";

    private final static String USERID = "id";
    private SharedPreferences mSharedPreferences;

    public CommonSPHelper(Context context) {
        // 获取sp对象

        this.mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存模式设置
     *
     * @param mode
     * @return
     */
    public boolean setMode(DayNight mode) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(MODE, mode.getName());
        return editor.commit();
    }

    /**
     * 夜间模式
     *
     * @return
     */
    public boolean isNight() {
        String mode = mSharedPreferences.getString(MODE, DayNight.DAY.getName());
        if (DayNight.NIGHT.getName().equals(mode)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 日间模式
     *
     * @return
     */
    public boolean isDay() {
        String mode = mSharedPreferences.getString(MODE, DayNight.DAY.getName());
        if (DayNight.DAY.getName().equals(mode)) {
            return true;
        } else {
            return false;
        }
    }

    //设置一个sp来保存 登录时候的个人id
    public boolean saveUserId(String id){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(USERID,id);
       return  editor.commit();
    }
    public String getUserid (){
        String string = mSharedPreferences.getString(USERID, null);
        return  string;
    }





}
