package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Claw {
    private Servo arm, roll, pitch, claw;
    private HardwareMap hw;

    public Claw (HardwareMap hardwaremap) {
        hw = hardwaremap;

        initialize();
    }

    public void initialize() {
        arm = hw.get(Servo.class, "arm");
        roll = hw.get(Servo.class, "roll");
        pitch = hw.get(Servo.class, "pitch");
        claw = hw.get(Servo.class, "claw");

        arm.scaleRange(0, 1); //range of motion for arm
        roll.scaleRange(0, 1); //range of motion for the roll servo
        pitch.scaleRange(0, 1); //range of motion for the pitch servo
        claw.scaleRange(0, 1); //range of motion for the claw servo
    }

    public void moveClaw(double position) {
        claw.setPosition(position);
    }
    public void moveRoll(double position) {
        roll.setPosition(position);
    }
    public void movePitch(double position) {
        pitch.setPosition(position);
    }
    public void moveArm(double position) {
        arm.setPosition(position);
    }

    public double getClawPos() {
        return claw.getPosition();
    }
    public double getRollPos() {
        return roll.getPosition();
    }
    public double getPitchPos() {
        return pitch.getPosition();
    }
    public double getArmPos() {
        return arm.getPosition();
    }
}
