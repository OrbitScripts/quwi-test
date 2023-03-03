package com.orbitsoft.quwi.data.remote;

import com.orbitsoft.quwi.data.remote.model.ChannelsResponse;
import com.orbitsoft.quwi.data.remote.model.LoginRequest;
import com.orbitsoft.quwi.data.remote.model.LoginResponse;
import com.orbitsoft.quwi.data.remote.model.UsersResponse;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class RemoteDataSource {

    private final QuwiApi api;

    @Inject
    public RemoteDataSource(@NotNull QuwiApi api) {
        this.api = api;
    }

    public Single<LoginResponse> login(String email, String password) {
        return api.login(new LoginRequest(email, password));
    }

    public Completable logout(String token) {
        return api.logout(token);
    }

    public Single<ChannelsResponse> getChannels(String token) {
        return api.getChannels("Bearer " + token);
    }

    public Single<UsersResponse> getPartnerInfos(String token, String ids) {
        return api.getPartnerInfo("Bearer " + token, ids);
    }
}
