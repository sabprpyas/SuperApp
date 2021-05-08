package com.sky.load.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sky.load.model.ThreadEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 下午4:11
 */
public class ThreadDAOImpl implements ThreadDAO {
    private DBHelper helper = null;

    public ThreadDAOImpl(Context context) {
        helper = DBHelper.getInstance(context);
    }

    @Override
    public synchronized void insertThread(ThreadEntity entity) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into threadentity(" +
                        "thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{entity.getId(), entity.getUrl(), entity.getStart(),
                        entity.getEnd(), entity.getFinished()});
        db.close();

    }

    @Override
    public synchronized void deleteThread(String url, int threadId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from threadentity where url=? and thread_id =?",
                new Object[]{url, threadId});
        db.close();
    } @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from threadentity where url=?",
                new Object[]{url});
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int threadId, int finished) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update threadentity set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url, threadId});
        db.close();
    }

    @Override
    public List<ThreadEntity> getThread(String url) {
        List<ThreadEntity> entities = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from threadentity where url =?",
                new String[]{url});
        while (cursor.moveToNext()) {
            ThreadEntity entity = new ThreadEntity();
            entity.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            entity.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            entity.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            entity.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            entity.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            entities.add(entity);
        }
        cursor.close();
        db.close();
        return entities;
    }

    @Override
    public boolean isExists(String url, int threadId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from threadentity where url=? and thread_id=?",
                new String[]{url, threadId + ""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
