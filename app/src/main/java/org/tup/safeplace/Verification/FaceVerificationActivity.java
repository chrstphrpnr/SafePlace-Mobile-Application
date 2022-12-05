package org.tup.safeplace.Verification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FaceVerificationActivity extends AppCompatActivity {

    private SharedPreferences userPref;
    private ImageView imgFaceImage;
    private Button btnFaceCaptureDone,btnCaptureFace;
    Bitmap bitmap;
    String encodedimage;

    private String currentPhotoPath;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_verification);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);

        imgFaceImage = findViewById(R.id.imgFaceImage);
        btnCaptureFace = findViewById(R.id.btnCaptureFace);
        btnFaceCaptureDone = findViewById(R.id.btnFaceCaptureDone);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);


        btnCaptureFace.setOnClickListener(v->{
            Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    String fileName = "photo";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


                    try {
                        File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);

                        currentPhotoPath = imageFile.getAbsolutePath();

                        Uri imageUri = FileProvider.getUriForFile(FaceVerificationActivity.this, "org.tup.safeplace.fileprovider", imageFile);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent, 111);

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

        btnFaceCaptureDone.setOnClickListener(view -> {
            startActivity(new Intent(FaceVerificationActivity.this, SafePlaceHomeScreenActivity.class));
            finish();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imgFaceImage.setImageBitmap(bitmap);
            ImageStore(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
        uploadFaceImage();
    }


    private void ImageStore(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        encodedimage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private void uploadFaceImage() {
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API.face_image, response -> {

            SharedPreferences.Editor editor = userPref.edit();
            editor.putString("status","Pending");
            editor.apply();

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
                return map;            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("face_img", encodedimage);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}



















