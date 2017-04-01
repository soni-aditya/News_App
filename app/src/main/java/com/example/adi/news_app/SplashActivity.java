package com.example.adi.news_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIMER=1500;
    ImageView spalsh_icon;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //Initializing Variables
        spalsh_icon=(ImageView)findViewById(R.id.icon);
        animation= AnimationUtils.loadAnimation(this,R.anim.splash_icon_anim);
        //Initiating Animation
        spalsh_icon.startAnimation(animation);
        //Initiating Splash Intent CountDown
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIMER);
    }
}
