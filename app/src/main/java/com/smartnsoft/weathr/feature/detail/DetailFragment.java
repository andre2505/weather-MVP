package com.smartnsoft.weathr.feature.detail;

import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartnsoft.weathr.R;
import com.smartnsoft.weathr.databinding.FragmentDetailBinding;
import com.smartnsoft.weathr.model.Forecast;

import java.io.Serializable;

public class DetailFragment extends Fragment {

    public static String FORECAST_OBJECT =  "FORECAST_OBJECT";
    public static String POSITION_ITEM =  "POSITION_ITEM";

    private FragmentDetailBinding mFragmentDetailBinding;

    public static DetailFragment newInstance(Forecast forecast, int position) {
        DetailFragment f = new DetailFragment();
        Bundle args = new Bundle();

        args.putSerializable(FORECAST_OBJECT, forecast);
        args.putInt(POSITION_ITEM, position);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        Forecast forecast = (Forecast) bundle.getSerializable(FORECAST_OBJECT);
        int position = bundle.getInt(POSITION_ITEM);
        mFragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        mFragmentDetailBinding.cardviewFragment.setCardBackgroundColor(getResources().getIntArray(R.array.colors)[position % 4]);
        mFragmentDetailBinding.setForecast(forecast);
        return mFragmentDetailBinding.getRoot();
    }

    public Forecast getShownIndex() {
        return (Forecast) getArguments().getSerializable(FORECAST_OBJECT);
    }


}
