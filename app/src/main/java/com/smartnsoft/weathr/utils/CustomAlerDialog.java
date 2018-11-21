package com.smartnsoft.weathr.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.smartnsoft.weathr.R;

public class CustomAlerDialog {

    public static AlertDialog.Builder parametersForecast(Context mContext){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.settings_city));
        LayoutInflater inflater = LayoutInflater.from(mContext);
        builder.setView(inflater.inflate(R.layout.dialog_parameters, null));
        return builder;
    }
}
