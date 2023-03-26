package org.tup.safeplace.ReportsMenuList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.tup.safeplace.BarangaysMenuList.Barangay;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

public class ReportDetailsActivity extends AppCompatActivity {

    TextView txtImgTitleReport,txtBarangayDetails,txtStreetDetails,txtPoliceSubstationDetails,txtDateDetails,txtTimeDetails,txtDateCommitDetails,txtTimeCommitDetails,txtReportContent,txtDetailedTitleReport;
    int position;
    ImageView btnReportsDetailsBack;
    ImageView img1, img2, img3;

    RelativeLayout relativedetailsreport1,relativedetailsreport4,relativedetailsreport11;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        btnReportsDetailsBack = findViewById(R.id.btnReportDetailsBack);
        txtImgTitleReport = findViewById(R.id.txtImgTitleReport);
        txtBarangayDetails = findViewById(R.id.txtBarangayDetails);
        relativedetailsreport1 = findViewById(R.id.relativedetailsreport1);
        txtPoliceSubstationDetails = findViewById(R.id.txtPoliceSubstationDetails);
        relativedetailsreport4 = findViewById(R.id.relativedetailsreport4);
        txtDateDetails = findViewById(R.id.txtDateDetails);
        txtTimeDetails = findViewById(R.id.txtTimeDetails);
        txtDateCommitDetails = findViewById(R.id.txtDateCommitDetails);
        txtTimeCommitDetails = findViewById(R.id.txtTimeCommitDetails);
        txtReportContent = findViewById(R.id.txtReportContent);
        relativedetailsreport11 = findViewById(R.id.relativedetailsreport11);
        txtDetailedTitleReport = findViewById(R.id.txtDetailedTitleReport);



        img1 = findViewById(R.id.imgReport1);
        img2 = findViewById(R.id.imgReport2);
        img3 = findViewById(R.id.imgReport3);

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) img1.getLayoutParams();



        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        String pic_1 = ReportListFragment.reportArrayList.get(position).getReport_images_1();
        String pic_2 = ReportListFragment.reportArrayList.get(position).getReport_images_2();
        String pic_3 = ReportListFragment.reportArrayList.get(position).getReport_images_3();


        if(pic_1.equals("null")){
            relativedetailsreport1.setVisibility(View.GONE);
            txtImgTitleReport.setVisibility(View.GONE);
        }
        if(pic_2.equals("null")){
            layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            img1.setLayoutParams(layoutParams1);
            img1.requestLayout();
        }
        if(pic_3.equals("null")){
            img3.setVisibility(View.GONE);
        }


//        Picasso.get().load(API.URL + Barangay.getImg()).resize(500, 0).centerCrop().into(imgDetailsBarangay);
        Picasso.get().load(API.URL + ReportListFragment.reportArrayList.get(position).getReport_images_1()).resize(500, 0).centerCrop().into(img1);
        Picasso.get().load(API.URL + ReportListFragment.reportArrayList.get(position).getReport_images_2()).resize(500, 0).centerCrop().into(img2);
        Picasso.get().load(API.URL + ReportListFragment.reportArrayList.get(position).getReport_images_3()).resize(500, 0).centerCrop().into(img3);

        String Address = ReportListFragment.reportArrayList.get(position).getStreet();
        String Barangay = ReportListFragment.reportArrayList.get(position).getBarangay();


        if(Address.equals("null")){
            txtBarangayDetails.setText(Barangay);
        }
        else {

            if(Address.equals("barangay_centralbicutan")){
                txtBarangayDetails.setText(Address+", "+"Barangay Central Signal Village");
            }

            if(Address.equals("barangay_centralsignalvillage")){
                txtBarangayDetails.setText(Address+", "+"Barangay Central Signal Village");
            }

            if(Address.equals("barangay_fortbonifacio")){
                txtBarangayDetails.setText(Address+", "+"Barangay Fort Bonifacio");
            }

            if(Address.equals("barangay_katuparan")){
                txtBarangayDetails.setText(Address+", "+"Barangay Katuparan");
            }

            if(Address.equals("barangay_maharlikavillage")){
                txtBarangayDetails.setText(Address+", "+"Barangay Maharlika Village");
            }

            if(Address.equals("barangay_northdaanghari")){
                txtBarangayDetails.setText(Address+", "+"Barangay North Daanghari");
            }

            if(Address.equals("barangay_northsignalvillage")){
                txtBarangayDetails.setText(Address+", "+"Barangay North Signal Village");
            }

            if(Address.equals("barangay_pinagsama")){
                txtBarangayDetails.setText(Address+", "+"Barangay Pinagsama");
            }

            if(Address.equals("barangay_southdaanghari")){
                txtBarangayDetails.setText(Address+", "+"Barangay South Daanghari");
            }

            if(Address.equals("barangay_southsignalvillage")){
                txtBarangayDetails.setText(Address+", "+"Barangay South Signal Village");
            }

            if(Address.equals("barangay_tanyag")){
                txtBarangayDetails.setText(Address+", "+"Barangay Tanyag");
            }

            if(Address.equals("barangay_upperbicutan")){
                txtBarangayDetails.setText(Address+", "+"Barangay Upper Bicutan");
            }

            if(Address.equals("barangay_westernbicutan")){
                txtBarangayDetails.setText(Address+", "+"Barangay Western Bicutan");
            }

        }


        String police_substation = ReportListFragment.reportArrayList.get(position).getPolice_substation();

        if(police_substation.equals("null")){
            relativedetailsreport4.setVisibility(View.GONE);
        }
        else{
            if(police_substation.equals("police_substation1")){
                txtPoliceSubstationDetails.setText("Police Substation 1");
            }
            if(police_substation.equals("police_substation2")){
                txtPoliceSubstationDetails.setText("Police Substation 2");
            }
            if(police_substation.equals("police_substation3")){
                txtPoliceSubstationDetails.setText("Police Substation 3");
            }
            if(police_substation.equals("police_substation6")){
                txtPoliceSubstationDetails.setText("Police Substation 6");
            }
            if(police_substation.equals("police_substation7")){
                txtPoliceSubstationDetails.setText("Police Substation 7");
            }
            if(police_substation.equals("police_substation8")){
                txtPoliceSubstationDetails.setText("Police Substation 8");
            }
        }


        txtDateDetails.setText(ReportListFragment.reportArrayList.get(position).getDate_reported());
        txtTimeDetails.setText(ReportListFragment.reportArrayList.get(position).getTime_reported());

        txtDateCommitDetails.setText(ReportListFragment.reportArrayList.get(position).getDate_commited());
        txtTimeCommitDetails.setText(ReportListFragment.reportArrayList.get(position).getTime_commited());

        String report_content = ReportListFragment.reportArrayList.get(position).getReport_details();

        if(report_content.equals("null")){
            relativedetailsreport11.setVisibility(View.GONE);
            txtDetailedTitleReport.setVisibility(View.GONE);
        }
        else{
            txtReportContent.setText(ReportListFragment.reportArrayList.get(position).getReport_details());
        }

        btnReportsDetailsBack.setOnClickListener(v->{
            onBackPressed();
            finish();
        });


    }
}