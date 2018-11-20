package com.bitoasis.websocket.inits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bitoasis.websocket.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void navigateToFragments(boolean isAddToBackStack, Fragment fragment, String tag) {
        if (isAddToBackStack) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentView, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentView, fragment, tag)
                    .commitAllowingStateLoss();
        }
    }

}
