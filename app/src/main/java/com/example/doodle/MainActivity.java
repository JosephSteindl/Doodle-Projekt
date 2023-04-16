package com.example.doodle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

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
                new LinearLayout.LayoutParams(230,230);
        FrameLayout layout = findViewById(R.id.myFrame);
        //layout.addView(imageView);

        Random random = new Random();
        int amountPlatforms = 30;
        ArrayList<ImageView> platforms = new ArrayList<>();
        for(int i=0;i<amountPlatforms;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.platform_default);
            imageView.setX(random.nextFloat()*830f);
            imageView.setY(random.nextFloat()*(1760f-(-80f)+(-80f)));//-80 bis 1760
            //Zufallszahl so lange generieren bis kein anderes element im Weg ist!

            imageView.setLayoutParams(layoutParams);
            layout.addView(imageView);

            @SuppressLint("ResourceType") Animation slideDownAnimation = AnimationUtils.loadAnimation(this,R.drawable.slide_down);
            imageView.startAnimation(slideDownAnimation);
            imageView.animate();

        }
    }
}