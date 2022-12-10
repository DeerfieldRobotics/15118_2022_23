package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@Autonomous(name = "PARKING_AUTO")
public class ParkingAuto extends LinearOpMode {
    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private int detectedTag;


    public void initialize() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap);

        while(opModeInInit()) {
            detectedTag = aprilTags.getID();
            telemetry.addData("DETECTED TAG: ", detectedTag);
            telemetry.update();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        if (isStopRequested()) {
            return;
        }

        Pose2d startPose = new Pose2d(38, -60, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence parkLeft = drive.trajectorySequenceBuilder(startPose)
                //TODO: CREATE LEFT PARKING TRAJECTORY
                .back(26)
                .strafeLeft(30)
                .build();

        TrajectorySequence parkMiddle = drive.trajectorySequenceBuilder(startPose)
                //TODO: CREATE MIDDLE PARKING TRAJECTORY
                .strafeLeft(30)
                .build();

        TrajectorySequence parkRight = drive.trajectorySequenceBuilder(startPose)
                //TODO: CREATE RIGHT PARKING TRAJECTORY
                .forward(26)
                .strafeLeft(30)
                .build();

        if (detectedTag == 1) {
            drive.followTrajectorySequence(parkLeft);
        } else if (detectedTag == 2) {
            drive.followTrajectorySequence(parkMiddle);
        } else if (detectedTag == 3) {
            drive.followTrajectorySequence(parkRight);
        }
    }

}
