package com.example.gesturerecognition1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ThirdActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private MediaRecorder recorder;
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Button mToggleButton;
    private TextView tv_timer;
    private TextView tv_time;
    private SharedPreferences sharedPreferences;
    private String gestureName;
    private String filePath;
    private File file;
    CountDownTimer countDownTimer;
    CountDownTimer recordingTime;
    boolean recording = false;
    private boolean isInit = false;
    boolean newFile = false;
    VideoView recordedVideoView;

    private Button uploadButton;
    private Button discardButton;

    private RelativeLayout recordingDecisionLayout;
    private FrameLayout cameraFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_third);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Record Gesture");

        recordingDecisionLayout = (RelativeLayout) findViewById(R.id.recording_decision_layout);
        cameraFrameLayout = (FrameLayout) findViewById(R.id.camera_frame);
        recordingDecisionLayout.setVisibility(View.GONE);
        recordedVideoView = (VideoView) findViewById(R.id.recordedVideo);

        uploadButton = (Button) findViewById(R.id.upload_button);
        discardButton = (Button) findViewById(R.id.discard_button);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadVideo uploadVideo = new UploadVideo();
                uploadVideo.upLoad2Server(file.getPath());
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        if(getIntent().hasExtra(Constants.GESTURE_NAME)) {
            gestureName = getIntent().getStringExtra(Constants.GESTURE_NAME);
        }

        surfaceView = (SurfaceView) findViewById(R.id.camera);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        tv_timer = (TextView) findViewById(R.id.tv_timer);
        tv_time = (TextView) findViewById(R.id.tv_time);
        mToggleButton = (Button) findViewById(R.id.bt_start);
        sharedPreferences =  this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        recordingTime = getRecordingCountDown();

        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // toggle video recording
            public void onClick(final View v) {

                countDownTimer = getCountDownTimer(v);

                if (((Button) v).getText().toString().equals("Start Recording")) {
                    countDownTimer.start();
                }
            }
        });

    }

    private void initRecorder(Surface surface) throws IOException{

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
        recorder.setPreviewDisplay(surface);
        recorder.setCamera(camera);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setMaxDuration(5000); // 5 seconds
        recorder.setOrientationHint(270);
        recorder.setVideoFrameRate(30);
        recorder.setVideoEncodingBitRate(3000000);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

        int i=0;
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.US);
        String format = s.format(new Date());

        file = new File(getExternalFilesDir(null).getPath() + "/" + Constants.APP_NAME + "/"
                + sharedPreferences.getString(Constants.RECORDING_ID,"0000") + "_" + gestureName + "_0_" + format  + ".mp4");

        while(file.exists()) {
            i++;
            file = new File(getExternalFilesDir(null).getPath() + "/" + Constants.APP_NAME + "/"
                    + sharedPreferences.getString(Constants.RECORDING_ID,"0000") + "_" + gestureName + "_" + i + "_" + format + ".mp4");
        }

        if(file.createNewFile()) {
            newFile = true;
            filePath = file.getPath();
        }

        recorder.setOutputFile(file.getPath());
        final File finalFile = file;
        recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
                    recorder.stop();
                    recorder.reset();
                    cameraFrameLayout.setVisibility(View.GONE);
                    recordingDecisionLayout.setVisibility(View.VISIBLE);
                    recordedVideoView.setVideoURI(Uri.fromFile(new File(finalFile.getPath())));
                    recordedVideoView.start();
                }
            }
        });

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        isInit = true;

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if(!isInit)
                initRecorder(surfaceHolder.getSurface());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private CountDownTimer getCountDownTimer(final View v){
        return new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                int a = (int) (millisUntilFinished / 1000);
                tv_timer.setText(a + " ");
                ((Button) v).setEnabled(false);
            }

            public void onFinish() {
                tv_timer.setVisibility(View.GONE);
                ((Button) v).setText("Recording");
                ((Button) v).setVisibility(View.INVISIBLE);
                recorder.start();
                recordingTime.start();
            }
        };
    }

    private CountDownTimer getRecordingCountDown(){
        return new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                int a = (int) (l / 1000);
                tv_time.setText(a + " sec left");
            }

            @Override
            public void onFinish() {
                if(recordingTime != null) {
                    recordingTime.cancel();
                }
            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
