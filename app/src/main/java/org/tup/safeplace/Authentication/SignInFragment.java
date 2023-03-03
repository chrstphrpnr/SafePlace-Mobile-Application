package org.tup.safeplace.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.util.HashMap;
import java.util.Map;


public class SignInFragment extends Fragment {

    private View view;

    private TextInputLayout layoutEmail,layoutPassword;
    private TextInputEditText txtEmail, txtPassword;

    private Button btnSignIn;

    private TextView txtSignup, txtForgotPassword, txtQrCodeLogin;


    private ProgressDialog dialog;


    public SignInFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init();
        return view;
    }

    private void init(){
        //TextLayout
        layoutEmail = view.findViewById(R.id.txtLayoutEmailSignIn);
        layoutPassword = view.findViewById(R.id.txtLayoutPasswordSignIn);

        //TextEditText
        txtEmail = view.findViewById(R.id.txtEmailSignIn);
        txtPassword = view.findViewById(R.id.txtPasswordSignIn);
        txtQrCodeLogin = view.findViewById(R.id.txtQrCodeLogin);

        //TextView for Sign
        btnSignIn = view.findViewById(R.id.btnSignIn);

        //SignUp Redirect
        txtSignup = view.findViewById(R.id.txtSignUp);

        //Forgot Password Redirect
        txtForgotPassword = view.findViewById(R.id.txtForgotPassword);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignup.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignUpFragment()).commit();
        });

        txtForgotPassword.setOnClickListener(v->{
            startActivity(new Intent(((AuthenticationActivity)getContext()), ForgotPasswordActivity.class));
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtEmail.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtPassword.getText().toString().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtQrCodeLogin.setOnClickListener(v->{
            startActivity(new Intent(((AuthenticationActivity)getContext()), QRLoginActivity.class));
        });



        btnSignIn.setOnClickListener(v->{
            if(validate()){
                login();
            }
        });
    }

    private boolean validate(){

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Please enter a valid Email");
            return false;
        }

        if (email.isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }

        if (password.isEmpty()){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Password is Required");
            return false;
        }

        if (password.length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 character");
            return false;
        }
        return true;

    }

    private void login(){
        dialog.setMessage("Loading...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, API.login_user, response -> {
            //we get response if connection success
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if(jsonObject.getBoolean("success")){
                    for (int i = 0; i <jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = userPref.edit();

                        editor.putString("token",jsonObject.getString("token"));

                        editor.putInt("id",object.getInt("id"));

                        editor.putString("fname",object.getString("fname"));
                        editor.putString("mname",object.getString("mname"));
                        editor.putString("lname",object.getString("lname"));
                        editor.putString("gender",object.getString("gender"));
                        editor.putString("birthdate",object.getString("birthdate"));
                        editor.putString("address",object.getString("address"));
                        editor.putString("contact",object.getString("contact"));
                        editor.putString("email",object.getString("email"));

                        editor.putString("status",object.getString("status"));

                        editor.putString("img",object.getString("img"));

                        editor.putBoolean("isLoggedIn",true);

                        editor.apply();
                    }


                    //if Success
                    startActivity(new Intent(((AuthenticationActivity)getContext()), SafePlaceHomeScreenActivity.class));
                    ((AuthenticationActivity) getContext()).finish();
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e){
                Toast.makeText(getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                dialog.dismiss();
            }
            dialog.dismiss();



        },error -> {
            //error if connection not success
            Toast.makeText(getContext(), "Error in Connection", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
            dialog.dismiss();

        }){
            //add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("email",txtEmail.getText().toString().trim());
                map.put("password",txtPassword.getText().toString());
                return map;
            }
        };
        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

}