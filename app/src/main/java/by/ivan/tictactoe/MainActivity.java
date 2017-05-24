package by.ivan.tictactoe;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import EventBusPOJO.GameResult;
import EventBusPOJO.GameStep;
import EventBusPOJO.MessageFromServer;
import EventBusPOJO.UserMove;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button one;
    private ImageView imgField1;
    private ImageView imgField2;
    private ImageView imgField3;
    private ImageView imgField4;
    private ImageView imgField5;
    private ImageView imgField6;
    private ImageView imgField7;
    private ImageView imgField8;
    private ImageView imgField9;
    private String playerTurn;
    private boolean stateTurn = true;
    private TextView textEnemy;
    private TextView textShape;
    public static String gameid;
    public static String key;
    public Handler h;
    String cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textEnemy = (TextView) findViewById(R.id.textEnemy);
        textShape = (TextView) findViewById(R.id.textShape);
        one = (Button) findViewById(R.id.button);
        one.setOnClickListener(this);
        imgField1 = (ImageView) findViewById(R.id.imgField1);
        imgField1.setOnClickListener(this);
        imgField2 = (ImageView) findViewById(R.id.imgField2);
        imgField2.setOnClickListener(this);
        imgField3 = (ImageView) findViewById(R.id.imgField3);
        imgField3.setOnClickListener(this);
        imgField4 = (ImageView) findViewById(R.id.imgField4);
        imgField4.setOnClickListener(this);
        imgField5 = (ImageView) findViewById(R.id.imgField5);
        imgField5.setOnClickListener(this);
        imgField6 = (ImageView) findViewById(R.id.imgField6);
        imgField6.setOnClickListener(this);
        imgField7 = (ImageView) findViewById(R.id.imgField7);
        imgField7.setOnClickListener(this);
        imgField8 = (ImageView) findViewById(R.id.imgField8);
        imgField8.setOnClickListener(this);
        imgField9 = (ImageView) findViewById(R.id.imgField9);
        imgField9.setOnClickListener(this);

        Intent gameInfo = getIntent();
        String enemy = gameInfo.getStringExtra("enemynickname");
        String shape = gameInfo.getStringExtra("shape");
        textEnemy.setText(enemy);
        textShape.setText(shape);
        gameid = gameInfo.getStringExtra("gameid");
        key = gameInfo.getStringExtra("shape");

        final int win = 777;
        final int looser = 666;
        final int draw = 111;

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        setOpponentTurn(imgField1);
                        break;
                    case 2:
                        setOpponentTurn(imgField2);
                        break;
                    case 3:
                        setOpponentTurn(imgField3);
                        break;
                    case 4:
                        setOpponentTurn(imgField4);
                        break;
                    case 5:
                        setOpponentTurn(imgField5);
                        break;
                    case 6:
                        setOpponentTurn(imgField6);
                        break;
                    case 7:
                        setOpponentTurn(imgField7);
                        break;
                    case 8:
                        setOpponentTurn(imgField8);
                        break;
                    case 9:
                        setOpponentTurn(imgField9);
                        break;
                    case win:
                        imgField1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField6.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField7.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField8.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField9.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        break;
                    case looser:
                        imgField1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField6.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField7.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField8.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        imgField9.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        break;
                    case draw:
                        imgField1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField6.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField7.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField8.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        imgField9.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.field));
                        break;
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgField1:
                move("1", imgField1);
                break;
            case R.id.imgField2:
                move("2", imgField2);
                break;
            case R.id.imgField3:
                move("3", imgField3);
                break;
            case R.id.imgField4:
                move("4", imgField4);
                break;
            case R.id.imgField5:
                move("5", imgField5);
                break;
            case R.id.imgField6:
                move("6", imgField6);
                break;
            case R.id.imgField7:
                move("7", imgField7);
                break;
            case R.id.imgField8:
                move("8", imgField8);
                break;
            case R.id.imgField9:
                move("9", imgField9);
                break;
        }
    }

    @Subscribe
    public void onGameResult(GameResult gresult, GameResult cel) {
        Message msg = new Message();
        String gameresult = gresult.gameresult;
        msg.what = Integer.valueOf(gameresult);
        h.sendMessage(msg);
    }

    @Subscribe
    public void onGameStep(GameStep gameStep) {
        Message msg = new Message();
        Log.i(TAG, "gameStep: " + gameStep.gameStep);
        String gamestep = gameStep.gameStep;
        msg.what = Integer.valueOf(gamestep);
        Log.i(TAG, "msg.what: " + msg.what);
        h.sendMessage(msg);
    }

    public void move(String f, ImageView iv) {
        if (stateTurn) {
            if (key.equals("X")) {
                iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
            } else {
                iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
            }
            EventBus.getDefault().post(new UserMove(f));
            iv.setClickable(false);
            stateTurn = false;
        } else {
            Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
        }
    }
    public void setOpponentTurn (ImageView iv) {
            if (key.equals("X")) {
                iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
            } else {
                iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
            }
            iv.setClickable(false);
            stateTurn = true;
    }
}

