package org.tup.safeplace.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import org.tup.safeplace.R;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {

    private View view;

    private TextInputLayout layoutEmail, layoutFirstName, layoutMiddleName, layoutLastName, layoutPassword, layoutConfirm;
    private TextInputEditText txtEmail, txtFirstName, txtMiddleName, txtLastName, txtPassword, txtConfirm;
    private Button btnSignUp;
    private TextView txtSignIn;
    private ProgressDialog dialog;


    public SignUpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init();
        return view;
    }

    private void init() {
        layoutEmail = view.findViewById(R.id.txtLayoutEmailSignUp);
        layoutPassword = view.findViewById(R.id.txtLayoutPasswordSignUp);
        layoutConfirm = view.findViewById(R.id.txtLayoutConfirmPasswordSignUp);
        layoutFirstName = view.findViewById(R.id.txtLayoutFirstNameSignUp);
        layoutMiddleName = view.findViewById(R.id.txtLayoutMiddleNameSignUp);
        layoutLastName = view.findViewById(R.id.txtLayoutLastNameSignUp);

        txtEmail = view.findViewById(R.id.txtEmailSignUp);
        txtPassword = view.findViewById(R.id.txtPasswordSignUp);
        txtConfirm = view.findViewById(R.id.txtConfirmPasswordSignUp);
        txtFirstName = view.findViewById(R.id.txtFirstNameSignUp);
        txtMiddleName = view.findViewById(R.id.txtMiddleNameNameSignUp);
        txtLastName = view.findViewById(R.id.txtLastNameSignUp);


        btnSignUp = view.findViewById(R.id.btnSignUp);

        txtSignIn = view.findViewById(R.id.txtSignIn);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignIn.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new SignInFragment()).commit();
        });

        btnSignUp.setOnClickListener(v -> {
            if (validate()) {
                register();
            }
        });


        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtEmail.getText().toString().isEmpty()) {
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
                if (txtPassword.getText().toString().length() > 7) {
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtConfirm.getText().toString().equals(txtPassword.getText().toString())) {
                    layoutConfirm.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtFirstName.getText().toString().isEmpty()) {
                    layoutFirstName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtMiddleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtMiddleName.getText().toString().isEmpty()) {
                    layoutMiddleName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtLastName.getText().toString().isEmpty()) {
                    layoutLastName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean validate() {

        String email = txtEmail.getText().toString();
        String firstName = txtFirstName.getText().toString();
        String middleName = txtMiddleName.getText().toString();
        String lastName = txtLastName.getText().toString();


        String password = txtPassword.getText().toString();
        String confirmation = txtConfirm.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Please enter a valid Email");
            return false;
        }

        if (email.isEmpty()) {
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }

        if (firstName.isEmpty()) {
            layoutFirstName.setErrorEnabled(true);
            layoutFirstName.setError("First Name is Required");
            return false;
        }

        if (middleName.isEmpty()) {
            layoutMiddleName.setErrorEnabled(true);
            layoutMiddleName.setError("Middle Name is Required");
            return false;
        }

        if (lastName.isEmpty()) {
            layoutLastName.setErrorEnabled(true);
            layoutLastName.setError("Last Name is Required");
            return false;
        }

        if (password.isEmpty()) {
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Password is Required");
            return false;
        }

        if (password.length() < 8) {
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 character");
            return false;
        }

        if (!confirmation.equals(password)) {
            layoutConfirm.setErrorEnabled(true);
            layoutConfirm.setError("Password Does Not Match");
            return false;
        }

        return true;

    }


    private void register() {
        dialog.setMessage("Registering On Process");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API.register_user, response -> {
            //we get response if connection success
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);                    //Make Shared preference user
                        SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = userPref.edit();

                        editor.putString("token", jsonObject.getString("token"));

                        editor.putInt("id", object.getInt("id"));

                        editor.putString("fname", object.getString("fname"));
                        editor.putString("mname", object.getString("mname"));
                        editor.putString("lname", object.getString("lname"));
                        editor.putString("email", object.getString("email"));

                        editor.putString("status", object.getString("status"));


                        editor.apply();
                    }

                    //if Success
                    startActivity(new Intent(getContext(), UserInfoRegisterActivity.class));
                    ((AuthenticationActivity) getContext()).finish();
                    Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                Toast.makeText(getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                dialog.dismiss();
            }
            dialog.dismiss();

        }, error -> {
            //error if connection not success
            Toast.makeText(getContext(), "Error in Connection", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
            dialog.dismiss();
        }) {
            //add parameters


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("fname", txtFirstName.getText().toString().trim());
                map.put("mname", txtMiddleName.getText().toString().trim());
                map.put("lname", txtLastName.getText().toString().trim());
                map.put("email", txtEmail.getText().toString().trim());
                map.put("password", txtPassword.getText().toString());
                return map;
            }
        };
        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }
}