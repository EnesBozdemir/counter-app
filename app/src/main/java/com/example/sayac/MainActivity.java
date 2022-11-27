package com.example.sayac;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    TextView value;
    Button minus, plus, ayar;
    int upperLimit;
    int lowerLimit;
    int currentValue;

    boolean upperVib;
    boolean upperSound;
    boolean lowerVib;
    boolean lowerSound;
    SetupClass setupClass;
    Vibrator vibrator =null;
    MediaPlayer player=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = (TextView) findViewById(R.id.value);
        minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        ayar = (Button) findViewById(R.id.ayar);

        Context context=getApplicationContext();
        setupClass =SetupClass.getInstance(context);
        vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        player = MediaPlayer.create(context, R.raw.beep);


        plus.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                valueUpdate(1);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentValue>lowerLimit)
                    currentValue--;
                value.setText(String.valueOf(currentValue));
            }
        });

        ayar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,SetupActivity.class);
               startActivity(intent);
           }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupClass.loadValues();
        getPreferences();
    }

    private void getPreferences() {
        currentValue = setupClass.currentValue;
        upperLimit = setupClass.upperLimit;
        lowerLimit = setupClass.lowerLimit;
        upperVib = setupClass.upperVib;
        upperSound = setupClass.upperSound;
        lowerVib = setupClass.lowerVib;
        lowerSound = setupClass.lowerSound;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean dispatchEvent(KeyEvent event){
        int action=event.getAction();
        int keyCode = event.getKeyCode();
        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(action==KeyEvent.ACTION_DOWN)
                    valueUpdate(5);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(action==KeyEvent.ACTION_UP)
                    valueUpdate(-5);
                return true;

        }
        return super.dispatchKeyEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void valueUpdate(int step){
        if(step<0){
            if(currentValue+step<lowerLimit){
                currentValue=lowerLimit;
                player.start();
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else
                currentValue += step;
        } else {
            if(currentValue + step >upperLimit){
                currentValue=upperLimit;
                player.start();
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                currentValue += step;
            }
        }
        value.setText(String.valueOf(currentValue));
    }

}