package co.test.application.menu;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import co.test.application.R;


public class MenuActivity extends AppCompatActivity {

    private RelativeLayout rootView;
    private int orientation;
    private String title="";
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI(checkLandscape());
    }

    private int checkLandscape() {
        if(getWindowManager().getDefaultDisplay().getHeight() <
                getWindowManager().getDefaultDisplay().getWidth())
           return orientation = Configuration.ORIENTATION_LANDSCAPE;
        else
           return orientation = Configuration.ORIENTATION_PORTRAIT;

    }

    private void initUI(int orientation) {
        setContentView(R.layout.activity_menu);
        rootView = findViewById(R.id.rootView);
        videoView = findViewById(R.id.video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageScreen = findViewById(R.id.screen);
        ImageView imageLogo = findViewById(R.id.logo);
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageScreen.setVisibility(View.GONE);
            imageLogo.setVisibility(View.VISIBLE);
        }else{
            imageScreen.setVisibility(View.VISIBLE);
            imageLogo.setVisibility(View.GONE);
        }
        if(getIntent() != null){
            title = getIntent().getStringExtra("Data");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(title);
            }
            //toolbar.setTitle(s);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
         //   super.onBackPressed();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rootView.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);
        findViewById(R.id.bottom_layout).setVisibility(View.VISIBLE);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.thank_you_for_serve_u));
        videoView.requestFocus();
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rootView.removeAllViews();
            initUI(newConfig.orientation);
        }else{
           rootView.removeAllViews();
           initUI(newConfig.orientation);
        }
        super.onConfigurationChanged(newConfig);
    }



}
