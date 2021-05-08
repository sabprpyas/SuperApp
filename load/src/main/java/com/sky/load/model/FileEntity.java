package com.sky.load.model;

import java.io.Serializable;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 上午10:12
 */
public class FileEntity implements Serializable{
    private int id;
    private String url;//地址
    private String fileName;//名称
    private int fileLength;//长度
    private int finished;//

    public FileEntity() {

    }
    public FileEntity(int id, String url, String fileName, int fileLength, int finished) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.fileLength = fileLength;
        this.finished = finished;
    }

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
