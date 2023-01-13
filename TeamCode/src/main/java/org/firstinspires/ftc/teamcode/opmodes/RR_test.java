package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;

@Autonomous
public class RR_test extends LinearOpMode {
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

        Pose2d startPose = new Pose2d(36,-63, Math.toRadians(180));
        drive.setPoseEstimate(startPose);

        TrajectorySequence capInitial = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(2)
                .forward(6)
                .splineTo(new Vector2d(19.5,-7.5),Math.toRadians(60))

                .strafeRight(10)
                .splineToSplineHeading(new Pose2d(40,-12,Math.toRadians(0)), Math.toRadians(0))
                .forward(19)
                .build();

        drive.followTrajectorySequenceAsync(capInitial);
    }
}
