package com.regent.tech.quote_saver.database.model;

public class Quote {

    public static final String TABLE_NAME = "quote";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUOTE = "quote";
    public static final String COLUMN_QUOTE_AUTHOR = "quote_author";
    public static final String COLUMN_QUOTE_SOURCE = "quote_source";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String quote;
    private String quote_author;
    private String quote_source;
    private String timestamp;



    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_QUOTE + " TEXT NOT NULL,"
            + COLUMN_QUOTE_AUTHOR + " TEXT DEFAULT RANDOM,"
            + COLUMN_QUOTE_SOURCE + " TEXT DEFAULT RANDOM,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public Quote(){}

    public Quote(int id, String quote, String quote_author, String quote_source, String timestamp){
        this.id = id;
        this.quote = quote;
        this.quote_author = quote_author;
        this.quote_source = quote_source;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getQuote_author() {
        return quote_author;
    }

    public void setQuote_author(String quote_author) {
        this.quote_author = quote_author;
    }

    public String getQuote_source() {
        return quote_source;
    }

    public void setQuote_source(String quote_source) {
        this.quote_source = quote_source;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
