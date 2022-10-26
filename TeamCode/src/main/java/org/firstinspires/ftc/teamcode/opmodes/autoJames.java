package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Drivetrain;

@Autonomous(name="Auto by james")
public class autoJames extends LinearOpMode {

    private Drivetrain drivetrain;

    @Override
    public void runOpMode() {

        initialize();
        waitForStart();

        while(opModeIsActive()) {

        }
    }

    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
    }

}
