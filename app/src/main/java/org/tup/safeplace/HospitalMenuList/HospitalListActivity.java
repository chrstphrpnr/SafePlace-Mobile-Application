package org.tup.safeplace.HospitalMenuList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import org.tup.safeplace.R;

public class HospitalListActivity extends AppCompatActivity {

    private ImageView btnSearch, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameHospitalListContainer,new HospitalListMenuFragment()).commit();
    }

}