package org.firstinspires.ftc.teamcode.utils;

<<<<<<< HEAD
=======
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
>>>>>>> parent of cae19e6 (started RR for new bot)
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClawMechanism {
    private Claw claw;
    private Slide slide;

    private SlideCheck sc;

    public boolean flip;

    private int slideTarget;
    
    public ClawMechanism(HardwareMap hw) {
        claw = new Claw(hw);
        slide = new Slide(hw);
        
        flip = true; //starts in flipped position
    }
    
    public void closeClaw(double position) {
        claw.moveClaw(position);
    }

    public void setSlideLevel(int level) {
        if(level == 0) {
            //maybe make it run down until it hits limit switch and then reset
            slideTarget = 0;
<<<<<<< HEAD
            slide.setTargetLevel(0);
        }
        else if(level == 1) {
            slideTarget = Slide.low;
            slide.setTargetLevel(Slide.low);
        }
        else if(level == 2) {
            slideTarget = Slide.medium;
            slide.setTargetLevel(Slide.medium);
        }
        else if(level == 3) {
            slideTarget = Slide.high;
            slide.setTargetLevel(Slide.high);
=======
            slide.setPosition(0);
        }
        else if(level == 1) {
            slideTarget = Slide.low;
            slide.setPosition(Slide.low);
        }
        else if(level == 2) {
            slideTarget = Slide.medium;
            slide.setPosition(Slide.medium);
        }
        else if(level == 3) {
            slideTarget = Slide.high;
            slide.setPosition(Slide.high);
>>>>>>> parent of cae19e6 (started RR for new bot)
        }
    }
    
    public void flipRoll() {
        if(flip) 
            claw.moveRoll(0);
        else
            claw.moveRoll(1);
    }
    //wacky shit if press flip twice cuz of threads, maybe synchronize or have a cancel button
    public void flipArm() {
        if(slide.getCurrentPosition() >= Slide.slideMin) {
            if(flip)
                claw.moveArm(0);
            else
                claw.moveArm(1);
        }
        else {
            if (slideTarget >= Slide.slideMin) {
                //start thread to check if slide goes up and then flip the arm
                sc = new SlideCheck(slide, claw, flip);
                sc.start();
            }
            else {
                int oldPos = Math.max(slide.getCurrentPosition(),slide.slideFlipLimit);
<<<<<<< HEAD
                slide.setTargetLevel(Slide.slideMin);
=======
                slide.setPosition(Slide.slideMin);
>>>>>>> parent of cae19e6 (started RR for new bot)
                //start thread to check if slide goes up and then flip the arm, then go back to old position
                sc = new SlideCheck(slide, claw, flip, oldPos);
                sc.start();
            }
        }
    }

    public SlideCheck getSlideCheck() {
        return sc;
    }

    public void flip() {
        flipArm();
        flipRoll();
    }

    public void setArm(double position) {
        claw.moveArm(position);
    }

    public void setRoll(double position) {
        claw.moveRoll(position);
    }

    public void setPitch(double position) {
        claw.movePitch(position);
    }

    public void setSlidePower(double power) {
        slide.move(power);
    }

    public void stopSlide() {slide.stop();}
    public Slide getSlide() {
        return slide;
    }
}
