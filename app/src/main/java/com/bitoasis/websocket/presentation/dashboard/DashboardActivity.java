package com.bitoasis.websocket.presentation.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.datamodels.TradeModel;
import com.bitoasis.websocket.inits.BaseFragment;
import com.bitoasis.websocket.utils.ConnectionUtils;
import com.bitoasis.websocket.inits.BaseActivity;
import com.bitoasis.websocket.sockets.CustomWebSocketListener;
import com.bitoasis.websocket.sockets.SocketDataListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DashboardActivity extends BaseActivity implements SocketDataListener {

    private WebSocket wSocket;
    private OkHttpClient client;
    private TextInputLayout inputTIL;
    private WeakReference<DashboardActivity> dashboardActivityWR;
    private BaseFragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashboardActivityWR = new WeakReference<>(this);
        initFields();
        TradeHighlightFragment tradeHighlightFragment = new TradeHighlightFragment();
        navigateToFragments(false, tradeHighlightFragment, TradeHighlightFragment.TAG);
        selectedFragment = tradeHighlightFragment;
        initSocketConnection();
    }

    private void initSocketConnection() {
        if (ConnectionUtils.isNetworkConnected(dashboardActivityWR.get())) {
            if (validInput())
                startSocketConnection();
        } else
            Toast.makeText(dashboardActivityWR.get(), getString(R.string.plzConnectToNetwork), Toast.LENGTH_SHORT).show();
    }

    /**
     * Initialize UI Fields
     */
    private void initFields() {
        updateScreenTitle(getString(R.string.tradeHighlight));
        inputTIL = findViewById(R.id.inputTIL);
        ((BottomNavigationView) findViewById(R.id.bottomBar)).setOnNavigationItemSelectedListener(navigationListener);
    }

    /**
     * Start Web Socket Connection
     */
    private void startSocketConnection() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://api2.poloniex.com").build();
        CustomWebSocketListener listener = new CustomWebSocketListener(this);
        wSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }


    /**
     * Bottom navigation bar click listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tradeHighlight:
                    updateScreenTitle(getString(R.string.tradeHighlight));
                    TradeHighlightFragment tradeHighlightFragment = new TradeHighlightFragment();
                    selectedFragment = tradeHighlightFragment;
                    navigateToFragments(false, tradeHighlightFragment, TradeHighlightFragment.TAG);
                    return true;
                case R.id.tradeSummary:
                    updateScreenTitle(getString(R.string.tradeSummary));
                    TradeSummaryFragment tradeSummaryFragment = new TradeSummaryFragment();
                    selectedFragment = tradeSummaryFragment;
                    navigateToFragments(false, tradeSummaryFragment, TradeSummaryFragment.TAG);
                    return true;

            }
            return false;
        }
    };

    /**
     * Update Screen title
     * based on the user selection
     */
    private void updateScreenTitle(String title) {
        ((TextView) findViewById(R.id.screenTitle)).setText(title);
    }

    /**
     * Validate user input
     * before making connection
     */
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
        dashboardActivityWR = null;
    }

    @Override
    public void onMessageReceived(final String data) {
        Log.d("onMessageReceived", data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectedFragment.updateUI(getTradeModel(data));
            }
        });

    }

    private TradeModel getTradeModel(String data) {
        TradeModel tradeModel = new TradeModel();
        try {
            String stringSplit = data.replace("[", "");
            stringSplit = stringSplit.replace("]", "");
            stringSplit = stringSplit.replace("\"", "");

            List<String> splitStr = Arrays.asList(stringSplit.split(","));
            if (splitStr.size() > 10) {
                tradeModel.setCurrencyId(Integer.parseInt(splitStr.get(2)));
                tradeModel.setLastTradePrice(splitStr.get(3));
                tradeModel.setLowestAsk(splitStr.get(4));
                tradeModel.setHighestBid(splitStr.get(5));
                tradeModel.setPertChange(splitStr.get(6));
                tradeModel.setBaseCurrency(splitStr.get(7));
                tradeModel.setQouteCurrency(splitStr.get(8));
                tradeModel.setIsFrozen(Integer.parseInt(splitStr.get(9)));
                tradeModel.setHighestTradePrice(splitStr.get(10));
                tradeModel.setLowestTradePrice(splitStr.get(11));
            }

           tradeModel.setGreater(getTradeMovementIndicator(tradeModel.getLastTradePrice()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tradeModel;
    }

    private boolean getTradeMovementIndicator(String tradePrice) {
        int input = Integer.parseInt(inputTIL.getEditText().getText().toString().trim());
        if (input > Double.parseDouble(tradePrice))
            return false;
        else
            return true;
    }
}
