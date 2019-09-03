package co.test.application.welcome;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import net.simplifiedcoding.speechtotext.MainActivity;

import java.util.ArrayList;

import co.test.application.R;
import co.test.application.selectservice.*;
public class WelcomeActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private String UserName="";
    private final int REQ_CODE_FOR_SPEECH_INPUT = 100;
    private  int VIDEO_STATUS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mVideoView = (VideoView)findViewById(R.id.videoView);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switch (VIDEO_STATUS) {
                    case 1:
                        playVideo(R.raw.whats_your_name, 2);
                        break;
                    case 2:
                        promtSpeechInput();
                        break;
                }
            }
        });
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                promtSpeechInput();
                return false;
            }
        });
        playVideo(R.raw.welcome_to_qic,1);

    }

    private void playVideo(int uri,int status) {
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+uri));
        mVideoView.requestFocus();
        mVideoView.start();
        VIDEO_STATUS = status;
    }


    private void promtSpeechInput() {
           Intent i = new Intent(this,MainActivity.class);
           startActivity(i);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getExtras() != null){
            UserName = intent.getExtras().getString("name");
            Intent i = new Intent(WelcomeActivity.this,
                    SelectServiceActivity.class);
            i.putExtra("Name",UserName);
            startActivity(i);
            finish();
        }
    }
}
