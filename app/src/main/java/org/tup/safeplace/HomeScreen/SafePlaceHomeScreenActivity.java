package org.tup.safeplace.HomeScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Authentication.AuthenticationActivity;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.HomeScreen.Hospital.HospitalCallScreenFragment;
import org.tup.safeplace.HomeScreen.Police.PoliceCallScreenFragment;
import org.tup.safeplace.R;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;


public class SafePlaceHomeScreenActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private MenuItem previewMenu;
    private FragmentStateAdapter pagerAdapter;

    private static  final int NUM_PAGES=3;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private SharedPreferences userPref;

    private TextView txtUserName,txtUserEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_place_home_screen);
        viewPager2 = findViewById(R.id.pager);
        bottomNavigationView = findViewById(R.id.bottomNav);
        drawerLayout = findViewById(R.id.frameHomeContainer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        View header = navigationView.getHeaderView(0);
        userPref = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        txtUserName = header.findViewById(R.id.txtUserNameDrawer);
        txtUserEmail = header.findViewById(R.id.txtDrawableUserEmail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.AccountMenu){
                    Toast.makeText(SafePlaceHomeScreenActivity.this, "Account", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.VerificationMenu){
                    Toast.makeText(SafePlaceHomeScreenActivity.this, "Verification", Toast.LENGTH_SHORT).show();

                }
                else if(id == R.id.SettingsMenu){
                    Toast.makeText(SafePlaceHomeScreenActivity.this, "Settings", Toast.LENGTH_SHORT).show();

                }
                else if(id == R.id.HelpMenu){
                    Toast.makeText(SafePlaceHomeScreenActivity.this, "Help", Toast.LENGTH_SHORT).show();

                }
                else if(id == R.id.LogoutMenu){
                    Toast.makeText(SafePlaceHomeScreenActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                    logout();
                }

                return false;
            }
        });


        pagerAdapter=new ScreenSlidePageAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setCurrentItem(1,true);
        bottomNavigationView.getMenu().findItem(R.id.homeMenu).setChecked(true);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.hospitalMenu).setChecked(true);
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

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.hospitalMenu:
                        viewPager2.setCurrentItem(0,true);
                        return true;
                    case R.id.homeMenu:
                        viewPager2.setCurrentItem(1,true);
                        return true;
                    case R.id.policeMenu:
                        viewPager2.setCurrentItem(2,true);
                        return true;
                }
                return false;
            }
        });

    }

    private void getData() {

        StringRequest request = new StringRequest(Request.Method.GET, API.get_user_info, response -> {

            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("user");
                if (jsonObject.getBoolean("success")){
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        txtUserName.setText(object.getString("fname")+" "+object.getString("lname"));
                        txtUserEmail.setText(object.getString("email"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            error.printStackTrace();
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private class ScreenSlidePageAdapter extends FragmentStateAdapter{

        public ScreenSlidePageAdapter(SafePlaceHomeScreenActivity safePlaceHomeScreenActivity) {
            super(safePlaceHomeScreenActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new HospitalCallScreenFragment();
                case 1:
                    return new HomeScreenFragment();
                case 2:
                    return new PoliceCallScreenFragment();
                default:
                    return null;
            }        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    private void logout(){
        StringRequest request = new StringRequest(Request.Method.GET, API.logout_user, response -> {
            try{
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }




}