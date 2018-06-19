package com.appdev.lance.meowmixer;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnStartRecord,btnStopRecord,btnPlay,btnStop;
    String pathSave="";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize view
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        btnStartRecord = findViewById(R.id.btnStartRecord);
        btnStopRecord = findViewById(R.id.btnStopRecord);

        if (checkPermissionFromDevice())
        {
            btnStartRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    pathSave = Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/"
                            + UUID.randomUUID().toString()+"_audio_record.3gp";
                    setupMediaRecorder();
                    try
                    {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    btnStartRecord.setEnabled(false);
                    btnStopRecord.setEnabled(true);
                    btnPlay.setEnabled(false);
                    btnStop.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Recording...", Toast.LENGTH_SHORT).show();

                }

            });
            btnStopRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaRecorder.stop();
                    btnStartRecord.setEnabled(true);
                    btnStopRecord.setEnabled(false);
                    btnPlay.setEnabled(true);
                    btnStop.setEnabled(false);
                }
            });

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStartRecord.setEnabled(false);
                    btnStopRecord.setEnabled(false);
                    btnPlay.setEnabled(false);
                    btnStop.setEnabled(true);

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            btnStartRecord.setEnabled(true);
                            btnStopRecord.setEnabled(false);
                            btnPlay.setEnabled(true);
                            btnStop.setEnabled(false);
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                    });
                    try
                    {
                        mediaPlayer.setDataSource(pathSave);
                        mediaPlayer.prepare();

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "Playing...", Toast.LENGTH_SHORT).show();
                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStop.setEnabled(false);
                    btnPlay.setEnabled(true);
                    btnStartRecord.setEnabled(true);
                    btnStopRecord.setEnabled(false);

                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        setupMediaRecorder();
                    }
                }
            });
        }
        else
        {
            requestPermission();
        }
    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }

    private boolean checkPermissionFromDevice()
    {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}
