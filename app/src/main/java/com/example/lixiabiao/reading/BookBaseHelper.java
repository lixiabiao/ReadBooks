package com.example.lixiabiao.reading;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lixiabiao.reading.BookDbSchema.BookTable;

/**
 * Created by lixiabiao on 16/6/11.
 */
public class BookBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "bookBase.db";

    public BookBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BookTable.NAME + "(" +
                "_id integer primary key autoincrement, "+
                BookTable.Cols.TITLE + "," +
                BookTable.Cols.AUTHOR + "," +
                BookTable.Cols.SUMMARY + "," +
                BookTable.Cols.PAGES + "," +
                BookTable.Cols.PRICE + "," +
                BookTable.Cols.URL + "," +
                BookTable.Cols.STATUS + "," +
                BookTable.Cols.ISBN13 +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
