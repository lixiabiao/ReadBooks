package com.example.lixiabiao.reading;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lixiabiao.reading.models.Book;

public class BookDetailActivity extends SingleFragmentActivity {
    public static final String EXTRA_BOOK =
            "com.example.lixiabiao.reading.book_position";
    public static final String FROM = "com.example.lixiabiao.reading.from";
    public static final String FROM_LOCAL = "local";
    public static final String FROM_SEARCH = "search";

    @Override
    public Fragment createFragment() {
        return BookDetailFragment.newInstance();
    }

    public static Intent newIntent(Context context, int p, String from) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK, p);
        intent.putExtra(FROM, from);

        return intent;
    }
}
