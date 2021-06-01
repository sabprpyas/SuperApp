package com.sky.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by sky on 16/5/10 下午3:50.
 * 自定义的handler
 */
public class UIHandler extends Handler {

    private IHandler handler;//回调接口，消息传递给注册者

    public UIHandler(Looper looper) {
        super(looper);
    }

    public UIHandler(Looper looper, IHandler handler) {
        super(looper);
        this.handler = handler;
    }

    public void setHandler(IHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (handler != null) {
            handler.handleMessage(msg);//有消息，就传递
        }
    }

    public interface IHandler {
        void handleMessage(Message msg);
    }
}