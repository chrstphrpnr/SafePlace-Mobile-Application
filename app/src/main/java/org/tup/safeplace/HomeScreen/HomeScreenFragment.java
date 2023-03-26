package org.tup.safeplace.HomeScreen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.BarangaysMenuList.BarangayListActivity;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.GoogleMaps.MapsActivity;
import org.tup.safeplace.HospitalMenuList.HospitalListActivity;
import org.tup.safeplace.PoliceStationMenuList.PoliceStationListActivity;
import org.tup.safeplace.R;
import org.tup.safeplace.Report.BarangayReport.BarangayReportActivity;
import org.tup.safeplace.Report.PoliceReport.PoliceReportActivity;
import org.tup.safeplace.ReportsMenuList.ReportListActivity;
import org.tup.safeplace.Verification.VerificationActivity;
import org.tup.safeplace.databinding.ActivityMapsBinding;

import java.util.HashMap;
import java.util.Map;


public class HomeScreenFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 101;
    GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private View view;
    private CardView menuHospitalList, menuPoliceStationList, menuBarangayList, menuReportList, mapView;
    private Button btnReportHere;
    private SharedPreferences userPref;
    private ImageView ic_zoom;
    private ActivityMapsBinding binding;
    private JSONArray result;

    private ScrollView scrollViewHomeScreen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        reportVerification();
        init();


        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        myLocation();


        return view;


    }

    private void reportVerification() {

        StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String status = object.getString("role");

                        btnReportHere.setOnClickListener(v -> {

                            if (status.equals("unverified_user")) {
                                Toast.makeText(getContext(), "Register First", Toast.LENGTH_SHORT).show();
                                showPopupWindow(view);

                            } else {
//                                Toast.makeText(getContext(), "Report Here", Toast.LENGTH_SHORT).show();
                                showPopupWindowReport(view);
                            }

                        });

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            error.printStackTrace();
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
                checkStatus();
            }
        });


    }

    private void checkStatus(){


        SharedPreferences userPref = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);


        StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String role = object.getString("role");

                        String status = object.getString("status");


                        if (role.equals("unverified_user")) {

                            if(status.equals("Pending")){
                                showPopupWindow();
//                                Toast.makeText(getContext(), "Your account is already in the process of verification, please wait.", Toast.LENGTH_SHORT).show();
                            }

                            if(status.equals("Rejected")){
                                startActivity(new Intent(getContext(), VerificationActivity.class));
                            }

                            if(status.equals("Unverified")){
                                startActivity(new Intent(getContext(), VerificationActivity.class));
                            }

                            if(status.equals("Banned")){
                                Toast.makeText(getContext(), "Your Account is Banned. Please Contact the Admin", Toast.LENGTH_SHORT).show();
                            }


                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            error.printStackTrace();

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    //PopUp Windows
    private void showPopupWindow() {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_pending_user_pop_up, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button txtSkipPending = popupView.findViewById(R.id.txtPendingClose);

        txtSkipPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }


    private void showPopupWindowReport(final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.report_pop_up_window, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button btnReportBarangay = popupView.findViewById(R.id.btnReportBarangay);

        Button btnReportPolice = popupView.findViewById(R.id.btnReportPolice);

        btnReportBarangay.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BarangayReportActivity.class));
            popupWindow.dismiss();
        });

        btnReportPolice.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PoliceReportActivity.class));
            popupWindow.dismiss();
        });


    }


    private void init() {

        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        menuHospitalList = view.findViewById(R.id.menuHospitalList);
        menuPoliceStationList = view.findViewById(R.id.menuPoliceStationList);
        mapView = view.findViewById(R.id.mapView);
        menuBarangayList = view.findViewById(R.id.menuBarangayList);
        btnReportHere = view.findViewById(R.id.btnReportHere);
//        menuReportList = view.findViewById(R.id.menuReportList);

        scrollViewHomeScreen = view.findViewById(R.id.scrollViewHomeScreen);

        ic_zoom = view.findViewById(R.id.ic_zoom);

        ic_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });

        mapView.setOnClickListener(v -> {
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
//
//        menuReportList.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), ReportListActivity.class));
//        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Location location = null;
        mMap = googleMap;
        myLocation();
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.mapstyle));


    }


    private void myLocation(){

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE);
            return;
        }


            Task<Location> task = fusedLocationProviderClient.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    mMap.addMarker(new MarkerOptions().position(myLocation).title("Current Location")
                            .icon(bitmapDescriptorFromVector(getActivity().getApplicationContext(), R.drawable.ic_usermarker)
                            ));

                    float zoomLevel = 15.0f; //This goes up to 21
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, zoomLevel));


                }
            });

            getHospitalsLocation();
            getBarangaysLocation();
            getPoliceStationsLocation();


    }



    //Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myLocation();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            myLocation();
        }
        else{
            mMap.clear();
        }
    }

    //Converts Image in Drawable to Bitmap
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int VectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, VectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    //Show markers of Hospitals
    private void getHospitalsLocation() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API.hospital_map, response -> {

            Log.d("JSONResult", response);
            JSONObject j = null;
            try {
                j = new JSONObject(response);
                result = j.getJSONArray("hospitals");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject object = result.getJSONObject(i);
                    String hospitalLatitude = object.getString("latitude");
                    String hospitalLongtitude = object.getString("longitude");
                    String hospital_name = object.getString("hospital_name");

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(hospitalLatitude), Double.parseDouble(hospitalLongtitude)))
                            .title(hospital_name)
                            .icon(bitmapDescriptorFromVector(getActivity().getApplicationContext(), R.drawable.ic_hospitalmarker))
                    );


                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);





    }

    //Show Markers of Barangay
    private void getBarangaysLocation() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API.barangay_map, response -> {

            Log.d("JSONResult", response);
            JSONObject j = null;
            try {
                j = new JSONObject(response);
                result = j.getJSONArray("barangay");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject object = result.getJSONObject(i);
                    String lat_i = object.getString("latitude");
                    String long_i = object.getString("longitude");
                    String barangay_name = object.getString("barangay_name");

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(lat_i), Double.parseDouble(long_i)))
                            .title(barangay_name)
                            .icon(bitmapDescriptorFromVector(getActivity().getApplicationContext(), R.drawable.ic_barangaymarker))
                    );


                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    //Show Markers of Police Station
    private void getPoliceStationsLocation() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API.policeStation_map, response -> {

            Log.d("JSONResult", response);
            JSONObject j = null;
            try {
                j = new JSONObject(response);
                result = j.getJSONArray("police_station");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject object = result.getJSONObject(i);
                    String lat_i = object.getString("latitude");
                    String long_i = object.getString("longitude");
                    String policestation_name = object.getString("policestation_name");

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(lat_i), Double.parseDouble(long_i)))
                            .title(policestation_name)
                            .icon(bitmapDescriptorFromVector(getActivity().getApplicationContext(), R.drawable.ic_policemarker))
                    );


                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(stringRequest);
    }


}