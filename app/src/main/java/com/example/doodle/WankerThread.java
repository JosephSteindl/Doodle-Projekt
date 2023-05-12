package com.example.doodle;

public class WankerThread extends Thread{
    Wanker wanker;
    public WankerThread(Wanker wanker){
        this.wanker=wanker;
    }
    public void run(){
        this.wanker.createAndStartAnimation();
    }
}
