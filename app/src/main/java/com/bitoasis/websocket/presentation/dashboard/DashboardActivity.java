package com.bitoasis.websocket.presentation.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.Utils.ConnectionUtils;
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
    }

    private void initFields() {
        inputTIL = findViewById(R.id.inputTIL);
        findViewById(R.id.fetchDetailsBtn).setOnClickListener(clickListener);
        ((BottomNavigationView)findViewById(R.id.bottomBar)).setOnNavigationItemSelectedListener(navigationListener);
    }

    private void startSocketConnection() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://api2.poloniex.com").build();
        CustomWebSocketListener listener = new CustomWebSocketListener(this);
        wSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

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

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tradeHighlight:
                    return true;
                case R.id.tradeSummary:
                    return true;

            }
            return false;
        }
    };

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
