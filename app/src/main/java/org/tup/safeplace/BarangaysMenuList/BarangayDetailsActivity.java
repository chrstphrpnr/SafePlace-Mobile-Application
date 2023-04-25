package org.tup.safeplace.BarangaysMenuList;

import android.content.Intent;
import android.os.Bundle;
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

import java.time.Year;
import java.util.Calendar;

public class BarangayDetailsActivity extends AppCompatActivity {

    TextView tvDetailBarangayTitle, tvDetailBarangayName, tvDetailBarangayCaptain, tvDetailBarangayLocation, tvDetailBarangaySchedule, tvDetailBarangayContacts,
            tvBrgyStatisticalReportCurrentYearTitle,tvBrgyStatisticalReportYear,tvBrgyStatisticalReportMonth;
    Barangay Barangay;
    ImageView imgDetailsBarangay, btnBarangayDetailsBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_details);

        tvDetailBarangayTitle = findViewById(R.id.txtDetailBarangayNameTitle);
        tvDetailBarangayName = findViewById(R.id.txtDetailBarangayName);
        tvDetailBarangayCaptain = findViewById(R.id.txtDetailBarangayCaptain);
        tvDetailBarangayLocation = findViewById(R.id.txtDetailBarangayLocation);
        tvDetailBarangaySchedule = findViewById(R.id.txtDetailBarangaySchedule);
        tvDetailBarangayContacts = findViewById(R.id.txtDetailBarangayContact);

        imgDetailsBarangay = findViewById(R.id.imgDetailsBarangay);

        tvBrgyStatisticalReportCurrentYearTitle = findViewById(R.id.txtBrgyStatisticalReportCurrentYearTitle);

        tvBrgyStatisticalReportYear = findViewById(R.id.txtBrgyStatisticalReportYear);
        tvBrgyStatisticalReportMonth = findViewById(R.id.txtStatisticalReportMonth);



        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            Barangay = (Barangay) intent.getSerializableExtra("item");

            String barangay_station_name = Barangay.getBarangay_name();


            tvDetailBarangayTitle.setText(barangay_station_name);
            tvDetailBarangayName.setText(Barangay.getBarangay_name());
            tvDetailBarangayCaptain.setText(Barangay.getBarangay_captain());
            tvDetailBarangayLocation.setText(Barangay.getBarangay_location());
            tvDetailBarangaySchedule.setText(Barangay.getBarangay_schedule());
            tvDetailBarangayContacts.setText(Barangay.getBarangay_contact());
            Picasso.get().load(API.URL + Barangay.getImg()).resize(500, 0).centerCrop().into(imgDetailsBarangay);

            if(barangay_station_name.equals("Barangay Central Bicutan")){
                barangay_centralbicutanYear();
                barangay_centralbicutanMonth();
            }

            if(barangay_station_name.equals("Barangay Central Signal Village")){
                barangay_centralsignalvillageYear();
                barangay_centralsignalvillageMonth();
            }

            if(barangay_station_name.equals("Barangay Fort Bonifacio")){
                barangay_fortbonifacioYear();
                barangay_fortbonifacioMonth();
            }

            if(barangay_station_name.equals("Barangay Katuparan")){
                barangay_katuparanYear();
                barangay_katuparanMonth();
            }

            if(barangay_station_name.equals("Barangay Maharlika Village")){
                barangay_maharlikavillageYear();
                barangay_maharlikavillageMonth();
            }

            if(barangay_station_name.equals("Barangay North Daanghari")){
                barangay_northdaanghariYear();
                barangay_northdaanghariMonth();
            }

            if(barangay_station_name.equals("Barangay North Signal Village")){
                barangay_northsignalvillageYear();
                barangay_northsignalvillageMonth();
            }

            if(barangay_station_name.equals("Barangay Pinagsama")){
                barangay_pinagsamaYear();
                barangay_pinagsamaMonth();
            }

            if(barangay_station_name.equals("Barangay South Daanghari")){
                barangay_southdaanghariYear();
                barangay_southdaanghariMonth();
            }

            if(barangay_station_name.equals("Barangay South Signal Village")){
                barangay_southsignalvillageYear();
                barangay_southsignalvillageMonth();
            }

            if(barangay_station_name.equals("Barangay Tanyag")){
                barangay_tanyagYear();
                barangay_tanyagMonth();
            }

            if(barangay_station_name.equals("Barangay Upper Bicutan")){
                barangay_upperbicutanYear();
                barangay_upperbicutanMonth();
            }

            if(barangay_station_name.equals("Barangay Western Bicutan")){
                barangay_westernbicutanYear();
                barangay_westernbicutanMonth();
            }

        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        tvBrgyStatisticalReportCurrentYearTitle.setText(String.valueOf(year));

        btnBarangayDetailsBack = findViewById(R.id.btnBarangayDetailsBack);
        btnBarangayDetailsBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

    }




    private void barangay_centralbicutanYear(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_centralbicutan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_centralsignalvillageYear(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_centralsignalvillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_fortbonifacioYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_fortbonifacio");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_katuparanYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_katuparan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_maharlikavillageYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_maharlikavillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_northdaanghariYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_northdaanghari");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_northsignalvillageYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_northsignalvillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_pinagsamaYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_pinagsama");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_southdaanghariYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_southdaanghari");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_southsignalvillageYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_southsignalvillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_tanyagYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_tanyag");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_upperbicutanYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_upperbicutan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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
    private void barangay_westernbicutanYear() {
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_westernbicutan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportYear.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportYear.append("\n");

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


    private void barangay_centralbicutanMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_centralbicutan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_centralsignalvillageMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_centralsignalvillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_fortbonifacioMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_fortbonifacio");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_katuparanMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_katuparan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_maharlikavillageMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_maharlikavillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_northdaanghariMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_northdaanghari");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_northsignalvillageMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_northsignalvillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_pinagsamaMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_pinagsama");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_southdaanghariMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_southdaanghari");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_southsignalvillageMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_southsignalvillage");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_tanyagMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_tanyag");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_upperbicutanMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_upperbicutan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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
    private void barangay_westernbicutanMonth(){
        StringRequest request = new StringRequest(Request.Method.GET, API.brgy_common_crime_month, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("barangay_westernbicutan");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int countingNumber = i + 1;
                            JSONObject object = jsonArray.getJSONObject(i);

                            String incident_type = object.getString("incident_type");

                            tvBrgyStatisticalReportMonth.append(countingNumber+". "+incident_type);
                            tvBrgyStatisticalReportMonth.append("\n");

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