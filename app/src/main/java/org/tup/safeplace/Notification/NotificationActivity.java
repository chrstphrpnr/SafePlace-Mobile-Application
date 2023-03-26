package org.tup.safeplace.Notification;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

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
                dialog.dismiss();



            }, error -> {
                Toast.makeText(this, "Please Try Again.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                dialog.dismiss();

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
            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);


        });


    }

    public void Notification() {
        dialog.setMessage("Loading...");
        dialog.show();
        notificationModelArrayList.clear();


        StringRequest request = new StringRequest(Request.Method.GET, API.notification, response -> {

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
                        dialog.dismiss();


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
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }




}