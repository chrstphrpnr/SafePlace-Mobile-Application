package org.tup.safeplace.Authentication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.util.HashMap;
import java.util.Map;

public class QRLoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private EditText qrCodetxt;
    private CodeScanner codeScanner;
    private CodeScannerView codeScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrlogin);

        codeScannerView = findViewById(R.id.codeScannerView);
        codeScanner = new CodeScanner(this, codeScannerView);
        qrCodetxt = findViewById(R.id.qrCodeEdtTxt);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CODE);
        }

        init();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.recreate();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        codeScanner.startPreview();
    }

    @Override
    protected void onStop() {
        super.onStop();
        codeScanner.stopPreview();
    }


    private void init() {
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String text = result.getText();
                        qrCodetxt.setText(text);


                        StringRequest request = new StringRequest(Request.Method.POST, API.qr_code_login, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //we get response if connection success
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("user");
                                    if (jsonObject.getBoolean("success")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = userPref.edit();
                                            editor.putString("token", jsonObject.getString("token"));

                                            editor.putInt("id", object.getInt("id"));

                                            editor.putString("fname", object.getString("fname"));
                                            editor.putString("mname", object.getString("mname"));
                                            editor.putString("lname", object.getString("lname"));
                                            editor.putString("gender", object.getString("gender"));
                                            editor.putString("birthdate", object.getString("birthdate"));
                                            editor.putString("address", object.getString("address"));
                                            editor.putString("contact", object.getString("contact"));
                                            editor.putString("email", object.getString("email"));

                                            editor.putString("status", object.getString("status"));

                                            editor.putString("img", object.getString("img"));

                                            editor.putBoolean("isLoggedIn", true);

                                            editor.apply();
                                        }


                                        //if Success
                                        startActivity(new Intent(QRLoginActivity.this, SafePlaceHomeScreenActivity.class));
                                        finish();
                                        Toast.makeText(QRLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    recreate();
                                    e.printStackTrace();
                                    Toast.makeText(QRLoginActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, error -> {
                            recreate();
                            error.printStackTrace();
                            Toast.makeText(QRLoginActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();

                        }) {

                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("qr_code", qrCodetxt.getText().toString().trim());
                                return map;
                            }
                        };
                        //add this request to requestqueue
                        RequestQueue queue = Volley.newRequestQueue(QRLoginActivity.this);
                        queue.add(request);


                    }
                });
            }
        });

    }
}




















