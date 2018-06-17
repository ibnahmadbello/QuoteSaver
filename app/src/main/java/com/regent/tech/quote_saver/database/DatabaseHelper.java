package com.regent.tech.quote_saver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.regent.tech.quote_saver.database.model.Quote;

import java.util.ArrayList;
import java.util.List;

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

    public List<Quote> getAllQuotes(){
        List<Quote> quotes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Quote.TABLE_NAME + " ORDER BY "
                + Quote.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Quote quote = new Quote();
                quote.setId(cursor.getInt(cursor.getColumnIndex(Quote.COLUMN_ID)));
                quote.setQuote(cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE)));
                quote.setQuote_author(cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE_AUTHOR)));
                quote.setQuote_source(cursor.getString(cursor.getColumnIndex(Quote.COLUMN_QUOTE_SOURCE)));
                quote.setTimestamp(cursor.getString(cursor.getColumnIndex(Quote.COLUMN_TIMESTAMP)));

                quotes.add(quote);

            } while (cursor.moveToNext());

            database.close();
        }

        return quotes;
    }

    public int getQuotesCount(){
        String selectQuery = "SELECT * FROM " + Quote.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateQuote(Quote quote){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Quote.COLUMN_QUOTE, quote.getQuote());
        values.put(Quote.COLUMN_QUOTE_SOURCE, quote.getQuote_source());
        values.put(Quote.COLUMN_QUOTE_AUTHOR, quote.getQuote_author());

        return database.update(Quote.TABLE_NAME, values, Quote.COLUMN_ID + " = ?",
                new String[]{String.valueOf(quote.getId())});
    }

    public void deleteQuote(Quote quote){
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(Quote.TABLE_NAME, Quote.COLUMN_ID + " = ?",
                new String[]{String.valueOf(quote.getId())});

        database.close();
    }

}
