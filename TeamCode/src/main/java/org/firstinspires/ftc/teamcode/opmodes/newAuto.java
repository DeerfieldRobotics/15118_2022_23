package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
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
    TrajectorySequence cycle;

    Pose2d startPose = new Pose2d(36, -63, Math.toRadians(180));

    @Override
    public void init() {
        intake = new RubberBandIntake(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "rightCam");
        slide = new Slide(hardwareMap);

        drive.setPoseEstimate(startPose);
    }
    @Override
    public void init_loop(){
        detectedTag = aprilTags.getID();
        telemetry.addData("DETECTED TAG: ", detectedTag);
        telemetry.update();
    }


    @Override
    public void start(){
        autoSequence = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(2)
                .forward(6)
                .splineTo(new Vector2d(19.5,-7.5),Math.toRadians(60))

                .strafeRight(10)
                .splineToSplineHeading(new Pose2d(40,-12,Math.toRadians(0)), Math.toRadians(0))
                .forward(19)

//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
                .build();

        cycle = drive.trajectorySequenceBuilder(autoSequence.end())
                .lineToConstantHeading(new Vector2d(43,-12))
                .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(90)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(24,-9), Math.toRadians(180))

                .strafeRight(0.1)
                .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(43,-12, Math.toRadians(0)), Math.toRadians(0))
                .lineToConstantHeading(new Vector2d(60,-12))
                .build();

        drive.followTrajectorySequenceAsync(autoSequence);
    }

    @Override
    public void loop() {
        drive.update();
        slide.update();
        intake.update();
    }
}
