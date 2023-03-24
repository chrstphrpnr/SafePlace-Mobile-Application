package org.tup.safeplace.BarangaysMenuList;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.tup.safeplace.R;

public class BarangayListActivity extends AppCompatActivity {

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_list);

        btnBack = findViewById(R.id.btnBarangayBack);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameBarangayListContainer, new BarangayListFragment()).commit();
    }
}