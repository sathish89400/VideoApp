package co.test.application.splash;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;

import co.test.application.R;
import co.test.application.welcome.WelcomeActivity;

 public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);
                setupWindowAnimations();
                finish();
            }
        },2500);
    }

     @TargetApi(Build.VERSION_CODES.LOLLIPOP)
     private void setupWindowAnimations() {
         Slide slide = new Slide();
         slide.setDuration(1500);
         getWindow().setExitTransition(slide);
     }
}
