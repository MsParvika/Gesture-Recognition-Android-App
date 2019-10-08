package com.example.gesturerecognition1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FirstActivity extends AppCompatActivity {

    public static final String[] GESTURES = {"Select One...", "buy", "house", "fun", "hope", "arrive", "really", "read", "lip", "mouth", "some", "communicate", "write", "create", "pretend", "sister", "man", "one", "drive", "perfect", "mother"};
    Spinner gestureDropDown;
    String dropDownValue;
    Intent intent;
    EditText asuId;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Constants.APP_NAME);
        gestureDropDown = (Spinner) findViewById(R.id.gestureDropDown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FirstActivity.this, android.R.layout.simple_list_item_1, GESTURES);
        this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        asuId = (EditText) findViewById(R.id.asu_id);
        sp =  this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        if(getIntent().getStringExtra(Constants.ASU_ID) != null){
            asuId.setText(getIntent().getStringExtra(Constants.ASU_ID));
        }

        gestureDropDown.setAdapter(adapter);
        gestureDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                dropDownValue = (String) gestureDropDown.getSelectedItem();
                if(!dropDownValue.equalsIgnoreCase("Select One...")) {
                    intent = new Intent(FirstActivity.this, SecondActivity.class);
                    intent.putExtra(Constants.GESTURE_NAME, dropDownValue);

                    if(!asuId.getText().toString().equals("") || asuId.getText() != null){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(Constants.ASU_ID, asuId.getText().toString());
                        editor.commit();
                    }

                    intent.putExtra(Constants.ASU_ID, sp.getString(Constants.ASU_ID, ""));
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    protected void onRestart() {
        if(getIntent().getStringExtra(Constants.ASU_ID) != null){
            asuId.setText(sp.getString(Constants.ASU_ID, ""));
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if(getIntent().getStringExtra(Constants.ASU_ID) != null){
            asuId.setText(sp.getString(Constants.ASU_ID, ""));
        }
        super.onResume();
    }
}
