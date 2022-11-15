package org.tup.safeplace.Privacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.tup.safeplace.Authentication.SignInFragment;
import org.tup.safeplace.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        getSupportFragmentManager().beginTransaction().replace(R.id.framePrivacyContainer,new TermsConditionFragment()).commit();
    }
}