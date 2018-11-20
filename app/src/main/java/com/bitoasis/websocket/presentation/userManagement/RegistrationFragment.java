package com.bitoasis.websocket.presentation.userManagement;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.inits.BaseActivity;
import com.bitoasis.websocket.presentation.dashboard.DashboardActivity;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    public static final String TAG = RegistrationFragment.class.getName();
    private WeakReference<BaseActivity> activityWR;
    private View parentView;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityWR = new WeakReference<>((BaseActivity) getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (parentView != null)
            return parentView;

        parentView = inflater.inflate(R.layout.fragment_registration, container, false);
        parentView.findViewById(R.id.closeBtn).setOnClickListener(clickListener);
        parentView.findViewById(R.id.registerBtn).setOnClickListener(clickListener);
        return parentView;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.closeBtn:
                    activityWR.get().getSupportFragmentManager().popBackStack();
                    break;

                case R.id.loginBtn:
                    activityWR.get().finishAffinity();
                    Intent dashboardIntent = new Intent(activityWR.get(), DashboardActivity.class);
                    startActivity(dashboardIntent);
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        clickListener = null;
        activityWR = null;
    }

}
