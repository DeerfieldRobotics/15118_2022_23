package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.IMUDrivetrain;

@Disabled
@Autonomous(name = "IMUDrivetrainTester")
public class IMUDrivetrainTester extends LinearOpMode {

    private IMUDrivetrain drivetrain;
    DcMotorEx fl, fr, bl, br;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while(opModeIsActive()) {
            drivetrain.turn(45);
            Thread.sleep(1000);
            drivetrain.turn(-45);
            Thread.sleep(1000);
            drivetrain.turn(180);
            Thread.sleep(1000);
            drivetrain.turn(-235);
            Thread.sleep(1000);
            drivetrain.turn(360);
            Thread.sleep(1000);
            telemetry.addData("Current", drivetrain.getAngle());
            telemetry.update();

        }

    }

    public void initialize() {
        drivetrain = new IMUDrivetrain(hardwareMap, telemetry);
    }
}