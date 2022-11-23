package org.tup.safeplace.HomeScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.tup.safeplace.BarangaysMenuList.BarangayListActivity;
import org.tup.safeplace.HospitalMenuList.HospitalListActivity;
import org.tup.safeplace.GoogleMaps.MapsActivity;
import org.tup.safeplace.PoliceStationMenuList.PoliceStationListActivity;
import org.tup.safeplace.R;


public class HomeScreenFragment extends Fragment {

    private View view;
    private CardView menuHospitalList, menuPoliceStationList, menuBarangayList, mapView;
    private Button btnReportHere;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        init();
        return view;

    }

    private void init() {
        menuHospitalList = view.findViewById(R.id.menuHospitalList);
        menuPoliceStationList = view.findViewById(R.id.menuPoliceStationList);
        mapView = view.findViewById(R.id.mapView);
        menuBarangayList = view.findViewById(R.id.menuBarangayList);
        btnReportHere = view.findViewById(R.id.btnReportHere);

        mapView.setOnClickListener(v->{
            startActivity(new Intent(getContext(), MapsActivity.class));
        });

        menuHospitalList.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), HospitalListActivity.class));
        });

        menuPoliceStationList.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PoliceStationListActivity.class));
        });

        menuBarangayList.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "BARANGAY", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), BarangayListActivity.class));
        });


        btnReportHere.setOnClickListener(v -> {
            Toast.makeText(getContext(), "REPORT HERE", Toast.LENGTH_SHORT).show();
        });
    }


}