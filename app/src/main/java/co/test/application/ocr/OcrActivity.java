package co.test.application.ocr;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.millertronics.millerapp.millerbcr.CameraReaderActivity;
import com.millertronics.millerapp.millerbcr.OcrMainActivity;

import co.test.application.menu.MenuActivity;
import co.test.application.R;

public class OcrActivity extends AppCompatActivity {

    private boolean orientation;
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        mVideoView = findViewById(R.id.ocr_videoView);
        checkLandscape();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
               Intent i = new Intent(OcrActivity.this, CameraReaderActivity.class);
               startActivity(i);
               finish();
            }
        });
    }

    private void playVideo(int uri) {
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+uri));
        mVideoView.requestFocus();
        mVideoView.start();
    }

    private void checkLandscape() {
        orientation = getWindowManager().getDefaultDisplay().getHeight() <
                getWindowManager().getDefaultDisplay().getWidth();
        if(!orientation){
            playVideo(R.raw.kindly_show_your_id);
        }else{
            playVideo(R.raw.kindly_show_yourid_hor);
        }
    }

    public void moveToNextAct(){
        Intent i = new Intent(OcrActivity.this,MenuActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkLandscape();

    }
}
