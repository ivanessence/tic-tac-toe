package by.ivan.tictactoe;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ivanessence on 19.05.2017.
 */

public class WebSockets extends Service {

    private static final String TAG = "WebSockets";
    private WebSocket ws;

    public WebSockets() {
        createWebSocketMethod();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                            Log.i(TAG, "onTextMessage1: " + message);
                            JSONObject jObject = new JSONObject(message);
                            String result = jObject.getString("status");
                            Log.i(TAG, "onTextMessage2: " + result);

                            if (result.equals("fail")) {
                                EventBus.getDefault().post(new MessageFromServer(message));
                            } else if (result.equals("success")) {
                                JSONObject usersObject = new JSONObject(message);
                                EventBus.getDefault().post(new MessageFromServer(message));
                            }

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

    @Subscribe
    public void onSelectedUser(UserEvent event) {
            Log.i(TAG, "onSelectedUser: " + event.userForDuel);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "invite");
            jsonObject.put("addEnemy", event.userForDuel);
            jsonObject.put("from", SignInActivity.NICKNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ws.sendText(jsonObject.toString());
    }

    public void sendMessage(String nickname) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
            jsonObject.put("cmd", "login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "sendMessage: " + nickname);
        ws.sendText(jsonObject.toString());
    }
}


