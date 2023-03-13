package org.tup.safeplace.HomeScreen.Barangay;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
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
import org.tup.safeplace.CallHistory.CallHistoryActivity;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BarangayCallScreenFragment extends Fragment {

    View view;
    public static final String TAG_NAME = "barangay_name";
    public static final String TAG_CONTACT = "barangay_contact";
    public static final String JSON_ARRAY = "data";

    private Spinner spinner;

    private ArrayList<String> barangays;

    private JSONArray data;

    private TextView barangayContact;
    private Button btnCallHistory;

    ImageButton btnBarangayCall;
    static int PERMISSION_CODE = 100;

    private SharedPreferences userPref;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_barangay_call_screen, container, false);
        init();
        return view;
    }

    private void init(){
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);


        barangays = new ArrayList<String>();



        spinner = view.findViewById(R.id.barangaySpinner);
        barangayContact = view.findViewById(R.id.barangayContactCall);
        btnBarangayCall = view.findViewById(R.id.btnBarangayCall);
        btnCallHistory = view.findViewById(R.id.btnCallHistoryBarangay);

        getData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                barangayContact.setText(getContact(i));
                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
                ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);



            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                barangayContact.setText("");
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 123);
            return;
        }

        btnCallHistory.setOnClickListener(v->{
                startActivity(new Intent(getContext(), CallHistoryActivity.class));
        });

        btnBarangayCall.setOnClickListener(v->{

            StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("user");
                    if (jsonObject.getBoolean("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String status = object.getString("role");


                            if (status.equals("unverified_user")) {
                                Toast.makeText(getContext(), "Register First", Toast.LENGTH_SHORT).show();

                            } else {

                                callLog();
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
            queue.add(request);


//            return true;
        });

    }

    private void callLog(){
        String name_contacted = spinner.getSelectedItem().toString();

        StringRequest request = new StringRequest(Request.Method.POST, API.barangay_call_log, response -> {

            String phoneNumber = barangayContact.getText().toString();
            Uri uri = Uri.parse("tel:" + Uri.encode(phoneNumber));
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
            intent.setData(uri);
            startActivity(intent);


        }, error -> {

            Toast.makeText(getContext(), "Error in Connection", Toast.LENGTH_SHORT).show();
            error.printStackTrace();

        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String, String> map =new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name_contacted",name_contacted);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(API.barangay_list_api, new Response.Listener<String>() {
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
                    getBarangay(data);
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

    private void getBarangay(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                barangays.add(json.getString(TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_text, barangays));

    }

    //Method to get barangay name of a particular position
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