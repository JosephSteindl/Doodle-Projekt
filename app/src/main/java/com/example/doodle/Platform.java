package com.example.doodle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class Platform extends androidx.appcompat.widget.AppCompatImageView {

    private float startPosition;
    private float myHeight;
    private float myWidth;

    public Platform(Context context) {
        super(context);
    }

    public Platform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Platform(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public float getStartPosition(){
        return this.startPosition;
    }
    public void setStartPosition(float startPosition){
        this.startPosition=startPosition;
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

    public void createAndStartAnimation(){
        Platform me = this;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(20 *1000);
        animator.setInterpolator(new LinearInterpolator());
        //animator.setInterpolator(new AccelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                float startX = me.getX();
                float startY = me.getY();
                float endX = me.getX();
                float endY = startPosition+2000f;
                float currentX = startX + (endX - startX) * progress;
                float currentY = startY + (endY - startY) * progress;
                me.setX(currentX);
                me.setY(currentY);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                System.out.println("Vorher:"+me.getY());
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                // Animation beendet

                float endX = me.getX();
                float endY = startPosition+2000f;
                me.setX(endX);
                me.setY(endY);
                System.out.println("Vorher:"+me.getY());


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

    public void setRandomPosition(ArrayList<Platform> platforms){
        Random random = new Random();

        //imageView.setX(random.nextFloat()*830f);

       // float startY = random.nextFloat()*(1760f-(-80f)+(-80f));
        //imageView.setY(startY);//-80 bis 1760
        boolean nochmal = true;
        float x = 0;
        float y = 0;
        while(nochmal){
            x = random.nextFloat()*830f;
            y = random.nextFloat()*(1760f-(-80f)+(-80f));;
            nochmal = false;
            for(int i=0;i<platforms.size();i++){
                float pX = platforms.get(i).getX();
                float pY = platforms.get(i).getY();
                float pHeight = platforms.get(i).getMyHeight();
                float pWidth = platforms.get(i).getMyWidth();
                System.out.println("X:"+x);
                System.out.println("Y:"+y);
                System.out.println("pX:"+pX);
                System.out.println("pY:"+pY);
                System.out.println("Height:"+pHeight);
                System.out.println("pWidth:"+pWidth);
                if(y>=pY-pHeight && y<=pY+pHeight && x>=pX-pWidth && x<=pX+pWidth){//
                    //Muss neu generiert werden
                    nochmal = true;
                    System.out.println("Nochmal!");
                    break;
                }
            }

        }
        this.setX(x);
        this.setY(y);

    }

    // Hier können Sie Ihre eigenen Methoden und Eigenschaften hinzufügen oder vorhandene Methoden überschreiben.

}
