package org.tup.safeplace.PoliceStationMenuList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

public class PoliceStationDetailActivity extends AppCompatActivity {
    PoliceStation PoliceStation;
    private TextView tvDetailPoliceTitle, tvDetailPoliceName, tvDetailPoliceCommander, tvDetailPoliceLocation, tvDetailPoliceSchedule, tvDetailPoliceWebsite, tvStatisticalReportYear, tvStatisticalReportMonth;
    private ImageView btnBack, imgDetailsPoliceStation;

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
        imgDetailsPoliceStation = findViewById(R.id.imgDetailsPoliceStation);

        tvStatisticalReportYear = findViewById(R.id.txtStatisticalReportYear);

        tvStatisticalReportMonth = findViewById(R.id.txtStatisticalReportMonth);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            PoliceStation = (PoliceStation) intent.getSerializableExtra("item");

            String police_station_name = PoliceStation.getPolicestation_name();


            tvDetailPoliceTitle.setText(police_station_name);
            tvDetailPoliceName.setText(PoliceStation.getPolicestation_name());
            tvDetailPoliceCommander.setText(PoliceStation.getPolicestation_commander());
            tvDetailPoliceLocation.setText(PoliceStation.getPolicestation_location());
            tvDetailPoliceSchedule.setText(PoliceStation.getPolicestation_schedule());
            tvDetailPoliceWebsite.setText(PoliceStation.getPolicestation_contact());
            Picasso.get().load(API.URL + PoliceStation.getImg()).resize(500, 0).centerCrop().into(imgDetailsPoliceStation);


            if(police_station_name.equals("Fort Bonifacio Police Sub-Station 1")){
                Sub1Year();
                Sub1Month();
            }
            if(police_station_name.equals("Western Bicutan Police Sub-Station 2")){
                Sub2Year();
                Sub2Month();

            }
            if(police_station_name.equals("Sub-Station 3 Pinagsama")){
                Sub3Year();
                Sub3Month();

            }
            if(police_station_name.equals("Police Sub-Station 6, Signal Village")){
                Sub6Year();
                Sub6Month();

            }
            if(police_station_name.equals("MCU Sub-Station 7 Taguig City Police Station")){
                Sub7Year();
                Sub7Month();

            }
            if(police_station_name.equals("Sub-Station 8 Tanyag Daang Hari")){
                Sub8Year();
                Sub8Month();

            }



        }


        btnBack.setOnClickListener(v -> {
            onBackPressed();
            finish();

        });
    }

    private void Sub1Year(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub1");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvStatisticalReportYear.append("\n");

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub2Year(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub2");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvStatisticalReportYear.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub3Year(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub3");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvStatisticalReportYear.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub6Year(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub6");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvStatisticalReportYear.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub7Year(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub7");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvStatisticalReportYear.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub8Year(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub8");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvStatisticalReportYear.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }


    private void Sub1Month(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub1");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvStatisticalReportMonth.append("\n");

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub2Month(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub2");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvStatisticalReportMonth.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub3Month(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub3");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvStatisticalReportMonth.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub6Month(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub6");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvStatisticalReportMonth.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub7Month(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub7");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvStatisticalReportMonth.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }
    private void Sub8Month(){
        StringRequest request = new StringRequest(Request.Method.GET, API.psub_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("policesub8");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            Log.d( "sample" , incident_type);




                            tvStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvStatisticalReportMonth.append("\n");




                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }



}