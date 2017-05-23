package by.ivan.tictactoe;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import EventBusPOJO.MessageFromServer;

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
    private boolean playerType = true;
    private ImageView changed;
    private BitmapDrawable zero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            case R.id.button:
                Log.i(TAG, "onClick: ");
                break;
            case R.id.imgField1:
                EventBus.getDefault().post(new MessageFromServer("f1"));
                if (stateTurn) {
                    if (playerType) {
                        imgField1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                        } else {imgField1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField1.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField2:
                EventBus.getDefault().post(new MessageFromServer("f2"));
                if (stateTurn) {
                    if (playerType) {
                        imgField2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField2.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField3:
                EventBus.getDefault().post(new MessageFromServer("f3"));
                if (stateTurn) {
                    if (playerType) {
                        imgField3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField3.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField4:
                EventBus.getDefault().post(new MessageFromServer("f4"));
                if (stateTurn) {
                    if (playerType) {
                        imgField4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField4.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField5:
                EventBus.getDefault().post(new MessageFromServer("f5"));
                if (stateTurn) {
                    if (playerType) {
                        imgField5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField5.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField6:
                EventBus.getDefault().post(new MessageFromServer("f6"));
                if (stateTurn) {
                    if (playerType) {
                        imgField6.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField6.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField6.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField7:
                EventBus.getDefault().post(new MessageFromServer("f7"));
                if (stateTurn) {
                    if (playerType) {
                        imgField7.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField7.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField7.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField8:
                EventBus.getDefault().post(new MessageFromServer("f8"));
                if (stateTurn) {
                    if (playerType) {
                        imgField8.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField8.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField8.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imgField9:
                EventBus.getDefault().post(new MessageFromServer("f9"));
                if (stateTurn) {
                    if (playerType) {
                        imgField9.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cross2));
                    } else {imgField9.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zero2));
                    }
                    imgField9.setClickable(false);
                } else {
                    Toast.makeText(this, "Ожидайте ход противника", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Subscribe
    public void giveData(MessageFromServer event) {

    }
}
