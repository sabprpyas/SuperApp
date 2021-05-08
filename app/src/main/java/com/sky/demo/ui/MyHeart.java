package com.sky.demo.ui;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.demo.R;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/2/19 下午3:16
 */
public class MyHeart extends BaseActivity {
    TextView tv;

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

