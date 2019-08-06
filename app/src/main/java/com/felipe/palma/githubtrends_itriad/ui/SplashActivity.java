package com.felipe.palma.githubtrends_itriad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

;import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.utils.Config;

/**
 * Created by Felipe Palma on 05/08/2019.
 */

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);



        progressBar = findViewById(R.id.progressBar);

        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();

        }, Config.SPLASH_TIME);

    }

}