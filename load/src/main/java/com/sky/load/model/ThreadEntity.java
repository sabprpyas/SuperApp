package com.sky.load.model;

import java.io.Serializable;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 上午11:03
 */
public class ThreadEntity implements Serializable {
    private  int id;
    private String url;//地址
    private int start;//开始位置
    private int end;//结束位置
    private int finished;//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
