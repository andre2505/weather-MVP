package com.smartnsoft.weathr.feature.detail;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.smartnsoft.weathr.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //compatibility all version

        View view = findViewById(R.id.navigation);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE && getResources().getBoolean(R.bool.isTablet)) {
            Log.e("DUAL PANE", "okokok");
            finish();
            return;
        }

        if (savedInstanceState == null) {
            DetailFragment details = new DetailFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.content_detail, details).commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
