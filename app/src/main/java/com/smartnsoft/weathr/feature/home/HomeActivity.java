package com.smartnsoft.weathr.feature.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.smartnsoft.weathr.BuildConfig;
import com.smartnsoft.weathr.feature.about.AboutActivity;
import com.smartnsoft.weathr.R;
import com.smartnsoft.weathr.base.mvp.MvpActivity;
import com.smartnsoft.weathr.databinding.ActivityHomeBinding;
import com.smartnsoft.weathr.model.City;
import com.smartnsoft.weathr.session.UserSession;
import com.smartnsoft.weathr.utils.CustomAlerDialog;
import com.smartnsoft.weathr.utils.Network;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends MvpActivity<HomePresenter> implements HomeView, DialogInterface.OnClickListener, View.OnClickListener {

    private ActivityHomeBinding activityHomeBinding;
    private AlertDialog mAlertDialogParameters;
    private HomeListFragment mFragementListHomeManager;
    private TextView customTextView;
    private Button customButton;
    private final static int PERMISSION_REQUEST = 42;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        //initialize toolbar
        setSupportActionBar(activityHomeBinding.navigation.toolbar);

        //initialize error view
        customTextView = (TextView) activityHomeBinding.customView.findViewById(R.id.custom_text);
        customButton = (Button) activityHomeBinding.customView.findViewById(R.id.custom_button);
        customButton.setVisibility(View.GONE);
        customTextView.setVisibility(View.GONE);
        customButton.setOnClickListener(this);

        //permission
        ActivityCompat.requestPermissions(HomeActivity.this, permissions, PERMISSION_REQUEST);
        //location
        location();

        //fragmentManager to communicate with child fragment
        mFragementListHomeManager = (HomeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list_home);
        if (UserSession.getCity(this) != null && UserSession.getForecast(this) != 0) {
            presenter.loadData(UserSession.getCity(this), UserSession.getForecast(this));
        } else {
            customTextView.setVisibility(View.VISIBLE);
            customTextView.setText(getString(R.string.begin_setting));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                getAlertDialogParameters();
                break;
            case R.id.about:
                Intent intent = new Intent();
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getDataSuccess(City model) {
        customTextView.setVisibility(View.GONE);
        customButton.setVisibility(View.GONE);
        activityHomeBinding.contentList.setVisibility(View.VISIBLE);
        activityHomeBinding.setCity(model);
        mFragementListHomeManager.getCityFromActivity(model.getForecasts());
        UserSession.setPreferences(this, model.getName(), model.getForecasts().size());
    }

    @Override
    public void getDataFail(String message) {
        Log.e("ERROR", message);
        activityHomeBinding.contentList.setVisibility(View.GONE);
        customTextView.setVisibility(View.VISIBLE);
        customButton.setVisibility(View.VISIBLE);
        if (!Network.NetworkIsEnabled(this)) {
            customTextView.setText(R.string.connectivity_problem);
        } else {
            customTextView.setText(R.string.unavailable_item);
        }
    }

    @Override
    public void refreshData() {
        presenter.loadData(UserSession.getCity(this), UserSession.getForecast(this));
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case -1:
                EditText mCityText = (EditText) mAlertDialogParameters.findViewById(R.id.edittext_city_dialog);
                EditText mParametersText = (EditText) mAlertDialogParameters.findViewById(R.id.edittext_parameters_dialog);
                if (!mParametersText.getText().toString().equals("") && !mCityText.getText().toString().equals("")) {
                    presenter.loadData(mCityText.getText().toString(), Integer.parseInt(mParametersText.getText().toString()));
                    dialogInterface.cancel();
                } else {
                    Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
                }
                break;
            case -2:
                dialogInterface.cancel();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        getAlertDialogParameters();
    }

    private void getAlertDialogParameters() {
        AlertDialog.Builder mAlertParameter = CustomAlerDialog.parametersForecast(HomeActivity.this);
        mAlertParameter.setPositiveButton("OK", this);
        mAlertParameter.setNegativeButton("Fermer", this);
        mAlertDialogParameters = mAlertParameter.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                location();
                break;
        }
    }

    private void location() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initLocation();
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            initLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                String locationUser = fetchedAddress.getAddressLine(0);
                Log.i("LOCATION", locationUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
