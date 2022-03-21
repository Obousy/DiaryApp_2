package com.mrworka.mydiary.db;

import android.content.Context;







import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;



public class ItemDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "itemList.db";

    public static final int DATABASE_VERSION = 1;

    private final Context fContext;

    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        fContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_JOURNAL_TABLE = "CREATE TABLE " +
                ItemContract.DiaryEntry.TABLE_NAME + " (" +
                ItemContract.DiaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.DiaryEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ItemContract.DiaryEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                ItemContract.DiaryEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT (datetime('now','localtime'))" +
                ");";

        db.execSQL(SQL_CREATE_JOURNAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.DiaryEntry.TABLE_NAME);

        onCreate(db);
    }
}