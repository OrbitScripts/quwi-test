package com.orbitsoft.quwi.data.remote;

import com.orbitsoft.quwi.data.remote.model.ChannelsResponse;
import com.orbitsoft.quwi.data.remote.model.LoginRequest;
import com.orbitsoft.quwi.data.remote.model.LoginResponse;
import com.orbitsoft.quwi.data.remote.model.UsersResponse;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuwiApi {

    @POST("auth/login")
    Single<LoginResponse> login(@Body LoginRequest body);


    @POST("auth/logout")
    Completable logout(@Header("Authorization") String authHeader);

    @GET("chat-channels")
    Single<ChannelsResponse> getChannels(@Header("Authorization") String authHeader);

    @GET("users/foreign")
    Single<UsersResponse> getPartnerInfo(@Header("Authorization") String authHeader,
                                         @Query("ids") String ids);
}
