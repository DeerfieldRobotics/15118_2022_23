package org.firstinspires.ftc.teamcode.testers;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.utils.AutoDrivetrain;

@Disabled
@Autonomous(name = "AutoDrivetrainTester")
public class AutoDrivetrainTester extends LinearOpMode {

    private AutoDrivetrain drivetrain;

    private Orientation lastAngles = new Orientation();
    private double curAngle = 0.0;

    private final int ticksPerInch = 22;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while(opModeIsActive()) {

            //telemetry.addData("Current Angle", drivetrain.getIMU().getAngle());
            //drivetrain.moveTicks(1000,0,0);

            //telemetry.addData("Front Left Current", drivetrain.getCurrent()[0]);
            //telemetry.addData("Front Right Current", drivetrain.getCurrent()[1]);
            //telemetry.addData("Back Left Current", drivetrain.getCurrent()[2]);
            //telemetry.addData("Back Right Current", drivetrain.getCurrent()[3]);

            //telemetry.addData("Front Left Position", drivetrain.getCurrentPosition()[0]);
            //telemetry.addData("Front Right Position", drivetrain.getCurrentPosition()[1]);
            //telemetry.addData("Back Left Position", drivetrain.getCurrentPosition()[2]);
            //telemetry.addData("Back Right Position", drivetrain.getCurrentPosition()[3]);
            //telemetry.addData("Proportion", drivetrain.getPIDFCoefficients()[0][0]);
            //telemetry.addData("Integral", drivetrain.getPIDFCoefficients()[0][1]);
            //telemetry.addData("Derivative", drivetrain.getPIDFCoefficients()[0][2]);
            //telemetry.addData("Feed Forward", drivetrain.getPIDFCoefficients()[0][3]);
            //telemetry.update();

            //Thread.sleep(5000);

            //drivetrain.moveTicks(0,0,0);

            //telemetry.addData("Front Left Current", drivetrain.getCurrent()[0]);
            //telemetry.addData("Front Right Current", drivetrain.getCurrent()[1]);
            //telemetry.addData("Back Left Current", drivetrain.getCurrent()[2]);
            //telemetry.addData("Back Right Current", drivetrain.getCurrent()[3]);

            //telemetry.addData("Front Left Position", drivetrain.getCurrentPosition()[0]);
            //telemetry.addData("Front Right Position", drivetrain.getCurrentPosition()[1]);
            //telemetry.addData("Back Left Position", drivetrain.getCurrentPosition()[2]);
            //telemetry.addData("Back Right Position", drivetrain.getCurrentPosition()[3]);
            //telemetry.addData("Proportion",d.getPIDFCoefficients()[0][0]);
            //telemetry.addData("Integral",d.getPIDFCoefficients()[0][1]);
            //telemetry.addData("Derivative",d.getPIDFCoefficients()[0][2]);
            //telemetry.addData("Front Feed",d.getPIDFCoefficients()[0][3]);
            telemetry.update();


            //Thread.sleep(5000);

        }
    }

    public void initialize() {
        drivetrain = new AutoDrivetrain(hardwareMap);
    }
}
