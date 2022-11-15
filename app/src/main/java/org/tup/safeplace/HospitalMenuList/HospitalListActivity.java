package org.tup.safeplace.HospitalMenuList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

public class HospitalListActivity extends AppCompatActivity {

    private ImageView btnSearch, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        btnBack = findViewById(R.id.btnHospitalBack);

        btnBack.setOnClickListener(v->{
            startActivity(new Intent(HospitalListActivity.this, SafePlaceHomeScreenActivity.class));
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameHospitalListContainer,new HospitalListMenuFragment()).commit();
    }


}