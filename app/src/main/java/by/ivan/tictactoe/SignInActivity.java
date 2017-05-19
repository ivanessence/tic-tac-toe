package by.ivan.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private EditText editSignIn;
    private Button btnSignIn;
    private WebSockets webSockets;

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                webSockets.sendMessage(editSignIn.getText().toString());
                break;
        }
    }

    @Subscribe
    public void onMessageEvent(MessageFromServer event) {
        Log.i(TAG, "onMessageEvent: " + event.message);
    }
}
