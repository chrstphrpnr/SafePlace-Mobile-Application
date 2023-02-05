package org.tup.safeplace.HomeScreen.Police;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.ArrayList;


public class PoliceCallScreenFragment extends Fragment {

    private View view;
    public static final String TAG_NAME = "policestation_name";
    public static final String TAG_CONTACT = "policestation_contact";
    public static final String JSON_ARRAY = "data";

    private Spinner spinner;

    private ArrayList<String> policeStations;

    private JSONArray data;

    private TextView policeContact;

    ImageButton btnPoliceCall;
    static int PERMISSION_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_police_call_screen, container, false);
        init();
        return view;
    }


    private void init(){

        policeStations = new ArrayList<String>();
        policeStations.add("Please Select Here...");

        spinner = view.findViewById(R.id.policeSpinner);
        policeContact = view.findViewById(R.id.policeContactCall);
        btnPoliceCall = view.findViewById(R.id.btnPoliceCall);


        getData();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                policeContact.setText(getContact(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                policeContact.setText("");
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
            return;
        }

        btnPoliceCall.setOnClickListener(v->{
            String phoneNumber = policeContact.getText().toString();
            Uri uri = Uri.parse("tel:" + Uri.encode(phoneNumber));
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
            intent.setData(uri);
            startActivity(intent);

        });


    }

    private void getData(){

        StringRequest stringRequest = new StringRequest(API.police_stations_list_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject j = null;
                try {
                    //Parsing the fetched Json String to JSON Object
                    try {
                        j = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Storing the Array of JSON String to our JSON Array
                    data = j.getJSONArray(JSON_ARRAY);

                    //Calling method getStudents to get the students from the JSON Array
                    getPoliceStations(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);


    }

    private void getPoliceStations(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                policeStations.add(json.getString(TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_text, policeStations));
    }

    //Method to get student name of a particular position
    private String getContact(int position){
        String contact="";
        try {
            //Getting object of given index
            JSONObject json = data.getJSONObject(position);

            //Fetching name from that object
            contact = json.getString(TAG_CONTACT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return contact;
    }










}