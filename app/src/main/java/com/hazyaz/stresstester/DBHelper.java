package com.hazyaz.stresstester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
     static final String DATABASE_NAME = "SQL";
     static final int DATABASE_VERSION = 1;
     static final String TABLE_NAME = "mine";
    static final String ID_COL = "id";
    public static final String NAME_COl = "name";
   public static final String Gen_COL = "gender";
    public static final String Interest_COL = "interest";
     public static final String Email_Col = "email";
   static final String Pass_Col = "password";
    public static final String Age_Col = "age";

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY, " +
                Email_Col + " TEXT," + Pass_Col + " TEXT," + NAME_COl + " TEXT," + Gen_COL + " TEXT," +
                Age_Col + " TEXT," + Interest_COL + " TEXT" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addName(String email, String pass, String name, String gender, String age, String interest) {
        ContentValues values = new ContentValues();
        values.put(Email_Col, email);
        values.put(Pass_Col, pass);
        values.put(NAME_COl, name);
        values.put(Gen_COL, gender);
        values.put(Age_Col, age);
        values.put(Interest_COL, interest);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    // below is the method for updating our courses
    public void update(String prevEmail,String Email, String Age, String Interest) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Email_Col, Email);
        values.put(Age_Col, Age);
        values.put(Interest_COL, Interest);

        db.update(TABLE_NAME, values, "email=?", new String[]{prevEmail});

        db.close();
    }


    public Cursor getName() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
