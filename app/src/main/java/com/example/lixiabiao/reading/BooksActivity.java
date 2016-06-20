package com.example.lixiabiao.reading;

import android.support.v4.app.Fragment;

/**
 * Created by lixiabiao on 16/6/12.
 */
public class BooksActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return BooksFragement.newInstance()  ;

    }
}
