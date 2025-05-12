package com.yourcompany.canvasanimation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private CanvasAnimationView animationView;
    private MaterialButton btnStart, btnPause, btnResume, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.animationView);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnResume = findViewById(R.id.btnResume);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener(v -> animationView.startAnimation());
        btnPause.setOnClickListener(v -> animationView.pauseAnimation());
        btnResume.setOnClickListener(v -> animationView.resumeAnimation());
        btnReset.setOnClickListener(v -> animationView.resetAnimation());
    }

    @Override
    protected void onPause() {
        super.onPause();
        animationView.pauseAnimation(); // pause if activity goes to background
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Optionally resume animation here or let the user re-initiate
    }
}
