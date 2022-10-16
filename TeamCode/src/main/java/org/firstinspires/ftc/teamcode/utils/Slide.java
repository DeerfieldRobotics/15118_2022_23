package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slide {

    private DcMotor s;
    private HardwareMap hw;

    private final float speed = 1;

    public final static int low, medium, high, slideMin;

    public Slide (HardwareMap hardwaremap) {
        hw = hardwaremap;

        initialize();
    }

    public void initialize() {

        //for preset levels:

        low = 100;
        medium = 200;
        high = 300;

        //minimum height for arm to pass under
        slideMin = 100;

         s = hw.get(DcMotor.class, "s");

        s.setMode(DcMotor.Runmode.STOP_AND_RESET_ENCODERS); //sets current encoder position to zero
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.Brake); //static power
    }

    public int getPosition() {
        return s.getCurrentPosition();
    }

    public void toPosition(int target) {
        s.setTargetPosition(target);
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        s.setPower(speed);
    }

    public void move(double amount) {
        s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        s.setPower(amount);
    }

    public void stop() {
        s.setPower(0);
    }

    public void resetEncoder() {
        s.setMode(DcMotor.Runmode.STOP_AND_RESET_ENCODERS); //sets current encoder position to zero
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}