package com.baituoyitong.kun.baituoyitong.mainpackage.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.Song;

import java.util.List;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.adapter
 * <p>
 * Created by ${kun} on 2017/4/28.
 */

public class MyAllMusicAdapter extends BaseAdapter {
    private static final String TAG = MyAllMusicAdapter.class.getSimpleName();
    private List<Song> mData;


    public MyAllMusicAdapter(List<Song> musicData) {
        //  接收数据
        mData=musicData;
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
        //布局展示

        MyViewHolder viewHolder = null;
        // 写一个盒子来
        if (convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.list_item_sound,null);
            viewHolder = new MyViewHolder();
            viewHolder.llNo= (RelativeLayout) convertView.findViewById(R.id.ll_No);
            viewHolder.imgPlaying= (ImageView) convertView.findViewById(R.id.img_Playing);
            viewHolder.btnSingle=(ImageView) convertView.findViewById(R.id.btn_Single);
            viewHolder.tv_sound_no= (TextView) convertView.findViewById(R.id.tv_sound_no);
            viewHolder.tv_sound_name= (TextView) convertView.findViewById(R.id.tv_sound_name);
            viewHolder.tv_sound_author= (TextView) convertView.findViewById(R.id.tv_sound_author);
            viewHolder.tv_sound_remark= (TextView) convertView.findViewById(R.id.tv_sound_remark);


            //记得装到盒子了
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (MyViewHolder) convertView.getTag();

        }
        //设置数据
        viewHolder.tv_sound_no.setText(position+"1");
        viewHolder.tv_sound_name.setText(mData.get(position).song);
        viewHolder.tv_sound_author.setText(mData.get(position).singer);

        return convertView;
    }


    class MyViewHolder{
        RelativeLayout llNo;
        ImageView imgPlaying,btnSingle;
        TextView tv_sound_no,tv_sound_name,tv_sound_author,tv_sound_remark;


    }
}
