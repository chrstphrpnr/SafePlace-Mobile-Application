package org.tup.safeplace.Authentication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoRegisterActivity extends AppCompatActivity {

    private TextInputLayout layoutAddress, layoutBirthDate, layoutGender, layoutContact;
    private EditText txtAddress, txtBirthDate, txtGender, txtContact;
    private TextView txtSelectPhoto;
    private Button btnContinue;
    private CircleImageView circleImageView;
    ActivityResultLauncher<String> mTakePhoto;
    private Bitmap bitmap = null;
    private SharedPreferences userPref;
    private ProgressDialog progressDialog;
    String encodedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_register);
        init();
    }

    private void init(){
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);

        layoutAddress = findViewById(R.id.txtLayoutAddressUserInfo);
        layoutBirthDate = findViewById(R.id.txtLayoutBirthDateUserInfo);
        layoutGender = findViewById(R.id.txtLayoutGenderUserInfo);
        layoutContact = findViewById(R.id.txtLayoutContactUserInfo);

        txtAddress = findViewById(R.id.txtAddressUserInfo);
        txtBirthDate = findViewById(R.id.txtBirthDateUserInfo);
        txtGender = findViewById(R.id.txtGenderUserInfo);
        txtContact = findViewById(R.id.txtContactUserInfo);

        txtSelectPhoto = findViewById(R.id.txtSelectPhoto);

        btnContinue = findViewById(R.id.btnContinueUserInfo);

        circleImageView = findViewById(R.id.imgProfileUserInfo);



        txtSelectPhoto.setOnClickListener(v->{
            Dexter.withContext(UserInfoRegisterActivity.this)
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


        btnContinue.setOnClickListener(v->{
            if(validate()){
                saveUserInfo();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode==RESULT_OK){

            Uri filePath = data.getData();

            try{
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                circleImageView.setImageBitmap(bitmap);

                ImageStore(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validate(){

        if (txtAddress.getText().toString().isEmpty()){
            layoutAddress.setErrorEnabled(true);
            layoutAddress.setError("Address is Required");
            return false;
        }

        if (txtBirthDate.getText().toString().isEmpty()){
            layoutBirthDate.setErrorEnabled(true);
            layoutBirthDate.setError("BirthDate is Required");
            return false;
        }

        if (txtContact.getText().toString().isEmpty()){
            layoutContact.setErrorEnabled(true);
            layoutContact.setError("Contact is Required");
            return false;
        }

        if (txtGender.getText().toString().isEmpty()){
            layoutGender.setErrorEnabled(true);
            layoutGender.setError("Gender is Required");
            return false;
        }

        return true;
    }

    private void saveUserInfo(){
        String address = txtAddress.getText().toString().trim();
        String birthdate = txtBirthDate.getText().toString().trim();
        String gender = txtGender.getText().toString().trim();
        String contact = txtContact.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, API.save_user_info, response->{

            try {
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")){
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("img",object.getString("img"));
                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();
                    startActivity(new Intent(UserInfoRegisterActivity.this,SafePlaceHomeScreenActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        },error -> {
            Toast.makeText(this, "Error in Connection", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }){
            //Add Token to headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String, String> map =new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }

            //add params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("address",address);
                map.put("birthdate",birthdate);
                map.put("gender",gender);
                map.put("contact",contact);
                map.put("img",encodedImage);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void ImageStore(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte [] imageBytes = byteArrayOutputStream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }











}