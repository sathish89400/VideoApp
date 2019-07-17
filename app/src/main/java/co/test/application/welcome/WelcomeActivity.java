package co.test.application.welcome;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import co.test.application.R;
import co.test.application.menu.MenuActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity implements ButtonClickListener {

    private VideoView mVideoView;
    private BottomSheetDialog bottomSheetDialog;
    private Button butQuote;
    private Button butRenewMotor;
    private Button butMotorClaims;
    private int VIDEO_STATUS = -1;
    private boolean fromMenuPage = false;
    private boolean orientation = false;
    private RelativeLayout rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        setupEnterWindowAnimations();
        checkLandscape();
        rootView = findViewById(R.id.fullscreen_content_controls);
        bottomSheetDialog = new BottomSheetDialog(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_welcome);
        mVideoView = findViewById(R.id.videoView);
        butQuote = findViewById(R.id.button1);
        butQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
        butRenewMotor = findViewById(R.id.button2);
        butRenewMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
        butMotorClaims = findViewById(R.id.button3);
        butMotorClaims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() ==  MotionEvent.ACTION_DOWN && !orientation){
                    if(bottomSheetDialog.isShowing()){
                        bottomSheetDialog.dismiss();
                    }else{
                        bottomSheetDialog.show();
                    }
                }
                return false;
            }
        });
        playVideo(R.raw.welcome_to_qic,0);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switch (VIDEO_STATUS){
                    case 0:
                        final View v = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.bottom_options,null,false);
                        bottomSheetDialog.setContentView(v);
                        butQuote = v.findViewById(R.id.button1);
                        butQuote.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onButtonClick(view);
                            }
                        });
                        butRenewMotor = v.findViewById(R.id.button2);
                        butRenewMotor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onButtonClick(view);
                            }
                        });
                        butMotorClaims = v.findViewById(R.id.button3);
                        butMotorClaims.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onButtonClick(view);
                            }
                        });
                        if(!orientation)
                            bottomSheetDialog.show();
                        break;
                    case 1:
                        navigateToMenuAct(getString(R.string.get_quote));
                        break;
                    case 2:
                        navigateToMenuAct(getString(R.string.motor_policy));
                        break;
                    case 3:
                        navigateToMenuAct(getString(R.string.manage_motor_policy));
                        break;
                }
            }
        });
    }

    private void checkLandscape() {
        orientation = getWindowManager().getDefaultDisplay().getHeight() <
                getWindowManager().getDefaultDisplay().getWidth();
    }

    private void navigateToMenuAct(String str) {
        if(fromMenuPage && !orientation){
            bottomSheetDialog.show();
            fromMenuPage=false;
        }else{
            moveToNextAct(str);
            setupExitWindowAnimations();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        orientation = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        rootView.removeAllViews();
        super.onConfigurationChanged(newConfig);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkLandscape();
        fromMenuPage = true;

        switch (VIDEO_STATUS){
            case 1:
                StopPlayVideo(R.raw.get_quate,1);
                break;
            case 2:
                StopPlayVideo(R.raw.get_motor_policy,2);
                break;
            case 3:
                StopPlayVideo(R.raw.manage_your_policy,3);
        }
    }


    private void moveToNextAct(String s) {
        Intent i = new Intent(this, MenuActivity.class);
        i.putExtra("Data",s);
        startActivity(i);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1500);
        getWindow().setExitTransition(slide);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1500);
        getWindow().setEnterTransition(fade);
    }


    private void playVideo(int uri,int status) {
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+uri));
        mVideoView.requestFocus();
        mVideoView.start();
        VIDEO_STATUS = status;
    }

    private void StopPlayVideo(int uri,int status) {
        Uri videoURI = Uri.parse("android.resource://"+getPackageName()+"/"+uri);
        mVideoView.setVideoURI(videoURI);
        mVideoView.seekTo(1);
        if(!orientation)
        bottomSheetDialog.show();
        VIDEO_STATUS = status;
        fromMenuPage = false;
    }


    @Override
    public void onButtonClick(View view) {
        bottomSheetDialog.dismiss();
        switch (view.getId()){
            case R.id.button1:
                playVideo(R.raw.get_quate,1);
                break;
            case R.id.button2:
                playVideo(R.raw.get_motor_policy,2);
                break;
            case R.id.button3:
                playVideo(R.raw.manage_your_policy,3);
                break;
        }
    }



}


