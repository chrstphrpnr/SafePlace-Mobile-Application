package org.tup.safeplace.Privacy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.tup.safeplace.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        getSupportFragmentManager().beginTransaction().replace(R.id.framePrivacyContainer, new TermsConditionFragment()).commit();
    }
}