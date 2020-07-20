package com.project.dj.ihouseservices;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Splash extends AppCompatActivity {
    CircleImageView logo;
    TextView Mname,subname;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo_img);
        Mname = findViewById(R.id.pro_txt);
        subname = findViewById(R.id.track_txt);


        // Font path
        String fontPath = "font/CircleD_Font_by_CrazyForMusic.ttf";
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        Mname.setTypeface(tf);
        subname.setTypeface(tf);

        if (savedInstanceState == null) {
            flyIn();
        }

        final Thread timmer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {

                    e.printStackTrace();


                } finally {
                    startActivity(new Intent(Splash.this,LoginActivity.class));
                }
            }
        };
        timmer.start();
    }

    private void flyIn() {
        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation);
        Mname.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        subname.startAnimation(animation);
    }

    //when splash activity pauses
    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

}
