package com.sky.demo.api;

import com.sky.demo.ui.MyApplication;
import com.sky.demo.utils.LogUtils;

import org.xutils.ex.HttpException;

/**
 * Created by sky on 15/12/9 下午8:54.
 * idataresult的实现类
 */
public abstract class IDataResultImpl<T> implements IDataResult<T>{
    @Override
    public void onFailure(HttpException exception, int code) {
        LogUtils.d("请求失败" + code);
        MyApplication.getInstance().showErroe(code);
    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {
    }

    @Override
    public abstract void onSuccessData(T data);

    @Override
    public void onStart() {
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onFinish() {
    }
}
