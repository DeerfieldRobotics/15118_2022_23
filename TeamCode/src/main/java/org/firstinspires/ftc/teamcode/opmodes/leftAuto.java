package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;

@Autonomous(name = "LEFT_AUTO")
public class leftAuto extends LinearOpMode {
    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private int detectedTag;
    private RubberBandIntake rubberBandIntake;


    public void initialize() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");
        rubberBandIntake = new RubberBandIntake(hardwareMap);

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

        TrajectorySequence groundStation = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(9)
                .turn(Math.toRadians(180))
                .forward(24)
                //.strafeRight(2)
                .addTemporalMarker(4, () -> {
                    rubberBandIntake.intake(-1);
                })
                .addTemporalMarker(6, () -> {
                    rubberBandIntake.intake(0);
                })
                .back(3)
                .build();

        TrajectorySequence parkLeft = drive.trajectorySequenceBuilder(groundStation.end())
                //TODO: CREATE LEFT PARKING TRAJECTORY
                .strafeRight(30)
                .build();

        TrajectorySequence parkMiddle = drive.trajectorySequenceBuilder(groundStation.end())
                //TODO: CREATE MIDDLE PARKING TRAJECTORY
                .back(21)
                .strafeRight(27)
                .build();

        TrajectorySequence parkRight = drive.trajectorySequenceBuilder(groundStation.end())
                //TODO: CREATE RIGHT PARKING TRAJECTORY
                .back(45)
                .strafeRight(32)
                .build();

        drive.followTrajectorySequence(groundStation);
        if (detectedTag == 1) {
            drive.followTrajectorySequence(parkLeft);
        } else if (detectedTag == 2) {
            drive.followTrajectorySequence(parkMiddle);
        } else if (detectedTag == 3) {
            drive.followTrajectorySequence(parkRight);
        }
    }

}
