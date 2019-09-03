package co.test.application.selectservice;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import co.test.application.R;
import co.test.application.ocr.OcrActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SelectServiceActivity extends AppCompatActivity implements ButtonClickListener {

    private VideoView mVideoView;
    private BottomSheetDialog bottomSheetDialog;
    private TextView butQuote;
    private TextView butRenewMotor;
    private TextView butMotorClaims;
    private int VIDEO_STATUS = -1;
    private boolean fromMenuPage = false;
    private boolean orientation = false;
    private ConstraintLayout rootView;
    private TextView textUsername;
    private String UserName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras() != null) {
            UserName = getIntent().getStringExtra("Name");
        }
        checkLandscape();
        initUI();

    }

    private void initUI() {
        setContentView(R.layout.activity_select_service);
        rootView = findViewById(R.id.fullscreen_content_controls);
        bottomSheetDialog = new BottomSheetDialog(this);

        mVideoView = findViewById(R.id.videoView);
        textUsername = findViewById(R.id.text_user_name);
        if(textUsername != null && !TextUtils.isEmpty(UserName))
            textUsername.setText(UserName);
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
        initialzeBottomLayout();
        showBottomDialog();
        if(orientation)
            StopPlayVideo(R.raw.get_quate_side_hor,1);
        else
            StopPlayVideo(R.raw.get_quate,1);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                switch (VIDEO_STATUS){
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

    private void showBottomDialog() {
        if(!orientation)
            bottomSheetDialog.show();
    }

    private void initialzeBottomLayout() {
        final View v = LayoutInflater.from(SelectServiceActivity.this).inflate(R.layout.bottom_options,null,false);
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        textUsername = v.findViewById(R.id.text_user_name);
        if(!TextUtils.isEmpty(UserName))
            textUsername.setText(UserName);
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
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        orientation = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        if(rootView != null){
            rootView.removeAllViews();
        }
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
                if(orientation)
                    StopPlayVideo(R.raw.get_quate_side_hor,1);
                else
                    StopPlayVideo(R.raw.get_quate,1);
                break;
            case 2:
                if(orientation)
                    StopPlayVideo(R.raw.get_motor_policy_renewwel_hor,2);
                else
                    StopPlayVideo(R.raw.get_motor_policy,2);
                break;
            case 3:
                if(orientation)
                    StopPlayVideo(R.raw.manage_your_policy_side_hor,3);
                else
                    StopPlayVideo(R.raw.manage_your_policy,3);
        }
    }


    private void moveToNextAct(String s) {
        Intent i = new Intent(this, OcrActivity.class);
        i.putExtra("Data",s);
        startActivity(i);
    }


    private void playVideo(int uri,int status) {
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+uri));
        mVideoView.requestFocus();
        mVideoView.start();
        VIDEO_STATUS = status;
    }

    private void StopPlayVideo(int uri,int status) {
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+uri));
        mVideoView.requestFocus();
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
                if(orientation)
                    playVideo(R.raw.get_quate_side_hor,1);
                else
                    playVideo(R.raw.get_quate,1);
                break;
            case R.id.button2:
                if(orientation)
                    playVideo(R.raw.get_motor_policy_renewwel_hor,2);
                else
                    playVideo(R.raw.get_motor_policy,2);
                break;
            case R.id.button3:
                if(orientation)
                    playVideo(R.raw.manage_your_policy_side_hor,3);
                else
                    playVideo(R.raw.manage_your_policy,3);
                break;
        }
    }

}


