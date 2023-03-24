package org.tup.safeplace.Verification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import org.tup.safeplace.R;

public class GuideTwoFragment extends Fragment {

    private View view;
    private Button btnGuideTwo;
    private ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guide_two, container, false);
        btnGuideTwo = view.findViewById(R.id.btnContinueGuideTwo);
        btnBack = view.findViewById(R.id.btnGuidTwoBack);

        btnGuideTwo.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameVerificationContainer, new GuideThreeFragment()).commit();
        });

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });


        return view;
    }
}