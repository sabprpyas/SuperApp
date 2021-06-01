package com.sky.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sky.R;

import java.io.Serializable;

/**
 * Created by sky on 16/5/10 下午3:50.
 * activity跳转
 */
public class JumpAct {
    /**
     * 跳转activity，并定义跳转动画
     *
     * @param packageContext A Context of the application package implementing
     *                       this class.
     * @param cls            The component class that is to be used for the intent.
     */
    public static void jumpActivity(Context packageContext, Class<?> cls) {
        jumpActivity(packageContext, new Intent(packageContext, cls));
    }

    public static void jumpActivity(Context packageContext, Class<?> cls, String name, CharSequence value) {
        jumpActivity(packageContext, new Intent(packageContext, cls).putExtra(name, value));
    }

    public static void jumpActivity(Context packageContext, Class<?> cls, String... values) {
        Intent intent = new Intent(packageContext, cls);
        String name = null;
        String value = null;
        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) name = values[i];
            else if (i % 2 == 1) value = values[i];
            intent.putExtra(name, value);
        }
        jumpActivity(packageContext, intent);
    }

    public static void jumpActivity(Context packageContext, Class<?> cls, String name, String entity) {
        jumpActivity(packageContext, new Intent(packageContext, cls).putExtra(name, entity));
    }

    public static void jumpActivity(Context packageContext, Class<?> cls, String name, Serializable entity) {
        jumpActivity(packageContext, new Intent(packageContext, cls).putExtra(name, entity));
    }

    public static void jumpActivity(Context packageContext, String componentName) {
        Intent intent = new Intent();
        intent.setClassName(packageContext, componentName);
        jumpActivity(packageContext, intent);
    }

    public static void jumpActivity(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        //overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
    }

}
