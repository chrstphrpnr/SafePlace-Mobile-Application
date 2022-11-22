package org.tup.safeplace.UserAccount;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.tup.safeplace.ChangePasswordOTP;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.HashMap;
import java.util.Map;

public class UserChangePasswordActivity extends AppCompatActivity {

    private TextInputLayout LayoutCurrentPassword,LayoutNewPassword,LayoutConfirmNewPassword;
    private TextInputEditText txtCurrentPassword,txtNewPassword,txtConfirmNewPassword;
    private Button btnSaveChangePassword;
    private SharedPreferences userPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);
        init();
    }

    private void init(){
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        LayoutCurrentPassword = findViewById(R.id.txtLayoutCurrentPassword);
        LayoutNewPassword = findViewById(R.id.txtLayoutNewPassword);
        LayoutConfirmNewPassword = findViewById(R.id.txtLayoutConfirmNewPassword);

        txtCurrentPassword = findViewById(R.id.txtCurrentPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmNewPassword = findViewById(R.id.txtConfirmNewPassword);

        btnSaveChangePassword = findViewById(R.id.btnSaveChangePassword);



        txtCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtCurrentPassword.getText().toString().isEmpty()){
                    LayoutCurrentPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtNewPassword.getText().toString().isEmpty()){
                    LayoutNewPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtConfirmNewPassword.getText().toString().isEmpty()){
                    LayoutConfirmNewPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSaveChangePassword.setOnClickListener(v->{
            if(validate()){
                changePassword();
            }
        });
    }

    private boolean validate(){
        String currentPassword = txtCurrentPassword.getText().toString();
        String newPassword = txtNewPassword.getText().toString();
        String confirmNewPassword = txtConfirmNewPassword.getText().toString();

        if(currentPassword.isEmpty()){
            LayoutCurrentPassword.setErrorEnabled(true);
            LayoutCurrentPassword.setError("Your Current Password is Required");
            return false;
        }

        if(newPassword.isEmpty()){
            LayoutNewPassword.setErrorEnabled(true);
            LayoutNewPassword.setError("Your New Password is Required");
            return false;
        }

        if(confirmNewPassword.isEmpty()){
            LayoutConfirmNewPassword.setErrorEnabled(true);
            LayoutConfirmNewPassword.setError("Your Confirmation Password is Required");
            return false;
        }

        if (currentPassword.equals(newPassword)){
            LayoutCurrentPassword.setErrorEnabled(true);
            LayoutCurrentPassword.setError("New Password should not be equal to the old password.");
            return false;
        }

        if (newPassword.equals(currentPassword)){
            LayoutNewPassword.setErrorEnabled(true);
            LayoutNewPassword.setError("New Password should not be equal to the old password.");
            return false;
        }

        if(!confirmNewPassword.equals(newPassword)){
            LayoutConfirmNewPassword.setErrorEnabled(true);
            LayoutConfirmNewPassword.setError("Inputted Password does not match");
            return false;
        }

        return true;
    }

    private void changePassword() {

        String old_password = txtCurrentPassword.getText().toString().trim();
        String password = txtNewPassword.getText().toString().trim();
        String confirm_password = txtConfirmNewPassword.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, API.change_password, response -> {
            Toast.makeText(this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserChangePasswordActivity.this, UserAccountActivity.class));
        }, error -> {
            Toast.makeText(this, "Current Password Doesn't Match", Toast.LENGTH_SHORT).show();
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
                map.put("old_password",old_password);
                map.put("password",password);
                map.put("confirm_password",confirm_password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void message(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}