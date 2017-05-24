package com.baituoyitong.kun.baituoyitong.mainpackage.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * com.baituoyitong.kun.baituoyitong.activity.adapter
 * <p>
 * Created by ${kun} on 2017/4/18.
 */

 public class MyPagerAdapter extends FragmentPagerAdapter {
    private  FragmentManager fm;
    private  List<Fragment> mData;
    String tittleList[] = new String[]{"聊天界面", "聊天查看界面"};
    public MyPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> fragmentList) {
        super(supportFragmentManager);
        this.fm=supportFragmentManager;
        this.mData=fragmentList;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    //  设置显示的标题
    @Override
    public CharSequence getPageTitle(int position) {


        return tittleList[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }
}
