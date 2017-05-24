package com.itheima.xfyuntest01;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public class MyShowAdapter extends BaseAdapter {

    private List<String> mData ;
    private Context mContext ;

    public MyShowAdapter(List<String> reList,Context con) {
        mData= reList;
        mContext=con;
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
