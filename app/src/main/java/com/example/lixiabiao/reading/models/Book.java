package com.example.lixiabiao.reading.models;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lixiabiao on 16/6/10.
 */
public class Book {
    private String title;
    private Map<String, String> images;
    private List<String> author;
    private String summary;
    private String price;
    private String pages;
    private int id;
    private String imageUrl;
    private String status;
    private String isbn13;

    public Book() {
        images = new HashMap<>();
        author = new ArrayList<>();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMediumUrl() {
        String Url = getImages().get("medium");
        return Url;
    }

    public  String getLargeUrl() {
        String Url = getImages().get("large");
        return Url;
    }

    public  String getSmallUrl() {
        String Url = getImages().get("small");
        return Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getAuthor() {
        String authorString = "";
        if (author.size() > 1 ){
            for (String a : author) {
                authorString = authorString + a;
                if (author.indexOf(a) != (author.size() - 1)) {
                    authorString += ", ";
                }
            }
        } else {
            authorString = author.get(0);
        }
        return authorString;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }
}
