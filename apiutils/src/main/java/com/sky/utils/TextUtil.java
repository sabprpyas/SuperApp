package com.sky.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/4/21 下午1:08
 */
public class TextUtil {
    @NonNull
    public static String getText(TextView text) {
        return text.getText().toString().trim();
    }

    public static boolean notNull(String text, String toast) {
        if (TextUtils.isEmpty(text)) {
           ToastUtils.showShort(ActivityLifecycle.getInstance().getCurrentActivity(),toast + "不能为空");
            return true;
        }
        return false;
    }

    @NonNull
    public static String getDecimalFormat(String num) {
        return getDecimalFormat(Double.parseDouble(num));
    }

    @NonNull
    public static String getDecimalFormat(double num) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(num);
    }

    /**
     * 清楚HTML
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
