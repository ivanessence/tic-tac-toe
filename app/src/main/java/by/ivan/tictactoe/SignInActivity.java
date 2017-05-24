package by.ivan.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import EventBusPOJO.MessageFromServer;

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

        editSignIn = (EditText) findViewById(R.id.editSignIn);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webSockets = new WebSockets();
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
                if (editSignIn.getText().toString().equals("")) {
                    Toast.makeText(this, "Введи свой ник, Пидор", Toast.LENGTH_SHORT).show();
                } else {
                    webSockets.sendMessage(editSignIn.getText().toString());
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageFromServer event) {
        Log.i(TAG, "onMessageEvent: " + event.message);
        if (event.message.equals("success")) {
            NICKNAME = editSignIn.getText().toString();
            Intent intent = new Intent(SignInActivity.this, GameMenuActivity.class);
            String users = null;
            startActivity(intent);
        } else if (event.message.equals("fail")) {
            Toast.makeText(this, "Данный пользователь уже есть, ПОШЁЛ НАХУЙ", Toast.LENGTH_LONG).show();
        }

    }
}
