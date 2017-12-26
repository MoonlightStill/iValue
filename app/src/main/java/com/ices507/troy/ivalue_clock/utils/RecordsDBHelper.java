package com.ices507.troy.ivalue_clock.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ices507.troy.ivalue_clock.R;
import com.ices507.troy.ivalue_clock.entity.Record;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by troy on 17-12-11.
 *
 * @Description Create database by information from assets/database.xml
 */

public class RecordsDBHelper extends SQLiteOpenHelper{
    private static final String TAG = "RecordsDBHelper";
    private InputStream in = null;
    private static final String DB_NAME = "records.db";
    public static final int DB_VERSION = 1;
    private static RecordsDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    private static final String TABLE_NAME = "records_table";

    private RecordsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public RecordsDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static RecordsDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new RecordsDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new RecordsDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    public String getDBName() {
        if (mHelper != null) {
            return mHelper.getDatabaseName();
        } else {
            return DB_NAME;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + DB_NAME + ";";
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + "TIME VARCHAR PRIMARY KEY NOT NULL," + "LATITUDE DOUBLE NOT NULL,"
                + "LONGITUDE DOUBLE NOT NULL," + "TYPE INTEGER NOT NULL,"
                + "AREA VARCHAR," + "DISTANCE DOUBLE)";
        Log.e(TAG, "create_sql:" + create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onUpgrade old version = " + oldVersion + ",new version = " + newVersion);
        if (newVersion > 1) {
//            String alter_sql=
        }
    }

    public int delete(String condition) {
        int count = mDB.delete(TABLE_NAME, condition, null);
        return count;
    }

    public int deleteAll() {
        return delete("1=1");
    }

    public ArrayList<Record> getAll() {
        String sql = String.format("select * from %s ", TABLE_NAME);
        Log.e(TAG, "query sql:" + sql);
        ArrayList<Record> records = new ArrayList<Record>();
        Cursor cursor = mDB.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            for(;;cursor.moveToNext()) {
                Record record = new Record();
                record.setTime(cursor.getString(0));
                record.setLatitude(cursor.getDouble(1));
                record.setLongitude(cursor.getDouble(2));
                record.setType(cursor.getInt(3));
                record.setArea(cursor.getString(4));
                record.setDistance(cursor.getDouble(5));
                records.add(record);
                if (cursor.isLast()) {
                    break;
                }
            }
        }
        cursor.close();
        return records;
    }

    public long insert(Record record) {
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(record);
        return insert(records);
    }

    private long insert(ArrayList<Record> records) {
        long result = -1;
        for(int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            ArrayList<Record> temp = new ArrayList<Record>();
            if (record.getTime() != null && record.getTime().length() > 0) {
                String condition = String.format("time='%s'", record.getTime());
                temp = query(condition);
                if (temp.size() > 0) {
                    result = update(record, condition);
                    continue;
                }
            }
            ContentValues cv = new ContentValues();
            cv.put("time", record.getTime());
            cv.put("latitude", record.getLatitude());
            cv.put("longitude", record.getLongitude());
            cv.put("type", record.getType());
            cv.put("area", record.getArea());
            cv.put("distance", record.getDistance());
            result = mDB.insert(TABLE_NAME, "", cv);
            if (result == -1) {
                Log.e(TAG, "Insert " + record + "\n failed");
                return result;
            }
        }
        return result;
    }

    private int update(Record record, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("time", record.getTime());
        cv.put("latitude", record.getLatitude());
        cv.put("longitude", record.getLongitude());
        cv.put("type", record.getType());
        cv.put("area", record.getArea());
        cv.put("distance", record.getDistance());
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    private ArrayList<Record> query(String condition) {
        String sql = String.format("select * from %s where %s", TABLE_NAME, condition);
        Log.e(TAG, "query sql:" + sql);
        ArrayList<Record> records = new ArrayList<Record>();
        Cursor cursor = mDB.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            for(;;cursor.moveToNext()) {
                Record record = new Record();
                record.setTime(cursor.getString(0));
                record.setLatitude(cursor.getDouble(1));
                record.setLongitude(cursor.getDouble(2));
                record.setType(cursor.getInt(3));
                record.setArea(cursor.getString(4));
                record.setDistance(cursor.getDouble(5));
                records.add(record);
                if (cursor.isLast()) {
                    break;
                }
            }
        }
        cursor.close();
        return records;
    }
}
