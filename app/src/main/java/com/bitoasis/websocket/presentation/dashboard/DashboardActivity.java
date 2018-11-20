package com.bitoasis.websocket.presentation.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.inits.BaseActivity;
import com.bitoasis.websocket.sockets.CustomWebSocketListener;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DashboardActivity extends BaseActivity {

    WebSocket wSocket;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        testSocketConnection();
    }

    private void testSocketConnection() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://api2.poloniex.com").build();
        CustomWebSocketListener listener = new CustomWebSocketListener();
        wSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wSocket != null){
            wSocket.cancel();
            wSocket = null;
        }
        client = null;
    }
}
