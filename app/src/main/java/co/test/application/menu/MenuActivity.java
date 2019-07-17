package co.test.application.menu;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Objects;

import co.test.application.R;


public class MenuActivity extends AppCompatActivity {

    private RelativeLayout rootView;
    private int orientation;
    private String title="";
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
        setupEnterWindowAnimations();
        rootView = findViewById(R.id.rootView);
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
            super.onBackPressed();
        setupExitWindowAnimations();

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
}
