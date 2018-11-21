package com.smartnsoft.weathr.feature.home;

import android.util.Log;

import com.smartnsoft.weathr.base.ui.BasePresenter;
import com.smartnsoft.weathr.model.City;

import io.reactivex.observers.DisposableObserver;


class HomePresenter extends BasePresenter<HomeView> {

    HomePresenter(HomeView view) {
        super.attachView(view);
    }

    void loadData(String city, Integer forecast) {
        addSubscribe(apiStores.getTemperature(city, forecast), new DisposableObserver<City>() {
            @Override
            public void onNext(City city) {
                //status response is always 200 because error request 400 is into bodyresponse
                try {
                    view.getDataSuccess(city);
                } catch (Throwable throwable){
                    onError(throwable);
                }
            }

            @Override
            public void onError(Throwable e) {
              view.getDataFail(e.toString());
            }

            @Override
            public void onComplete() {
                //hide load spinner
            }
        });
    }
}
