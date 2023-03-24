package org.tup.safeplace.HospitalMenuList;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.tup.safeplace.R;

public class HospitalListActivity extends AppCompatActivity {

    private ImageView btnSearch, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        btnBack = findViewById(R.id.btnHospitalBack);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameHospitalListContainer, new HospitalListMenuFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}