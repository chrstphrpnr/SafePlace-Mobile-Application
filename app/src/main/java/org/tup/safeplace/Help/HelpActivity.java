package org.tup.safeplace.Help;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.tup.safeplace.R;

public class HelpActivity extends AppCompatActivity {

    private Button btnGettingStarted,btnServices,btnSecurity;
    private RelativeLayout relativeGettingStarted,relativeServices,relativeSecurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btnGettingStarted = findViewById(R.id.btnGettingStarted);
        relativeGettingStarted = findViewById(R.id.relativeGettingStarted);


        btnGettingStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relativeGettingStarted.getVisibility() == View.VISIBLE) {
                    btnGettingStarted.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0);
                    relativeGettingStarted.setVisibility(View.GONE);

                } else {
                    btnGettingStarted.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_collapse, 0);
                    relativeGettingStarted.setVisibility(View.VISIBLE);
                }
            }
        });

        btnServices = findViewById(R.id.btnServices);
        relativeServices = findViewById(R.id.relativeServices);

        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relativeServices.getVisibility() == View.VISIBLE) {
                    btnServices.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0);
                    relativeServices.setVisibility(View.GONE);

                } else {
                    btnServices.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_collapse, 0);
                    relativeServices.setVisibility(View.VISIBLE);
                }
            }
        });


        btnSecurity = findViewById(R.id.btnSecurity);
        relativeSecurity = findViewById(R.id.relativeSecurity);


        btnSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (relativeSecurity.getVisibility() == View.VISIBLE) {
                    btnSecurity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0);
                    relativeSecurity.setVisibility(View.GONE);

                } else {
                    btnSecurity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_collapse, 0);
                    relativeSecurity.setVisibility(View.VISIBLE);
                }

            }
        });










    }
}