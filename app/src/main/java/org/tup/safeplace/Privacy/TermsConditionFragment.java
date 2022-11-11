package org.tup.safeplace.Privacy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.tup.safeplace.Authentication.AuthenticationActivity;
import org.tup.safeplace.R;


public class TermsConditionFragment extends Fragment {
    private View view;
    private Button btnAccept, btnDecline;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_terms_condition, container, false);
        buttonAccept();
        buttonDecline();
        return view;
    }

    private void buttonDecline() {
        btnDecline = view.findViewById(R.id.btnDeclineTerm);

        try {
            prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean firstTimeLoad = prefs.getBoolean("first_time_cancel", false);
            if (firstTimeLoad) {
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDecline.setOnClickListener(v->{
            getActivity().finishAffinity();
            System.exit(0);
        });
    }

    private void buttonAccept() {
        btnAccept = view.findViewById(R.id.btnAcceptTerm);

        try {
            prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean firstTimeLoad = prefs.getBoolean("first_time_load", true);
            if (!firstTimeLoad) {
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAccept.setOnClickListener(v->{
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_time_load", false);
            editor.commit();
            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
            startActivity(intent);
        });
    }



}