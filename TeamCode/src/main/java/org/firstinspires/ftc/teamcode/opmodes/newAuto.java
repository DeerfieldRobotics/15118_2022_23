package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;
@Autonomous(name = "peppa pig")
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
        slide.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);
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
                .forward(4)

                .addTemporalMarker(2,()->{
                    slide.setPower(1);
                    slide.setTarget(Slide.HIGH);

                })

                .splineTo(new Vector2d(24,-4), Math.toRadians(60))
                .addTemporalMarker(()->{
                    intake.updatePower(-1);
                })

                .lineToSplineHeading(new Pose2d(14,-10,Math.toRadians(0)))
                .lineToSplineHeading(new Pose2d(65,-12,Math.toRadians(0)))


                .addTemporalMarker(4,()->{
                    intake.updatePower(0);
                })


                //might change
                .addTemporalMarker(5, () -> {
                    slide.setPower(1);
                    slide.setTarget(700);
                })

                //cycle
                .addTemporalMarker(7,()->{
                    intake.updatePower(1);
                })
                .lineToConstantHeading(new Vector2d(43,-12))
//              .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(135)), Math.toRadians(135))
                .splineToSplineHeading(new Pose2d(28.5,-7, Math.toRadians(125)), Math.toRadians(125))
//                .addTemporalMarker()
                .waitSeconds(0.1)
                .back(1)
//               .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0);
                .addTemporalMarker(8.5,()->{
                    slide.setPower(1);
                    slide.setTarget(Slide.HIGH);
                    intake.updatePower(0);
                })
                .splineToSplineHeading(new Pose2d(46,-12, Math.toRadians(0)), Math.toRadians(0))
                .addTemporalMarker(11,()->{
                    intake.updatePower(-1);
                })
                .addTemporalMarker(12,()->{
                    slide.setPower(1);
                    intake.updatePower(0);
                    slide.setTarget(500);
                })
                .lineToConstantHeading(new Vector2d(65,-12))

//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
//                .addDisplacementMarker(() -> drive.followTrajectoryAsync(cycle))
                .build();

        cycle = drive.trajectorySequenceBuilder(autoSequence.end())
                .lineToConstantHeading(new Vector2d(43,-12))
//                                        .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(135)), Math.toRadians(135))
                .splineToSplineHeading(new Pose2d(28.5,-7, Math.toRadians(125)), Math.toRadians(125))

                .waitSeconds(0.1)
                .back(3)
//               .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0))
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
