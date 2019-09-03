package com.millertronics.millerapp.millerbcr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OcrMainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button profileListButton;
    private Button cameraReaderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileListButton = (Button) findViewById(R.id.profileListButton);
        cameraReaderButton = (Button) findViewById(R.id.cameraReaderButton);

        profileListButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(OcrMainActivity.this, ProfileListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cameraReaderButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(OcrMainActivity.this, CameraReaderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
