package com.sky.load;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.sky.load.http.DownloadService;
import com.sky.load.model.FileEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        List<FileEntity> files = new ArrayList<>();
        FileEntity file1 = new FileEntity(0, "http://bcscdn.baidu.com/netdisk/BaiduYun_2.4.4.dmg", "BaiduYun_2.4.4.dmg", 0, 0);
        FileEntity file2 = new FileEntity(1, "http://bcscdn.baidu.com/netdisk/BaiduYunGuanjia_5.3.6.exe", "BaiduYunGuanjia_5.3.6.exe", 0, 0);
        FileEntity file3 = new FileEntity(2, "http://bcscdn.baidu.com/netdisk/BaiduYun_7.11.5.apk", "BaiduYun_7.11.5.apk", 0, 0);
        FileEntity file4 = new FileEntity(3, "http://dlsw.baidu.com/sw-search-sp/soft/ca/13442/Thunder_dl_7.9.42.5050.1449557123.exe", "Thunder_dl_7.9.42.5050.1449557123.exe", 0, 0);
        FileEntity file5 = new FileEntity(4, "https://git.oschina.net/msjg/SuperApp/repository/archive/master?utf8=%E2%9C%93&ref=master&captcha=xjj4yj&commit=+%E4%B8%8B%E8%BD%BD", "supper", 0, 0);
        files.add(file1);
        files.add(file2);
        files.add(file3);
        files.add(file4);
        files.add(file5);
        listView = (ListView) findViewById(R.id.list_item);
        adapter = new LoadAdapter();
        adapter.setFiles(files);
        listView.setAdapter(adapter);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.LOAD_UPDATE);
        filter.addAction(DownloadService.LOAD_FINISH);
        registerReceiver(receiver, filter);
    }

    LoadAdapter adapter;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.LOAD_UPDATE.equals(intent.getAction())) {
                adapter.setProgress(intent.getIntExtra("finished", 0),intent.getStringExtra("url"));
            }else if(DownloadService.LOAD_FINISH.equals(intent.getAction())){
                FileEntity file = (FileEntity) intent.getSerializableExtra("fileEntity");
                adapter.setProgress(intent.getIntExtra("finished", 0),file.getUrl());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
