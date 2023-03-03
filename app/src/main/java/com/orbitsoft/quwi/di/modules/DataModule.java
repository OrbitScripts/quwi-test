package com.orbitsoft.quwi.di.modules;

import static com.orbitsoft.quwi.data.local.LocalDataSource.PREFS_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import com.orbitsoft.quwi.data.local.LocalDataSource;
import com.orbitsoft.quwi.data.remote.QuwiApi;
import com.orbitsoft.quwi.data.remote.RemoteDataSource;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    RemoteDataSource provideRemoteDataSource(QuwiApi api) {
        return new RemoteDataSource(api);
    }

    @Provides
    LocalDataSource provideLocalDataSource(@ApplicationContext Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return new LocalDataSource(prefs);
    }
}
