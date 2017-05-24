package com.baituoyitong.kun.baituoyitong.mainpackage.service;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.service
 * <p>
 * Created by ${kun} on 2017/5/2.
 */

public interface IControlMusic {
    //把想暴露的方法定义在接口里
    void callPlayMusic(String path);

    void callPauseMusic();

    void callRePlayMusic();

    void callSeekToPosition(int position);

    void callPreNext(int position);
    boolean callISPlay();
}
