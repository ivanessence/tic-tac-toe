package by.ivan.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private EditText editSignIn;
    private Button btnSignIn;
    private WebSockets webSockets;
    public static String NICKNAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        webSockets = new WebSockets();
        editSignIn = (EditText) findViewById(R.id.editSignIn);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                webSockets.sendMessage(editSignIn.getText().toString());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageFromServer event) {
        Log.i(TAG, "onMessageEvent: " + event.message);
        JSONObject jObject  = null;
        String status = null;
        try {
            jObject = new JSONObject(event.message);
            status = jObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (status.equals("success")) {
            NICKNAME = editSignIn.getText().toString();
            Intent intent = new Intent(SignInActivity.this, GameMenuActivity.class);
            String users = null;
            try {
                users = jObject.getString("users");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            intent.putExtra("users", users);
            startActivity(intent);
        } else if (status.equals("fail")) {
            Toast.makeText(this, "Данный пользователь уже есть, ПОШЁЛ НАХУЙ", Toast.LENGTH_LONG).show();
        }

    }
}
