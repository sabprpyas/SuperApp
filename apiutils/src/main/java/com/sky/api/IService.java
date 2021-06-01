package com.sky.api;

/**
 * Created by sky on 16/5/10 下午3:50.
 */
public interface IService {
    /**
     * 开启服务
     */
    void startService();

    /**
     * 开启服务
     */
    void bindService();

    /**
     * 停止服务
     */
    void stopService();
}
