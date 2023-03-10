package org.tup.safeplace.Verification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IdentificationCardInformationActivity extends AppCompatActivity {

    private Button btnContinue;
    private ImageView btnback, frontIdCam, frontIdImage,backIdCam, backIdImage;
    private SharedPreferences userPref;
    private TextView frontGuidetxt,backGuidetxt,frontRecapture,backRecapture;

    private TextInputLayout txtLayoutIdentificationCardNumber,dropDownLayoutIdType;
    private TextInputEditText edtIdentificationCardNumber;

    private RelativeLayout relativelayoutFront,relativelayoutBack;




    Bitmap bitmapfront, bitmapback;
    String encodedimagefront,encodedimageback;

    private String currentPhotoPathBackId, currentPhotoPathFrontId;

    private ProgressDialog dialog;


    String[] identificationCards = {"National ID","School ID", "Postal ID"};
    AutoCompleteTextView autoCompleteIdList;
    ArrayAdapter<String> adapterItems;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification_card_information);
        init();
    }

    private void init(){
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        relativelayoutFront = findViewById(R.id.relativelayoutFront);
        frontRecapture = findViewById(R.id.frontRecapture);
        relativelayoutBack = findViewById(R.id.relativelayoutBack);
        backRecapture = findViewById(R.id.backRecapture);



        edtIdentificationCardNumber = findViewById(R.id.edtIdentificationCardNumber);
        txtLayoutIdentificationCardNumber = findViewById(R.id.txtLayoutIdentificationCardNumber);

        dropDownLayoutIdType = findViewById(R.id.dropDownLayoutIdType);
        autoCompleteIdList = findViewById(R.id.autoCompleteIdtxt);



        frontIdCam = findViewById(R.id.frontIdCam);
        backIdCam = findViewById(R.id.backIdCam);

        frontIdImage = findViewById(R.id.frontIdImage);

        frontGuidetxt = findViewById(R.id.frontGuidetxt);

        backIdImage = findViewById(R.id.backIdImage);
        backGuidetxt = findViewById(R.id.backGuidetxt);

        btnContinue = findViewById(R.id.btnContinueCardInformation);



        adapterItems = new ArrayAdapter<String>(this,R.layout.identification_list_item, identificationCards);
        autoCompleteIdList.setAdapter(adapterItems);



        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        FrontIdCapture();
        BackIdCapture();


        btnback = findViewById(R.id.btnCardInfoBack);
        btnback.setOnClickListener(v->{
            onBackPressed();
        });

        btnContinue.setOnClickListener(v->{
            if(validate()){
                postCardDetails();
            }
        });

        frontRecapture.setOnClickListener(v->{

            Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    String fileNameFront = "photo";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


                    try {
                        File imageFileFront = File.createTempFile(fileNameFront,".jpg",storageDirectory);

                        currentPhotoPathFrontId = imageFileFront.getAbsolutePath();

                        Uri imageUriFront = FileProvider.getUriForFile(IdentificationCardInformationActivity.this, "org.tup.safeplace.fileprovider", imageFileFront);
                        Intent intentFront = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentFront.putExtra(MediaStore.EXTRA_OUTPUT,imageUriFront);
                        startActivityForResult(intentFront, 111);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                }
            }).check();
        });

        backRecapture.setOnClickListener(v->{

            Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    String fileNameBack = "photo";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


                    try {
                        File imageFileBack = File.createTempFile(fileNameBack,".jpg",storageDirectory);

                        currentPhotoPathBackId = imageFileBack.getAbsolutePath();

                        Uri imageUriBack = FileProvider.getUriForFile(IdentificationCardInformationActivity.this, "org.tup.safeplace.fileprovider", imageFileBack);
                        Intent intentBack = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentBack.putExtra(MediaStore.EXTRA_OUTPUT,imageUriBack);
                        startActivityForResult(intentBack, 112);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                }
            }).check();

        });


    }

    private void FrontIdCapture(){

        frontIdCam.setOnClickListener(v->{
            Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    String fileNameFront = "photo";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


                    try {
                        File imageFileFront = File.createTempFile(fileNameFront,".jpg",storageDirectory);

                        currentPhotoPathFrontId = imageFileFront.getAbsolutePath();

                        Uri imageUriFront = FileProvider.getUriForFile(IdentificationCardInformationActivity.this, "org.tup.safeplace.fileprovider", imageFileFront);
                        Intent intentFront = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentFront.putExtra(MediaStore.EXTRA_OUTPUT,imageUriFront);
                        startActivityForResult(intentFront, 111);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                }
            }).check();
        });


    }

    private void BackIdCapture(){
        backIdCam.setOnClickListener(v->{


            Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    String fileNameBack = "photo";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


                    try {
                        File imageFileBack = File.createTempFile(fileNameBack,".jpg",storageDirectory);

                        currentPhotoPathBackId = imageFileBack.getAbsolutePath();

                        Uri imageUriBack = FileProvider.getUriForFile(IdentificationCardInformationActivity.this, "org.tup.safeplace.fileprovider", imageFileBack);
                        Intent intentBack = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentBack.putExtra(MediaStore.EXTRA_OUTPUT,imageUriBack);
                        startActivityForResult(intentBack, 112);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                }
            }).check();


        });

    }

    private boolean validate(){
        String idNumber = edtIdentificationCardNumber.getText().toString();
        String IdType = autoCompleteIdList.getText().toString();

        if(IdType.isEmpty()){
            dropDownLayoutIdType.setErrorEnabled(true);
            dropDownLayoutIdType.setError("Your Id Type is Required");
            return false;
        }


        if(idNumber.isEmpty()){
            txtLayoutIdentificationCardNumber.setErrorEnabled(true);
            txtLayoutIdentificationCardNumber.setError("Your ID Number is Required");
            return false;
        }




        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {

            bitmapfront = BitmapFactory.decodeFile(currentPhotoPathFrontId);
            Matrix matrixfront = new Matrix();
            matrixfront.postRotate(0);
            Bitmap rotatedfront = Bitmap.createBitmap(bitmapfront, 0, 0, bitmapfront.getWidth(), bitmapfront.getHeight(), matrixfront, true);
            frontIdImage.setImageBitmap(rotatedfront);
            ImageStoreFront(bitmapfront);

            uploadFrontId();


        }
        if(requestCode == 112 && resultCode == RESULT_OK){

            bitmapback = BitmapFactory.decodeFile(currentPhotoPathBackId);
            Matrix matrixback = new Matrix();
            matrixback.postRotate(0);
            Bitmap rotatedback = Bitmap.createBitmap(bitmapback, 0, 0, bitmapback.getWidth(), bitmapback.getHeight(), matrixback, true);
            backIdImage.setImageBitmap(rotatedback);
            ImageStoreBack(bitmapback);
            uploadBackId();


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ImageStoreFront(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        encodedimagefront = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void ImageStoreBack(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        encodedimageback = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadFrontId() {

        frontIdCam.setVisibility(View.INVISIBLE);
        frontGuidetxt.setVisibility(View.INVISIBLE);
        relativelayoutFront.setVisibility(View.VISIBLE);
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API.front_id, response -> {

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        }, error -> {

            Toast.makeText(this, "Please Try Again.", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
            dialog.dismiss();


        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_picture_front", encodedimagefront);
                return map;
            }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }

    private void uploadBackId() {
        backIdCam.setVisibility(View.INVISIBLE);
        backGuidetxt.setVisibility(View.INVISIBLE);
        relativelayoutBack.setVisibility(View.VISIBLE);
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API.back_id, response -> {

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        }, error -> {

            Toast.makeText(this, "Please Try Again.", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
            dialog.dismiss();

        }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }


            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_picture_back", encodedimageback);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);


    }


    private void postCardDetails(){

        String id_type = autoCompleteIdList.getText().toString().trim();
        String id_number = edtIdentificationCardNumber.getText().toString().trim();

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
                    startActivity(new Intent(IdentificationCardInformationActivity.this, FaceVerificationActivity.class));
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