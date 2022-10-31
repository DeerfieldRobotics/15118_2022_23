package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slide {

    private DcMotorEx s;
    private HardwareMap hw;

    private final float speed = 1;

    public final static int low = 1000;
    public final static int medium = 1600;
    public final static int high = 2200;
    public final static int slideMin = 1000;
    public final static int slideFlipLimit = 500;

    public Slide (HardwareMap hardwaremap) {
        hw = hardwaremap;

        initialize();
    }

    public void initialize() {
         s = (DcMotorEx) hw.get(DcMotor.class, "slide");

        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets current encoder position to zero
        s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //static power
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
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public DcMotorEx getMotor() {
        return s;
    }
}
