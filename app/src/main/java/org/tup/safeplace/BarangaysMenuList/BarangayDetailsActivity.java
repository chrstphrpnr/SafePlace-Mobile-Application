package org.tup.safeplace.BarangaysMenuList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.tup.safeplace.HospitalMenuList.HospitalListMenuFragment;
import org.tup.safeplace.R;

public class BarangayDetailsActivity extends AppCompatActivity {

    TextView tvDetailBarangayTitle,tvDetailBarangayName,tvDetailBarangayCaptain,tvDetailBarangayLocation,tvDetailBarangaySchedule,tvDetailBarangayContacts;
    Barangay Barangay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barangay_details);

        tvDetailBarangayTitle = findViewById(R.id.txtDetailBarangayNameTitle);
        tvDetailBarangayName = findViewById(R.id.txtDetailBarangayName);
        tvDetailBarangayCaptain = findViewById(R.id.txtDetailBarangayCaptain);
        tvDetailBarangayLocation = findViewById(R.id.txtDetailBarangayLocation);
        tvDetailBarangaySchedule = findViewById(R.id.txtDetailBarangaySchedule);
        tvDetailBarangayContacts = findViewById(R.id.txtDetailBarangayContact);

        Intent intent = getIntent();

        if (intent.getExtras()!=null){
            Barangay = (Barangay) intent.getSerializableExtra("item");

            tvDetailBarangayTitle.setText(Barangay.getBarangay_name());
            tvDetailBarangayName.setText(Barangay.getBarangay_name());
            tvDetailBarangayCaptain.setText(Barangay.getBarangay_captain());
            tvDetailBarangayLocation.setText(Barangay.getBarangay_location());
            tvDetailBarangaySchedule.setText(Barangay.getBarangay_schedule());
            tvDetailBarangayContacts.setText(Barangay.getBarangay_contact());
        }



    }
}