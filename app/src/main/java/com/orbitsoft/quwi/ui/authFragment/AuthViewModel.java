package com.orbitsoft.quwi.ui.authFragment;

import androidx.lifecycle.ViewModel;

import com.orbitsoft.quwi.data.local.LocalDataSource;
import com.orbitsoft.quwi.data.remote.RemoteDataSource;
import com.orbitsoft.quwi.data.remote.model.LoginResponse;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class AuthViewModel extends ViewModel {


    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final BehaviorSubject<LoginResponse> loginResponse = BehaviorSubject.create();

    @Inject
    public AuthViewModel(@NonNull RemoteDataSource remoteDataSource,
                         @NonNull LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }
    public BehaviorSubject<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public void login(String email, String password) {
        compositeDisposable.add(
                remoteDataSource.login(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loginResponse -> {
                            localDataSource.saveToken(loginResponse.getToken());
                            this.loginResponse.onNext(loginResponse);
                        }, throwable -> {
                            System.out.print(throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}