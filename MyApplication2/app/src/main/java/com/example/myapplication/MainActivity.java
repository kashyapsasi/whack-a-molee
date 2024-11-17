package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private GridLayout gridLayout;
    private int score = 0;
    private Random random = new Random();
    private Handler handler = new Handler();
    private Runnable moleRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);
        gridLayout = findViewById(R.id.gridLayout);

        // Set onClickListeners for each button
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setVisibility(View.INVISIBLE);
            button.setOnClickListener(v -> {
                score++;
                scoreTextView.setText("Score: " + score);
                v.setBackgroundResource(R.drawable.hammer); // Replace mole with hammer on click
                handler.postDelayed(() -> v.setVisibility(View.INVISIBLE), 500); // Hide after 0.5s
            });
        }

        // Start the mole appearance
        moleRunnable = new Runnable() {
            @Override
            public void run() {
                showRandomMole();
                handler.postDelayed(this, 1000); // Repeat every second
            }
        };
        handler.post(moleRunnable);
    }

    private void showRandomMole() {
        // Hide all moles first
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setVisibility(View.INVISIBLE);
            button.setBackgroundResource(R.drawable.button_background); // Reset to mole image
        }

        // Randomly choose a button to appear
        int moleIndex = random.nextInt(gridLayout.getChildCount());
        gridLayout.getChildAt(moleIndex).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(moleRunnable); // Stop the mole appearance updates when activity is destroyed
    }
}
