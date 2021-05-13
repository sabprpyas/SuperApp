package com.sky.demo.ui;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/12/13 下午5:53
 */
public class RecycleHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
//    private static BaseHolder holder;

    public RecycleHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

//    public static BaseHolder getHolder(View view) {
//        if (holder == null) {
//            synchronized (BaseHolder.class) {
//                if (holder == null) {
//                    holder = new BaseHolder(view);
//                }
//            }
//        }
//        return holder;
//    }

    public <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public void setText(int id, CharSequence text) {
//        TextView tv=getView(id);
//        tv.setText(text);
        ((TextView) getView(id)).setText(text);
    }

    public void setImage(int id, int imgId) {
        getView(id).setBackgroundResource(imgId);
    }

    public void setImageBitmap(int id, Bitmap bm) {
        ((ImageView) getView(id)).setImageBitmap(bm);

    }
}
