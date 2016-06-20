package com.example.lixiabiao.reading;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.lixiabiao.reading.models.Book;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.lixiabiao.reading.BookDbSchema.*;

/**
 * Created by lixiabiao on 16/6/13.
 */
public class BookCursorWrapper extends CursorWrapper {

    public BookCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Book getBook() {
        int id = getInt(getColumnIndex(BookTable.Cols.ID));
        String title = getString(getColumnIndex(BookTable.Cols.TITLE));
        String author = getString(getColumnIndex(BookTable.Cols.AUTHOR));
        String summary = getString(getColumnIndex(BookTable.Cols.SUMMARY));
        String price = getString(getColumnIndex(BookTable.Cols.PRICE));
        String pages = getString(getColumnIndex(BookTable.Cols.PAGES));
        String url = getString(getColumnIndex(BookTable.Cols.URL));
        String status = getString(getColumnIndex(BookTable.Cols.STATUS));
        String isbn = getString(getColumnIndex(BookTable.Cols.ISBN13));

        Book book = new Book();
        book.setTitle(title);
        List<String> authors = new ArrayList<>();
        authors.add(author);
        book.setAuthor(authors);
        book.setSummary(summary);
        book.setPrice(price);
        book.setPages(pages);
        book.setImageUrl(url);
        book.setId(id);
        book.setStatus(status);
        book.setIsbn13(isbn);

        return book;
    }
}
