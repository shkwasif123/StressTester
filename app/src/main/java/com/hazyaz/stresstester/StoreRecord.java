package com.hazyaz.stresstester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoreRecord extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Records";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "myRecord";
    private static final String ID_COL = "id";
    public static final String Email_COL = "email";
    public static final String Stress_level = "stressLevel";
    public static final String date_Col = "date";

    public StoreRecord(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                date_Col + " TEXT, " +
                Email_COL + " TEXT, " +
                Stress_level + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addName(String email, String stressPercent, String date) {
        ContentValues values = new ContentValues();
        values.put(Email_COL, email);
        values.put(Stress_level, stressPercent);
        values.put(date_Col, date);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getName() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
