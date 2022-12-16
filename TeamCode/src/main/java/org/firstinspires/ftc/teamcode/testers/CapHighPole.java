package org.firstinspires.ftc.teamcode.testers;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

public class CapHighPole extends OpMode {

    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private Slide slide;
    private RubberBandIntake intake;
    private int detectedTag;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap);
        slide = new Slide(hardwareMap);
    }

    @Override
    public void start(){
        init();

        Pose2d startPose = new Pose2d(60, -11.3, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence autoSequence = drive.trajectorySequenceBuilder(startPose)
//                .strafeRight(2)
//                .forward(6)
//                .splineTo(new Vector2d(19.5,-7.5),Math.toRadians(60))
//
//                .addTemporalMarker(2.75,()->{
//                    slide.targetLevel = 3;
//                })
//
//                .addTemporalMarker(4,()->{
//                    intake.intake(-1);
//                })
//
//                .strafeRight(10)
//                .splineToSplineHeading(new Pose2d(40,-12,Math.toRadians(0)), Math.toRadians(0))
//                .forward(19)
//
//                .addTemporalMarker(4.75, ()->{
//                    intake.intake(0);
//                    slide.targetLevel =0;
//                })

                .addTemporalMarker(0,()->{
                    intake.intake(1);
                    slide.targetLevel = 1;
                })
                .addTemporalMarker(0.2,()->{
                    intake.intake(0);
                })

                .lineToConstantHeading(new Vector2d(43,-12))
                .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(90)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(24,-9), Math.toRadians(180))
                .addTemporalMarker(1.3,()->{
                    slide.targetLevel = 3;
                })

                .addTemporalMarker(3,()->{
                    intake.intake(-1);
                })

                .strafeRight(0.1)
                .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(43,-12, Math.toRadians(0)), Math.toRadians(0))
                .lineToConstantHeading(new Vector2d(60,-12))

                .addTemporalMarker(4,()->{
                    intake.intake(0);
                    slide.targetLevel = 0;
                })

                .addTemporalMarker(6,()->{
                    intake.intake(1);
                })

                .build();

        drive.followTrajectorySequenceAsync(autoSequence);
    }

    @Override
    public void loop() {
        drive.update();

        slide.update();
    }
}
