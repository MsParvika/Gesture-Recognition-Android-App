package com.example.gesturerecognition1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

public class SecondActivity extends AppCompatActivity {

    String path = "";
    VideoView viedeoPlaceHolder;
    Button practice;
    String gestureName;
    Intent secondIntent;
    long startTime = 0;
    FileOutputStream fo ;
    File logFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if (getIntent().hasExtra("gestureName")) {
            gestureName = getIntent().getStringExtra("gestureName");
        }
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
                startCamera();
                /*secondIntent = new Intent(SecondActivity.this, ThirdActivity.class);
                secondIntent.putExtra("gestureName", gestureName);
                startActivity(secondIntent);*/

            }
        });

    }

    public void startCamera() {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //permission not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                //doing nothing
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        101);
            }
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // Permission not granted

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {*/
            // Permission  granted
            File f = new File(Environment.getExternalStorageDirectory(), "GestureRecognition");

            if (!f.exists()) {
                f.mkdirs();
            }

            startTime = System.currentTimeMillis() - startTime;

            Intent t = new Intent(this, ThirdActivity.class);
            t.putExtra("gestureName", gestureName);
            t.putExtra("timeElapsed", startTime);


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

            startActivity(t);

       // }


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

}
