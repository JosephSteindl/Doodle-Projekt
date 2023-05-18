package com.example.doodle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

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

    public Wanker(@NonNull Context context) {
        super(context);
    }

    public Wanker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Wanker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        animator.setDuration(500);//(long)(animateDistance*0.2)
        animator.setInterpolator(new LinearInterpolator());
        this.fertig = false;

        //animator.setInterpolator(new AccelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                //System.out.println("Animiere:"+counter++);
                float progress = (float) animation.getAnimatedValue();
                float startX = me.getX();
                float startY = me.getY();
                float endX = me.getX();
                float endY = startPosition-animateDistance;
                float currentX = startX + (endX - startX) * progress;
                float currentY = startY + (endY - startY) * progress;
                me.setX(currentX);
                me.setY(currentY);
                /*if(me.equals(me.whereAmI.get(0))){
                    System.out.println("Ich bin der erste und werde Animiert!!");
                    System.out.println("Aktuelles Y:"+me.getY());
                    System.out.println("Animation Start-Y:"+startPosition);
                    System.out.println("Animation End-Y:"+(startPosition+1000f));
                }*/
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

                float endX = me.getX();
                float endY = startPosition-animateDistance;
                me.setStartPosition(startPosition-animateDistance);
                me.setX(endX);
                me.setY(endY);
                //System.out.println("Nachher:"+me.getY());
                /*if(me.getY()>2000){
                    System.out.println("Lösche mich, weil ich außerhalb bin!");
                    me.whereAmI.remove(me);
                    System.out.println("Länge jetzt:"+me.whereAmI.size());
                }*/

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
        animator.setDuration(2 * 1000);
        animator.setInterpolator(new LinearInterpolator());
        //animator.setInterpolator(new AccelerateInterpolator());
        this.fertig = false;
        this.animateDistance = this.defaultBottomY-startPosition;

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                //System.out.println("Animiere:"+counter++);
                float progress = (float) animation.getAnimatedValue();
                float startX = me.getX();
                float startY = me.getY();
                float endX = me.getX();
                float endY = startPosition + animateDistance;
                float currentX = startX + (endX - startX) * progress;
                float currentY = startY + (endY - startY) * progress;
                me.setX(currentX);
                me.setY(currentY);
                /*if(me.equals(me.whereAmI.get(0))){
                    System.out.println("Ich bin der erste und werde Animiert!!");
                    System.out.println("Aktuelles Y:"+me.getY());
                    System.out.println("Animation Start-Y:"+startPosition);
                    System.out.println("Animation End-Y:"+(startPosition+1000f));
                }*/
                if(me.getY() > me.getScreenHeight()){
                    System.out.println("=====Verloren=====");
                    animator.cancel();
                    for(int i=0;i<me.getMainActivity().getPlatforms().size();i++){
                        me.getMainActivity().getPlatforms().get(i).setStop(true);
                    }
                    //me.getMainActivity().prepareForRestart();

                }else if(me.checkCollission()){
                    System.out.println("Kollission erkannt!");
                    //animator.pause();
                    animator.cancel();
                    me.setX(currentX);
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

                float endX = me.getX();
                float endY = startPosition + animateDistance;
                me.setStartPosition(startPosition + animateDistance);
                me.setX(endX);
                me.setY(endY);
                //System.out.println("Nachher:" + me.getY());
                /*if(me.getY()>2000){
                    System.out.println("Lösche mich, weil ich außerhalb bin!");
                    me.whereAmI.remove(me);
                    System.out.println("Länge jetzt:"+me.whereAmI.size());
                }*/
                //me.createAndStartAnimation();
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
    private int collisionCount = 0;
    public boolean checkCollission(){
        //System.out.println("Ckeck collision!");
        /*ArrayList<Platform> platforms = this.mainActivity.getPlatforms();
        for(int i=0;i<platforms.size();i++){
            Rect rect1 = new Rect();
            this.getHitRect(rect1);
            Rect rect2 = new Rect();
            platforms.get(i).getHitRect(rect2);
            if(Rect.intersects(rect1,rect2)){
                return true;
            }
        }
        return false;*/
        ArrayList<Platform> platforms = this.mainActivity.getPlatforms();
        Coordinate wankerLU = new Coordinate(this.getX(),this.getY()+this.getMyHeight());
        Coordinate wankerRU = new Coordinate(this.getX()+this.myWidth,this.getY()+this.getMyHeight());
        for(int i=0;i<platforms.size();i++){
            Platform p = platforms.get(i);
            Coordinate platformLO = new Coordinate(p.getX(),p.getY());
            Coordinate platformRO = new Coordinate(p.getX()+p.getMyWidth(),p.getY());
            float puffer = 30f;
            //System.out.println("Y-Wanker:"+(this.getY()+this.getMyHeight()));
            //System.out.println("Y-oben-platf.:"+p.getY());
            //System.out.println("Y-unten-platf.:"+(p.getY()+p.getMyHeight()));
            if(this.getY()+this.getMyHeight() >= p.getY()-puffer && this.getY()+this.getMyHeight() <= p.getY()+p.getMyHeight()){//this.getY() >= p.getY()-puffer && this.getY() <= p.getY()+puffer
                //System.out.println("Platform ist jetzt auf gleicher höhe wie Wanker!"+collisionCount++);
                //Y-Koordinate vergleichen
                if((wankerLU.getX() > platformLO.getX() && wankerLU.getX() < platformRO.getX()) ||(wankerRU.getX() > platformLO.getX() && wankerRU.getX() < platformRO.getX())){
                   //wenn LU zwischen Plattformgrenzen oder RU zwischen Plattformgrenzen
                    /*for(int j=0;j<platforms.size();j++){
                        if(!platforms.get(j).equals(p)){
                            platforms.get(j).setVisibility(View.INVISIBLE);
                        }
                    }*/
                    //System.out.println("Index:"+i);
                    return true;
                }
            }
        }

        return false;
    }
}
