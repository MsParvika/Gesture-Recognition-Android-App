package com.example.gesturerecognition1;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class ThirdActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private MediaRecorder recorder;
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Button mToggleButton;
    private TextView tv_timer;
    private TextView tv_time;
    boolean recording = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        recorder = new MediaRecorder();
        initRecorder();
        setContentView(R.layout.activity_third);

        surfaceView = (SurfaceView) findViewById(R.id.camera);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        tv_timer = (TextView) findViewById(R.id.timer);
        //tv_time = (TextView) findViewById(R.id.tv_time);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.setClickable(true);
        surfaceView.setOnClickListener(this);

    }

    private void initRecorder() {
        if(camera == null) {
            camera = Camera.open(1);
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
            camera.unlock();
        }
        if(recorder == null)
            recorder = new MediaRecorder();
        recorder.setCamera(camera);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

        CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);

        // TODO : File name using ID
/*        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/GestureRecognition/"
                + sharedPreferences.getString(INTENT_ID,"0000")+"_"+word+"_0_"+format  + ".mp4");
        //just to be safe
        while(file.exists()) {
            i++;
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign/"
                    + sharedPreferences.getString(INTENT_ID,"0000")+"_"+word+"_"+i +"_"+format+ ".mp4");
        }*/
        // TODO : Till here

        recorder.setOutputFile("/sdcard/videocapture_example.mp4");
        recorder.setMaxDuration(5000); // 50 seconds
        recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
    }

    public void onClick(View v) {
        if (recording) {
            recorder.stop();
            recording = false;

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        } else {
            recording = true;
            recorder.start();
        }
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(surfaceHolder.getSurface());

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            recorder.stop();
            recording = false;
        }
        recorder.release();
        finish();
    }
}
