package com.example.doodle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Platform> platforms = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button start_btn = findViewById(R.id.start_btn);
        start_btn.setOnClickListener(v-> generatePlatforms());
    }
    public void generatePlatforms(){
        final Button start_btn = findViewById(R.id.start_btn);
        start_btn.setVisibility(View.GONE);


        LinearLayout.LayoutParams layoutParams =
                //new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //new LinearLayout.LayoutParams(230,130);//230 230
                new LinearLayout.LayoutParams(230,ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout layout = findViewById(R.id.myFrame);
        //layout.addView(imageView);

        Random random = new Random();
        int amountPlatforms = 30;

        for(int i=0;i<amountPlatforms;i++){
            Platform imageView = new Platform(this);
            platforms.add(imageView);
            imageView.setImageResource(R.drawable.platform_default);
            imageView.setLayoutParams(layoutParams);
            imageView.setMyWidth(230);
            imageView.setMyHeight((float)(71*0.9583333));


            layout.addView(imageView);
            //System.out.println(imageView.hei);

            //imageView.setX(random.nextFloat()*830f);

            //float startY = random.nextFloat()*(1760f-(-80f)+(-80f));
            //imageView.setY(startY);//-80 bis 1760

            imageView.setRandomPosition(platforms);
            //imageView.setY(-80f);//-80 bis 1760
            imageView.setStartPosition(imageView.getY());
            //imageView.setStartPosition(-80f);
            //Zufallszahl so lange generieren bis kein anderes element im Weg ist!




            //imageView.createAndStartAnimation();


        }


    }
    private void meineMethode(){
        System.out.println("Nachher:"+platforms.get(0).getX());
    }
}