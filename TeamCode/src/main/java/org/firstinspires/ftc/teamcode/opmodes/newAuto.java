package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;
@Autonomous
public class newAuto extends OpMode {

    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private Slide slide;
    private RubberBandIntake intake;
    private int detectedTag;

    TrajectorySequence autoSequence;

    @Override
    public void init() {
        intake = new RubberBandIntake(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "rightWeb");
        slide = new Slide(hardwareMap);

        Pose2d startPose = new Pose2d(60, -12, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        drive.followTrajectorySequenceAsync(autoSequence);

        autoSequence = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(2)
                .forward(12)
                .splineTo(new Vector2d(19.5,-7.5),Math.toRadians(60))

                .strafeRight(10)
                .splineToSplineHeading(new Pose2d(40,-12,Math.toRadians(0)), Math.toRadians(0))
                .forward(19)
                .build();
        drive.followTrajectorySequenceAsync(autoSequence);
    }

    @Override
    public void start(){

    }

    @Override
    public void loop() {
        drive.update();
        slide.update();
    }
}
