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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.tup.safeplace.HomeScreen.Hospital.HospitalCallScreenFragment;
import org.tup.safeplace.HomeScreen.Police.PoliceCallScreenFragment;
import org.tup.safeplace.R;
import androidx.appcompat.widget.Toolbar;


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

                }

                return false;
            }
        });
        userPref = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        View header = navigationView.getHeaderView(0);
        txtUserName = header.findViewById(R.id.txtUserNameDrawer);
        txtUserEmail = header.findViewById(R.id.txtDrawableUserEmail);

        userPref = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
        String fname = userPref.getString("fname","");
        String lname = userPref.getString("lname","");

        String email = userPref.getString("email","");


        if(fname != null || lname != null){
            txtUserName.setText(fname + " " + lname);
        }

        if (email != null){
            txtUserEmail.setText(email);
        }

        if (email.equals(null)){
            txtUserEmail.setText("");
        }

        if (fname.equals(null)||lname.equals(null)){
            txtUserEmail.setText("");
        }




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
}