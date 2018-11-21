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
import com.bitoasis.websocket.inits.BaseFragment;
import com.bitoasis.websocket.presentation.dashboard.DashboardActivity;

import java.lang.ref.WeakReference;

public class LoginFragment extends BaseFragment {

    public static final String TAG = LoginFragment.class.getName();
    private WeakReference<BaseActivity> activityWR;
    private View parentView;

    public LoginFragment() {
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

        parentView = inflater.inflate(R.layout.fragment_login, container, false);
        parentView.findViewById(R.id.closeBtn).setOnClickListener(clickListener);
        parentView.findViewById(R.id.loginBtn).setOnClickListener(clickListener);
        return parentView;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeBtn:
                    closeFragment(activityWR.get());
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
