package com.sky.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by sky on 16/5/10 下午3:50.
 * 处理text文字的工具类
 */
public class TextUtil {
    /**
     * 获取控件中的内容
     *
     * @param text
     * @return
     */
    @NonNull
    public static String getText(TextView text) {
        return text.getText().toString().trim();
    }

    /**
     * 非空判断,同时提示
     *
     * @param text
     * @param toast
     * @return
     */
    public static boolean notNull(String text, String toast) {
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showShort(ActivityLifecycle.getInstance().getCurrentActivity(), toast + "不能为空");
            return true;
        }
        return false;
    }

    /**
     * 获取两位小数
     *
     * @param num
     * @return
     */
    @NonNull
    public static String getDecimalFormat(String num) {
        return getDecimalFormat(Double.parseDouble(num));
    }

    /**
     * 获取两位小数
     *
     * @param num
     * @return
     */
    @NonNull
    public static String getDecimalFormat(double num) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(num);
    }

    /**
     * 清除HTML
     *
     * @param content
     * @return
     */
    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        content = content.replaceAll("\r\n", "");
        content = content.replaceAll("&nbsp;", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }
}
