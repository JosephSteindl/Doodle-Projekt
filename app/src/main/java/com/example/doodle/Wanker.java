package com.example.doodle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Wanker extends androidx.appcompat.widget.AppCompatImageView{
    private float myHeight;
    private float myWidth;
    private float startPosition;
    private MainActivity mainActivity;
    private boolean fertig = true;
    private float animateDistance = 1000;
    private float defaultTopY;
    private float defaultBottomY;
    private float screenHeight;
    private int jumps = 0;
    private TextView scoreDisplay;

    public Wanker(@NonNull Context context) {
        super(context);
    }

    public Wanker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Wanker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public TextView getScoreDisplay() {
        return scoreDisplay;
    }

    public void setScoreDisplay(TextView scoreDisplay) {
        this.scoreDisplay = scoreDisplay;
    }

    public int getJumps() {
        return jumps;
    }

    public void setJumps(int jumps) {
        this.jumps = jumps;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setDefaultTopY(float defaultTopY) {
        this.defaultTopY = defaultTopY;
    }

    public void setDefaultBottomY(float defaultBottomY) {
        this.defaultBottomY = defaultBottomY;
    }

    public boolean isFertig() {
        return fertig;
    }
    public void setFertig(boolean fertig) {
        this.fertig = fertig;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setMyHeight(float myHeight) {
        this.myHeight = myHeight;
    }

    public void setMyWidth(float myWidth) {
        this.myWidth = myWidth;
    }

    public float getMyHeight() {
        return myHeight;
    }

    public float getMyWidth() {
        return myWidth;
    }

    public float getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(float startPosition) {
        this.startPosition = startPosition;
    }
    public float getAnimateDistance() {
        return animateDistance;
    }

    public void createAndStartAnimation(){
        Wanker me = this;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        this.animateDistance = startPosition-defaultTopY;
        //System.out.println("animatedistance:"+this.animateDistance);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        this.fertig = false;

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                //System.out.println("Animiere:"+counter++);
                float progress = (float) animation.getAnimatedValue();
                float startY = me.getY();
                float endY = startPosition-animateDistance;
                float currentY = startY + (endY - startY) * progress;
                me.setY(currentY);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                //System.out.println("Vorher:"+me.getY());
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation beendet
                float endY = startPosition-animateDistance;
                me.setStartPosition(startPosition-animateDistance);
                me.setY(endY);
                me.createAndAnimateDown();
            }
            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }
            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });

        animator.start();
    }
    public void createAndAnimateDown() {
        Wanker me = this;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(6 * 1000);
        animator.setInterpolator(new LinearInterpolator());
        this.fertig = false;
        this.animateDistance = this.defaultBottomY-startPosition;

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                float startY = me.getY();
                float endY = startPosition + animateDistance;
                float currentY = startY + (endY - startY) * progress;
                me.setY(currentY);

                if(me.getY() > me.getScreenHeight()){
                    System.out.println("=====Verloren=====");
                    animator.cancel();
                    for(int i=0;i<me.getMainActivity().getPlatforms().size();i++){
                        me.getMainActivity().getPlatforms().get(i).setStop(true);
                    }
                }else if(me.checkCollission()){
                    System.out.println("Kollission erkannt!");
                    me.setJumps(me.getJumps()+1);
                    me.getScoreDisplay().setText(Integer.toString(me.getJumps()));
                    animator.cancel();
                    me.setY(currentY);
                    for(int i=0;i<me.getMainActivity().getPlatforms().size();i++){
                        me.getMainActivity().getPlatforms().get(i).setPause(true);
                    }
                    me.setFertig(true);
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                //System.out.println("Vorher:" + me.getY());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation beendet
                float endY = startPosition + animateDistance;
                me.setStartPosition(startPosition + animateDistance);
                me.setY(endY);
                me.setFertig(true);
            }
            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
            }
            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
            }
        });

        animator.start();
    }
    public boolean checkCollission(){
        ArrayList<Platform> platforms = this.mainActivity.getPlatforms();
        Coordinate wankerLU = new Coordinate(this.getX(),this.getY()+this.getMyHeight());
        Coordinate wankerRU = new Coordinate(this.getX()+this.myWidth,this.getY()+this.getMyHeight());
        for(int i=0;i<platforms.size();i++){
            Platform p = platforms.get(i);
            Coordinate platformLO = new Coordinate(p.getX(),p.getY());
            Coordinate platformRO = new Coordinate(p.getX()+p.getMyWidth(),p.getY());
            float puffer = 30f;
            if(this.getY()+this.getMyHeight() >= p.getY()-puffer && this.getY()+this.getMyHeight() <= p.getY()+p.getMyHeight()){//this.getY() >= p.getY()-puffer && this.getY() <= p.getY()+puffer
                //System.out.println("Platform ist jetzt auf gleicher höhe wie Wanker!");
                if((wankerLU.getX() > platformLO.getX() && wankerLU.getX() < platformRO.getX()) ||(wankerRU.getX() > platformLO.getX() && wankerRU.getX() < platformRO.getX())){
                   //wenn LU zwischen Plattformgrenzen oder RU zwischen Plattformgrenzen
                    //System.out.println("Index:"+i);
                    return true;
                }
            }
        }
        return false;
    }
}
