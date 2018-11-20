package com.bitoasis.websocket.inits;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class BaseFragment extends Fragment {

    public void closeFragment(AppCompatActivity activity){
        activity.getSupportFragmentManager().popBackStack();
    }

    public void updateUI(){}
}
