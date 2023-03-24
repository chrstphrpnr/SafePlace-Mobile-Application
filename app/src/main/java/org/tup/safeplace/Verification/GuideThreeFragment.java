package org.tup.safeplace.Verification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import org.tup.safeplace.R;


public class GuideThreeFragment extends Fragment {

    private View view;
    private Button btnGuideThree;
    private ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guide_three, container, false);

        btnGuideThree = view.findViewById(R.id.btnContinueGuideThree);
        btnBack = view.findViewById(R.id.btnGuideThreeBack);

        btnGuideThree.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), UpdateInformationActivity.class));
            ((VerificationActivity) getContext()).finish();
        });

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        return view;
    }
}