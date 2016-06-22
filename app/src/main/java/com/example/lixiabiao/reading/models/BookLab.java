package com.example.lixiabiao.reading.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lixiabiao.reading.BookBaseHelper;
import com.example.lixiabiao.reading.BookCursorWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.example.lixiabiao.reading.BookDbSchema.*;

/**
 * Created by lixiabiao on 16/6/10.
 */
public class BookLab {
    private static final String TAG = "BookLab" ;
    private static BookLab mBookLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private Books searchBooks;
    private List<Book> localBooks;


    public static BookLab get(Context context) {
        if (mBookLab == null) {
            mBookLab = new BookLab(context);

        }
        return mBookLab;
    }

    private BookLab(Context context) {
        mContext = context;
        mDatabase = new BookBaseHelper(mContext).getWritableDatabase();
    }

    public List<Book> getSearchBooks() {
        return searchBooks.getBooks();
    }

    public void setSearchBooks(Books searchBooks) {
        this.searchBooks = searchBooks;
    }

    public static ContentValues getContentValues(Book book) {
        ContentValues values = new ContentValues();
        values.put(BookTable.Cols.TITLE, book.getTitle());
        values.put(BookTable.Cols.AUTHOR, book.getAuthor());
        values.put(BookTable.Cols.SUMMARY, book.getSummary());
        values.put(BookTable.Cols.PAGES, book.getPages());
        values.put(BookTable.Cols.PRICE, book.getPrice());
        values.put(BookTable.Cols.URL, book.getMediumUrl());
        values.put(BookTable.Cols.STATUS, book.getStatus());
        values.put(BookTable.Cols.ISBN13, book.getIsbn13());
        return values;
    }

    private BookCursorWrapper queryBooks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BookTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new BookCursorWrapper(cursor);
    }

    public void addBook(Book b) {
        List<Book> findBooks = findBookByIsbn(b.getIsbn13());
        Log.d(TAG,"FindBooks:" + findBooks.size());
        if (findBooks.size() > 0) {
            updateBook(b);
        } else {
            Log.d(TAG,b.getMediumUrl());
            localBooks.add(b);
            ContentValues values = BookLab.getContentValues(b);
            mDatabase.insert(BookTable.NAME, null, values);
        }
    }

    public void delete(Book b) {
        int row = mDatabase.delete(BookTable.NAME,BookTable.Cols.ID + " = ?",new String[] {String.valueOf(b.getId())});
        Log.d(TAG, "Delete:" + row);
    }

    public List<Book>  getLocalBooks() {
        if (localBooks != null) {
            Log.d(TAG,localBooks.size() + "");
            return localBooks;
        }
        localBooks = new ArrayList<>();
        BookCursorWrapper cursor = queryBooks(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                localBooks.add(cursor.getBook());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return localBooks;
    }

    private List<Book> findBookByIsbn(String isbn) {
        List<Book> books = new ArrayList<>();
        String whereClause = BookTable.Cols.ISBN13 + " = ?";
        String[] whereArgs = new String[] { isbn };
        Log.d(TAG, whereClause + isbn);

        BookCursorWrapper cursor = queryBooks(whereClause, whereArgs);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                books.add(cursor.getBook());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return books;
    }

    public void setLocalBooks(List<Book> localBooks) {
        this.localBooks = localBooks;
    }

    public void updateBook(Book book) {
        int id = book.getId();
        ContentValues values = getContentValues(book);
        mDatabase.update(BookTable.NAME, values,
                BookTable.Cols.ID + " = ?",
                new String[] { String.valueOf(id) });
    }


}
