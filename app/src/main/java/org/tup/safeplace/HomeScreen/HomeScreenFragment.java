package org.tup.safeplace.HomeScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.BarangaysMenuList.BarangayListActivity;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HospitalMenuList.HospitalListActivity;
import org.tup.safeplace.GoogleMaps.MapsActivity;
import org.tup.safeplace.PoliceStationMenuList.PoliceStationListActivity;
import org.tup.safeplace.R;
import org.tup.safeplace.Report.ReportActivity;
import org.tup.safeplace.ReportsMenuList.ReportListActivity;
import org.tup.safeplace.Verification.VerificationActivity;

import java.util.HashMap;
import java.util.Map;


public class HomeScreenFragment extends Fragment {

    private View view;
    private CardView menuHospitalList, menuPoliceStationList, menuBarangayList, menuReportList, mapView;
    private Button btnReportHere;

    private SharedPreferences userPref;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        reportVerification();
        init();
        return view;


    }

    private void reportVerification() {

        StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")){
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String status = object.getString("role");

                        btnReportHere.setOnClickListener(v -> {

                            if(status.equals("unverified_user")){
                                Toast.makeText(getContext(), "Register First", Toast.LENGTH_SHORT).show();
                                showPopupWindow(view);

                            }

                            else{
                                startActivity(new Intent(getContext(), ReportActivity.class));
                                Toast.makeText(getContext(), "Report Here", Toast.LENGTH_SHORT).show();
                            }

                        });

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            error.printStackTrace();
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    private void showPopupWindow(final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_unregistered_user_pop_up, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView skip = popupView.findViewById(R.id.txtSkipUnregistered);

        TextView unregisteredRegister = popupView.findViewById(R.id.txtRegisterUnregistered);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });

        unregisteredRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VerificationActivity.class));
            }
        });





    }




    private void init() {

        userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
        menuHospitalList = view.findViewById(R.id.menuHospitalList);
        menuPoliceStationList = view.findViewById(R.id.menuPoliceStationList);
        mapView = view.findViewById(R.id.mapView);
        menuBarangayList = view.findViewById(R.id.menuBarangayList);
        btnReportHere = view.findViewById(R.id.btnReportHere);
        menuReportList = view.findViewById(R.id.menuReportList);

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
            startActivity(new Intent(getContext(), BarangayListActivity.class));
        });

        menuReportList.setOnClickListener(v->{
            startActivity(new Intent(getContext(), ReportListActivity.class));
        });







    }



}