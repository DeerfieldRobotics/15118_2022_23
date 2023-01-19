package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Drivetrain;

@Autonomous(name = "savingandrew")
public class savingandrew extends LinearOpMode {

    Drivetrain d = new Drivetrain(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {

        d.run_using_encoder();
        d.floatMotors();

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("fl", d.getMotorPositions()[0]);
            telemetry.addData("fr", d.getMotorPositions()[1]);
            telemetry.addData("bl", d.getMotorPositions()[2]);
            telemetry.addData("br", d.getMotorPositions()[3]);
            telemetry.update();

        }
    }
}
