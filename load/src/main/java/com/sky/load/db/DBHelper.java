package com.sky.load.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/29 下午4:12
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "download.db";
    private static final int VERSION = 1;
    private static final String SQL_CREATE = "create table threadentity" +
            "(_id integer primary key autoincrement," +
            "thread_id integer," +
            "url text," +
            "start integer," +
            "end integer," +
            "finished integer)";
    private static final String SQL_DROP = "drop table if exists threadentity";
    private static DBHelper helper;

    public static DBHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (DBHelper.class) {
                if (helper == null) helper = new DBHelper(context);
            }
        }
        return helper;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
