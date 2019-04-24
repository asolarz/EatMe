package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null,
                DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.TempHistory.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DatabaseContract.TempHistory.DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    void insertScore(SQLiteDatabase db, String user, int score) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TempHistory.COLUMN_NAME_USER, user);
        values.put(DatabaseContract.TempHistory.COLUMN_NAME_SCORE, score);
        long newRowId;
        newRowId = db.insert(DatabaseContract.TempHistory.TABLE_NAME, null, values);
    }


    Cursor getScores(SQLiteDatabase db) {
        String[] columns = {"_ID", DatabaseContract.TempHistory.COLUMN_NAME_USER,
                DatabaseContract.TempHistory.COLUMN_NAME_SCORE, DatabaseContract.TempHistory.COLUMN_NAME_DATE};
        Cursor cursor = db.query(DatabaseContract.TempHistory.TABLE_NAME, columns,
                null, null, null, null, DatabaseContract.TempHistory.COLUMN_NAME_SCORE + " DESC");

        return cursor;
    }

}
