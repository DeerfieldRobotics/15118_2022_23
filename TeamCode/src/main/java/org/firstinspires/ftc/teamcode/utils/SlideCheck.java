package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.teamcode.utils.Slide.slideMin;

public class SlideCheck extends Thread {
    private Slide slide;
    private Claw claw;

    private final int armDelay = 1000;

    private boolean targetLow = false;

    private boolean flip;

    private int oldPos;

    public SlideCheck(Slide s, Claw c, boolean f) {
        slide = s;
        claw = c;
        flip = f;
    }
    public SlideCheck(Slide s, Claw c, boolean f, int o) {
        slide = s;
        claw = c;
        flip = f;

        oldPos = o;
        targetLow = true;
    }

    @Override
    public void run() {
        while(slide.getCurrentPosition() < Slide.slideMin) { //checks every .1 seconds if it is at least minimum
            try{Thread.sleep(100);}catch(Exception e){}
            if(slide.getTargetPosition() < Slide.slideMin)
                return; //If slide target changes it will return
            if(targetLow && slide.getTargetPosition() != slideMin) //if target has changed, make sure it goes to new target
                oldPos = slide.getTargetPosition();
        }
        if(flip)
            claw.moveArm(0);
        else
            claw.moveArm(1);
        if(targetLow) {
            try {
                Thread.sleep(armDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            slide.setTargetLevel(oldPos);
        }
    }
}
