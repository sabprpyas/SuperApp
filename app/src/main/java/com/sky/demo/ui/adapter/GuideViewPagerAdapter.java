package com.sky.demo.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/** 
* @ClassName: GuideViewPagerAdapter 
* @Description: 引导页面
* @author LiBin
* @date 2015年3月26日 下午5:34:14 
*  
*/
public class GuideViewPagerAdapter extends PagerAdapter{
	private List<View> views;
	public GuideViewPagerAdapter(List<View> views) {
		super();
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = views.get(position);
		container.addView(view);
		return view;
	}


}
