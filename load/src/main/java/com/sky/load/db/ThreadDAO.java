package com.sky.load.db;

import com.sky.load.model.ThreadEntity;

import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 下午4:05
 */
public interface ThreadDAO {
    //插入线程信息
    void insertThread(ThreadEntity threadEntity);
    //删除线程信息
    void deleteThread(String url,int threadId);
    void deleteThread(String url);
    //更新线程信息
    void updateThread(String url,int threadId,int finished);
    //获取线程信息
    List<ThreadEntity> getThread(String url);
    //判断线程是否存在
    boolean isExists(String url,int threadId);
}
