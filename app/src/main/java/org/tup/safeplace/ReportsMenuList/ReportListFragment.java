package org.tup.safeplace.ReportsMenuList;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.BarangaysMenuList.Barangay;
import org.tup.safeplace.BarangaysMenuList.BarangayDetailsActivity;
import org.tup.safeplace.CallHistory.CallHistory;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ReportListFragment extends Fragment {
    private View view;
    public static ArrayList<Report> reportArrayList = new ArrayList<>();
    ListView listViewReport;
    ReportAdapter adapter;
    Report reports;
    private SharedPreferences userPref;

    private ImageView btnMenuListBarangay;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_report_list, container, false);
        userPref = getContext().getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);

        btnMenuListBarangay = view.findViewById(R.id.btnMenuListBarangay);


        btnMenuListBarangay.setOnClickListener(v->{

            requireActivity().onBackPressed();
            requireActivity().finish();

        });

        listViewReport = view.findViewById(R.id.myListViewReports);
        adapter = new ReportAdapter(getContext(), reportArrayList);
        listViewReport.setAdapter(adapter);


        listViewReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getActivity().getApplicationContext(), ReportDetailsActivity.class).putExtra("position", position));
            }
        });


        showReportList();

        return view;
    }


    private void showReportList(){

        StringRequest request = new StringRequest(Request.Method.GET, API.view_reports, response -> {
            reportArrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("reports");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        String complainant_identity = object.getString("complainant_identity");
                        String report_details = object.getString("report_details");
                        String report_status = object.getString("report_status");
                        String date_reported = object.getString("date_reported");
                        String time_reported = object.getString("time_reported");
                        String year_reported = object.getString("year_reported");
                        String date_commited = object.getString("date_commited");
                        String time_commited = object.getString("time_commited");
                        String incident_type = object.getString("incident_type");
                        String barangay = object.getString("barangay");
                        String police_substation = object.getString("police_substation");
                        String report_images_1 = object.getString("report_images_1");
                        String report_images_2 = object.getString("report_images_2");
                        String report_images_3 = object.getString("report_images_3");
                        String street = object.getString("street");



                        reports = new Report(complainant_identity, report_details, report_status, date_reported,time_reported,
                                year_reported,date_commited,time_commited,incident_type,barangay,police_substation,report_images_1,report_images_2,report_images_3,street);

                        reportArrayList.add(reports);
                        adapter.notifyDataSetChanged();

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }

}