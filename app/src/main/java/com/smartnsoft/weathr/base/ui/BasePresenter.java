package com.smartnsoft.weathr.base.ui;

import android.util.Log;

import com.smartnsoft.weathr.model.City;
import com.smartnsoft.weathr.network.NetworkClient;
import com.smartnsoft.weathr.network.NetworkStore;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SafeObserver;
import io.reactivex.schedulers.Schedulers;


public class BasePresenter<V> {
    public V view;
    //protected NetworkStores apiStores;
    private CompositeDisposable mCompositeDisposable;
    protected NetworkStore apiStores;

   public void attachView(V view) {
        this.view = view;
        apiStores = NetworkClient.getClient().create(NetworkStore.class);
    }

    public void dettachView() {
        this.view = null;
        onUnsubscribe();
    }

    void onUnsubscribe() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Observable observable, DisposableObserver subscriber) {
        //this.subscriber = subscriber;
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        Disposable disposable = (Disposable) observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);

         mCompositeDisposable.add(disposable);
    }

    public void onDestroy(){
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
