package com.sky.demo.model;

import java.io.Serializable;

/**
 * @author LiBin
 * @Description: TODO 数据交互模型
 * @date 2015/9/17 11:04
 */
public class MData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String type;
    public T dataList;//多种类型数据，一般是List集合，比如获取所有员工列表
}
