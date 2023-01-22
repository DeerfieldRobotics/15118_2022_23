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
import org.firstinspires.ftc.robotcore.external.Telemetry;
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
    private TrajectorySequence leftPark, midPark, rightPark;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        rubberBandIntake = new RubberBandIntake(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");

        slide = new Slide(hardwareMap);
        slide.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);


    }
    public void init_loop(){
        detectedTag = aprilTags.getID();
        telemetry.addData("DETECTED ID: ", detectedTag);
        telemetry.update();
    }

    public void start() {
        Pose2d startPose = new Pose2d(-36, -63, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence strafeLeft = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(2)
                .build();



        leftPark = drive.trajectorySequenceBuilder(strafeLeft.end())
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
                //.splineTo(new Vector2d(-47, -13), Math.toRadians(180))
                .lineToSplineHeading(new Pose2d(-62,-15,Math.toRadians(182)))
                // .splineTo(new Vector2d(-63, -17.5), Math.toRadians(180))

                .forward(4)

                //START OF CONE CYCEL
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
                .back(5)
                //SPLINE TO POLE
                .splineToSplineHeading(new Pose2d(-28.5,-7.5,Math.toRadians(80)),Math.toRadians(30))

                .addTemporalMarker(13, () -> {
                    slide.setPower(1);
                    slide.setTarget(3200);
                })
                .addTemporalMarker(15.2, ()->{
                    rubberBandIntake.updatePower(-1);
                })
                .addTemporalMarker(15.7,() ->{
                    rubberBandIntake.updatePower(0);
                })

                .back(3)
                .addTemporalMarker(() -> {
                    slide.setTarget(0);
                    slide.setPower(0);
                })
                //park
                .lineToSplineHeading(new Pose2d(-60,-13,Math.toRadians(90)))
                .build();

        midPark = drive.trajectorySequenceBuilder(strafeLeft.end())

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
                //.splineTo(new Vector2d(-47, -13), Math.toRadians(180))
                .lineToSplineHeading(new Pose2d(-62,-15,Math.toRadians(182)))
                // .splineTo(new Vector2d(-63, -17.5), Math.toRadians(180))

                .forward(4)

                //START OF CONE CYCEL
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
                .back(5)
                //SPLINE TO POLE
                .splineToSplineHeading(new Pose2d(-28.5,-7.5,Math.toRadians(80)),Math.toRadians(30))

                .addTemporalMarker(13, () -> {
                    slide.setPower(1);
                    slide.setTarget(3200);
                })
                .addTemporalMarker(15.2, ()->{
                    rubberBandIntake.updatePower(-1);
                })
                .addTemporalMarker(15.7,() ->{
                    rubberBandIntake.updatePower(0);
                })

                .back(3)
                .addTemporalMarker(() -> {
                    slide.setTarget(0);
                    slide.setPower(0);
                })
                //park
                .lineToSplineHeading(new Pose2d(-40,-13,Math.toRadians(90)))
                .build();

        rightPark = drive.trajectorySequenceBuilder(strafeLeft.end())


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
                //.splineTo(new Vector2d(-47, -13), Math.toRadians(180))
                .lineToSplineHeading(new Pose2d(-62,-15,Math.toRadians(182)))
                // .splineTo(new Vector2d(-63, -17.5), Math.toRadians(180))

                .forward(4)

                //START OF CONE CYCEL
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
                .back(5)
                //SPLINE TO POLE
                .splineToSplineHeading(new Pose2d(-28.5,-7.5,Math.toRadians(80)),Math.toRadians(30))

                .addTemporalMarker(13, () -> {
                    slide.setPower(1);
                    slide.setTarget(3200);
                })
                .addTemporalMarker(15.2, ()->{
                    rubberBandIntake.updatePower(-1);
                })
                .addTemporalMarker(15.7,() ->{
                    rubberBandIntake.updatePower(0);
                })

                .back(3)
                .addTemporalMarker(() -> {
                    slide.setTarget(0);
                    slide.setPower(0);
                })
                .lineToSplineHeading(new Pose2d(-14,-13, Math.toRadians(90)))
                .build();

        drive.followTrajectorySequence(strafeLeft);


        if(detectedTag == 1){
            drive.followTrajectorySequenceAsync(leftPark);
        } else if(detectedTag == 2){
            drive.followTrajectorySequenceAsync(midPark);
        } else if(detectedTag == 3){
            drive.followTrajectorySequenceAsync(rightPark);
        }


    }

    @Override
    public void loop() {
        drive.update();
        slide.update();
        rubberBandIntake.update();
    }
}
