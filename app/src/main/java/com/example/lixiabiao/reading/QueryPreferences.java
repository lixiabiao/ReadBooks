package com.example.lixiabiao.reading;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by lixiabiao on 16/6/12.
 */
public class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }
    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_SEARCH_QUERY, query)
             .apply();
        }
}
