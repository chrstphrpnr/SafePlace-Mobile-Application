package org.tup.safeplace.Verification;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.tup.safeplace.Authentication.SignUpFragment;
import org.tup.safeplace.HomeScreen.SafePlaceHomeScreenActivity;
import org.tup.safeplace.R;

import java.util.Objects;

public class GuideOneFragment extends Fragment {

    private View view;
    private Button btnGuideOne;
    private ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_guide_one, container, false);

        btnGuideOne = view.findViewById(R.id.btnContinueGuideOne);

        btnBack = view.findViewById(R.id.btnGuidOneBack);


        btnGuideOne.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameVerificationContainer,new GuideTwoFragment()).commit();
        });

        btnBack.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });

        return view;
    }

}