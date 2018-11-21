package com.smartnsoft.weathr.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartnsoft.weathr.R;
import com.smartnsoft.weathr.databinding.HomeRecyclerViewBinding;
import com.smartnsoft.weathr.model.Forecast;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<Forecast> forecasts;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Forecast item, int color);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private HomeRecyclerViewBinding homeRecyclerViewBinding;

        // each data item is just a string in this case
        private ViewHolder(final HomeRecyclerViewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.homeRecyclerViewBinding = itemBinding;
        }

        private void homeRecyclerViewBinding(final Forecast forecast, final int position, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(forecast, position);
                }
            });
        }
    }

    public ForecastAdapter(List<Forecast> forecasts, Context context, OnItemClickListener listener) {
        this.forecasts = forecasts;
        this.context = context;
        this.onItemClickListener = listener;
    }

    public void updateList(List<Forecast> forecasts){
        this.forecasts = forecasts;
        notifyDataSetChanged();
    }

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HomeRecyclerViewBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.home_recycler_view, parent, false);
        return new ViewHolder(binding);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.homeRecyclerViewBinding.setForecast(forecasts.get(position));
        holder.homeRecyclerViewBinding(forecasts.get(position), position, onItemClickListener);

        CardView cardView = (CardView) holder.itemView.findViewById(R.id.cardview_home);
        cardView.setCardBackgroundColor(context.getResources().getIntArray(R.array.colors)[position % 4]);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }
}