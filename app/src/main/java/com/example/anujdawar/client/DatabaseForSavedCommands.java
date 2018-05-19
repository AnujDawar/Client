package com.example.anujdawar.client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseForSavedCommands extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "COMMAND";
    public static final String COL_3 = "DEVICE1";
    public static final String COL_4 = "DEVICE2";
    public static final String COL_5 = "DEVICE3";
    public static final String COL_6 = "DEVICE4";
    public static final String COL_7 = "DEVICE5";

    public DatabaseForSavedCommands(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,COMMAND TEXT,DEVICE1 TEXT,DEVICE2 TEXT" +
                ",DEVICE3 TEXT,DEVICE4 TEXT,DEVICE5 TEXT)");
    }

    public boolean insertData(String commandToinsert, String dev1, String dev2, String dev3, String dev4, String dev5)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, commandToinsert);
        contentValues.put(COL_3, dev1);
        contentValues.put(COL_4, dev2);
        contentValues.put(COL_5, dev3);
        contentValues.put(COL_6, dev4);
        contentValues.put(COL_7, dev5);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public boolean updateData(String id, String command, String dev1, String dev2, String dev3, String dev4, String dev5)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, command);
        contentValues.put(COL_3, dev1);
        contentValues.put(COL_4, dev2);
        contentValues.put(COL_5, dev3);
        contentValues.put(COL_6, dev4);
        contentValues.put(COL_7, dev5);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});

        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}
