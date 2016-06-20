package com.example.lixiabiao.reading;

/**
 * Created by lixiabiao on 16/6/11.
 */
public class BookDbSchema {
    public static final class BookTable {
        public static final String NAME = "books";
        public static final class Cols {
            public static final String TITLE = "title";
            public static final String URL = "url";
            public static final String AUTHOR = "author";
            public static final String SUMMARY = "summary";
            public static final String PAGES = "pages";
            public static final String PRICE = "price";
            public static final String ID = "_id";
            public static final String STATUS = "status";
            public static final String ISBN13 = "isbn";
        }
    }
}
