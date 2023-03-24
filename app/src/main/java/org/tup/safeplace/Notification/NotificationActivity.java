package org.tup.safeplace.Notification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    public static ArrayList<NotificationModel> notificationModelArrayList = new ArrayList<>();
    ListView listView;
    NotificationAdapter adapter;
    NotificationModel notificationModel;
    ImageView btnBack;
    MaterialButton btnRead;
    private SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);

        btnBack = findViewById(R.id.btnNotificationBack);

        btnRead = findViewById(R.id.btnRead);


        listView = findViewById(R.id.myListView);
        adapter = new NotificationAdapter(this, notificationModelArrayList);
        listView.setAdapter(adapter);

        Notification();


        btnBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        btnRead.setOnClickListener(v -> {

            StringRequest request = new StringRequest(Request.Method.POST, API.notification_mark_as_read, response -> {

                startActivity(getIntent());
                finish();


            }, error -> {
                Toast.makeText(this, "Please Try Again.", Toast.LENGTH_SHORT).show();
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


        });


    }

    public void Notification() {
        StringRequest request = new StringRequest(Request.Method.GET, API.notification, response -> {
            notificationModelArrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("notification");

                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String message = object.getString("message");
                        String status = object.getString("status");


                        notificationModel = new NotificationModel(message, status);

                        notificationModelArrayList.add(notificationModel);
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