package org.tup.safeplace.Verification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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

import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class IdentificationCardPictureActivity extends AppCompatActivity {

    private SharedPreferences userPref;
    private ImageView imgFrontId, imgBackId;
    private Button btnSubmit;
    Bitmap bitmap;
    String encodedimage;

    private CardView cardview1, cardview2;

    private String currentPhotoPath;


    private static final String api_front = "http://192.168.15.27:8080/api/verification_frontId";
    private static final String api_back = "http://192.168.15.27:8080/api/verification_backId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification_card_picture);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);


        imgFrontId = findViewById(R.id.imgFrontId);
        cardview1 = findViewById(R.id.cardview1);

        imgBackId = findViewById(R.id.imgBackId);
        cardview2 = findViewById(R.id.cardview2);


        btnSubmit = findViewById(R.id.btnSubmit);


        cardview1.setOnClickListener(v -> {

            Dexter.withContext(getApplicationContext())
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            String fileName = "photo";
                            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


                            try {
                                File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);

                                currentPhotoPath = imageFile.getAbsolutePath();

                                Uri imageUri = FileProvider.getUriForFile(IdentificationCardPictureActivity.this, "org.tup.safeplace.fileprovider", imageFile);
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
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();

        });


        btnSubmit.setOnClickListener(v -> {
            uploadFrontId();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imgFrontId.setImageBitmap(bitmap);
            ImageStore(bitmap);
        }



        super.onActivityResult(requestCode, resultCode, data);
    }


    private void ImageStore(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        encodedimage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private void uploadFrontId() {

        StringRequest request = new StringRequest(Request.Method.POST, api_front, response -> {

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        }, error -> {

            Toast.makeText(this, "Error in Connection", Toast.LENGTH_SHORT).show();
            error.printStackTrace();

        }) {

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
                map.put("id_picture_front", encodedimage);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

}