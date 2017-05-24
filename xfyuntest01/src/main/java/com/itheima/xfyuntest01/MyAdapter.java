package com.itheima.xfyuntest01;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public class MyAdapter extends BaseAdapter {
    private List<String> mData ;
    private  Context mContext ;

    public MyAdapter(List<String> s, Context context) {
        mData= s;
        mContext=context;

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = new TextView(mContext);
        }


        TextView textView = (TextView) convertView;
        textView.setText(mData.get(position));

        return convertView;
    }
}
