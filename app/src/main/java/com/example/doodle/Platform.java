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
    private boolean pause;
    private ArrayList<Platform> whereAmI;
    private boolean fertig = true;
    private MainActivity mainActivity;
    private float screenHeight;
    public Platform(Context context){
        super(context);
    }
    public Platform(Context context,ArrayList<Platform> whereAmI) {
        super(context);
        this.whereAmI = whereAmI;
    }

    public Platform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Platform(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getScreenHeight() {
        return screenHeight;
    }
    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public boolean isFertig() {
        return fertig;
    }
    public void setFertig(boolean fertig) {
        this.fertig = fertig;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isPause() {
        return pause;
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

    private int counter = 0;
    public void createAndStartAnimation(){
        this.fertig = false;
        Platform me = this;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1*1000);
        animator.setInterpolator(new LinearInterpolator());
        //animator.setInterpolator(new AccelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                //System.out.println("Animiere:"+counter++);
                float progress = (float) animation.getAnimatedValue();
                float startX = me.getX();
                float startY = me.getY();
                float endX = me.getX();
                float endY = startPosition+1000f;
                float currentX = startX + (endX - startX) * progress;
                float currentY = startY + (endY - startY) * progress;
                me.setX(currentX);
                me.setY(currentY);
                if(me.isPause()){
                    //System.out.println("Plattform wurde pausiert!");
                    //animator.pause();
                    animator.cancel();
                    me.setX(currentX);
                    me.setY(currentY);
                    me.setStartPosition(me.getY());
                    me.setPause(false);

                    me.setFertig(true);
                    boolean all = true;
                    for(int i=0;i<whereAmI.size();i++){
                        if(whereAmI.get(i).isFertig() == false){
                            all = false;
                            break;
                        }
                    }
                    if(all){
                        //System.out.println("Bereit zum weiteranimieren!");
                        me.getMainActivity().weiterGenerieren();
                    }
                }

                if(me.getY()>me.getScreenHeight()){
                    System.out.println("ScreenHeight:"+me.getScreenHeight());
                    System.out.println("Lösche mich, weil ich außerhalb bin!");
                    me.whereAmI.remove(me);
                    System.out.println("Länge jetzt:"+me.whereAmI.size());
                }
                if(me.equals(me.whereAmI.get(0))){
                    //System.out.println("Ich bin der erste und werde Animiert!!");
                    //System.out.println("Aktuelles Y:"+me.getY());
                    //System.out.println("Animation Start-Y:"+startPosition);
                    //System.out.println("Animation End-Y:"+(startPosition+1000f));
                }
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
                float endY = startPosition+1000f;
                me.setStartPosition(startPosition+1000f);
                me.setX(endX);
                me.setY(endY);
                //System.out.println("Plattform am Ende!");
                me.setFertig(true);
                //System.out.println("Nachher:"+me.getY());
                if(me.getY()>me.getScreenHeight()){
                    System.out.println("Lösche mich, weil ich außerhalb bin!");
                    me.whereAmI.remove(me);
                    System.out.println("Länge jetzt:"+me.whereAmI.size());
                }

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

    public void setRandomPosition(ArrayList<Platform> platforms,float min, float max){
        Random random = new Random();

        //imageView.setX(random.nextFloat()*830f);

       // float startY = random.nextFloat()*(1760f-(-80f)+(-80f));
        //imageView.setY(startY);//-80 bis 1760
        boolean nochmal = true;
        float x = 0;
        float y = 0;
        while(nochmal){
            x = random.nextFloat()*830f;
            //y = random.nextFloat()*(1760f-(-(-1680f))+(-(-1680f)));//random.nextFloat()*(1760f-(-80f)+(-80f)); -1680
            y = random.nextFloat() * (max - min) + min;
            nochmal = false;
            for(int i=0;i<platforms.size();i++){
                float pX = platforms.get(i).getX();
                float pY = platforms.get(i).getY();
                float pHeight = platforms.get(i).getMyHeight();
                float pWidth = platforms.get(i).getMyWidth();
                /*System.out.println("X:"+x);
                System.out.println("Y:"+y);
                System.out.println("pX:"+pX);
                System.out.println("pY:"+pY);
                System.out.println("Height:"+pHeight);
                System.out.println("pWidth:"+pWidth);*/
                if(y>=pY-pHeight && y<=pY+pHeight && x>=pX-pWidth && x<=pX+pWidth){//
                    //Muss neu generiert werden
                    nochmal = true;
                    //System.out.println("Nochmal!");
                    break;
                }
            }

        }
        this.setX(x);
        this.setY(y);

    }

    // Hier können Sie Ihre eigenen Methoden und Eigenschaften hinzufügen oder vorhandene Methoden überschreiben.

}
