package by.ivan.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        usersListView = (ListView) findViewById(R.id.usersListView);
        button = (Button) findViewById(R.id.button2) ;
        button.setOnClickListener(this);

        Intent intent = getIntent();
        Log.i(TAG, "onCreate: " + intent.getStringExtra("users"));
        try {
            jsonArray = new JSONArray(intent.getStringExtra("users"));
            Log.i(TAG, "onCreate: " + jsonArray.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userList = getUsersList(jsonArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, userList);
        usersListView.setAdapter(adapter);
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
