package com.smartnsoft.weathr.feature.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartnsoft.weathr.R;
import com.smartnsoft.weathr.adapter.ForecastAdapter;
import com.smartnsoft.weathr.feature.detail.DetailActivity;
import com.smartnsoft.weathr.feature.detail.DetailFragment;
import com.smartnsoft.weathr.model.Forecast;

import java.util.List;

public class HomeListFragment extends Fragment implements ForecastAdapter.OnItemClickListener, DialogInterface.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean mDualPane;
    private RecyclerView mRecyclerView;
    private ForecastAdapter forecastAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private HomeView mHomeView;
    private List<Forecast> mForecasts;
    private Forecast mForecast;
    private int mPosition;
    private static final String KEY_FORECAST = "KEY_FORECAST";
    private static final String KEY_POSITION = "KEY_POSITION";
    public HomeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //no binding because no data this fragment just delegate to the homelist
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_home);
        mSwipeRefresh = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_home);

        //initialize recycler view
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        //initialize swipe refresh
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeColors(getResources().getIntArray(R.array.colors));

        View detailCity = getActivity().findViewById(R.id.details_city);

        mDualPane = detailCity != null && detailCity.getVisibility() == View.VISIBLE;
        if (savedInstanceState != null) {
            mForecast = (Forecast) savedInstanceState.getSerializable(KEY_FORECAST);
            mPosition = savedInstanceState.getInt(KEY_POSITION, 0);
        }

        if (mDualPane) {
            showDetails(mForecast, mPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_FORECAST, mForecast);
        outState.putInt(KEY_POSITION, mPosition);
    }


    @Override
    public void onItemClick(Forecast item, int position) {
        mForecast = item;
        showDetails(item, position);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
    }

    public void getCityFromActivity(List<Forecast> forecasts) {
        mSwipeRefresh.setRefreshing(false);
        if (mForecast == null && mDualPane) {
            mForecast = forecasts.get(0);
            showDetails(mForecast, 0);
        }
        if(forecastAdapter == null) {
            forecastAdapter = new ForecastAdapter(forecasts, getActivity(), this);
            mRecyclerView.setAdapter(forecastAdapter);
        }else {
           forecastAdapter.updateList(forecasts);
        }
    }
    void showDetails(Forecast forecast, int position) {
        mForecast = forecast;
        mPosition = position;
        if (mDualPane) {
            DetailFragment details = (DetailFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.details);

            if (details == null || mForecast != details.getShownIndex()) {

                details = DetailFragment.newInstance(forecast, position);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (forecast != null) {
                    ft.replace(R.id.details_city, details);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailActivity.class);
            intent.putExtra(DetailFragment.FORECAST_OBJECT, mForecast);
            intent.putExtra(DetailFragment.POSITION_ITEM, position);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        mHomeView.refreshData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mHomeView = (HomeView) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "ERROR");
        }
    }
}
