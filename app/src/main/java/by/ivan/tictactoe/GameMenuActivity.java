package by.ivan.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import EventBusPOJO.Enemy;
import EventBusPOJO.Game;
import EventBusPOJO.Invite;
import EventBusPOJO.InviteAccept;
import EventBusPOJO.UserEvent;
import EventBusPOJO.UserList;
import EventBusPOJO.UserListResult;

public class GameMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GameMenuActivity";

    private Button button;
    private ListView usersListView;
    private JSONArray jsonArray;
    private List userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        EventBus.getDefault().register(this);
        usersListView = (ListView) findViewById(R.id.usersListView);
        button = (Button) findViewById(R.id.button2) ;
        button.setOnClickListener(this);

        EventBus.getDefault().post(new UserList("getUserList"));

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i(TAG, "onItemClick: ");
                String selectedUser = (String) usersListView.getItemAtPosition(position);
                EventBus.getDefault().post(new UserEvent(selectedUser));
            }

        });
    }

    @Subscribe
    public void onSelectedUser(UserListResult event) {
        try {
            jsonArray = new JSONArray(event.userListResult);
            Log.i(TAG, "onCreate: " + jsonArray.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userList = getUsersList(jsonArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, userList);
        usersListView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEnemy(Enemy enemy) {
        AlertDialog.Builder kissEnemyAssAlert = new AlertDialog.Builder(this);
        kissEnemyAssAlert.setMessage("Ваш противник " + enemy.enemy + " послал Вас нахуй");
        kissEnemyAssAlert.setCancelable(true);
        kissEnemyAssAlert.setPositiveButton(
                "Понятненько",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = kissEnemyAssAlert.create();
        alert11.show();
    }

    @Subscribe
    public void onStartGame(Game game) {
        Intent intent = new Intent(GameMenuActivity.this, MainActivity.class);
        intent.putExtra("gameid", game.gameid);
        intent.putExtra("enemynickname", game.enemynickname);
        Toast.makeText(this, "ПОНЕСЛАСЬ", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    @Subscribe
    public void onInvite(final Invite invite) {
        AlertDialog.Builder inviteToBattle = new AlertDialog.Builder(this);
        inviteToBattle.setMessage("Ваш противник " + invite.enemy + " приглашает Вас на игру");
        inviteToBattle.setCancelable(true);
        inviteToBattle.setPositiveButton(
                "Понятненько",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        EventBus.getDefault().post(new InviteAccept("invite_accept", invite.gameid));
                    }
                });
        AlertDialog alert11 = inviteToBattle.create();
        alert11.show();
    }

    public List getUsersList(JSONArray jsonArray) {
        List users = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                users.add(jsonArray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(GameMenuActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
