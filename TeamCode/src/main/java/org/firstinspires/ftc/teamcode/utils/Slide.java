package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slide {

    public DcMotorEx s;
    private HardwareMap hw;

    private final float speed = 1;
    //TODO: tune these values
    public final static int low = 1560;
    public final static int medium = 2670;
    public final static int high = 3760;
    
    public final static int slideMin = 1000;
    public final static int slideFlipLimit = 500;

    int target = 0;

    public Slide (HardwareMap hardwaremap) {
        hw = hardwaremap;

        initialize();
    }

    public void initialize() {
<<<<<<< HEAD
<<<<<<< HEAD
         s = (DcMotorEx) hw.get(DcMotor.class, "slide");
=======
         s = (DcMotorImplEx) hw.get(DcMotor.class, "slide");
>>>>>>> parent of cae19e6 (started RR for new bot)
=======
         s = (DcMotorImplEx) hw.get(DcMotor.class, "slide");
>>>>>>> parent of cae19e6 (started RR for new bot)

        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets current encoder position to zero
        s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        s.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.BRAKE); //static power
    }

    public int getCurrentPosition() {
        return s.getCurrentPosition();
    }

    public int getTargetPosition() {
        return s.getTargetPosition();
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public void setTargetLevel(int target) {
=======
    public void setPosition(int target) {
>>>>>>> parent of cae19e6 (started RR for new bot)
=======
    public void setPosition(int target) {
>>>>>>> parent of cae19e6 (started RR for new bot)
        s.setTargetPosition(target);
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        s.setPower(speed);
    }
    
    //TODO: Tune these values
<<<<<<< HEAD
<<<<<<< HEAD
    public void moveSlide(int level) {
        if(level == 0) {
            //maybe make it run down until it hits limit switch and then reset
            setTargetLevel(0);
        }
        else if(level == 1) {
            setTargetLevel(Slide.low);
        }
        else if(level == 2) {
            setTargetLevel(Slide.medium);
        }
        else if(level == 3) {
            setTargetLevel(Slide.high);
        }

        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
=======
=======
>>>>>>> parent of cae19e6 (started RR for new bot)
    public void setSlideLevel(int level) {
        if(level == 0) {
            //maybe make it run down until it hits limit switch and then reset
            setPosition(0);
        }
        else if(level == 1) {
            setPosition(Slide.low);
        }
        else if(level == 2) {
            setPosition(Slide.medium);
        }
        else if(level == 3) {
            setPosition(Slide.high);
        }
<<<<<<< HEAD
>>>>>>> parent of cae19e6 (started RR for new bot)
=======
>>>>>>> parent of cae19e6 (started RR for new bot)
    }

    public void move(double amount) {
        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //static power
        s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        s.setPower(amount);
    }

    public void stop() {
        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //static power
        s.setPower(0);
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public void setPower(double pow){
        s.setPower(pow);
    }

=======
>>>>>>> parent of cae19e6 (started RR for new bot)
=======
>>>>>>> parent of cae19e6 (started RR for new bot)
    public void resetEncoder() {
        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets current encoder position to zero
    }

    public DcMotorEx getMotor() {
        return s;
    }
}
