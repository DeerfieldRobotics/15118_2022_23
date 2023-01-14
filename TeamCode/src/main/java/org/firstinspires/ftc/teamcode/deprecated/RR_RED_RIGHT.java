package org.firstinspires.ftc.teamcode.deprecated;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@Autonomous(name = "RR_RIGHT")
public class RR_RED_RIGHT extends LinearOpMode {
    private SampleMecanumDrive drive;
    private RubberBandIntake rubberBandIntake;
    private Slide slide;
    private AprilTags aprilTags;
    private int detectedTag;

    public void initialize() {
        drive = new SampleMecanumDrive(hardwareMap);
        rubberBandIntake = new RubberBandIntake(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");
        slide = new Slide(hardwareMap);
        slide.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);

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

        if(isStopRequested()) {
            return;
        }

        Pose2d startPose = new Pose2d(38, -60, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence coneCycle = drive.trajectorySequenceBuilder(startPose)
                .forward(21)
                .addTemporalMarker(1, () -> {
                    rubberBandIntake.intake(-1);
                })
                .addTemporalMarker(2.25, () -> {
                    rubberBandIntake.intake(0);
                })
                .back(2)
                .strafeLeft(20)
                .splineTo(new Vector2d(56, -7), Math.toRadians(90))


                //GET FROM CONE STACK
                .addTemporalMarker(7, () -> {
                    while(slide.s.getCurrentPosition() < 565) {
                        slide.s.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.BRAKE);
                        slide.s.setTargetPosition(565);
                        slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        slide.s.setPower(1);
                    }
                    slide.setPower(0.1);
                })
                .forward(2)
                .back(15)

                //LOW

                /*
                .lineToConstantHeading(new Vector2d(46,-7))
                //SLIDE -> LOW
                .lineToSplineHeading(new Pose2d(36,-7,Math.toRadians(-45)))
                .forward(7)
                //OUTTAKE
                .lineToSplineHeading(new Pose2d(36,-7,Math.toRadians(0)))
                //SLIDE DOWN
                .lineToConstantHeading(new Vector2d(56,-7))
                // INTAKE
                */

                //Pick up another cone
                /*
                .addTemporalMarker(10, () -> {
                    while(slide.s.getCurrentPosition() < 565) {
                        slide.s.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.BRAKE);
                        slide.s.setTargetPosition(565);
                        slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        slide.s.setPower(1);
                    }
                    slide.setPower(0.1);
                })

                */
                // HIGH

                /*
                .lineToConstantHeading(new Vector2d(44,-7))
                //SLIDE -> HIGH
                .lineToSplineHeading(new Pose2d(36 ,-7,Math.toRadians(-135)))
                .forward(7)
                // OUTTAKE
                .lineToSplineHeading(new Pose2d(36 ,-7,Math.toRadians(0)))
                //SLIDE DOWN
                .lineToConstantHeading(new Vector2d(56,-7))

                //INTAKE
                */

                //PARK

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
