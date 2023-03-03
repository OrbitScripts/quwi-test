package com.orbitsoft.quwi.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class LocalDataSource {

    public static final String PREFS_NAME = "local.prefs";

    public static final String PREFS_TOKEN = "local.prefs.token";
    @ApplicationContext
    Context context;

    public LocalDataSource(SharedPreferences prefs) {
        this.prefs = prefs;
    }
    private final SharedPreferences prefs;
//

    public void saveToken(String token) {
        prefs.edit().putString(PREFS_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(PREFS_TOKEN, "");
    }
}
