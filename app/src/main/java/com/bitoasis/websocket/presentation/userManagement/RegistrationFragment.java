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
import com.bitoasis.websocket.dbhelper.AppDatabase;
import com.bitoasis.websocket.dbhelper.DbHelper;
import com.bitoasis.websocket.entity.User;
import com.bitoasis.websocket.inits.BaseActivity;
import com.bitoasis.websocket.inits.BaseFragment;
import com.bitoasis.websocket.presentation.dashboard.DashboardActivity;
import com.bitoasis.websocket.utils.AppUtils;
import com.bitoasis.websocket.utils.SharedPrefUtils;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends BaseFragment {

    public static final String TAG = RegistrationFragment.class.getName();
    private WeakReference<BaseActivity> activityWR;
    private View parentView;
    private TextInputLayout firstNameTIL, lastNameTIL, emailTIL, passwdTIL;

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
        initFields();
        return parentView;
    }

    private void initFields() {
        firstNameTIL = parentView.findViewById(R.id.firstNameTIL);
        lastNameTIL = parentView.findViewById(R.id.lastNameTIL);
        emailTIL = parentView.findViewById(R.id.emailTIL);
        passwdTIL = parentView.findViewById(R.id.passwdTIL);
        parentView.findViewById(R.id.closeBtn).setOnClickListener(clickListener);
        parentView.findViewById(R.id.registerBtn).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeBtn:
                    closeFragment(activityWR.get());
                    break;

                case R.id.registerBtn:
                    if (isValidForm())
                        insertUserInDB();

                    break;
            }
        }
    };

    private void insertUserInDB() {
        try {
            User user = getUserModel();

            if (isUserExist(getUserModel().getEmail())) {
                Toast.makeText(activityWR.get(), getString(R.string.userExist), Toast.LENGTH_SHORT).show();
                return;
            }

            long id = DbHelper.getAppDatabase(activityWR.get()).userDao().insert(user);
            if (id > 0) {
                SharedPrefUtils.setLogin(activityWR.get(), true);
                activityWR.get().finishAffinity();
                Intent dashboardIntent = new Intent(activityWR.get(), DashboardActivity.class);
                startActivity(dashboardIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private User getUserModel() {
        User user = new User();
        String firstName = firstNameTIL.getEditText().getText().toString().trim();
        String lastName = lastNameTIL.getEditText().getText().toString().trim();
        String email = emailTIL.getEditText().getText().toString().trim();
        String passwd = passwdTIL.getEditText().getText().toString().trim();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwd);
        return user;
    }

    private boolean isUserExist(String email) {
        User user = DbHelper.getAppDatabase(activityWR.get()).userDao().getUserByEmail(email);
        if (user != null) return true;
        else return false;
    }

    private boolean isValidForm() {
        boolean isFormValid = true;
        resetForm();

        String firstName = firstNameTIL.getEditText().getText().toString().trim();
        String lastName = lastNameTIL.getEditText().getText().toString().trim();
        String email = emailTIL.getEditText().getText().toString().trim();
        String passwd = passwdTIL.getEditText().getText().toString().trim();

        if (AppUtils.isInValidString(firstName)) {
            firstNameTIL.setError(getString(R.string.enterFirstName));
            isFormValid = false;
        }

        if (AppUtils.isInValidString(lastName)) {
            lastNameTIL.setError(getString(R.string.enterLastName));
            isFormValid = false;
        }

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
        firstNameTIL.setError("");
        firstNameTIL.setErrorEnabled(false);
        lastNameTIL.setError("");
        lastNameTIL.setErrorEnabled(false);
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
