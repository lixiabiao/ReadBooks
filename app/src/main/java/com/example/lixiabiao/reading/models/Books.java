package com.example.lixiabiao.reading.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiabiao on 16/6/10.
 */
public class Books {
    private int count;
    private int total;
    private List<Book> books;

    public Books() {
        books = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
