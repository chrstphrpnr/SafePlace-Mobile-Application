package org.tup.safeplace.PoliceStationMenuList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.tup.safeplace.R;

public class PoliceStationDetailActivity extends AppCompatActivity {
    private TextView tvDetailPoliceTitle,tvDetailPoliceName, tvDetailPoliceCommander,tvDetailPoliceLocation,tvDetailPoliceSchedule,tvDetailPoliceWebsite;
    private ImageView btnBack;

    PoliceStation PoliceStation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station_detail);

        btnBack = findViewById(R.id.btnPoliceStationDetailBack);

        tvDetailPoliceTitle = findViewById(R.id.txtDetailPoliceStationNameTitle);
        tvDetailPoliceName = findViewById(R.id.txtDetailPoliceStationName);
        tvDetailPoliceCommander = findViewById(R.id.txtDetailPoliceStationCommander);
        tvDetailPoliceLocation = findViewById(R.id.txtDetailPoliceStationLocation);
        tvDetailPoliceSchedule = findViewById(R.id.txtDetailPoliceStationSchedule);
        tvDetailPoliceWebsite = findViewById(R.id.txtDetailPoliceStationWebsite);

        Intent intent = getIntent();

        if (intent.getExtras()!=null){
            PoliceStation = (PoliceStation) intent.getSerializableExtra("item");

            tvDetailPoliceTitle.setText(PoliceStation.getPolicestation_name());
            tvDetailPoliceName.setText(PoliceStation.getPolicestation_name());
            tvDetailPoliceCommander.setText(PoliceStation.getPolicestation_commander());
            tvDetailPoliceLocation.setText(PoliceStation.getPolicestation_location());
            tvDetailPoliceSchedule.setText(PoliceStation.getPolicestation_schedule());
            tvDetailPoliceWebsite.setText(PoliceStation.getPolicestation_contact());
        }




        btnBack.setOnClickListener(v->{
            startActivity(new Intent(PoliceStationDetailActivity.this, PoliceStationListActivity.class));

        });
    }
}