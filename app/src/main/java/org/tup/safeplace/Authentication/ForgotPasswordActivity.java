package org.tup.safeplace.Authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout LayoutEmailForgotPassword;
    TextInputEditText txtEmailForgotPassword;
    Button btnResetPassword;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);


        LayoutEmailForgotPassword = findViewById(R.id.txtLayoutEmailForgotPassword);
        txtEmailForgotPassword = findViewById(R.id.txtEmailForgotPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(v->{
            resetPassword();
        });


    }

    public void resetPassword(){


        String email = txtEmailForgotPassword.getText().toString().trim();

        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API.forgot_password, response -> {
            Toast.makeText(this, "Sent to Email! Please check your email to reset your password", Toast.LENGTH_SHORT).show();
            dialog.dismiss();


        }, error -> {

            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
            dialog.dismiss();


        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email",email);
                return map;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


}