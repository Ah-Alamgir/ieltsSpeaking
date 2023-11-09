package com.ielts.speaking.publicClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteDataHelp extends SQLiteOpenHelper {

    public static final String name = "userData";
    public  final String COLUMN_ID = "id";
    public static final String TABLE_NAME = "ALLBOOKS";
    public final String BOOK_NAME = "Cambridge";
    public  final String COLUMN_1 = "Answer_1";
    public  final String COLUMN_2 = "Answer_2";
    public  final String COLUMN_3 = "Answer_3";
    public final String COLUMN_4 = "Answer_4";


    public SqliteDataHelp(@Nullable Context context) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE "+ TABLE_NAME+"("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                BOOK_NAME+ " TEXT," + COLUMN_1 + " TEXT," + COLUMN_2 + " TEXT," + COLUMN_3 + " TEXT," + COLUMN_4 + " TEXT)";
        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Integer isDatabaseEmpty() {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = Integer.parseInt(String.valueOf(DatabaseUtils.queryNumEntries(db, TABLE_NAME)));
        return count;
    }


    String TAG = "AirtableApiClient";
    public void saveDataInSQLite(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void deleteDataInSQLite(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<String> getDataList(){
        ArrayList<String> BOOK_LIST= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (cursor.moveToNext()){
            BOOK_LIST.add(cursor.getString(1));
            ContentValues valusesList = new ContentValues();
            valusesList.put(COLUMN_1,cursor.getString(2));
            valusesList.put(COLUMN_2,cursor.getString(3));
            valusesList.put(COLUMN_3,cursor.getString(4));
            valusesList.put(COLUMN_4,cursor.getString(5));

            ANSWER_LIST.add(valusesList);
        }
        cursor.close();
        return BOOK_LIST;
    }
    public ArrayList<ContentValues> ANSWER_LIST = new ArrayList<ContentValues>();


}
