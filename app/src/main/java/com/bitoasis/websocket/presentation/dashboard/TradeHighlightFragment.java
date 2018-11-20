package com.bitoasis.websocket.presentation.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.inits.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeHighlightFragment extends BaseFragment {

    public static final String TAG = TradeHighlightFragment.class.getName();

    public TradeHighlightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trade_highlight, container, false);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        Log.e("=====" ,"Trade Highlight");
    }
}
