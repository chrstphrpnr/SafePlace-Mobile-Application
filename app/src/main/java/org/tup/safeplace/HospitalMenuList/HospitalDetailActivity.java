package org.tup.safeplace.HospitalMenuList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

public class HospitalDetailActivity extends AppCompatActivity {
    private Hospital Hospital;
    private TextView tvDetailHospitalTitle,tvDetailHospitalName, tvDetailHospitalType,tvDetailHospitalDirector,tvDetailHospitalLocation,tvDetailHospitalSchedule,txtDetailHospitalWebsite;
    private ImageView btnHospitalDetailBack,imgDetailsHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        tvDetailHospitalTitle = findViewById(R.id.txtDetailHospitalNameTitle);
        tvDetailHospitalName = findViewById(R.id.txtDetailHospitalName);

        tvDetailHospitalType = findViewById(R.id.txtDetailHospitalType);
        tvDetailHospitalDirector = findViewById(R.id.txtDetailHospitalDirector);
        tvDetailHospitalLocation = findViewById(R.id.txtDetailHospitalLocation);
        tvDetailHospitalSchedule = findViewById(R.id.txtDetailHospitalSchedule);
        txtDetailHospitalWebsite = findViewById(R.id.txtDetailHospitalWebsite);
        imgDetailsHospital = findViewById(R.id.imgDetailsHospital);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Hospital = (Hospital) intent.getSerializableExtra("item");
            tvDetailHospitalTitle.setText(Hospital.getHospital_name());
            tvDetailHospitalName.setText(Hospital.getHospital_name());
            tvDetailHospitalType.setText(Hospital.getHospital_type());
            tvDetailHospitalDirector.setText(Hospital.getHospital_medical_director());
            tvDetailHospitalLocation.setText(Hospital.getHospital_location());
            tvDetailHospitalSchedule.setText(Hospital.getHospital_schedule());
            txtDetailHospitalWebsite.setText(Hospital.getHospital_contact());
            Picasso.get().load(API.URL+Hospital.getImg()).resize(500,0).centerCrop().into(imgDetailsHospital);


        }

        btnHospitalDetailBack = findViewById(R.id.btnHospitalDetailBack);
        btnHospitalDetailBack.setOnClickListener(v->{
            startActivity(new Intent(HospitalDetailActivity.this, HospitalListActivity.class));
            finish();
        });







    }
}