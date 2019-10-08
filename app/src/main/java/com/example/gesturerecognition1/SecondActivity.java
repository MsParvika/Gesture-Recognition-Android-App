package com.example.gesturerecognition1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    String path = "";
    VideoView viedeoPlaceHolder;
    Button practice;
    String gestureName;
    String asuId;
    Intent secondIntent;
    long startTime = 0;
    FileOutputStream fo;
    File logFile;
    private SharedPreferences sp;

    boolean grantedPermissions = false;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET};
    public static final int MULTIPLE_PERMISSIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Practice Gesture");
        if (getIntent().hasExtra(Constants.GESTURE_NAME)) {
            gestureName = getIntent().getStringExtra(Constants.GESTURE_NAME);
            asuId = getIntent().getStringExtra(Constants.ASU_ID);
        }

        sp = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        viedeoPlaceHolder = (VideoView) findViewById(R.id.signVideo);
        practice = (Button) findViewById(R.id.practice);
        startVideo(gestureName);
        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }

            }
        };
        viedeoPlaceHolder.setOnCompletionListener(onCompletionListener);
        viedeoPlaceHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!viedeoPlaceHolder.isPlaying()) {
                    viedeoPlaceHolder.start();
                }
            }
        });

        practice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkPermissions()){
                    startRecording();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                        }else{
                            grantedPermissions = true;
                        }
                    }
                }
                if(grantedPermissions){
                    startRecording();
                }
            }
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void startRecording() {

        File f = new File(getExternalFilesDir(null).getPath() + "/" + Constants.APP_NAME + "/");

        if (!f.exists()) {
            f.mkdirs();
        }

//        startTime = System.currentTimeMillis() - startTime;

        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra(Constants.GESTURE_NAME, gestureName);
        intent.putExtra(Constants.ASU_ID, sp.getString(Constants.ASU_ID, ""));
//        intent.putExtra("timeElapsed", startTime);


        // TODO : Check this part
           /* final String id = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE).getString(INTENT_ID, "00000000");
            try {
                fo = new FileOutputStream(logFile, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fo);
                outputStreamWriter.append("User with ID " + id + " recording video for sign " + gestureName + "\n");
                outputStreamWriter.close();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }*/

        // TODO : TIll HERE

        startActivity(intent);
    }


    //  "buy", " house", " fun", " hope", " arrive", " really", " read",
    // " lip", " mouth", " some", " communicate", " write", " create", 
    // " pretend", " sister", " man", " one", " drive", " perfect", " mother"};

    public void startVideo(String text) {
        if (text.equalsIgnoreCase("buy")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.buy;
        } else if (text.equalsIgnoreCase("house")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.house;
        } else if (text.equalsIgnoreCase("fun")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.fun;
        } else if (text.equalsIgnoreCase("hope")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.hope;
        } else if (text.equalsIgnoreCase("arrive")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.arrive;
        } else if (text.equalsIgnoreCase("really")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.really;
        } else if (text.equalsIgnoreCase("read")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.read;
        } else if (text.equalsIgnoreCase("lip")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.lip;
        } else if (text.equalsIgnoreCase("mouth")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.mouth;
        } else if (text.equalsIgnoreCase("communicate")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.comm;
        } else if (text.equalsIgnoreCase("some")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.some;
        } else if (text.equalsIgnoreCase("write")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.write;
        } else if (text.equalsIgnoreCase("create")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.create;
        } else if (text.equalsIgnoreCase("pretend")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.pretend;
        } else if (text.equalsIgnoreCase("sister")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.sister;
        } else if (text.equalsIgnoreCase("man")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.man;
        } else if (text.equalsIgnoreCase("one")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.one;
        } else if (text.equalsIgnoreCase("drive")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.drive;
        } else if (text.equalsIgnoreCase("perfect")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.perfect;
        } else if (text.equalsIgnoreCase("mother")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.mother;
        }
        if (!path.isEmpty()) {
            Uri uri = Uri.parse(path);
            viedeoPlaceHolder.setVideoURI(uri);
            viedeoPlaceHolder.start();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onRestart() {
        if (!viedeoPlaceHolder.isPlaying()) {
            viedeoPlaceHolder.start();
        }
        super.onRestart();
    }
}
