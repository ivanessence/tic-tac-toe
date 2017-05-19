package by.ivan.tictactoe;

import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ivanessence on 19.05.2017.
 */

public class WebSockets {

    private static final String TAG = "WebSockets";
    private WebSocket ws;

    public WebSockets() {
        createWebSocketMethod();
    }

    private void createWebSocketMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ws = new WebSocketFactory().createSocket("ws://192.168.0.162:8000/ws");
                    ws.connect();
                    ws.addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket websocket, String message) throws Exception {
                            Log.i(TAG, "onTextMessage: " + message);
                            EventBus.getDefault().post(new MessageFromServer(message));
                        }
                    });
                } catch (WebSocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void sendMessage(String nickname) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "sendMessage: " + nickname);
        ws.sendText(jsonObject.toString());
    }
}


