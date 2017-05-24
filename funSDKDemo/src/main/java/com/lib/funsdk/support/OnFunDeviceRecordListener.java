package com.lib.funsdk.support;

import com.lib.funsdk.support.models.FunDevRecordFile;

import java.util.List;

public interface OnFunDeviceRecordListener extends OnFunListener {

    public void onRequestRecordListSuccess(List<FunDevRecordFile> files);

    public void onRequestRecordListFailed(final Integer errCode);

}
