package com.smartnsoft.weathr.feature.home;

import android.content.Intent;

import com.smartnsoft.weathr.model.City;

public interface HomeView {

    void getDataSuccess(City model);

    void getDataFail(String message);

    void refreshData();
}
