package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@Autonomous(name = "RR_RED_RIGHT")
public class RR_RED_RIGHT extends LinearOpMode {
    private SampleMecanumDrive drive;
    private RubberBandIntake rubberBandIntake;
    private Slide slide;
    //private AprilTags aprilTags;
    private int detectedTag;

    public void initialize() {
        drive = new SampleMecanumDrive(hardwareMap);
        rubberBandIntake = new RubberBandIntake(hardwareMap);

        slide = new Slide(hardwareMap);
        slide.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);

        /*while(opModeInInit()) {
            detectedTag = aprilTags.getID();
            telemetry.addData("DETECTED TAG: ", detectedTag);
        }*/
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        if(isStopRequested()) {
            return;
        }

        Pose2d startPose = new Pose2d(30, -60, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence coneCycle = drive.trajectorySequenceBuilder(startPose)
                //.splineTo(new Vector2d(58,-60), Math.toRadians(0))
                .forward(20)
                .addTemporalMarker(1, () -> {
                    rubberBandIntake.intake(-0.5);
                })
                .addTemporalMarker(2, () -> {
                    rubberBandIntake.intake(0);
                })
                .setReversed(true)
                .splineTo(new Vector2d(44, -60), Math.toRadians(180))
                .setReversed(false)
                .splineTo(new Vector2d(58, -12), Math.toRadians(90))
                .turn(Math.toRadians(-90))
                .build();
        /*
        Trajectory parkLeft = drive.trajectoryBuilder(coneCycle.end())
                //TODO: CREATE LEFT PARKING TRAJECTORY
                .build();

        Trajectory parkMiddle = drive.trajectoryBuilder(coneCycle.end())
                //TODO: CREATE MIDDLE PARKING TRAJECTORY
                .build();

        Trajectory parkRight = drive.trajectoryBuilder(coneCycle.end())
                //TODO: CREATE RIGHT PARKING TRAJECTORY
                .build();
        */
        drive.followTrajectorySequence(coneCycle);
        /*
        if(detectedTag == 1) {
            drive.followTrajectory(parkLeft);
        } else if(detectedTag == 2) {
            drive.followTrajectory(parkMiddle);
        } else if (detectedTag == 3) {
            drive.followTrajectory(parkRight);
        }
        */
    }

}
