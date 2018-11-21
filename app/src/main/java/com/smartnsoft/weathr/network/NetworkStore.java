package com.smartnsoft.weathr.network;

import com.smartnsoft.weathr.model.City;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface NetworkStore {

    @GET("shared/weather/index.php")
    Observable<City> getTemperature(@Query("city") String name, @Query("forecasts") Integer forecast);

}
