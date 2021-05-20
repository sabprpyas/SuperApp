package com.sky.api;

/**
 * @author LiBin
 * @ClassName: IBase
 * @Description: TODO BaseActivity的接口，主要定义了Activity中常用的功能,BaseFragment中也可以用
 * @date 2015年3月26日 下午4:01:00
 */
public interface IBase {

    //初始化View
    void initViews();

    //载入数据
    void initData();

    //判断是否有网络连接,没有返回false
    boolean hasInternetConnected();

    //获取用户名
    String getUserName();

    //获取用户id
    String getUserId();

    //获取手机号
    String getPhone();

    //获取用户token
    String getToken();

    //显示dialog
    void showLoading();

    //隐藏 dialog
    void hideLoading();

    //用户是否在线（当前网络是否重连成功）
    boolean getUserOnlineState();

    //设置用户在线状态 true 在线 false 不在线
    void setUserOnlineState(boolean isOnline);
}
