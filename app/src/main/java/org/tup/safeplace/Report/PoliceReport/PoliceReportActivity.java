package org.tup.safeplace.Report.PoliceReport;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;
import org.tup.safeplace.Report.BarangayReport.BarangayReportActivity;
import org.tup.safeplace.ReportsMenuList.ReportListActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class PoliceReportActivity extends AppCompatActivity {

    RelativeLayout relativeLayoutImage2, relativeLayoutImage3;
    TextInputLayout edtLayoutStreetLocationReport, dropDownPoliceReportLayout, dropDownBarangayReportLayout, dropIncidentTypeLayout, edtReportDetailsLayout,edtLayoutIncidentReportPolice;
    TextInputEditText txtInputStreetReport, txtReportDetails,txtInputIncidentReportPolice;
    AutoCompleteTextView autoCompleteBarangaytxt, autoCompletePoliceStationtxt, autoCompleteIncidentTypetxt;
    String[] barangay_array =
            {
                    "Barangay Central Bicutan",
                    "Barangay Central Signal Village",
                    "Barangay Fort Bonifacio",
                    "Barangay Katuparan",
                    "Barangay Maharlika Village",
                    "Barangay North Daanghari",
                    "Barangay North Signal Village",
                    "Barangay Pinagsama",
                    "Barangay South Daanghari",
                    "Barangay South Signal Village",
                    "Barangay Tanyag",
                    "Barangay Upper Bicutan",
                    "Barangay Western Bicutan"

            };
    ArrayAdapter<String> barangayAdapterItems;
    String[] barangayDB =
            {
                    "barangay_centralbicutan",
                    "barangay_centralsignalvillage",
                    "barangay_fortbonifacio",
                    "barangay_katuparan",
                    "barangay_maharlikavillage",
                    "barangay_northdaanghari",
                    "barangay_northsignalvillage",
                    "barangay_pinagsama",
                    "barangay_southdaanghari",
                    "barangay_southsignalvillage",
                    "barangay_tanyag",
                    "barangay_upperbicutan",
                    "barangay_westernbicutan"
            };
    String[] police_array =
            {

                    "Police Substation 1",
                    "Police Substation 2",
                    "Police Substation 3",
                    "Police Substation 6",
                    "Police Substation 7",
                    "Police Substation 8",


            };
    ArrayAdapter<String> policeAdapterItems;
    String[] policeDB =
            {

                    "police_substation1",
                    "police_substation2",
                    "police_substation3",
                    "police_substation6",
                    "police_substation7",
                    "police_substation8",

            };
    String[] incident_type_array = {"Murder", "Homicide", "Robbery","Theft", "Physical Injury", "Rape", "Carnapping Motor Vehicle", "Carnapping Motorcycle", "Others"};
    ArrayAdapter<String> incidentAdapterItems;
    TextView txtchkbx, txtBarangay, txtPoliceStation, txtNearestBarangay, txtNearestPoliceStation,txtOtherIncident;
    CheckBox anonymousCheckBox;
    String image1, image2, image3;
    ImageView evidence_1, evidence_2, evidence_3, btnReportBackPolice;
    private SharedPreferences userPref;
    private Button btnSubmitReportBlotter, btnDatePickerReport, btnReportTimePickerReport, report_image_1, report_image_2, report_image_3;
    private DatePickerDialog datePickerDialog;
    private Bitmap bitmap = null;
    private EditText edtOtherIncident;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_police);

        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        //ImageView
        evidence_1 = findViewById(R.id.police_evidence_1);
        evidence_2 = findViewById(R.id.police_evidence_2);
        evidence_3 = findViewById(R.id.police_evidence_3);
        btnReportBackPolice = findViewById(R.id.btnReportBackPolice);

        btnReportBackPolice.setOnClickListener(v->{
            onBackPressed();
        });

        //RelativeLayout
        relativeLayoutImage2 = findViewById(R.id.police_relativeLayoutImage2);
        relativeLayoutImage3 = findViewById(R.id.police_relativeLayoutImage3);

        //Upload Image Button
        report_image_1 = findViewById(R.id.police_report_image_1);
        report_image_2 = findViewById(R.id.police_report_image_2);
        report_image_3 = findViewById(R.id.police_report_image_3);


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
        barangayAdapterItems = new ArrayAdapter<String>(this, R.layout.barangay_list_item, barangay_array);
        autoCompleteBarangaytxt.setAdapter(barangayAdapterItems);

        txtBarangay = findViewById(R.id.txtBarangay);
        txtNearestPoliceStation = findViewById(R.id.txtNearestPoliceStation);
        autoCompleteBarangaytxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int pos = -1;
                for (int i = 0; i < barangay_array.length; i++) {
                    if (autoCompleteBarangaytxt.getText().toString().equals(barangay_array[position]))
                        pos = position;
                    break;
                }
                String id = barangayDB[pos];
                txtBarangay.setText(id);


                if (txtBarangay.getText().toString().equals("barangay_centralbicutan")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 7");
                }

                if (txtBarangay.getText().toString().equals("barangay_centralsignalvillage")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 6");
                }

                if (txtBarangay.getText().toString().equals("barangay_fortbonifacio")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 1");
                }

                if (txtBarangay.getText().toString().equals("barangay_katuparan")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 6");
                }

                if (txtBarangay.getText().toString().equals("barangay_maharlikavillage")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 7");
                }

                if (txtBarangay.getText().toString().equals("barangay_northdaanghari")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 8");
                }


                if (txtBarangay.getText().toString().equals("barangay_northsignalvillage")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 6");
                }

                if (txtBarangay.getText().toString().equals("barangay_pinagsama")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 3");
                }

                if (txtBarangay.getText().toString().equals("barangay_southdaanghari")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 8");
                }

                if (txtBarangay.getText().toString().equals("barangay_southsignalvillage")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 6");
                }

                if (txtBarangay.getText().toString().equals("barangay_tanyag")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 8");
                }

                if (txtBarangay.getText().toString().equals("barangay_upperbicutan")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 7");
                }

                if (txtBarangay.getText().toString().equals("barangay_westernbicutan")) {
                    txtNearestPoliceStation.setText("Nearest Police Substation: Police Substation 2");
                }


            }
        });


        //Police Station
        policeAdapterItems = new ArrayAdapter<String>(this, R.layout.police_list_item, police_array);
        autoCompletePoliceStationtxt.setAdapter(policeAdapterItems);

        txtPoliceStation = findViewById(R.id.txtPoliceStation);
        txtNearestBarangay = findViewById(R.id.txtNearestBarangay);
        autoCompletePoliceStationtxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int pos = -1;
                for (int i = 0; i < police_array.length; i++) {
                    if (autoCompletePoliceStationtxt.getText().toString().equals(police_array[position]))
                        pos = position;
                    break;
                }
                String id = policeDB[pos];
                txtPoliceStation.setText(id);

                if (txtPoliceStation.getText().toString().equals("police_substation1")) {
                    txtNearestBarangay.setText("Nearest Barangay: Fort Bonifacio");
                }

                if (txtPoliceStation.getText().toString().equals("police_substation2")) {
                    txtNearestBarangay.setText("Nearest Barangay: Western Bicutan");
                }

                if (txtPoliceStation.getText().toString().equals("police_substation3")) {
                    txtNearestBarangay.setText("Nearest Barangay: Pinagsama");
                }

                if (txtPoliceStation.getText().toString().equals("police_substation6")) {
                    txtNearestBarangay.setText("Nearest Barangay: Katuparan, Central Signal Village, North Signal Village, South Signal Village");
                }

                if (txtPoliceStation.getText().toString().equals("police_substation7")) {
                    txtNearestBarangay.setText("Nearest Barangay: Central Bicutan, Maharlika Village, Upper Bicutan");
                }

                if (txtPoliceStation.getText().toString().equals("police_substation8")) {
                    txtNearestBarangay.setText("Nearest Barangay: North Daanghari, South Daanghari, Tanyag");
                }
            }
        });

        //Incident Type
        txtOtherIncident = findViewById(R.id.txtOtherIncident);

        incidentAdapterItems = new ArrayAdapter<String>(this, R.layout.incident_type_list_item, incident_type_array);
        autoCompleteIncidentTypetxt.setAdapter(incidentAdapterItems);

        edtLayoutIncidentReportPolice = findViewById(R.id.edtLayoutIncidentReportPolice);
        txtInputIncidentReportPolice = findViewById(R.id.txtInputIncidentReportPolice);

        autoCompleteIncidentTypetxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                txtInputIncidentReportPolice.setText(autoCompleteIncidentTypetxt.getText().toString().trim());

                if(txtInputIncidentReportPolice.getText().toString().equals("Others")){
                    txtInputIncidentReportPolice.setText("");
                    edtLayoutIncidentReportPolice.setVisibility(View.VISIBLE);
                }




            }
        });







        //DatePicker
        btnDatePickerReport = findViewById(R.id.btnDatePickerReport);
        initDatePicker();
        btnDatePickerReport.setText(getTodaysDate());

        txtchkbx = findViewById(R.id.txtchkbx);

        //TimePicker
        btnReportTimePickerReport = findViewById(R.id.btnReportTimePickerReport);
        btnReportTimePickerReport.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int mins = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(PoliceReportActivity.this, TimePickerDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    c.set(Calendar.MINUTE, minute);
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


        report_image_1.setOnClickListener(v -> {
            Dexter.withContext(PoliceReportActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();

        });

        report_image_2.setOnClickListener(v -> {
            Dexter.withContext(PoliceReportActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();

        });

        report_image_3.setOnClickListener(v -> {
            Dexter.withContext(PoliceReportActivity.this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select Image"), 3);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();
        });


        btnSubmitReportBlotter = findViewById(R.id.btnSubmitReportBlotter);
        btnSubmitReportBlotter.setOnClickListener(v -> {
            SubmitReport();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Uri filePath1 = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath1);
                bitmap = BitmapFactory.decodeStream(inputStream);
                evidence_1.setImageBitmap(bitmap);

                ImageStore1(bitmap);
                relativeLayoutImage2.setVisibility(View.VISIBLE);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {

            Uri filePath2 = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath2);
                bitmap = BitmapFactory.decodeStream(inputStream);
                evidence_2.setImageBitmap(bitmap);

                ImageStore2(bitmap);
                relativeLayoutImage3.setVisibility(View.VISIBLE);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        if (requestCode == 3 && resultCode == RESULT_OK) {

            Uri filePath3 = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath3);
                bitmap = BitmapFactory.decodeStream(inputStream);
                evidence_3.setImageBitmap(bitmap);

                ImageStore3(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void ImageStore1(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        image1 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void ImageStore2(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        image2 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void ImageStore3(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        image3 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year, month, day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(year, month, day);
                btnDatePickerReport.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int year, int month, int day) {
        return year + "-" + month + "-" + day;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }


    private void SubmitReport() {
        dialog.setMessage("Submitting Report...");
        dialog.show();

        String street = txtInputStreetReport.getText().toString().trim();
        String barangay = txtBarangay.getText().toString().trim();
        String police_substation = txtPoliceStation.getText().toString().trim();
        String incident_type = txtInputIncidentReportPolice.getText().toString().trim();
        String date_commited = btnDatePickerReport.getText().toString().trim();
        String time_commited = btnReportTimePickerReport.getText().toString().trim();
        String report_details = txtReportDetails.getText().toString().trim();

        if (anonymousCheckBox.isChecked()) {
            txtchkbx.setText("anonymous");
        } else {
            txtchkbx.setText("not anonymous");
        }

        String complainant_identity = txtchkbx.getText().toString().trim();


        StringRequest request = new StringRequest(Request.Method.POST, API.report_police, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    startActivity(new Intent(PoliceReportActivity.this, ReportListActivity.class));
                    finish();
                    Toast.makeText(PoliceReportActivity.this, "Report Submitted Successfully", Toast.LENGTH_SHORT).show();

                } catch (Error error) {
                    error.printStackTrace();
                    dialog.dismiss();

                }
                dialog.dismiss();


            }
        }, error -> {
            Toast.makeText(PoliceReportActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
            dialog.dismiss();

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("street", street);
                map.put("barangay", barangay);
                map.put("police_substation", police_substation);
                map.put("incident_type", incident_type);
                map.put("date_commited", date_commited);
                map.put("time_commited", time_commited);
                map.put("report_details", report_details);
                map.put("complainant_identity", complainant_identity);

                if (image1 != null) {
                    map.put("report_images_1", image1);
                }
                if (image2 != null) {
                    map.put("report_images_2", image2);
                }
                if (image3 != null) {
                    map.put("report_images_3", image3);
                }


                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }


}