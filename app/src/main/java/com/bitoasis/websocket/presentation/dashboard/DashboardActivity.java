package com.bitoasis.websocket.presentation.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.utils.ConnectionUtils;
import com.bitoasis.websocket.inits.BaseActivity;
import com.bitoasis.websocket.sockets.CustomWebSocketListener;
import com.bitoasis.websocket.sockets.SocketDataListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DashboardActivity extends BaseActivity implements SocketDataListener {

    private WebSocket wSocket;
    private OkHttpClient client;
    private TextInputLayout inputTIL;
    private WeakReference<DashboardActivity> dashboardActivityWR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardActivityWR = new WeakReference<>(this);
        initFields();
        navigateToFragments(false, new TradeHighlightFragment(), TradeHighlightFragment.TAG);
    }

    /**
     * Initialize UI Fields
     * */
    private void initFields() {
        updateScreenTitle(getString(R.string.tradeHighlight));
        inputTIL = findViewById(R.id.inputTIL);
        findViewById(R.id.fetchDetailsBtn).setOnClickListener(clickListener);
        ((BottomNavigationView)findViewById(R.id.bottomBar)).setOnNavigationItemSelectedListener(navigationListener);
    }

    /**
     * Start Web Socket Connection
     * */
    private void startSocketConnection() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://api2.poloniex.com").build();
        CustomWebSocketListener listener = new CustomWebSocketListener(this);
        wSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    /**
     * View Click listener
     * */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fetchDetailsBtn:
                    if (ConnectionUtils.isNetworkConnected(dashboardActivityWR.get())) {
                        if (validInput())
                            startSocketConnection();
                    } else
                        Toast.makeText(dashboardActivityWR.get(), getString(R.string.plzConnectToNetwork), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * Bottom navigation bar click listener
     * */
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tradeHighlight:
                    updateScreenTitle(getString(R.string.tradeHighlight));
                    navigateToFragments(true, new TradeHighlightFragment(), TradeHighlightFragment.TAG);
                    return true;
                case R.id.tradeSummary:
                    updateScreenTitle(getString(R.string.tradeSummary));
                    navigateToFragments(true, new TradeSummaryFragment(), TradeSummaryFragment.TAG);
                    return true;

            }
            return false;
        }
    };

    /**
     * Update Screen title
     * based on the user selection
     * */
    private void updateScreenTitle(String title){
        ((TextView)findViewById(R.id.screenTitle)).setText(title);
    }

    /**
     * Validate user input
     * before making connection
     * */
    private boolean validInput() {
        String input = inputTIL.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            inputTIL.setError(getString(R.string.plzEnterInput));
            return false;
        } else {
            inputTIL.setErrorEnabled(false);
            inputTIL.setError("");
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wSocket != null) {
            wSocket.cancel();
            wSocket = null;
        }
        client = null;
        clickListener = null;
        dashboardActivityWR = null;
    }

    @Override
    public void onMessageReceived(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            Log.d("onMessage Parsed", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
