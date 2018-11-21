package com.bitoasis.websocket.presentation.userManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.dbhelper.DbHelper;
import com.bitoasis.websocket.entity.User;
import com.bitoasis.websocket.inits.BaseActivity;
import com.bitoasis.websocket.inits.BaseFragment;
import com.bitoasis.websocket.presentation.dashboard.DashboardActivity;
import com.bitoasis.websocket.utils.AppUtils;

import java.lang.ref.WeakReference;

public class LoginFragment extends BaseFragment {

    public static final String TAG = LoginFragment.class.getName();
    private WeakReference<BaseActivity> activityWR;
    private View parentView;
    private TextInputLayout emailTIL, passwdTIL;

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
        initFields();
        return parentView;
    }

    private void initFields() {
        emailTIL = parentView.findViewById(R.id.emailTIL);
        passwdTIL = parentView.findViewById(R.id.passwdTIL);
        parentView.findViewById(R.id.closeBtn).setOnClickListener(clickListener);
        parentView.findViewById(R.id.loginBtn).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeBtn:
                    closeFragment(activityWR.get());
                    break;

                case R.id.loginBtn:
                    if (isValidForm())
                        checkUserInDB();
                    break;
            }
        }
    };

    private void checkUserInDB() {
        try {
            String email = emailTIL.getEditText().getText().toString().trim();
            String passwd = passwdTIL.getEditText().getText().toString().trim();
            User user = DbHelper.getAppDatabase(activityWR.get()).userDao().getUserByEmail(email);

            if (user != null
                    && user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(passwd)) {

                activityWR.get().finishAffinity();
                Intent dashboardIntent = new Intent(activityWR.get(), DashboardActivity.class);
                startActivity(dashboardIntent);
            } else
                Toast.makeText(activityWR.get(), getString(R.string.userNotExist), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidForm() {
        boolean isFormValid = true;
        resetForm();

        String email = emailTIL.getEditText().getText().toString().trim();
        String passwd = passwdTIL.getEditText().getText().toString().trim();

        if (AppUtils.isInValidString(email)) {
            emailTIL.setError(getString(R.string.enterEmail));
            isFormValid = false;
        }

        if (!AppUtils.isValidEmail(email)) {
            emailTIL.setError(getString(R.string.enterValidEmail));
            isFormValid = false;
        }

        if (AppUtils.isInValidString(passwd)) {
            passwdTIL.setError(getString(R.string.enterPasswd));
            isFormValid = false;
        }

        return isFormValid;
    }

    private void resetForm() {
        emailTIL.setError("");
        emailTIL.setErrorEnabled(false);
        passwdTIL.setError("");
        passwdTIL.setErrorEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clickListener = null;
        activityWR = null;
    }
}
