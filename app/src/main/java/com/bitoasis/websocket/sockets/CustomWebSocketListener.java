package com.bitoasis.websocket.sockets;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


public class CustomWebSocketListener extends WebSocketListener {
    private static final int CLOSURE_STATUS = 1000;
    private SocketDataListener socketDataListener;

    public CustomWebSocketListener(SocketDataListener socketDataListener){
        this.socketDataListener = socketDataListener;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {

        webSocket.send("{\n" +
                "\n" +
                "\"command\": \"subscribe\",\n" +
                "\n" +
                "\"channel\": 1002\n" +
                "\n" +
                "}");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        socketDataListener.onMessageReceived(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    }
}
