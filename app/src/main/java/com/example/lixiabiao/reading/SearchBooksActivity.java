package com.example.lixiabiao.reading;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SearchBooksActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return SearchBooksFragement.newInstance()  ;
    }
    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, SearchBooksActivity.class);
        return i;


    }
}
