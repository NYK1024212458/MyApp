package com.baituoyitong.kun.baituoyitong.mainpackage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baituoyitong.kun.baituoyitong.R;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.DownloadSoundListActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.activity.LocalSoundListActivity;
import com.baituoyitong.kun.baituoyitong.mainpackage.adapter.MyMeunListAdapter;
import com.baituoyitong.kun.baituoyitong.mainpackage.bean.Song;
import com.baituoyitong.kun.baituoyitong.mainpackage.utils.MusicUtil;

import java.util.List;

/**
 * com.baituoyitong.kun.baituoyitong.mainpackage.fragment
 * <p>
 * 大体的思路就是,就是在这一业获取数据 之后就是关于数据的传递问题   获取本地的size  设置显示    页面就是 播放所有和  右侧的管理页面    iteam 一个textview展示序列  一个送那么 一个是艺术家
 * 右边点击出现 dialog收藏还是删除     之后再详细的说明一下子
 *
 *
 * 下载管理也是一个思路  主要就是展示下载的个数   点击之后跳转到 对应的fragment 里面  这个fragment包括一个viewpage   展示的就是下载和没有下载的数据  布局差不多
 *
 *
 *
 * Created by ${kun} on 2017/4/28.
 */

public class MusicFragment extends Fragment implements View.OnClickListener {
    private static final String TAG  = MusicFragment.class.getSimpleName();
    private Context mContext;

    //本地歌曲信息
    private List<Song> localMusicData;

    //下载歌曲信息
    private List<Song> songDownload;


    //歌单信息
    private List<Song> songMenu;



    //歌单信息
    private ListView lv_song_menu ;
    private LinearLayout localMusicLayout;
    private TextView tv_local_music,tv_local_downloaded;
    private ImageView iv_my_list_arrow;
    private TextView tv_my_list1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();

        // 获取本机的音乐数据
        initData();
    }

    private void initData() {
        //  获取本地音乐的数据
        localMusicData = MusicUtil.getMusicData(mContext);

        // 获取下载的数据  // TODO: 2017/4/28



        Log.d(TAG, "initData: ");
        // 获取歌单的数据   // TODO: 2017/4/28





    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //转化布局初始化  替换
        View rootView = inflater.inflate(R.layout.fragment_localmusic, null);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        //  获取里面的额 本地音乐  设置点击事件和  歌曲信息
        localMusicLayout = (LinearLayout) rootView.findViewById(R.id.localMusicLayout);
        //设置点击事件
        localMusicLayout.setOnClickListener(this);

        //获取展示本地信息的  textview
        tv_local_music = (TextView) rootView.findViewById(R.id.tv_local_music);


        // 获取下载
       rootView.findViewById(R.id.downloadLayout).setOnClickListener(this);
        //获取展示本地信息的  textview
        tv_local_downloaded = (TextView) rootView.findViewById(R.id.tv_local_downloaded);

        // 获取歌单的布局
        rootView.findViewById(R.id.rlPrivate).setOnClickListener(this);

        //获取箭头设置动画
        iv_my_list_arrow = (ImageView) rootView.findViewById(R.id.iv_my_list_arrow);

        //歌单信息
        tv_my_list1 = (TextView) rootView.findViewById(R.id.tv_my_list1);


        // 获取里面的里面的listview  展示的使我们创建的歌单
        lv_song_menu = (ListView) rootView.findViewById(R.id.lv_song_menu);
        //设置适配器




        //设置  本地音乐的数据
        tv_local_music.setText("("+localMusicData.size()+")");
        //设置下载的数据
        /*tv_local_downloaded.setText("("+songDownload.size()+")");
        //设置歌单信息
        tv_my_list1.setText("("+songMenu.size()+")");*/
    }


    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.localMusicLayout:
               //点击进入  本地音乐的Activity中  里面
               Intent intent = new Intent(mContext, LocalSoundListActivity.class);
               mContext.startActivity(intent);

               break;
           case R.id.downloadLayout:
               //点击进入  下载音乐的fragment 里面

               Intent intent1 = new Intent(mContext, DownloadSoundListActivity.class);
               mContext.startActivity(intent1);


               break;
           case R.id.rlPrivate:
               //  // TODO: 2017/4/28  动画的实现
               //点击显示  歌单数据

               Animation rotateAnimation2= AnimationUtils.loadAnimation(mContext, R.anim.rotate_arrow);

               rotateAnimation2.setFillAfter(!rotateAnimation2.getFillAfter());
               iv_my_list_arrow.startAnimation(rotateAnimation2);

               /*//设置listview的适配器
               MyMeunListAdapter myMeunListAdapter = new MyMeunListAdapter(songMenu);
               lv_song_menu.setAdapter(myMeunListAdapter );*/

               break;
       }

    }
}
