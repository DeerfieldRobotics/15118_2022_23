package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMUDrivetrain {
    DcMotorEx fl, fr, bl, br;

    BNO055IMU imu;
    private Orientation lastAngles = new Orientation();
    private double curAngle;

    private final double speedMult = 0.8;

    private final double kP = 10;
    private final double kI = 0.1;
    private final double kD = 1;
    private final double ff = 2.5;

    private Telemetry telemetry;

    public IMUDrivetrain(HardwareMap hw, Telemetry t) {
        telemetry = t;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hw.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //gets motors from hardware map
        fl = hw.get(DcMotorEx.class, "fl");
        fr = hw.get(DcMotorEx.class, "fr");
        bl = hw.get(DcMotorEx.class, "bl");
        br = hw.get(DcMotorEx.class, "br");

        //sets motor modes
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets motor behavior
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //change as needed to get proper behavior
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public double normalize(double angle){
        if(angle > 180){
            angle -= 360;
        } else if(angle < -180){
            angle += 360;
        }
        return angle;
    }

    public void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        curAngle = 0;
    }

    public double getAngle() {

        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        curAngle = (orientation.firstAngle+180);

        lastAngles = orientation;

        return curAngle;
    }

    public void turn(double degrees) {
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        resetAngle();

        double target = normalize(degrees);

        double error = target;
        double current_pos = getAngle();

        double target_pos = (current_pos+degrees)%360;
        if(target_pos<0){
            target_pos+=360;
        }

        while(Math.abs(error) > 1) {
            current_pos = getAngle();
            double motorPower = (error < 0 || error > 180 ? -0.3 : 0.3);
            fl.setPower(-motorPower);
            fr.setPower(motorPower);
            bl.setPower(-motorPower);
            br.setPower(motorPower);

            error = target_pos - current_pos;
            if (Math.abs(error) > 180) {
                error = current_pos-target_pos;
            }

            telemetry.addData("angle",getAngle());
            telemetry.addData("error",error);
            telemetry.addData("target",target_pos);

            telemetry.update();
        }
        telemetry.addLine("finished");
        telemetry.update();
        setAllPower(0);
    }

    public void turnTo(double degrees) {
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double error = degrees - orientation.firstAngle;

        normalize(error);

        turn(error);
    }

    public void setAllPower(double power) {
        fl.setPower(power);
        fr.setPower(power);
        bl.setPower(power);
        br.setPower(power);
    }
}
