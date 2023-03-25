package org.tup.safeplace.Authentication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoRegisterActivity extends AppCompatActivity {

    ActivityResultLauncher<String> mTakePhoto;
    String encodedImage;
    String[] gender = {"Male", "Female"};
    AutoCompleteTextView genderDropdown;
    ArrayAdapter<String> adapterItems;
    private TextInputLayout layoutAddress, layoutContact;
    private EditText txtAddress, txtContact;
    private TextView txtSelectPhoto;
    private Button btnContinue, btnDatePicker;
    private CircleImageView circleImageView;
    private Bitmap bitmap = null;
    private SharedPreferences userPref;
    private ProgressDialog dialog;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_register);
        init();
        initDatePicker();
    }

    private void init() {
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        layoutAddress = findViewById(R.id.txtLayoutAddressUserInfo);
//        layoutBirthDate = findViewById(R.id.txtLayoutBirthDateUserInfo);
        layoutContact = findViewById(R.id.txtLayoutContactUserInfo);

        txtAddress = findViewById(R.id.txtAddressUserInfo);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        txtContact = findViewById(R.id.txtContactUserInfo);
        txtSelectPhoto = findViewById(R.id.txtSelectPhoto);

        btnContinue = findViewById(R.id.btnContinueUserInfo);

        genderDropdown = findViewById(R.id.dropDownGender);
        adapterItems = new ArrayAdapter<String>(this, R.layout.gender_list_item, gender);
        genderDropdown.setAdapter(adapterItems);

        circleImageView = findViewById(R.id.imgProfileUserInfo);

        txtSelectPhoto.setOnClickListener(v -> {
            Dexter.withContext(UserInfoRegisterActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();
        });

        btnDatePicker.setText(getTodaysDate());

        btnContinue.setOnClickListener(v -> {
            if (validate()) {
                saveUserInfo();
            }
        });

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year, month, day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(year, month, day);
                btnDatePicker.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int year, int month, int day) {
        return year + "-" + month + "-" + day;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Uri filePath = data.getData();

            try {
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

    private boolean validate() {

        if (txtAddress.getText().toString().isEmpty()) {
            layoutAddress.setErrorEnabled(true);
            layoutAddress.setError("Address is Required");
            return false;
        }


        if (txtContact.getText().toString().isEmpty()) {
            layoutContact.setErrorEnabled(true);
            layoutContact.setError("Contact is Required");
            return false;
        }


        return true;
    }

    private void saveUserInfo() {
        String address = txtAddress.getText().toString().trim();
        String birthdate = btnDatePicker.getText().toString().trim();
        String gender = genderDropdown.getText().toString().trim();
        String contact = txtContact.getText().toString().trim();

        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API.save_user_info, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        SharedPreferences.Editor editor = userPref.edit();
                        editor.putString("img", object.getString("img"));

                        editor.putString("address", object.getString("address"));
                        editor.putString("birthdate", object.getString("birthdate"));
                        editor.putString("gender", object.getString("gender"));
                        editor.putString("contact", object.getString("contact"));


                        editor.putBoolean("isLoggedIn", true);

                        editor.apply();
                    }


                    startActivity(new Intent(UserInfoRegisterActivity.this, SafePlaceHomeScreenActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                dialog.dismiss();

            }

            dialog.dismiss();


        }, error -> {
            Toast.makeText(this, "Error in Connection", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
            dialog.dismiss();
        }) {
            //Add Token to headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }

            //add params
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("address", address);
                map.put("birthdate", birthdate);
                map.put("gender", gender);
                map.put("contact", contact);

                if (encodedImage != null) {
                    map.put("img", encodedImage);
                }

                return map;
            }
        };

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void ImageStore(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


}