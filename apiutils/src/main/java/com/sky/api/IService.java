package com.sky.api;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/5/9 下午11:39
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
