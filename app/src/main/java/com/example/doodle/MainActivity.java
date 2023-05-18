package com.example.doodle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Platform> platforms = new ArrayList<>();
    private Wanker wanker;
    private int count = 0;
    private float width;
    private float height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button start_btn = findViewById(R.id.start_btn);
        start_btn.setOnClickListener(v-> generatePlatforms());

        //Wanker erstellen
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(140,ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout layout = findViewById(R.id.myFrame);
        this.wanker = new Wanker(this);
        this.wanker.setImageResource(R.drawable.man_default);
        this.wanker.setLayoutParams(layoutParams);
        layout.addView(this.wanker);
        this.wanker.setMyHeight(290f*0.9210526315789f +180);
        this.wanker.setMyWidth(140-75);

        DisplayMetrics dM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dM);
        this.width = dM.widthPixels;
        this.height = dM.heightPixels;
        System.out.println("Width:"+width);
        System.out.println("height:"+height);
        this.wanker.setX(width/2 - this.wanker.getWidth() -75);
        //breite /2 - breiteWanker/2
        System.out.println("Wanker höhe:"+this.wanker.getMyHeight());
        this.wanker.setY(height-this.wanker.getMyHeight()-800);
        this.wanker.setStartPosition(this.wanker.getY());
        this.wanker.setMainActivity(this);
        this.wanker.setDefaultTopY(this.wanker.getY()-1000);
        this.wanker.setDefaultBottomY(2500);
        this.wanker.setScreenHeight(height);

        //meineTestFunction();
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        float x = event.getX(); // X-Koordinate des Touch-Ereignisses
                        float y = event.getY(); // Y-Koordinate des Touch-Ereignisses
                        // Weitere Verarbeitung der Koordinaten
                        System.out.println("geklickt:"+event.getX());
                        wanker.setX(event.getX());
                        return true;
                }
                return false;
            }
        });
        this.wanker.setScoreDisplay(findViewById(R.id.score));

    }
    public void meineTestFunction(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(140,ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout layout = findViewById(R.id.myFrame);
        Wanker test = new Wanker(this);
        test.setImageResource(R.drawable.man_default);
        test.setLayoutParams(layoutParams);
        layout.addView(test);
        test.setMyHeight(290f*0.9210526315789f + 180);
        test.setMyWidth(140-75);
        test.setX(300);
        test.setY(400);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(230,ViewGroup.LayoutParams.WRAP_CONTENT);
        Platform imageView = new Platform(this,this.platforms);
        imageView.setImageResource(R.drawable.platform_default);
        imageView.setLayoutParams(layoutParams1);
        imageView.setMyWidth(230-50);
        imageView.setMyHeight((float)(71*0.9583333));
        layout.addView(imageView);
        imageView.setX(300-imageView.getMyWidth());
        imageView.setY(400f+test.getMyHeight());
    }
    public void generatePlatforms(){
        final Button start_btn = findViewById(R.id.start_btn);
        start_btn.setText("Nochmal");
        start_btn.setVisibility(View.INVISIBLE);
        //start_btn.setVisibility(View.GONE);
        //start_btn.setOnClickListener(v-> weiterGenerieren());


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(230,ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout layout = findViewById(R.id.myFrame);
        //layout.addView(imageView);

        Random random = new Random();
        int amountPlatforms = 20;//20 Änderung

        for(int i=0;i<amountPlatforms;i++){
            Platform imageView = new Platform(this,this.platforms);
            imageView.setMainActivity(this);
            platforms.add(imageView);
            imageView.setImageResource(R.drawable.platform_default);
            imageView.setLayoutParams(layoutParams);
            imageView.setMyWidth(230-50);
            imageView.setMyHeight((float)(71*0.9583333));
            imageView.setScreenHeight(this.height);

            layout.addView(imageView);
            //System.out.println(imageView.hei);

            //imageView.setX(random.nextFloat()*830f);

            //float startY = random.nextFloat()*(1760f-(-80f)+(-80f));
            //imageView.setY(startY);//-80 bis 1760

            imageView.setRandomPosition(platforms,1760f,-1680f); //Änderung -> zeile wieder gültig machen und unteren 2 zeilen wegmachen
            //imageView.setX(400);
            //imageView.setY(200);
            //imageView.setY(-80f);//-80 bis 1760
            imageView.setStartPosition(imageView.getY());
            //imageView.setStartPosition(-80f);
            //Zufallszahl so lange generieren bis kein anderes element im Weg ist!

            //imageView.createAndStartAnimation();
        }
        weiterGenerieren();
    }
    private WankerThread wankerThread;
    public void weiterGenerieren(){
        /*this.wankerThread = new WankerThread(this.wanker);
        this.wankerThread.start();*/

        //System.out.println("Generiere weiter...");
        float yOberstes = 0;
        for(int i=0;i<platforms.size();i++){
            if(platforms.get(i).getY()<yOberstes){
                yOberstes = platforms.get(i).getY();
            }
        }
        //System.out.println("Oberstes Y:"+yOberstes);

        LinearLayout.LayoutParams layoutParams =
                //new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //new LinearLayout.LayoutParams(230,130);//230 230
                new LinearLayout.LayoutParams(230,ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout layout = findViewById(R.id.myFrame);
        //layout.addView(imageView);

        Random random = new Random();
        int amountPlatforms = 5;//10 Änderung
        if(this.platforms.size()>100)amountPlatforms = 0;

        for(int i=0;i<amountPlatforms;i++){
            Platform imageView = new Platform(this,this.platforms);
            imageView.setMainActivity(this);
            platforms.add(imageView);
            imageView.setImageResource(R.drawable.platform_default);
            imageView.setLayoutParams(layoutParams);
            imageView.setMyWidth(230-50);
            imageView.setMyHeight((float)(71*0.9583333));
            imageView.setScreenHeight(this.height);
            layout.addView(imageView);

            imageView.setRandomPosition(platforms,yOberstes-1760,yOberstes);
            imageView.setStartPosition(imageView.getY());
        }
        this.wanker.createAndStartAnimation();
        for(int i=0;i<platforms.size();i++){
            platforms.get(i).createAndStartAnimation();
        }
        //System.out.println("Es gibt jetzt "+platforms.size()+" Plattformen");
    }
    public void prepareForRestart(){
        Button start_btn = findViewById(R.id.start_btn);
        start_btn.setVisibility(View.VISIBLE);

        for(int i=0;i<this.platforms.size();i++){
            this.platforms.get(i).setVisibility(View.INVISIBLE);
        }
        //this.platforms.clear();
        start_btn.setOnClickListener(v->generatePlatforms());
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}