package org.tup.safeplace.Verification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.tup.safeplace.R;

public class VerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameVerificationContainer, new GuideOneFragment()).commit();
    }
}