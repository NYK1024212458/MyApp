package com.baituoyitong.kun.baituoyitong.mainpackage.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baituoyitong.kun.baituoyitong.mainpackage.bean.Song;

import java.util.List;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.adapter
 * <p>
 * Created by ${kun} on 2017/4/28.
 */

public class MyMeunListAdapter extends BaseAdapter {
    private List<Song> mData;
    public MyMeunListAdapter(List<Song> songMenu) {
        mData=songMenu;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
