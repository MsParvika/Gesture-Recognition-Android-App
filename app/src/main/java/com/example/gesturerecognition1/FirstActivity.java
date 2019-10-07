package com.example.gesturerecognition1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FirstActivity extends AppCompatActivity {

    public static final String[] GESTURES = {"Select One...", "buy", "house", "fun", "hope", "arrive", "really", "read", "lip", "mouth", "some", "communicate", "write", "create", "pretend", "sister", "man", "one", "drive", "perfect", "mother"};
    Spinner gestureDropDown;
    String dropDownValue;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestureDropDown = (Spinner) findViewById(R.id.gestureDropDown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FirstActivity.this, android.R.layout.simple_list_item_1, GESTURES);

        gestureDropDown.setAdapter(adapter);
        gestureDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                dropDownValue = (String) gestureDropDown.getSelectedItem();
                if(!dropDownValue.equalsIgnoreCase("Select One...")) {
                    intent = new Intent(FirstActivity.this, SecondActivity.class);
                    intent.putExtra("gestureName", dropDownValue);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
