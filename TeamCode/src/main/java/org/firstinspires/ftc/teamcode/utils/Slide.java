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
         s = (DcMotorImplEx) hw.get(DcMotor.class, "slide");

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

    public void setPosition(int target) {
        s.setTargetPosition(target);
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        s.setPower(speed);
    }
    
    //TODO: Tune these values
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

    public void resetEncoder() {
        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets current encoder position to zero
    }

    public DcMotorEx getMotor() {
        return s;
    }
}
