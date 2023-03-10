package org.tup.safeplace.Verification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class UpdateInformationActivity extends AppCompatActivity {

    private SharedPreferences userPref;
    private EditText txtUpdateFName, txtUpdateMName, txtUpdateLName, txtUpdateGender, txtUpdateBirthdate, txtUpdateAddress, txtUpdateContact;
    Button btnInfoSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);

        userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);


        txtUpdateFName = findViewById(R.id.txtUpdateFName);
        txtUpdateMName = findViewById(R.id.txtUpdateMName);
        txtUpdateLName = findViewById(R.id.txtUpdateLName);
        txtUpdateGender = findViewById(R.id.txtUpdateGender);
        txtUpdateBirthdate = findViewById(R.id.txtUpdateBirthdate);
        txtUpdateAddress = findViewById(R.id.txtUpdateAddress);
        txtUpdateContact = findViewById(R.id.txtUpdateContact);
        btnInfoSubmit = findViewById(R.id.btnInfoSubmit);


        btnInfoSubmit.setOnClickListener(v->{
            UpdateData();
        });


        getData();


    }

    private void UpdateData() {

        String fname = txtUpdateFName.getText().toString().trim();
        String mname = txtUpdateMName.getText().toString().trim();
        String lname = txtUpdateLName.getText().toString().trim();
        String gender = txtUpdateGender.getText().toString().trim();
        String birthdate = txtUpdateBirthdate.getText().toString().trim();
        String address = txtUpdateAddress.getText().toString().trim();
        String contact = txtUpdateContact.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, API.update_information, response -> {


            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if(jsonObject.getBoolean("success")){
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        SharedPreferences.Editor editor = userPref.edit();

                        editor.putString("fname",object.getString("fname"));
                        editor.putString("mname",object.getString("mname"));
                        editor.putString("lname",object.getString("lname"));
                        editor.putString("gender",object.getString("gender"));
                        editor.putString("birthdate",object.getString("birthdate"));
                        editor.putString("address",object.getString("address"));
                        editor.putString("contact",object.getString("contact"));
                        editor.putString("email",object.getString("email"));

                        editor.apply();
                    }

                    Toast.makeText(UpdateInformationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateInformationActivity.this, IdentificationCardInformationActivity.class));
                    finish();


                }
            } catch (JSONException e) {
                Toast.makeText(UpdateInformationActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }



        }, error -> {

            Toast.makeText(this, "Error in Connection", Toast.LENGTH_SHORT).show();
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
                map.put("fname",fname);
                map.put("mname",mname);
                map.put("lname",lname);
                map.put("gender",gender);
                map.put("birthdate",birthdate);
                map.put("address",address);
                map.put("contact",contact);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



    private void getData(){

        StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        txtUpdateFName.setText(object.getString("fname"));
                        txtUpdateMName.setText(object.getString("mname"));
                        txtUpdateLName.setText(object.getString("lname"));
                        txtUpdateGender.setText(object.getString("gender"));
                        txtUpdateBirthdate.setText(object.getString("birthdate"));
                        txtUpdateAddress.setText(object.getString("address"));
                        txtUpdateContact.setText(object.getString("contact"));


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