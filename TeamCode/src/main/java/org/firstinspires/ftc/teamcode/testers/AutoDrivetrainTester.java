package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.AutoDrivetrain;

@Autonomous(name = "AutoDrivetrainTester")
public class AutoDrivetrainTester extends LinearOpMode {

    private AutoDrivetrain d;
    DcMotorEx fl, fr, bl, br;

    private final int ticksPerInch = 22;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        while(opModeIsActive()) {

            d.moveTicks(1000,0,0);

            telemetry.addData("Front Left Current",d.getCurrent()[0]);
            telemetry.addData("Front Right Current",d.getCurrent()[1]);
            telemetry.addData("Back Left Current",d.getCurrent()[2]);
            telemetry.addData("Back Right Current",d.getCurrent()[3]);

            telemetry.addData("Front Left Position",d.getCurrentPosition()[0]);
            telemetry.addData("Front Right Position",d.getCurrentPosition()[1]);
            telemetry.addData("Back Left Position",d.getCurrentPosition()[2]);
            telemetry.addData("Back Right Position",d.getCurrentPosition()[3]);
            telemetry.update();

            Thread.sleep(5000);

            d.moveTicks(0,0,0);

            telemetry.addData("Front Left Current",d.getCurrent()[0]);
            telemetry.addData("Front Right Current",d.getCurrent()[1]);
            telemetry.addData("Back Left Current",d.getCurrent()[2]);
            telemetry.addData("Back Right Current",d.getCurrent()[3]);

            telemetry.addData("Front Left Position",d.getCurrentPosition()[0]);
            telemetry.addData("Front Right Position",d.getCurrentPosition()[1]);
            telemetry.addData("Back Left Position",d.getCurrentPosition()[2]);
            telemetry.addData("Back Right Position",d.getCurrentPosition()[3]);
            telemetry.addData("Derivative",d.getPIDFCoefficients()[0][1]);
            telemetry.update();


            Thread.sleep(5000);

        }
    }

    public void initialize() {
        d = new AutoDrivetrain(hardwareMap);
    }

}
