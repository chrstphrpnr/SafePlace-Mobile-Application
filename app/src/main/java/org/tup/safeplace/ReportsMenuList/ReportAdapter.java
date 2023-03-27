package org.tup.safeplace.ReportsMenuList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

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
        TextView tvReportTo = view.findViewById(R.id.txt_report_barangay);

        TextView tvReportDateReported = view.findViewById(R.id.txt_report_date_reported);
//        TextView tvReportPoliceStation = view.findViewById(R.id.txt_report_police_station);
//
        ImageView type = view.findViewById(R.id.img_type_report);


        String police_substation = arrayListReport.get(position).getPolice_substation();
        String report_status = arrayListReport.get(position).getReport_status();
        String barangay = arrayListReport.get(position).getBarangay();
        String police = arrayListReport.get(position).getPolice_substation();



        tvReportDetails.setText(arrayListReport.get(position).getIncident_type());
        tvReportStatus.setText(arrayListReport.get(position).getReport_status());
        tvReportDateReported.setText(arrayListReport.get(position).getDate_reported());


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





        if(police_substation.equals("null")){

            tvReportTo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_barangay_red, 0, 0, 0);
            Drawable[] drawables = tvReportTo.getCompoundDrawables();
            Drawable drawableLeft = drawables[0];

// Create a new drawable with the desired color
            DrawableCompat.setTint(drawableLeft, ContextCompat.getColor(view.getContext(), R.color.red));

            if (barangay.equals("barangay_centralbicutan")){
                tvReportTo.setText("Barangay Central Bicutan");
            }


            if(barangay.equals("barangay_centralsignalvillage")){
                tvReportTo.setText("Barangay Central Signal Village");
            }

            if(barangay.equals("barangay_fortbonifacio")){
                tvReportTo.setText("Barangay Fort Bonifacio");
            }

            if(barangay.equals("barangay_katuparan")){
                tvReportTo.setText("Barangay Katuparan");
            }

            if(barangay.equals("barangay_maharlikavillage")){
                tvReportTo.setText("Barangay Maharlika Village");
            }

            if(barangay.equals("barangay_northdaanghari")){
                tvReportTo.setText("Barangay North Daanghari");
            }

            if(barangay.equals("barangay_northsignalvillage")){
                tvReportTo.setText("Barangay North Signal Village");
            }

            if(barangay.equals("barangay_pinagsama")){
                tvReportTo.setText("Barangay Pinagsama");
            }

            if(barangay.equals("barangay_southdaanghari")){
                tvReportTo.setText("Barangay South Daanghari");
            }

            if(barangay.equals("barangay_southsignalvillage")){
                tvReportTo.setText("Barangay South Signal Village");
            }

            if(barangay.equals("barangay_tanyag")){
                tvReportTo.setText("Barangay Tanyag");
            }

            if(barangay.equals("barangay_upperbicutan")){
                tvReportTo.setText("Barangay Upper Bicutan");
            }

            if(barangay.equals("barangay_westernbicutan")){
                tvReportTo.setText("Barangay Western Bicutan");
            }

            if(barangay.equals("barangay_westernbicutan")){
                tvReportTo.setText("Barangay Western Bicutan");
            }



        }
        else {
            tvReportTo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_police_blue, 0, 0, 0);
            // Get the existing drawableLeft
            Drawable[] drawables = tvReportTo.getCompoundDrawables();
            Drawable drawableLeft = drawables[0];

// Create a new drawable with the desired color
            DrawableCompat.setTint(drawableLeft, ContextCompat.getColor(view.getContext(), R.color.safeplace_btnblue));


            if(police.equals("police_substation1")){
                tvReportTo.setText("Police Substation 1");
            }
            if(police.equals("police_substation2")){
                tvReportTo.setText("Police Substation 2");
            }
            if(police.equals("police_substation3")){
                tvReportTo.setText("Police Substation 3");

            }
            if(police.equals("police_substation6")){
                tvReportTo.setText("Police Substation 6");
            }
            if(police.equals("police_substation7")){
                tvReportTo.setText("Police Substation 7");
            }
            if(police.equals("police_substation8")){
                tvReportTo.setText("Police Substation 8");
            }
        }

//
//        tvReportDateReported.setText(arrayListReport.get(position).getDate_reported());
//

        return view;
    }
}
