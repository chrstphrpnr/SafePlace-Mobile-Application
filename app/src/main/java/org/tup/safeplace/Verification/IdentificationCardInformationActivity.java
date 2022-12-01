package org.tup.safeplace.Verification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tup.safeplace.Authentication.UserInfoRegisterActivity;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.util.HashMap;
import java.util.Map;

public class IdentificationCardInformationActivity extends AppCompatActivity {

    private EditText edtCardType,edtCardNumber;
    private Button btnContinue;
    private ImageView btnback;
    private SharedPreferences userPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification_card_information);
        init();
    }

    private void init(){
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);


        edtCardType = findViewById(R.id.edtIdentificationCardType);
        edtCardNumber = findViewById(R.id.edtIdentificationCardNumber);
        btnContinue = findViewById(R.id.btnContinueCardInformation);
        btnback = findViewById(R.id.btnCardInfoBack);

        btnback.setOnClickListener(v->{
            onBackPressed();
        });

        btnContinue.setOnClickListener(v->{
            postCardDetails();
        });

    }

    private void postCardDetails(){

        String id_type = edtCardType.getText().toString().trim();
        String id_number = edtCardNumber.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, API.id_information, response -> {

            try{

                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if(jsonObject.getBoolean("success")){
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        SharedPreferences.Editor editor = userPref.edit();
                        editor.putString("id_type",object.getString("id_type"));
                        editor.putString("id_number",object.getString("id_number"));

//                        editor.putBoolean("IsIdDetails",true);
                        editor.apply();
                    }

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(IdentificationCardInformationActivity.this, SafePlaceHomeScreenActivity.class));
                    finish();
                }

            } catch (Exception e){
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
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
                map.put("id_type",id_type);
                map.put("id_number",id_number);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

}