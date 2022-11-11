package org.tup.safeplace.Onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import org.tup.safeplace.Authentication.AuthenticationActivity;
import org.tup.safeplace.Privacy.PrivacyActivity;
import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    OnboardingAdapter onboardingAdapter;
    LinearLayout layoutOnboardingIndicator;
    MaterialButton buttonOnboaringAction;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_SafePlace_NoActionBar);
        setContentView(R.layout.activity_onboarding);


        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicator);
        buttonOnboaringAction = findViewById(R.id.buttonOnboardingAction);


        setupOnboardingItem();
        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewpager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicator();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        try {
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            boolean firstTimeLoading = prefs.getBoolean("first_time_finish",true);
            if (!firstTimeLoading){
                nextFragment();
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        buttonOnboaringAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }
                else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("first_time_finish",false);
                    editor.commit();
                    nextFragment();
                }
            }
        });

    }

    private void nextFragment(){
        startActivity(new Intent(getApplicationContext(), PrivacyActivity.class));
        finish();
    }



    private void setupOnboardingItem() {

        List<OnboardingItems> onboardingItems = new ArrayList<>();

        OnboardingItems first_feature = new OnboardingItems();
        first_feature.setTitle("Feature Number One");
        first_feature.setDescription("There are various of features items in our application for SafePlace");
        first_feature.setImage(R.drawable.onboarding_image_1);


        OnboardingItems second_feature = new OnboardingItems();
        second_feature.setTitle("Feature Number Two");
        second_feature.setDescription("There are various of features items in our application for SafePlace");
        second_feature.setImage(R.drawable.onboarding_image_2);


        OnboardingItems third_feature = new OnboardingItems();
        third_feature.setTitle("Feature Number Three");
        third_feature.setDescription("There are various of features items in our application for SafePlace");
        third_feature.setImage(R.drawable.onboarding_image_3);

        OnboardingItems fourth_feature = new OnboardingItems();
        fourth_feature.setTitle("Feature Number Four");
        fourth_feature.setDescription("There are various of features items in our application for SafePlace");
        fourth_feature.setImage(R.drawable.onboarding_image_4);

        onboardingItems.add(first_feature);
        onboardingItems.add(second_feature);
        onboardingItems.add(third_feature);
        onboardingItems.add(fourth_feature);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicator(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);

        for (int i = 0; i <indicators.length ; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));

            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }


    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicator.getChildCount();
        for(int i=0; i<childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active));
            }
            else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            }
        }

        if (index == onboardingAdapter.getItemCount() - 1){
            buttonOnboaringAction.setText("Start");
        }
        else {
            buttonOnboaringAction.setText("Next");
        }

    }
}