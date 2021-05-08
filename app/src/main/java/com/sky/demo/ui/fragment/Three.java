package com.sky.demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.sky.demo.R;
import com.sky.demo.ui.BaseFragment;
import com.sky.demo.utils.LogUtils;

import org.xutils.view.annotation.ContentView;


@ContentView(R.layout.fragment_three)
public class Three extends BaseFragment {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LogUtils.d("msg=0");
                    int math1 = (int) (Math.random() * 10);
                    handler.sendEmptyMessageDelayed(math1, 5000);
                    break;
                case 1:
                    LogUtils.d("msg=1");

                    int math2 = (int) (Math.random() * 10);
                    handler.sendEmptyMessageDelayed(math2, 10000);
                    break;
                default:
                    LogUtils.d("msg="+msg.what);
                    int math3 = (int) (Math.random() * 10);
                    handler.sendEmptyMessageDelayed(math3, 100);
                    break;
            }

        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        handler.post(new Runnable() {
//            public void run() {
//                //通知错误消息
//            }
//        });
        new Animator().run();
    }

    class Animator implements Runnable {

        @Override
        public void run() {
//            while (!Thread.currentThread().isInterrupted()) {
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
            try {
                Thread.sleep(00);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
//            }
        }
    }
}
