package org.tup.safeplace.UserAccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.tup.safeplace.R;

import java.util.concurrent.TimeUnit;

public class ChangePasswordOTP extends AppCompatActivity {

    private EditText PhoneNumber, oneTimePassword;
    private Button btnOTP;

    private boolean otpSent = false;
    private final String CountryCode = "+63";
    private String id = "";

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_otp);

        FirebaseApp.initializeApp(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);


        PhoneNumber = findViewById(R.id.edtPhoneNumber);
        oneTimePassword = findViewById(R.id.edtOTP);

        btnOTP = findViewById(R.id.btnOTP);

        btnOTP.setOnClickListener(v -> {

            dialog.setMessage("Loading...");
            dialog.show();

            if (otpSent) {
                final String getOTP = oneTimePassword.getText().toString();

                if (id.isEmpty()) {
                    Toast.makeText(ChangePasswordOTP.this, "Unable to verify OTP", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, getOTP);
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser userDetails = task.getResult().getUser();
                                Toast.makeText(ChangePasswordOTP.this, "Verified", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                startActivity(new Intent(ChangePasswordOTP.this, UserChangePasswordActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ChangePasswordOTP.this, "Something Went Wrong. Please Try Again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        }
                    });

                }

            } else {
                final String getMobileNumber = PhoneNumber.getText().toString();

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(CountryCode + "" + getMobileNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(ChangePasswordOTP.this, "OTP sent Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(ChangePasswordOTP.this, "Something went Wrong. Please Try Again", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                dialog.dismiss();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                dialog.dismiss();
                                oneTimePassword.setVisibility(View.VISIBLE);
                                btnOTP.setText("Verified OTP");
                                id = s;
                                otpSent = true;
                            }
                        }).build();

                PhoneAuthProvider.verifyPhoneNumber(options);

            }

        });

    }
}