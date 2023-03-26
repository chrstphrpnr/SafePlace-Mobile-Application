package org.tup.safeplace.ReportsMenuList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.tup.safeplace.R;

import java.util.List;

public class ReportAdapter extends BaseAdapter {

    Context context;
    List<Report> arrayListReport;

    public ReportAdapter(Context context, List<Report> arrayListReport) {
        this.context = context;
        this.arrayListReport = arrayListReport;
    }

    @Override
    public int getCount() {
        return arrayListReport.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.report_list_menu_item, null);

        TextView tvReportDetails = view.findViewById(R.id.txt_report_incident_type);
        TextView tvReportStatus = view.findViewById(R.id.txt_report_status);
        TextView tvReportBarangay = view.findViewById(R.id.txt_report_barangay);

//        TextView tvReportDateReported = view.findViewById(R.id.txt_report_date_reported);
//        TextView tvReportPoliceStation = view.findViewById(R.id.txt_report_police_station);
//
        ImageView type = view.findViewById(R.id.img_type_report);


        String police_substation = arrayListReport.get(position).getPolice_substation();
        String report_status = arrayListReport.get(position).getReport_status();
        String barangay = arrayListReport.get(position).getBarangay();


        tvReportDetails.setText(arrayListReport.get(position).getIncident_type());
        tvReportStatus.setText(arrayListReport.get(position).getReport_status());



        if (report_status.equals("Pending")){
            tvReportStatus.setTextColor(Color.parseColor("#F6BF0A"));
            type.setImageResource(R.drawable.ic_yellow_pending);
        }

        if (report_status.equals("Responded")){
            tvReportStatus.setTextColor(Color.parseColor("#007300"));
            type.setImageResource(R.drawable.ic_green_responded);

        }

        if (report_status.equals("Transferred")){
            tvReportStatus.setTextColor(Color.parseColor("#022C84"));
            type.setImageResource(R.drawable.ic_blur_transfered);

        }

        if (barangay.equals("barangay_centralbicutan")){
            tvReportBarangay.setText("Barangay Central Bicutan");
        }




//
//        if(police_substation.equals("null")){
//            type.setImageResource(R.drawable.ic_barangay_red);
//
//        }
//        else {
//            type.setImageResource(R.drawable.ic_police_blue);
//        }

//
//        tvReportDateReported.setText(arrayListReport.get(position).getDate_reported());
//

        return view;
    }
}
