package org.tup.safeplace.UserAccount;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAccountActivity extends AppCompatActivity {

    private SharedPreferences userPref;
    private CircleImageView imgAccountImage;
    private EditText txtUserAccountName,txtUserAccountEmail,txtUserAccountGender,txtUserAccountBirthDate,txtUserAccountAddress,txtUserAccountContactNumber;
    private TextView txtSelectAccountPhoto,txtSaveAccount;
    private Button btnChangePassword;


    private Bitmap bitmap = null;
    private String encodedImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        userPref = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        txtUserAccountName = findViewById(R.id.txtUserAccountName);
        txtUserAccountEmail = findViewById(R.id.txtUserAccountEmail);
        txtUserAccountGender = findViewById(R.id.txtUserAccountGender);
        txtUserAccountBirthDate = findViewById(R.id.txtUserAccountBirthDate);
        txtUserAccountAddress = findViewById(R.id.txtUserAccountAddress);
        txtUserAccountContactNumber = findViewById(R.id.txtUserAccountContactNumber);

        btnChangePassword = findViewById(R.id.btnChangePassword);



        imgAccountImage = findViewById(R.id.userProfileImage);
        txtSaveAccount = findViewById(R.id.txtSaveAccount);

        txtSelectAccountPhoto = findViewById(R.id.txtSelectAccountPhoto);

        txtSelectAccountPhoto.setOnClickListener(v->{
            Dexter.withContext(UserAccountActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();
        });

        txtSaveAccount.setOnClickListener(v->{
            updateData();
        });

        getData();

        btnChangePassword.setOnClickListener(v->{
            startActivity(new Intent(UserAccountActivity.this, ChangePasswordOTP.class));
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode==RESULT_OK){

            Uri filePath = data.getData();

            try{
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgAccountImage.setImageBitmap(bitmap);

                ImageStore(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateData() {

        StringRequest request = new StringRequest(Request.Method.POST, API.save_user_info, response -> {

            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if(jsonObject.getBoolean("success")){
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        SharedPreferences.Editor editor = userPref.edit();
                        editor.putString("img",object.getString("img"));
                        editor.apply();
                        finish();
                        startActivity(getIntent());

                    }
                }
            } catch (JSONException e) {
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
                map.put("img",encodedImage);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    private void ImageStore(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte [] imageBytes = byteArrayOutputStream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void getData() {

        StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")){
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        txtUserAccountName.setText(object.getString("fname")+" "+object.getString("mname")+" "+object.getString("lname"));
                        txtUserAccountEmail.setText(object.getString("email"));
                        Picasso.get().load(API.URL+object.getString("img")).resize(500,0).centerCrop().into(imgAccountImage);
                        txtUserAccountGender.setText(object.getString("gender"));
                        txtUserAccountBirthDate.setText(object.getString("birthdate"));
                        txtUserAccountAddress.setText(object.getString("address"));
                        txtUserAccountContactNumber.setText(object.getString("contact"));

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);



    }
}