package com.smartnsoft.weathr.model;

import android.support.v4.content.ContextCompat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smartnsoft.weathr.R;

import java.util.List;

public class City {

    @SerializedName("city")
    @Expose
    private String name;

    @SerializedName("forecasts")
    @Expose
    private List<Forecast> forecasts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
