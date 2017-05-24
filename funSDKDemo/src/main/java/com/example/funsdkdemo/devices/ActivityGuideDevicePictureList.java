package com.example.funsdkdemo.devices;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.funsdkdemo.ActivityDemo;
import com.example.funsdkdemo.R;
import com.example.funsdkdemo.adapter.DeviceCameraPicAdapter;
import com.lib.SDKCONST;
import com.lib.funsdk.support.OnFunDeviceOptListener;
import com.lib.funsdk.support.config.OPCompressPic;
import com.lib.funsdk.support.models.FunDevice;
import com.lib.funsdk.support.FunPath;
import com.lib.funsdk.support.FunSupport;
import com.lib.funsdk.support.models.FunFileData;
import com.lib.sdk.struct.H264_DVR_FILE_DATA;
import com.lib.sdk.struct.H264_DVR_FINDINFO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Demo: 设备图片列表,点击图片后大开原图参考->ActivityGuideDevicePicBrowser
 *
 */
public class ActivityGuideDevicePictureList extends ActivityDemo implements OnClickListener,
        AdapterView.OnItemClickListener, OnFunDeviceOptListener, RadioGroup.OnCheckedChangeListener{

    private FunDevice mFunDevice = null;
    private String mFileType = null;
    private H264_DVR_FINDINFO findInfo = null;
    private int pictureType = SDKCONST.SDK_File_Type.SDK_PIC_ALL;
    private List<FunFileData> mDatas = new ArrayList<FunFileData>();
    private DeviceCameraPicAdapter mDeviceCameraPicAdapter;
    private Calendar calendar;

    private TextView mTextTitle = null;
    private ImageButton mBtnBack = null;
    private ImageButton mBtnDateSelector = null;
    private ListView mImageList = null;
    private RadioGroup rgPicType = null;
    private RadioButton rbAll = null;
    private RadioButton rbAlarm = null;
    private RadioButton rbManual = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_picture_list);

        int devId = getIntent().getIntExtra("FUN_DEVICE_ID", 0);
        FunDevice funDevice = FunSupport.getInstance().findDeviceById(devId);
        if (null == funDevice) {
            finish();
            return;
        } else {
            mFunDevice = funDevice;
        }

        FunPath.onCreateSptTempPath(mFunDevice.getSerialNo());

        mFileType = getIntent().getStringExtra("FILE_TYPE");
        if (null == mFileType) {
            mFileType = "jpg";
        }

        calendar = Calendar.getInstance();

        mTextTitle = (TextView) findViewById(R.id.textViewInTopLayout);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mTextTitle.setText(sdf.format(calendar.getTime()));
        mBtnBack = (ImageButton) findViewById(R.id.backBtnInTopLayout);
        mBtnBack.setOnClickListener(this);
        mBtnDateSelector = (ImageButton) findViewById(R.id.ib_date_selector);
        mBtnDateSelector.setOnClickListener(this);
        mImageList = (ListView) findViewById(R.id.lv_images);
        mDeviceCameraPicAdapter = new DeviceCameraPicAdapter(this, mImageList, mFunDevice, mDatas);
        mImageList.setAdapter(mDeviceCameraPicAdapter);
        mImageList.setOnItemClickListener(this);
        rgPicType = (RadioGroup) findViewById(R.id.rg_pic_type);
        rgPicType.setOnCheckedChangeListener(this);
        rbAll = (RadioButton) findViewById(R.id.rb_all);
        rbAlarm = (RadioButton) findViewById(R.id.rb_alarm);
        rbManual = (RadioButton) findViewById(R.id.rb_manual);
        rbAll.setChecked(true);

        FunSupport.getInstance().registerOnFunDeviceOptListener(this);
        onSearchPicture(calendar.getTime(), 0);
    }

    @Override
    protected void onDestroy() {

        FunSupport.getInstance().removeOnFunDeviceOptListener(this);

        // 是否Adapter中使用的资源
        if (null != mDeviceCameraPicAdapter) {
            mDeviceCameraPicAdapter.release();
        }
        
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtnInTopLayout: {
                // 返回/退出
                finish();
            }
            break;
            case R.id.ib_date_selector: {
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        mTextTitle.setText(sdf.format(calendar.getTime()));
                        onSearchPicture(calendar.getTime(), 0);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
            break;


        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_all:
                pictureType = SDKCONST.SDK_File_Type.SDK_PIC_ALL;
                break;
            case R.id.rb_alarm:
                pictureType = SDKCONST.SDK_File_Type.SDK_PIC_DETECT;
                break;
            case R.id.rb_manual:
                pictureType = SDKCONST.SDK_File_Type.SDK_PIC_MANUAL;
                break;
        }
        mDeviceCameraPicAdapter.resetDispItem();
        onSearchPicture(calendar.getTime(), 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int type;
        FunFileData imageInfo = mDatas.get(position);

        if (imageInfo != null) {
            type = imageInfo.getFileType();
        } else {
            type = 0;
        }

        Intent it = null;
        if (type == SDKCONST.PicFileType.PIC_BURST_SHOOT
                || type == SDKCONST.PicFileType.PIC_TIME_LAPSE) {
            it = new Intent(this, ActivityGuideDevicePicBrowser.class);
        } else {
            it = new Intent(this, ActivityGuideDeviceNormalPic.class);
        }
        it.putExtra("position", position);
        it.putExtra("FUN_DEVICE_ID", mFunDevice.getId());
        startActivity(it);
    }

    private void onSearchPicture(Date date, int flag) {
        showWaitDialog();
        findInfo = new H264_DVR_FINDINFO();
        findInfo.st_1_nFileType = pictureType;
        initSearchInfo(findInfo, date, 0, flag);
        FunSupport.getInstance().requestDeviceFileList(mFunDevice, findInfo);
    }

    private void initSearchInfo(H264_DVR_FINDINFO info, Date date, int channel,
                                int flag) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        info.st_2_startTime.st_0_dwYear = c.get(Calendar.YEAR);
        info.st_2_startTime.st_1_dwMonth = c.get(Calendar.MONTH) + 1;
        info.st_2_startTime.st_2_dwDay = c.get(Calendar.DATE);
        info.st_2_startTime.st_3_dwHour = 0;
        info.st_2_startTime.st_4_dwMinute = 0;
        info.st_2_startTime.st_5_dwSecond = 0;
        info.st_3_endTime.st_0_dwYear = c.get(Calendar.YEAR);
        info.st_3_endTime.st_1_dwMonth = c.get(Calendar.MONTH) + 1;
        info.st_3_endTime.st_2_dwDay = c.get(Calendar.DATE);
        if (flag == 0) {
            info.st_3_endTime.st_3_dwHour = 23;
            info.st_3_endTime.st_4_dwMinute = 59;
            info.st_3_endTime.st_5_dwSecond = 59;
        } else {
            info.st_3_endTime.st_3_dwHour = c.get(Calendar.HOUR_OF_DAY);
            info.st_3_endTime.st_4_dwMinute = c.get(Calendar.MINUTE);
            info.st_3_endTime.st_5_dwSecond = c.get(Calendar.SECOND);
        }
        info.st_0_nChannelN0 = channel;
    }


    @Override
    public void onDeviceLoginSuccess(FunDevice funDevice) {

    }

    @Override
    public void onDeviceLoginFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceGetConfigSuccess(FunDevice funDevice, String configName, int nSeq) {

    }

    @Override
    public void onDeviceGetConfigFailed(FunDevice funDevice, Integer errCode) {

    }

    @Override
    public void onDeviceSetConfigSuccess(final FunDevice funDevice, 
    		final String configName) {

    }

    @Override
    public void onDeviceSetConfigFailed(final FunDevice funDevice, 
    		final String configName, final Integer errCode) {

    }

    @Override
    public void onDeviceChangeInfoSuccess(final FunDevice funDevice) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDeviceChangeInfoFailed(final FunDevice funDevice, final Integer errCode) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void onDeviceOptionSuccess(final FunDevice funDevice, final String option) {
		// TODO Auto-generated method stub
	}
	
    @Override
	public void onDeviceOptionFailed(final FunDevice funDevice, final String option, final Integer errCode) {
		// TODO Auto-generated method stub
	}

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice) {

    }

    private void notifyDataSetChanged() {
        if (null != mDeviceCameraPicAdapter) {
            mDeviceCameraPicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeviceFileListChanged(FunDevice funDevice, H264_DVR_FILE_DATA[] datas) {

        hideWaitDialog();

        if (null != funDevice
                && null != mFunDevice
                && funDevice.getId() == mFunDevice.getId()) {

            mDatas.clear();
            for (H264_DVR_FILE_DATA data : datas) {
                FunFileData funFileData = new FunFileData(data, new OPCompressPic());
                mDatas.add(funFileData);
            }

            mFunDevice.mDatas = mDatas;

            notifyDataSetChanged();
            
            if ( mDatas.size() == 0 ) {
            	showToast(R.string.device_pic_list_empty);
            }
        }


    }


}
