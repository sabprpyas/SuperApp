package com.sky.demo.ui.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.sky.demo.R;

import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/11 上午12:27
 */
public abstract class BasePop<T> extends PopupWindow {
    protected int width;
    protected int height;
    protected List<T> popDatas;
    protected View view;
    protected Context context;

    public BasePop(View contentView) {
        this(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public BasePop(View contentView, int width, int height) {
        this(contentView, width, height, true);
    }

    public BasePop(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        this.view = contentView;
        context = contentView.getContext();
        setAnimationStyle(R.style.AnimationFade);

        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        initView();
        initEvent();
    }


    public List<T> getDatas() {
        return popDatas;
    }

    public void setDatas(List<T> datas) {
        this.popDatas = datas;
        initDatas();
    }

    protected void initView() {
    }

    protected void initEvent() {
    }

    protected abstract void initDatas();
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

}
