package org.tup.safeplace.Report;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;
import org.tup.safeplace.ReportsMenuList.Report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ReportActivity extends AppCompatActivity {

    private SharedPreferences userPref;



    TextInputLayout edtLayoutStreetLocationReport, dropDownPoliceReportLayout, dropDownBarangayReportLayout, dropIncidentTypeLayout, edtReportDetailsLayout;

    TextInputEditText txtInputStreetReport, txtReportDetails;

    AutoCompleteTextView autoCompleteBarangaytxt, autoCompletePoliceStationtxt, autoCompleteIncidentTypetxt;

    private Button btnSubmitReportBlotter, btnDatePickerReport, btnReportTimePickerReport;
    private DatePickerDialog datePickerDialog;




    String[] barangay_array = {"Upper Bicutan","Lower Bicutan", "Signal Village"};
    ArrayAdapter<String> barangayAdapterItems;

    String[] police_array = {"Police Sub Station 1","Police Sub Station 2", "Police Sub Station 3"};
    ArrayAdapter<String> policeAdapterItems;

    String[] incident_type_array = {"Physical Injury","Thief", "Robbery"};
    ArrayAdapter<String> incidentAdapterItems;

    TextView txtchkbx;

    CheckBox anonymousCheckBox;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);

        //TextInputLayout
        edtLayoutStreetLocationReport = findViewById(R.id.edtLayoutStreetLocationReport);
        dropDownBarangayReportLayout = findViewById(R.id.dropDownBarangayReportLayout);
        dropDownPoliceReportLayout = findViewById(R.id.dropDownPoliceReportLayout);
        dropIncidentTypeLayout = findViewById(R.id.dropIncidentTypeLayout);
        edtReportDetailsLayout = findViewById(R.id.edtReportDetailsLayout);

        //InputEditText
        txtInputStreetReport = findViewById(R.id.txtInputStreetReport);
        txtReportDetails = findViewById(R.id.txtReportDetails);

        //DropDown
        autoCompleteBarangaytxt = findViewById(R.id.autoCompleteBarangaytxt);
        autoCompletePoliceStationtxt = findViewById(R.id.autoCompletePoliceStationtxt);
        autoCompleteIncidentTypetxt = findViewById(R.id.autoCompleteIncidentTypetxt);

        //Barangay
        barangayAdapterItems = new ArrayAdapter<String>(this,R.layout.barangay_list_item, barangay_array);
        autoCompleteBarangaytxt.setAdapter(barangayAdapterItems);

        //Police Station
        policeAdapterItems = new ArrayAdapter<String>(this,R.layout.police_list_item, police_array);
        autoCompletePoliceStationtxt.setAdapter(policeAdapterItems);

        //Incident Type
        incidentAdapterItems = new ArrayAdapter<String>(this,R.layout.incident_type_list_item, incident_type_array);
        autoCompleteIncidentTypetxt.setAdapter(incidentAdapterItems);

        //DatePicker
        btnDatePickerReport = findViewById(R.id.btnDatePickerReport);
        initDatePicker();
        btnDatePickerReport.setText(getTodaysDate());

        txtchkbx = findViewById(R.id.txtchkbx);

        //TimePicker
        btnReportTimePickerReport = findViewById(R.id.btnReportTimePickerReport);

        btnReportTimePickerReport.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int mins = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(ReportActivity.this, TimePickerDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    c.set(Calendar.MINUTE,minute);
                    c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                    SimpleDateFormat format = new SimpleDateFormat("K:mm:00");
                    String time = format.format(c.getTime());
                    btnReportTimePickerReport.setText(time);


                }
            }, hours, mins, true
            );
            timePickerDialog.show();



        });


        anonymousCheckBox = findViewById(R.id.anonymousCheckBox);


        btnSubmitReportBlotter = findViewById(R.id.btnSubmitReportBlotter);
        btnSubmitReportBlotter.setOnClickListener(v->{
            SubmitReport();
        });

    }

    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year,month,day);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(year,month,day);
                btnDatePickerReport.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }

    private String makeDateString(int year, int month, int day) {
        return year +"-" + month + "-" + day ;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

//    public void onCheckBoxedClicked(View view){
//        Boolean checked = ((CheckBox) view).isChecked();
//
//        if(anonymousCheckBox.isChecked()){
//            checked = true;
//            String complainant_identity= "anonymous";
//        }
//        else {
//            checked = false;
//            String complainant_identity= "not_anonymous";
//
//        }
//
//    }



    private void SubmitReport(){
        String street = txtInputStreetReport.getText().toString().trim();
        String barangay = autoCompleteBarangaytxt.getText().toString().trim();
        String police_substation = autoCompletePoliceStationtxt.getText().toString().trim();
        String incident_type = autoCompleteIncidentTypetxt.getText().toString().trim();
        String date_commited = btnDatePickerReport.getText().toString().trim();
        String time_commited = btnReportTimePickerReport.getText().toString().trim();
        String report_details = txtReportDetails.getText().toString().trim();

        if(anonymousCheckBox.isChecked()){
            txtchkbx.setText("anonymous");
        }
        else {
            txtchkbx.setText("not_anonymous");
        }

        String complainant_identity = txtchkbx.getText().toString().trim();










        StringRequest request = new StringRequest(Request.Method.POST, API.report, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    startActivity(new Intent(ReportActivity.this, SafePlaceHomeScreenActivity.class));
                }catch (Error error){
                    error.printStackTrace();
                }


            }
        }, error -> {
            Toast.makeText(ReportActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String, String> map =new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("street",street);
                map.put("barangay",barangay);
                map.put("police_substation",police_substation);
                map.put("incident_type",incident_type);
                map.put("date_commited",date_commited);
                map.put("time_commited",time_commited);
                map.put("report_details",report_details);
                map.put("complainant_identity",complainant_identity);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



}