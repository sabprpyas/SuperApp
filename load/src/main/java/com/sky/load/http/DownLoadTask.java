package com.sky.load.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sky.load.db.ThreadDAO;
import com.sky.load.db.ThreadDAOImpl;
import com.sky.load.model.FileEntity;
import com.sky.load.model.ThreadEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 下午4:01
 */
public class DownLoadTask {
    private Context context;
    private FileEntity fileEntity = null;
    private int finished = 0;
    public boolean isPause = false;
    private ThreadDAO mDAO = null;
    private int threadCount = 3;
    private List<DownloadThread> threadList;
    public static ExecutorService executorService = Executors.newCachedThreadPool();


    public DownLoadTask(Context context, FileEntity fileEntity, int threadCount) {
        this.context = context;
        this.fileEntity = fileEntity;
        this.threadCount = threadCount;
        mDAO = new ThreadDAOImpl(context);
    }

    public void download() {
        List<ThreadEntity> threads = mDAO.getThread(fileEntity.getUrl());
        if (threads.size() == 0) {
            int length = fileEntity.getFileLength() / threadCount;
            for (int i = 0; i < threadCount; i++) {
                ThreadEntity entity = new ThreadEntity();
                entity.setId(i);
                entity.setUrl(fileEntity.getUrl());
                entity.setStart(i * length);
                entity.setEnd((i + 1) * length - 1);
                entity.setFinished(0);
                if (i == threadCount - 1) {
                    entity.setEnd(fileEntity.getFileLength());
                }
                threads.add(entity);
                mDAO.insertThread(entity);

            }
        }
        threadList = new ArrayList<>();
        for (ThreadEntity thread : threads) {
            DownloadThread load = new DownloadThread(thread);
//            load.start();
            DownLoadTask.executorService.execute(load);
            threadList.add(load);
        }
    }

    private synchronized void checkAllThread() {
        boolean allIsFinish = true;
        for (DownloadThread thread : threadList) {
            if (!thread.isFinish)
                allIsFinish = false;
        }
        if (allIsFinish) {
            Intent intent = new Intent(DownloadService.LOAD_FINISH);
            intent.putExtra("finished", finished * 100 / fileEntity.getFileLength());
            intent.putExtra("fileEntity", fileEntity);
            context.sendBroadcast(intent);
            mDAO.deleteThread(fileEntity.getUrl());

        }
    }

    class DownloadThread extends Thread {
        private ThreadEntity threadEntity;
        private boolean isFinish;

        public DownloadThread(ThreadEntity threadEntity) {
            this.threadEntity = threadEntity;
        }

        @Override
        public void run() {
            super.run();

            HttpURLConnection connection = null;
            RandomAccessFile accessFile = null;
            InputStream stream = null;
            try {
                URL url = new URL(threadEntity.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                int start = threadEntity.getStart() + threadEntity.getFinished();
                connection.setRequestProperty("Range", "bytes=" + start + "-" + threadEntity.getEnd());
                File file = new File(DownloadService.DOWNLOAD_PATH, fileEntity.getFileName());
                accessFile = new RandomAccessFile(file, "rwd");
                accessFile.seek(start);

                Intent intent = new Intent(DownloadService.LOAD_UPDATE);
                finished += threadEntity.getFinished();
                int code = connection.getResponseCode();
                Log.i("code", "connection.getResponseCode()=" + code);
                if (code == HttpURLConnection.HTTP_PARTIAL) {
                    stream = connection.getInputStream();

                    byte[] buffer = new byte[1024 * 2];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = stream.read(buffer)) != -1) {
                        accessFile.write(buffer, 0, len);
                        finished += len;
                        threadEntity.setFinished(threadEntity.getFinished() + len);
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", finished * 100 / fileEntity.getFileLength());
                            intent.putExtra("url", fileEntity.getUrl());
                            context.sendBroadcast(intent);
                        }
                        if (isPause) {
                            mDAO.updateThread(threadEntity.getUrl(), threadEntity.getId(), threadEntity.getFinished());
                            return;
                        }
                    }
                    isFinish = true;
                    checkAllThread();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.disconnect();
                    accessFile.close();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
