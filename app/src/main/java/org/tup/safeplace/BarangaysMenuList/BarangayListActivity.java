package org.tup.safeplace.BarangaysMenuList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

public class BarangayListActivity extends AppCompatActivity {

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_list);

        btnBack = findViewById(R.id.btnBarangayBack);

        btnBack.setOnClickListener(v->{
            startActivity(new Intent(BarangayListActivity.this, SafePlaceHomeScreenActivity.class));
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameBarangayListContainer,new BarangayListFragment()).commit();
    }
}