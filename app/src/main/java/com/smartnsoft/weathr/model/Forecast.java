package com.smartnsoft.weathr.model;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smartnsoft.weathr.R;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Forecast implements Serializable {

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("temperatureMin")
    @Expose
    private Integer temperatureMin;

    @SerializedName("temperatureMax")
    @Expose
    private Integer temperatureMax;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("uvIndex")
    @Expose
    private Integer uvIndex;

    @SerializedName("windDirection")
    @Expose
    private String winDirection;

    @SerializedName("windSpeed")
    @Expose
    private Integer winSpeed;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Integer temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Integer temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Integer uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getWinDirection() {
        return winDirection;
    }

    public void setWinDirection(String winDirection) {
        this.winDirection = winDirection;
    }

    public Integer getWinSpeed() {
        return winSpeed;
    }

    public void setWinSpeed(Integer winSpeed) {
        this.winSpeed = winSpeed;
    }

    public Drawable getImage(Context context, String type) {
        switch (type) {
            case "RAINY":
                return ContextCompat.getDrawable(context, R.drawable.rain);
            case "STORMY":
                return ContextCompat.getDrawable(context, R.drawable.thunderstorm);
            case "SUNNY":
                return ContextCompat.getDrawable(context, R.drawable.clear_sky);
            case "CLOUDY":
                return ContextCompat.getDrawable(context, R.drawable.broken_clouds);
            case "SNOWY":
                return ContextCompat.getDrawable(context, R.drawable.snow);
            default:
                return null;
        }
    }

    public String newFormatDate(Date date) {
        String newstring = new SimpleDateFormat("EEEE dd MMMM", Locale.FRANCE).format(date);
        return newstring;
    }
}
