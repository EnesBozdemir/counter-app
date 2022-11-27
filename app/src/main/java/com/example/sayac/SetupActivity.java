package com.example.sayac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SetupActivity extends AppCompatActivity {

    int upLimit=20;
    Button uplus, uminus, dplus, dminus;
    EditText upvalue, dvalue;
    CheckBox upVib, upSound, lowVib, lowSound;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        uplus=(Button) findViewById(R.id.up_plus);
        uminus=(Button) findViewById(R.id.up_minus);
        upvalue=(EditText) findViewById(R.id.upperLimit);

        dplus=(Button) findViewById(R.id.low_plus);
        dminus=(Button) findViewById(R.id.low_minus);
        dvalue=(EditText) findViewById(R.id.lowLimit);

        upVib=(CheckBox) findViewById(R.id.up_vib);
        upSound=(CheckBox) findViewById(R.id.up_sound);
        lowVib=(CheckBox) findViewById(R.id.low_vib);
        lowSound=(CheckBox) findViewById(R.id.low_sound);

        Context context=getApplicationContext();
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();

        upvalue.setText(String.valueOf(upLimit));

        uplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLimit++;
                upvalue.setText(String.valueOf(upLimit));
            }
        });

        uminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLimit--;
                upvalue.setText(String.valueOf(upLimit));
            }
        });

        upvalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b){
                upLimit = Integer.valueOf(upvalue.getText().toString());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        upLimit = Integer.valueOf(upvalue.getText().toString());
        editor.putInt("UpperLimit", upLimit);
        editor.commit();
    }
}