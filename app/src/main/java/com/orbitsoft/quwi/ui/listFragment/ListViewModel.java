package com.orbitsoft.quwi.ui.listFragment;

import androidx.lifecycle.ViewModel;

import com.orbitsoft.quwi.data.local.LocalDataSource;
import com.orbitsoft.quwi.data.remote.RemoteDataSource;
import com.orbitsoft.quwi.data.remote.model.ChannelResponse;
import com.orbitsoft.quwi.data.remote.model.UserResponse;
import com.orbitsoft.quwi.data.remote.model.UsersResponse;
import com.orbitsoft.quwi.ui.authFragment.models.ChannelInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

@HiltViewModel
public class ListViewModel extends ViewModel {

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final BehaviorSubject<List<ChannelInfo>> listChannelInfo = BehaviorSubject.create();
    private final PublishSubject<Boolean> isNeedLogout = PublishSubject.create();

    private ChannelResponse savedMessagesInfo;
    private final Map<Integer, ChannelResponse> channelInfoMap = new HashMap<>();

    public BehaviorSubject<List<ChannelInfo>> getListChannelInfo() {
        return listChannelInfo;
    }

    public PublishSubject<Boolean> getIsNeedLogout() {
        return isNeedLogout;
    }

    @Inject
    public ListViewModel(@NonNull RemoteDataSource remoteDataSource,
                         @NonNull LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        compositeDisposable.add(
                remoteDataSource.getChannels(localDataSource.getToken())
                        .subscribeOn(Schedulers.io())
                        .flatMap(channelsResponse -> {
                            channelsResponse.getChannels().forEach(channelResponse -> {
                                if (!(channelResponse.getIdUsers().length == 1 &&
                                        channelResponse.getIdUsers()[0] == channelResponse.getPartner())) {
                                    channelInfoMap.put(channelResponse.getPartner(), channelResponse);
                                } else {
                                    savedMessagesInfo = channelResponse;
                                }
                            });
                            return getPartnersInfo();
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(usersResponse -> {
                            List<ChannelInfo> channels = new ArrayList<>();
                            usersResponse.getUsers().forEach(userResponse -> {
                                ChannelResponse channelResponse = channelInfoMap.get(userResponse.getId());
                                channels.add(new ChannelInfo(channelResponse, userResponse));
                            });
                            channels.add(0, new ChannelInfo(savedMessagesInfo, new UserResponse()
                                    .getSavedMessagesUserResponse()));
                            listChannelInfo.onNext(channels);
                        }, throwable -> {
                            System.out.print("faaf" + throwable.getMessage());
                        })
        );

    }

    private Single<UsersResponse> getPartnersInfo() {
        StringBuilder ids = new StringBuilder();
        for (Object id : channelInfoMap.keySet().toArray()) {
            if (id instanceof Integer) {
                if (ids.length() != 0) {
                    ids.append(",");
                }
                ids.append(id);
            }
        }
        return remoteDataSource.getPartnerInfos(localDataSource.getToken(), ids.toString());
    }

    public void logout() {
        compositeDisposable.add(
                remoteDataSource.logout(localDataSource.getToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            isNeedLogout.onNext(true);
                        }, throwable -> {
                            //nothing
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}