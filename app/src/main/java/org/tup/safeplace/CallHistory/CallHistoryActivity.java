package org.tup.safeplace.CallHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CallHistoryActivity extends AppCompatActivity {

    private SharedPreferences userPref;


    private ListView myListViewBarangayCallHistory;
    CallHistoryAdapter adapter;
    public static ArrayList<CallHistory> callHistoryArrayList = new ArrayList<>();
    CallHistory barangay;

    private ImageView btnCallHistoryBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);

        userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        myListViewBarangayCallHistory = findViewById(R.id.myListViewBarangayCallHistory);
        adapter =new CallHistoryAdapter(this, callHistoryArrayList);
        myListViewBarangayCallHistory.setAdapter(adapter);

        btnCallHistoryBack = findViewById(R.id.btnCallHistoryBack);

        btnCallHistoryBack.setOnClickListener(v->{
            onBackPressed();
            finish();
        });

        callHistory();

    }

    private void callHistory(){

        StringRequest request = new StringRequest(Request.Method.GET, API.call_history, response -> {
            callHistoryArrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("call_log");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        String name_contacted = object.getString("name_contacted");
                        String type_contacted = object.getString("type_contacted");
                        String date_contacted = object.getString("date_contacted");
                        String time_contacted = object.getString("time_contacted");

                        barangay = new CallHistory(name_contacted,type_contacted,date_contacted,time_contacted);
                        callHistoryArrayList.add(barangay);
                        adapter.notifyDataSetChanged();

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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}