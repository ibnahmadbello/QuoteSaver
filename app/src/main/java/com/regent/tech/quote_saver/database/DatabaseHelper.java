package com.regent.tech.quote_saver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.regent.tech.quote_saver.database.model.Quote;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "quotes.db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Quote.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Quote.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertQuote(String quote, String quote_author, String quote_source){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Quote.COLUMN_QUOTE, quote);
        values.put(Quote.COLUMN_QUOTE_AUTHOR, quote_author);
        values.put(Quote.COLUMN_QUOTE_SOURCE, quote_source);

        long id = database.insert(Quote.TABLE_NAME, null, values);

        database.close();

        return id;
    }

    public Quote getQuote(long id){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Quote.TABLE_NAME, new String[]{Quote.COLUMN_QUOTE_AUTHOR,
                Quote.COLUMN_QUOTE, Quote.COLUMN_TIMESTAMP}, Quote.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Quote quote = new Quote(
                cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE_AUTHOR)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE_SOURCE)),
                cursor.getString(cursor.getColumnIndex(Quote.COLUMN_TIMESTAMP))
        );

        cursor.close();

        return quote;
    }
}
