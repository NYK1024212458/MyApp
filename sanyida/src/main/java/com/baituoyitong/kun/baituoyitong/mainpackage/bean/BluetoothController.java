package com.baituoyitong.kun.baituoyitong.mainpackage.bean;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import java.util.Set;

/**
 * com.baituoyitong.kun.baituoyitong.activity.bean
 * <p>
 * Created by ${kun} on 2017/4/11.
 */

public class BluetoothController {
    /**
     * //是一个蓝牙控制管理类
     * //注意权限的设置
     * 蓝牙管理类
     */
    //获取上下文  开启跳转的
private Context mcContext ;

    private BluetoothAdapter mAdapter;


    public BluetoothController(Context context) {
        this.mcContext = context;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * 判断当前设备是否支持蓝牙
     *
     * @return
     */
    public boolean isSupportBluetooth() {
        if (mAdapter != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取蓝牙的状态
     *
     * @return
     */
    public boolean getBluetoothStatus() {
        if (mAdapter != null) {
            return mAdapter.isEnabled();
        }
        return false;
    }

    /**
     * 打开蓝牙
     *
     * @param activity
     * @param requestCode
     */
    public void turnOnBluetooth(Activity activity, int requestCode) {
        if (mAdapter != null && !mAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 关闭蓝牙
     */
    public void turnOffBluetooth() {
        if (mAdapter != null && mAdapter.isEnabled()) {
            mAdapter.disable();
        }
    }

    /**
     * 获取已经配对的设备
     * 前提条件是,打开蓝牙,获得授权
     */
    public Set<BluetoothDevice> getConnectedDevice() {
        //判断是否打开和授权
        if ((mAdapter != null) && mAdapter.isEnabled()) {
            return mAdapter.getBondedDevices();

        }
        return null;

    }


    /**
     * 此方法是用来扫面周围可见的蓝牙设备
     * 时间是十二秒左右,可以设置为三十秒
     */
    public void openDiscoverAblity() {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //定义持续时间
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        mcContext.startActivity(discoverableIntent);
    }

    // 开始搜索周围可见得蓝牙设备
    public void searchDevice() {
        mAdapter.startDiscovery();
    }


    // 获取本地蓝牙适配器
    public BluetoothAdapter getmAdapter() {
        return mAdapter;
    }

}

