package org.tup.safeplace.PoliceStationMenuList;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.tup.safeplace.R;

public class PoliceStationListActivity extends AppCompatActivity {
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station_list);
        btnBack = findViewById(R.id.btnPoliceStationBack);


        btnBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.framePoliceStationListContainer, new PoliceStationListMenuFragment()).commit();

    }
}