package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@Autonomous(name = "NEW_AUTO")
public class TestAuto extends OpMode {
    private SampleMecanumDrive drive;
    private RubberBandIntake rubberBandIntake;
    private Slide slide;
    private AprilTags aprilTags;
    private int detectedTag;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        rubberBandIntake = new RubberBandIntake(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");
        slide = new Slide(hardwareMap);
        slide.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void start() {
        Pose2d startPose = new Pose2d(-36, -63, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence strafeLeft = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(2)
                .build();

        TrajectorySequence coneCycle = drive.trajectorySequenceBuilder(strafeLeft.end())
                .splineTo(new Vector2d(-17, -48), Math.toRadians(90))
                .splineTo(new Vector2d(-9, -29), Math.toRadians(50))

                //RAISE SLIDE
                .addTemporalMarker(0.5, () -> {
                    slide.setPower(1);
                    slide.setTarget(3200);
                })
                //OUTTAKE CONE
                .addTemporalMarker(3, () -> {
                    rubberBandIntake.updatePower(-1);
                })
                //STOP OUTTAKE
                .addTemporalMarker(4.5, () -> {
                    rubberBandIntake.updatePower(0);
                })
                //.waitSeconds(.5)
                //LOWER SLIDE
                .addTemporalMarker(5.5, () -> {
                    slide.setPower(1);
                    slide.setTarget(1000);
                })

                .setReversed(true)
                .splineTo(new Vector2d(-17, -35), Math.toRadians(270))
                .setReversed(false)

                .splineTo(new Vector2d(-23, -12), Math.toRadians(180))
                //.waitSeconds(4)
                .splineTo(new Vector2d(-47, -13), Math.toRadians(180))
                .splineTo(new Vector2d(-62, -17), Math.toRadians(180))

                .forward(4)

                //DROP INTAKE ONTO CONE STACK
                .addTemporalMarker(() -> {
                    rubberBandIntake.updatePower(1);
                    slide.setPower(.3);
                    slide.setTarget(500);
                })
                .waitSeconds(2)
                //PICKUP INTAKE
                .addTemporalMarker(() -> {
                    slide.setPower(.5);
                    slide.setTarget(1000);
                    rubberBandIntake.updatePower(0);
                })

                .build();

        /*TrajectorySequence leftPark = drive.trajectorySequenceBuilder(coneCycle.end())
                .build();

        TrajectorySequence midPark = drive.trajectorySequenceBuilder(coneCycle.end())
                .build();

        TrajectorySequence rightPark = drive.trajectorySequenceBuilder(coneCycle.end())
                .build();
        */
        drive.followTrajectorySequence(strafeLeft);
        drive.followTrajectorySequenceAsync(coneCycle);
    }

    @Override
    public void loop() {
        drive.update();
        slide.update();
        rubberBandIntake.update();
    }
}
