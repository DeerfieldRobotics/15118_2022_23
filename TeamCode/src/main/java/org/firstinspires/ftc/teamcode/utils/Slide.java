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

    public Slide (HardwareMap hardwaremap) {
        hw = hardwaremap;

    }

    public void initialize() {
        s = hw.get(DcMotor.class, "s");

        s.setMode(DcMotor.Runmode.STOP_AND_RESET_ENCODERS); //sets current encoder position to zero
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.Brake); //static power
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
