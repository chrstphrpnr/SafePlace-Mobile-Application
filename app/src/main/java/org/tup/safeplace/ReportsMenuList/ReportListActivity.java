package org.tup.safeplace.ReportsMenuList;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.tup.safeplace.BarangaysMenuList.BarangayListFragment;
import org.tup.safeplace.R;

public class ReportListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);



        getSupportFragmentManager().beginTransaction().replace(R.id.frameReportListContainer, new ReportListFragment()).commit();


    }
}