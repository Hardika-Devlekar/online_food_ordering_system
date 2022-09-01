package com.example.sizzlingbites.ui.user.files;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sizzlingbites.R;

public class SplashScreenActivity extends AppCompatActivity {
    //init layout views
    TextView slogan;
    ImageView name;
    RelativeLayout relativeLayout;
    Animation slogananim, layoutanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //init ui views
        name = findViewById(R.id.app_name);
        slogan = findViewById(R.id.slogan);
        relativeLayout = findViewById(R.id.relativeLayout);

        slogananim = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.bottom_to_top);
        layoutanim = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.fall_down);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutanim);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slogan.setVisibility(View.VISIBLE);
                        slogan.setAnimation(slogananim);
                    }
                }, 800);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}