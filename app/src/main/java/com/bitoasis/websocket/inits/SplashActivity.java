package com.bitoasis.websocket.inits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.presentation.dashboard.DashboardActivity;
import com.bitoasis.websocket.presentation.userManagement.UserManagementActivity;
import com.bitoasis.websocket.utils.SharedPrefUtils;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    private WeakReference<SplashActivity> splashActivityWR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashActivityWR = new WeakReference<>(this);
        initFields();
        checkLoginState();
    }

    /**
     * Check Login State for redirection
     * */
    private void checkLoginState() {
        if (SharedPrefUtils.isLoggedIn(splashActivityWR.get())){
            Intent dashboardIntent = new Intent(splashActivityWR.get(), DashboardActivity.class);
            startActivity(dashboardIntent);
            finish();
        }
    }

    /**
     * Initialize UI elements
     */
    private void initFields() {
        findViewById(R.id.loginBtn).setOnClickListener(clickListener);
        findViewById(R.id.registerBtn).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginBtn:
                    openUserManagement(UserManagementActivity.FragmentTypes.Login.ordinal());
                    break;

                case R.id.registerBtn:
                    openUserManagement(UserManagementActivity.FragmentTypes.Registration.ordinal());
                    break;
            }
        }
    };

    /**
     * Opens user management activity with
     * selected fragment
     *
     * @param ordinal*/
    private void openUserManagement(int ordinal) {
        Intent userManagementIntent = new Intent(splashActivityWR.get(), UserManagementActivity.class);
        userManagementIntent.putExtra(UserManagementActivity.FRAGMENT_ID, ordinal);
        startActivity(userManagementIntent);
    }

    /**
     * Release references for GC
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clickListener = null;
        splashActivityWR = null;
    }
}
