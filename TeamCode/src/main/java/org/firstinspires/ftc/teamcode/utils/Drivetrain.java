package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain
{
    private DcMotor fl, fr, bl, br;
    private HardwareMap hw;

    private double DRIVETRAIN_SPEED_MODIFIER = 1;
    public Drivetrain (HardwareMap hardwaremap) {
        hw = hardwaremap;


        initialize();
    }

    public void initialize() {
        fr = hw.get(DcMotor.class, "fr");
        fl = hw.get(DcMotor.class, "fl");
        br = hw.get(DcMotor.class, "br");
        bl = hw.get(DcMotor.class, "bl");

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    /**
     * Moves drivetrain
     *
     * @param strafe speed of strafe between 0 and 1
     * @param turn speed of turn between 0 and 1
     * @param forward speed of forward between 0 and 1
     */
    public void move(double strafe, double turn, double forward)
    {
        turn *= -DRIVETRAIN_SPEED_MODIFIER;
        forward *= DRIVETRAIN_SPEED_MODIFIER;
        strafe *= -DRIVETRAIN_SPEED_MODIFIER;
        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn - strafe);
        bl.setPower(forward + turn - strafe);
        br.setPower(forward - turn + strafe);
    }
    public void stop() {
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

    }
    public void setMode(String newMode)
    {
        if(newMode.equals("AUTO"))
        {

            setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else if(newMode.equals("TELEOP"))
        {

            setEncoderMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } else
        {

        }
    }

    public void setSpeed(double newSpeed)
    {
        DRIVETRAIN_SPEED_MODIFIER = newSpeed;
    }

    public void setEncoderMode(DcMotor.RunMode encoderMode)
    {
        fr.setMode(encoderMode);
        fl.setMode(encoderMode);
        br.setMode(encoderMode);
        bl.setMode(encoderMode);
    }
    public int[] getEncoderTicks()
    {
        return new int[]
                {fr.getCurrentPosition(), fl.getCurrentPosition(), br.getCurrentPosition(), bl.getCurrentPosition()};
    }
    public void forwards(boolean forward, int amount, int time_ms)
    {

        if (!forward) {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(amount);
        } else {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(-amount);
        }
    }
    public void strafe(boolean left, int amount, int time_ms)
    {
        if (!left) {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        } else {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        }
    }

    public void turn(boolean left, int amount, int time_ms) {
        if (!left) {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        } else {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        }
    }
}