package org.tup.safeplace.PoliceStationMenuList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

public class PoliceStationListActivity extends AppCompatActivity {
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station_list);
        btnBack = findViewById(R.id.btnPoliceStationBack);


        btnBack.setOnClickListener(v->{
            startActivity(new Intent(PoliceStationListActivity.this, SafePlaceHomeScreenActivity.class));
            finish();
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.framePoliceStationListContainer,new PoliceStationListMenuFragment()).commit();

    }
}