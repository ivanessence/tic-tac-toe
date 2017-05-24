package by.ivan.tictactoe;

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

import EventBusPOJO.Enemy;
import EventBusPOJO.Game;
import EventBusPOJO.GameStep;
import EventBusPOJO.Invite;
import EventBusPOJO.InviteAccept;
import EventBusPOJO.InviteCancel;
import EventBusPOJO.MessageFromServer;
import EventBusPOJO.UserEvent;
import EventBusPOJO.UserList;
import EventBusPOJO.UserListResult;
import EventBusPOJO.UserMove;

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
                            String result = null;
                            JSONObject jObject = new JSONObject(message);
                            if(jObject.has("status")) {
                                result = jObject.getString("status");
                                EventBus.getDefault().post(new MessageFromServer(result));
                                Log.i(TAG, "STATUS");
                            } else if(jObject.has("users")) { // Если тут, то это сюда, а не там и смысл без всех тех, что необходимы до этого
                                result = jObject.getString("users");
                                EventBus.getDefault().post(new UserListResult(result));
                                Log.i(TAG, "USERS");
                            } else if(jObject.has("game")) { //Если приходит json gameid то запускаем игру
                                result = jObject.getString("game");
                                String gameid = jObject.getString("gameid");
                                String enemynickname = jObject.getString("enemynickname");
                                String key = jObject.getString("key");
                                EventBus.getDefault().post(new Game(result, gameid, enemynickname, key));
                                Log.i(TAG, "GAMEID");
//                            } else if(jObject.has("enemy")) { //Если это, то оппонент не дал согласие на игру, и нам приходит отказ
//                                result = jObject.getString("enemy");
//                                EventBus.getDefault().post(new Enemy(result));
//                                Log.i(TAG, "ENEMY");
                            } else if(jObject.has("cmd")) {
                                String enemy = jObject.getString("from");
                                Log.i(TAG, "ENEMY: " + enemy);
                                String gameid = jObject.getString("gameid");
                                EventBus.getDefault().post(new Invite(enemy, gameid));
                            } else if(jObject.has("gamestep")) {
                                String gamestep = jObject.getString("gamestep");
                                EventBus.getDefault().post(new GameStep(gamestep));
                                Log.i(TAG, "GAMESTEP: " + gamestep);
                            }

                            Log.i(TAG, "onTextMessage2: " + result);

//                            if (result.equals("fail")) {
//                                EventBus.getDefault().post(new MessageFromServer(message));
//                            } else if (result.equals("success")) {
//                                EventBus.getDefault().post(new MessageFromServer(message));
//                            } else if (result.equals("users")) {
////                                JSONObject jObject2 = new JSONObject(message);
////                                String result2 = jObject2.getString("users");
//                                Log.i(TAG, "USERLIST: " + result);
//                                EventBus.getDefault().post(new MessageFromServer(result));
//                            }
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "invite");
            jsonObject.put("enemy", event.userForDuel);
            jsonObject.put("from", SignInActivity.NICKNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onSelectedUser: " + jsonObject.toString());
        ws.sendText(jsonObject.toString());
    }

    @Subscribe
    public void getUserList(UserList event) {
        Log.i(TAG, "getUserList: ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "getUserList");
            jsonObject.put("nickname", SignInActivity.NICKNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ws.sendText(jsonObject.toString());
    }

    @Subscribe
    public void inviteAccept(InviteAccept inviteAccept) {
        Log.i(TAG, "inviteAccept: ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "invite_accept");
            jsonObject.put("gameid", inviteAccept.gameid);
            jsonObject.put("enemy", inviteAccept.enemy);
            jsonObject.put("from", SignInActivity.NICKNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ws.sendText(jsonObject.toString());
    }

    @Subscribe
    public void inviteCancel(InviteCancel inviteCancel) {
        Log.i(TAG, "inviteCancel: ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "invite_cancel");
            jsonObject.put("gameid", inviteCancel.gameid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ws.sendText(jsonObject.toString());
    }

    @Subscribe
    public void onUserMove(UserMove userMove) {
        Log.i(TAG, "onUserMove: ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "game");
            jsonObject.put("gameid", MainActivity.gameid);
            jsonObject.put("cell", userMove.userMove);
            jsonObject.put("key", MainActivity.key);
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


