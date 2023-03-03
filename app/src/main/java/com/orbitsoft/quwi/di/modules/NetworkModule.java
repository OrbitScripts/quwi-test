package com.orbitsoft.quwi.di.modules;

import com.orbitsoft.quwi.BuildConfig;
import com.orbitsoft.quwi.data.remote.QuwiApi;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@NotNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://api.quwi.com/v2/")
                .client(okHttpClient)
                .build();
    }


    @Provides
    @Singleton
    QuwiApi provideApiService(@NotNull Retrofit retrofit) {
        return retrofit.create(QuwiApi.class);
    }
}
