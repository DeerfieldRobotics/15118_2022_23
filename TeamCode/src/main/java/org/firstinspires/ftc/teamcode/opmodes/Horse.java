package org.firstinspires.ftc.teamcode.opmodes;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@Autonomous(name = "your mom deez nuts")
public class Horse extends LinearOpMode {
    public Drivetrain drivetrain;
    public AprilTags aprilTags;
    public ElapsedTime runtime;
    public SampleMecanumDrive drive;
    public RubberBandIntake intake;
    public Slide slide;
    public Pose2d startPose;


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        int detectedID = 0;

        while(opModeInInit()){
            detectedID = aprilTags.getID();
            drive.setPoseEstimate(startPose);
        }

        waitForStart();

        runtime.reset();

        telemetry.addLine("Runtime:" + runtime.milliseconds());

        if (isStopRequested()) return;

        Trajectory forwards = drive.trajectoryBuilder(startPose)
                .forward(3)
                .addDisplacementMarker(() -> {
                    drive.turn(-90);
                })
                .build();

        runtime.reset();

        Trajectory terminal = drive.trajectoryBuilder(new Pose2d(-35, 59, Math.toRadians(180)))
                .forward(25)
                .addDisplacementMarker(() -> {
                    while(runtime.milliseconds() <= 1500){
                        intake.intake(-1);
                    }
                })
                .build();

        runtime.reset();
        
        Trajectory low = drive.trajectoryBuilder(new Pose2d(-60, 59, Math.toRadians(180)))
                .lineToSplineHeading(new Pose2d(-57,11,Math.toRadians(180)))
                .addDisplacementMarker(40, () -> {
                    slide.setSlideLevel(1);
                    slide.setPower(1);
                })
                .addDisplacementMarker(44, () -> {
                    slide.setSlideLevel(1);
                    slide.setPower(1);
                    intake.intake(1);
                })
                .lineToSplineHeading(new Pose2d(-35,11,Math.toRadians(135)))
                .addDisplacementMarker(20, () -> {
                    slide.setSlideLevel(1);
                    slide.setPower(1);
                })
                .forward(8)
                .addTemporalMarker(10, () -> {
                    while(runtime.milliseconds() >= 5 && runtime.milliseconds() <= 6000){
                        intake.intake(-1);
                    }
                })
                .back(8)
                .build();
        
//        Trajectory back1 = drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(135)))
//                .lineToSplineHeading(new Pose2d(-57,11,Math.toRadians(180)))
//                .back(20)
//                .build();
//
//        Trajectory high1 = drive.trajectoryBuilder(new Pose2d(-37,11,Math.toRadians(180)))
//                .lineToSplineHeading(new Pose2d(-35,11,Math.toRadians(-45)))
//                .forward(6)
//                .back(6)
//                .addDisplacementMarker(() -> {
//                    drive.turn(-135);
//                })
//                .build();
//
//        Trajectory back2 = drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(180)))
//                .lineToSplineHeading(new Pose2d(-57,11,Math.toRadians(180)))
//                .back(20)
//                .build();
//
//        Trajectory high2 = drive.trajectoryBuilder(new Pose2d(-37,11,Math.toRadians(180)))
//                .lineToSplineHeading(new Pose2d(-35,11,Math.toRadians(-45)))
//                .forward(6)
//                .back(6)
//                .addDisplacementMarker(() -> {
//                    drive.turn(-135);
//                })
//                .build();

        drive.followTrajectory(forwards);
        drive.followTrajectory(terminal);
        drive.followTrajectory(low);
//        drive.followTrajectory(back1);
//        drive.followTrajectory(high1);
//        drive.followTrajectory(back2);
//        drive.followTrajectory(high2);


    }

    public void initialize(){
        drivetrain = new Drivetrain(hardwareMap);
        aprilTags = new AprilTags(hardwareMap);
        runtime = new ElapsedTime();
        drive = new SampleMecanumDrive(hardwareMap);
        intake = new RubberBandIntake(hardwareMap);
        slide = new Slide(hardwareMap);

        startPose = new Pose2d(-35, 62, Math.toRadians(-90));
    }

    public void park(int id){
        Trajectory parkingPath= drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(-45)))
                .build();
        switch(id){
            case 1:
                //left
                parkingPath = drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(-45)))

                    .build();
                break;
            case 2:
                //middle
                parkingPath = drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(-45)))
                        .build();
                break;
            case 3:
                //right
                parkingPath = drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(-45)))
                        .build();

                break;
            default:
                break;
        }
        drive.followTrajectory(parkingPath);
    }

}
