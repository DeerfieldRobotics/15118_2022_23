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

@Autonomous(name = "TestAuto")
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
                .build();

        drive.followTrajectorySequenceAsync(coneCycle);
    }

    @Override
    public void loop() {
        drive.update();
        slide.update();
    }
}
