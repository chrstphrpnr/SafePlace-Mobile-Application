package org.tup.safeplace.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Authentication.AuthenticationActivity;
import org.tup.safeplace.BarangaysMenuList.BarangayListActivity;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HospitalMenuList.HospitalListActivity;
import org.tup.safeplace.PoliceStationMenuList.PoliceStationListActivity;
import org.tup.safeplace.R;

import java.util.HashMap;
import java.util.Map;


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