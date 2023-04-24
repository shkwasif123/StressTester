package com.hazyaz.stresstester;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SignedInUser extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Authorized";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "SignedUser";
    public static final String ID_COL = "id";
     public static final String EMAIL_COL = "email";

    public SignedInUser(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                EMAIL_COL + " TEXT" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_COL, "");
        db.update(TABLE_NAME, contentValues, null, null);
    }
    public void update(String prevEmail,String Email) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EMAIL_COL, Email);

        db.update(TABLE_NAME, values, "email=?", new String[]{prevEmail});

        db.close();
    }


    public void addCurrentSignedIn(String email) {
        ContentValues values = new ContentValues();
        values.put(EMAIL_COL, email);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getName() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
