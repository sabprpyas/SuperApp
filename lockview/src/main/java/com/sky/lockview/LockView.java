package com.sky.lockview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/12/7 上午11:27
 */
public class LockView extends View {


    public LockView(Context context) {
        this(context,null);
    }

    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
