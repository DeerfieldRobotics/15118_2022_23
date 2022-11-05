package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class AutoDrivetrain {
    DcMotorEx fl, fr, bl, br;

    private final int ticksPerInch = 22;

    private final double speedMult = 0.8;


    public AutoDrivetrain(HardwareMap hw) {
        fl = hw.get(DcMotorEx.class, "fl");
        fr = hw.get(DcMotorEx.class, "fr");
        bl = hw.get(DcMotorEx.class, "bl");
        br = hw.get(DcMotorEx.class, "br");

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void moveTicks(int forward, int strafe, int turn) {
        fl.setTargetPosition(forward-turn-strafe);
        fr.setTargetPosition(forward+turn+strafe);
        bl.setTargetPosition(forward-turn+strafe);
        br.setTargetPosition(forward+turn-strafe);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double speed = 1*speedMult;

        fl.setPower(speed);
        fr.setPower(speed);
        bl.setPower(speed);
        br.setPower(speed);
    }

    public void moveInches(double forward, double strafe, double turn) {
        moveTicks(ticksPerInch*(int)forward, ticksPerInch*(int)strafe, ticksPerInch*(int)turn);
    }

    public double[] getCurrent() {
        return new double[]{fl.getCurrent(CurrentUnit.AMPS),fr.getCurrent(CurrentUnit.AMPS),bl.getCurrent(CurrentUnit.AMPS),br.getCurrent(CurrentUnit.AMPS)};
    }

    public double[] getCurrentPosition() {
        return new double[]{fl.getCurrentPosition(),fr.getCurrentPosition(),bl.getCurrentPosition(),br.getCurrentPosition()};
    }

    public double[][] getPIDFCoefficients() {
        return new double[][]{{fl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,fl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, fl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d}
                            ,{fr.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,fr.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, fr.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d}
                            ,{bl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,bl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, bl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d}
                            ,{br.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,br.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, br.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d}};
    }
}
