package com.sky.demo.ui;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.demo.R;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/2/19 下午3:16
 */
public class MyHeart extends BaseActivity {
    TextView tv;
    private Lock lock= new ReentrantLock();
    int nub =1;
    //lock测试
    public void nub(){
        lock.lock();//锁定
        try{
            nub++;
        }finally {
            lock.unlock();//解锁
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView view = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        View group = LayoutInflater.from(this).inflate(R.layout.activity_toolbar, null);
        layout.addView(group);

        tv = new TextView(this);
        layout.addView(tv);

        view.addView(layout);
        setContentView(view);
        setToolbar();
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setText(getMyHeart());
    }

    private String getMyHeart() {
        return getString(R.string.daodejing);
    }
}

