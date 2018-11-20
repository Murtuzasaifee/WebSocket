package com.bitoasis.websocket.presentation.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.inits.BaseActivity;
import com.bitoasis.websocket.sockets.CustomWebSocketListener;
import com.bitoasis.websocket.sockets.SocketDataListener;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class DashboardActivity extends BaseActivity implements SocketDataListener{

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
        CustomWebSocketListener listener = new CustomWebSocketListener(this);
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
