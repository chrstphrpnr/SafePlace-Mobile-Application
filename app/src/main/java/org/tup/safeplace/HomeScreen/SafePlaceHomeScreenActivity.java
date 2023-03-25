package org.tup.safeplace.HomeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Authentication.AuthenticationActivity;
import org.tup.safeplace.CallHistory.CallHistoryActivity;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.Barangay.BarangayCallScreenFragment;
import org.tup.safeplace.HomeScreen.Police.PoliceCallScreenFragment;
import org.tup.safeplace.Notification.NotificationActivity;
import org.tup.safeplace.R;
import org.tup.safeplace.UserAccount.UserAccountActivity;
import org.tup.safeplace.Verification.VerificationActivity;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class SafePlaceHomeScreenActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FragmentManager fragmentManager;
    private MenuItem previewMenu;
    private FragmentStateAdapter pagerAdapter;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences userPref;

    private TextView txtUserName, txtUserVerificationStatus;

    private CircleImageView imgProfile;
    private final String imgUrl = "";

    private ImageView notificationBell,btnCallLogs;
    private Menu optionsMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_place_home_screen);

        viewPager2 = findViewById(R.id.pager);
        bottomNavigationView = findViewById(R.id.bottomNav);
        drawerLayout = findViewById(R.id.frameHomeContainer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        notificationBell = findViewById(R.id.notificationBell);


        View header = navigationView.getHeaderView(0);
        userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        txtUserName = header.findViewById(R.id.txtUserNameDrawer);
        txtUserVerificationStatus = header.findViewById(R.id.txtUserVerificationStatus);

//        txtUserEmail = header.findViewById(R.id.txtDrawableUserEmail);
        imgProfile = header.findViewById(R.id.userProfileImage);

        notificationBell.setOnClickListener(v -> {
            startActivity(new Intent(SafePlaceHomeScreenActivity.this, NotificationActivity.class));
        });

        btnCallLogs = findViewById(R.id.btnCallLogs);
        btnCallLogs.setOnClickListener(v->{
            startActivity(new Intent(SafePlaceHomeScreenActivity.this, CallHistoryActivity.class));
        });


        //Drawable Drawer and Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.AccountMenu) {
                startActivity(new Intent(SafePlaceHomeScreenActivity.this, UserAccountActivity.class));
            } else if (id == R.id.VerificationMenu) {


                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                boolean requested = userPref.getBoolean("verification_submitted", true);


                StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                        if (jsonObject.getBoolean("success")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String status = object.getString("role");

                                if (status.equals("unverified_user")) {

//                                        if (requested){
//                                            Toast.makeText(SafePlaceHomeScreenActivity.this, "Your account is already in the process of verification, please wait.", Toast.LENGTH_SHORT).show();
//                                            showPopupWindow();
//                                        }
//                                        else {
                                    startActivity(new Intent(SafePlaceHomeScreenActivity.this, VerificationActivity.class));
                                    SharedPreferences.Editor editor = userPref.edit();
                                    editor.putBoolean("verification_submitted", false);
                                    editor.apply();
//                                        }
                                }

                                if (status.equals("verified_user")) {
                                    Toast.makeText(SafePlaceHomeScreenActivity.this, "Your Account is already registered.", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = userPref.edit();
                                    editor.putBoolean("verification_submitted", true);
                                    editor.apply();
                                    showPopupWindowVerified();
                                }

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

                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(request);


            } else if (id == R.id.HelpMenu) {
                Toast.makeText(SafePlaceHomeScreenActivity.this, "Help", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.LogoutMenu) {
                Toast.makeText(SafePlaceHomeScreenActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                logout();
            }

            return false;
        });

        //Slide PageAdapter
        pagerAdapter = new ScreenSlidePageAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setCurrentItem(1, true);

        //Set Default Bottom Navigation Menu to HomeMenu
        bottomNavigationView.getMenu().findItem(R.id.homeMenu).setChecked(true);

        //Highlight which icon when viewpager is changed
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.barangayMenu).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.homeMenu).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.policeMenu).setChecked(true);
                        break;
                }
            }
        });

        //Bottom Navigation Changing Page when bottom navigation icon is changed
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.barangayMenu:
                        viewPager2.setCurrentItem(0, true);
                        return true;
                    case R.id.homeMenu:
                        viewPager2.setCurrentItem(1, true);
                        return true;
                    case R.id.policeMenu:
                        viewPager2.setCurrentItem(2, true);
                        return true;
                }
                return false;
            }
        });


        userPref = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        String fname = userPref.getString("fname", "");
        String lname = userPref.getString("lname", "");

//        String email = userPref.getString("email","");

        if (fname != null || lname != null) {
            txtUserName.setText(fname + " " + lname);
        }

//        if (email != null){
//            txtUserEmail.setText(email);
//        }
//
//        if (email.equals(null)){
//            txtUserEmail.setText("");
//        }

        if (fname.equals(null) || lname.equals(null)) {
            txtUserName.setText("");
        }

    }


    //    Volley Get Method for Display of UserInformation in Drawable Drawer
    private void getData() {

        StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        txtUserName.setText(object.getString("fname") + " " + object.getString("lname"));

                        String status = object.getString("role");

                        if (status.equals("unverified_user")) {
                            txtUserVerificationStatus.setText("Unverified User");
                        }

                        if (status.equals("verified_user")) {
                            txtUserVerificationStatus.setText("Verified User");
                        }

                        Picasso.get().load(API.URL + object.getString("img")).resize(500, 0).centerCrop().into(imgProfile);

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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }


    //    OnResume Method for getData Method
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    //Logout Method for user logging out
    private void logout() {
        StringRequest request = new StringRequest(Request.Method.GET, API.logout_user, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(SafePlaceHomeScreenActivity.this, AuthenticationActivity.class));
                    finish();

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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    //PopUp Windows
    private void showPopupWindow() {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_pending_user_pop_up, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(drawerLayout, Gravity.CENTER, 0, 0);

        Button txtSkipPending = popupView.findViewById(R.id.txtPendingClose);

        txtSkipPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    //PopUp Windows
    private void showPopupWindowVerified() {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_registered_user_pop_up, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(drawerLayout, Gravity.CENTER, 0, 0);

        Button txtSkipPending = popupView.findViewById(R.id.txtRegisteredClose);

        txtSkipPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    //Check Notification
    private void check_notification() {

        TextView notification_count = findViewById(R.id.txt_notif_count);


        StringRequest request = new StringRequest(Request.Method.GET, API.notification_check_unread, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("notification");


                if (jsonObject.getBoolean("success")) {
//                    for (int i = 0; i <jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);

                    notification_count.setText(Integer.toString(jsonArray.length()));

//                    }
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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        check_notification();
    }

    //Bottom Navigation Slide Pager
    private class ScreenSlidePageAdapter extends FragmentStateAdapter {

        public ScreenSlidePageAdapter(SafePlaceHomeScreenActivity safePlaceHomeScreenActivity) {
            super(safePlaceHomeScreenActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new BarangayCallScreenFragment();
                case 1:
                    return new HomeScreenFragment();
                case 2:
                    return new PoliceCallScreenFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}