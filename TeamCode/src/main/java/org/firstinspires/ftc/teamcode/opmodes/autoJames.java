package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.IMU;

@Autonomous(name="Auto by james")
public class autoJames extends LinearOpMode {

    private Drivetrain drivetrain;
    private IMU imu;

    @Override
    public void runOpMode() {

        initialize();
        waitForStart();
        drivetrain.move(0, 0, .5);
        while(opModeIsActive()) {

        }
    }

    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
        imu = new IMU(hardwareMap);
    }

}
