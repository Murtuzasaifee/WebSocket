package com.bitoasis.websocket.presentation.userManagement;

import android.os.Bundle;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.inits.BaseActivity;

public class UserManagementActivity extends BaseActivity {

    public static final String FRAGMENT_ID = "fragmentId";

    public enum FragmentTypes{
        Login,
        Registration
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        int which = getIntent().getIntExtra(FRAGMENT_ID, -1);
        navigateToParticularFragment(which);
    }

    public void navigateToParticularFragment(int which) {
        FragmentTypes fragmentTypes = FragmentTypes.values()[which];
        switch (fragmentTypes) {
            case Login:
                navigateToFragments(false, new LoginFragment(), LoginFragment.TAG);
                break;

            case Registration:
                navigateToFragments(false, new RegistrationFragment(), RegistrationFragment.TAG);
                break;

        }

    }

}
