package com.sky.demo.ui.activity.custom;

import android.os.Bundle;

import com.sky.demo.R;
import com.sky.demo.ui.BaseActivity;

/**
 * @author 彬 QQ 1136096189
 * @Description: TODO 下拉
 * @date 2015/8/19 15:31
 */
public class PullDownActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulldown);
        showToast(getIntent().getStringExtra("message"));
    }
}
