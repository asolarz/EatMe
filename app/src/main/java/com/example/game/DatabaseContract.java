package com.example.game;

import android.provider.BaseColumns;


public class DatabaseContract {
    public DatabaseContract() {
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "game.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String DATE_TYPE = " DATETIME DEFAULT CURRENT_TIMESTAMP";
    private static final String COMMA_SEP = ",";


    public static abstract class TempHistory implements BaseColumns {
        public static final String TABLE_NAME = "score";
        public static final String COLUMN_NAME_USER = "user";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_DATE= "date";
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_USER + TEXT_TYPE + COMMA_SEP + COLUMN_NAME_DATE + DATE_TYPE
                 + COMMA_SEP + COLUMN_NAME_SCORE +
                INT_TYPE + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME;
    }

}
