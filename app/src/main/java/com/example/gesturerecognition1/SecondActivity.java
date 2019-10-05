package com.example.gesturerecognition1;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class SecondActivity extends AppCompatActivity {

    String path;
    VideoView viedeoPlaceHolder;
    Button practice;
    String gestureName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if(getIntent().hasExtra("gestureName")) {
            gestureName = getIntent().getStringExtra("gestureName");
        }
        viedeoPlaceHolder = (VideoView) findViewById(R.id.signVideo);
        practice = (Button) findViewById(R.id.practice);
        startVideo(gestureName);
        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.start();

                }

            }
        };
        viedeoPlaceHolder.setOnCompletionListener(onCompletionListener);
        viedeoPlaceHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!viedeoPlaceHolder.isPlaying()) {
                    viedeoPlaceHolder.start();
                }
            }
        });
    }

    //  "buy", " house", " fun", " hope", " arrive", " really", " read",
    // " lip", " mouth", " some", " communicate", " write", " create", 
    // " pretend", " sister", " man", " one", " drive", " perfect", " mother"};

    public void startVideo(String text) {
        if(text.equalsIgnoreCase("buy")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.buy;
        } else if(text.equalsIgnoreCase("house")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.house;
        } else if (text.equalsIgnoreCase("fun")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.fun;
        }else if (text.equalsIgnoreCase("hope")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.hope;
        }else if (text.equalsIgnoreCase("arrive")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.arrive;
        }else if (text.equalsIgnoreCase("really")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.really;
        }else if (text.equalsIgnoreCase("read")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.read;
        }else if (text.equalsIgnoreCase("lip")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.lip;
        }else if (text.equalsIgnoreCase("mouth")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.mouth;
        }else if (text.equalsIgnoreCase("communicate")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.comm;
        }else if (text.equalsIgnoreCase("some")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.some;
        }else if (text.equalsIgnoreCase("write")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.write;
        }else if (text.equalsIgnoreCase("create")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.create;
        }else if (text.equalsIgnoreCase("pretend")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.pretend;
        }else if (text.equalsIgnoreCase("sister")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.sister;
        }else if (text.equalsIgnoreCase("man")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.man;
        }else if (text.equalsIgnoreCase("one")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.one;
        }else if (text.equalsIgnoreCase("drive")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.drive;
        }else if (text.equalsIgnoreCase("perfect")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.perfect;
        }else if (text.equalsIgnoreCase("mother")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.mother;
        }
        if(!path.isEmpty()) {
            Uri uri = Uri.parse(path);
            viedeoPlaceHolder.setVideoURI(uri);
            viedeoPlaceHolder.start();
        }

    }

}
