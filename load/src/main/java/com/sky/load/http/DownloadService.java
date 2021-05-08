package com.sky.load.http;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sky.load.model.FileEntity;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 下午2:15
 */
public class DownloadService extends Service {
    public static final String LOAD_START = "START";
    public static final String LOAD_STOP = "STOP";
    public static final String LOAD_UPDATE = "UPDATE";
    public static final String LOAD_FINISH = "FINISH";
    public static File DOWNLOAD_PATH = null;
    //    private DownLoadTask task;
    private Map<Integer, DownLoadTask> taskMap = new LinkedHashMap<>();


//    public static final String DOWNLOAD_PATH =
//            Environment.getExternalStorageDirectory().getAbsolutePath() +
//                    "/downloads/";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent ==null){
            Log.i("super","intent=你大爷的，空个毛线啊");
            return super.onStartCommand(intent, flags, startId);
        }
        String action = null;
        try {
             action=intent.getAction();
        }catch (Exception o){
            if (o instanceof NullPointerException){
                Log.i("super",action+"ntent.getAction()=你大爷的，空个毛线啊");
                return super.onStartCommand(intent, flags, startId);
            }
        }
        if (LOAD_START.equals(intent.getAction())) {
            FileEntity file = (FileEntity) intent.getSerializableExtra("file");

            DOWNLOAD_PATH = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            analysisThread thread=new analysisThread(file);
            DownLoadTask.executorService.execute(thread);
        } else if (LOAD_STOP.equals(intent.getAction())) {
            FileEntity file = (FileEntity) intent.getSerializableExtra("file");
            DownLoadTask task = taskMap.get(file.getId());
            if (task != null) task.isPause = true;

        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    FileEntity fileEntity = (FileEntity) msg.obj;
                    DownLoadTask task = new DownLoadTask(DownloadService.this, fileEntity, 3);
                    task.download();
                    taskMap.put(fileEntity.getId(), task);
                    break;
            }
        }
    };

    class analysisThread extends Thread {
        FileEntity file;

        public analysisThread(FileEntity file) {
            this.file = file;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection connection = null;
            RandomAccessFile accessFile = null;
            try {
                URL url = new URL(file.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                int length = -1;
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    length = connection.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
//                File dir = new File(DOWNLOAD_PATH);
//                if (!dir.exists()){
//                    dir.mkdir();
//                }
                File fileName = new File(DOWNLOAD_PATH, file.getFileName());
                accessFile = new RandomAccessFile(fileName, "rwd");
                accessFile.setLength(length);
                file.setFileLength(length);
                handler.obtainMessage(0, file).sendToTarget();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                try {
                    accessFile.close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }
}
