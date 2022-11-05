package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.AutoDrivetrain;
import org.firstinspires.ftc.teamcode.utils.IMUDrivetrain;

@Autonomous(name = "IMUDrivetrainTester")
public class IMUDrivetrainTester extends LinearOpMode {

    private IMUDrivetrain drivetrain;
    DcMotorEx fl, fr, bl, br;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        drivetrain.turn(-90);
        telemetry.addData("Current", drivetrain.getAngle());
        telemetry.update();

        while(opModeIsActive()) {

        }
    }

    public void initialize() {
        drivetrain = new IMUDrivetrain(hardwareMap);
    }
}