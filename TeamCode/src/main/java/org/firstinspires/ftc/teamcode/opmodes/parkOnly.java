package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;

@Autonomous(name = "PARKING_ONLY")
public class parkOnly extends LinearOpMode {
    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private int detectedTag;


    public void initialize() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");

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
                .strafeLeft(2)
                .back(19)
                .strafeLeft(35)
                .build();

        TrajectorySequence parkMiddle = drive.trajectorySequenceBuilder(startPose)
                //TODO: CREATE MIDDLE PARKING TRAJECTORY
                .strafeLeft(35)
                .build();


        TrajectorySequence parkRight = drive.trajectorySequenceBuilder(startPose)
                //TODO: CREATE RIGHT PARKING TRAJECTORY
                .strafeLeft(2)
                .forward(21)
                .strafeLeft(35)
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
