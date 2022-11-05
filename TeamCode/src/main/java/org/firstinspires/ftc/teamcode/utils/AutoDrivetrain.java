package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class AutoDrivetrain {
    DcMotorEx fl, fr, bl, br;

    public BNO055IMU imu;
    private Orientation lastAngles = new Orientation();
    private double curAngle = 0.0;

    private final int ticksPerInch = 22;

    private final double speedMult = 0.8;

    private final double kP = 10;
    private final double kI = 0.1;
    private final double kD = 1;
    private final double ff = 2.5;

    public AutoDrivetrain(HardwareMap hw) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hw.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

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

        fl.setVelocityPIDFCoefficients(kP,kI,kD,ff);
        fr.setVelocityPIDFCoefficients(kP,kI,kD,ff);
        bl.setVelocityPIDFCoefficients(kP,kI,kD,ff);
        br.setVelocityPIDFCoefficients(kP,kI,kD,ff);
    }

    public void moveTicks(int forward, int strafe, int turn) {
        fl.setTargetPosition(forward - turn - strafe);
        fr.setTargetPosition(forward + turn + strafe);
        bl.setTargetPosition(forward - turn + strafe);
        br.setTargetPosition(forward + turn - strafe);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double speed = 1 * speedMult;

        fl.setPower(speed);
        fr.setPower(speed);
        bl.setPower(speed);
        br.setPower(speed);
    }

    public void moveInches(double forward, double strafe, double turn) {
        moveTicks(ticksPerInch*(int)forward, ticksPerInch*(int)strafe, ticksPerInch*(int)turn);
    }

    public void setMotorPower(double fl, double fr, double bl, double br) {
        this.fl.setPower(fl);
        this.fr.setPower(fr);
        this.bl.setPower(bl);
        this.br.setPower(br);
    }

    public void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        curAngle = 0;
    }

    public double getAngle() {
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = orientation.firstAngle - lastAngles.firstAngle;

        if(deltaAngle > 180) {
            deltaAngle -= 360;
        } else if(deltaAngle <= -180) {
            deltaAngle += 360;
        }

        curAngle += deltaAngle;
        lastAngles = orientation;

        return curAngle;
    }

    public void turn(double degrees) {
        resetAngle();

        double error = degrees;

        while(Math.abs(error) > 2) {
            double motorPower;
            if(error < 0) {
                motorPower = -0.3;
            } else {
                motorPower = 0.3;
            }
            setMotorPower(-motorPower, motorPower, -motorPower, motorPower);
            error = degrees - getAngle();
            //telemetry.addData("Error", error);
            //telemetry.update();
        }
        this.fl.setPower(0);
        this.fr.setPower(0);
        this.bl.setPower(0);
        this.br.setPower(0);
    }

    public void turnTo(double degrees) {
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double error = degrees - orientation.firstAngle;

        if(error >  180) {
            error -= 360;
        } else if(error < -180) {
            error += 360;
        }

        turn(error);
    }

    public double[] getCurrent() {
        return new double[]{fl.getCurrent(CurrentUnit.AMPS),fr.getCurrent(CurrentUnit.AMPS),bl.getCurrent(CurrentUnit.AMPS),br.getCurrent(CurrentUnit.AMPS)};
    }

    public double[] getCurrentPosition() {
        return new double[]{fl.getCurrentPosition(),fr.getCurrentPosition(),bl.getCurrentPosition(),br.getCurrentPosition()};
    }

    public double[][] getPIDFCoefficients() {
        return new double[][]{{fl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,fl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, fl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d, fl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).f}
            ,{fr.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,fr.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, fr.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d, fr.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).f}
            ,{bl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,bl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, bl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d, bl.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).f}
            ,{br.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p,br.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i, br.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d, br.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).f}};
    }
}
