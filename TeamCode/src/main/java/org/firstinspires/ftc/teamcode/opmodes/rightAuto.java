package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;

@Autonomous(name = "RIGHT_AUTO")
public class rightAuto extends LinearOpMode {
    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private int detectedTag;
    private RubberBandIntake rubberBandIntake;


    public void initialize() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");
        rubberBandIntake = new RubberBandIntake(hardwareMap);

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


        if (isStopRequested()) {
            return;
        }

        Pose2d startPose = new Pose2d(38, -60, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence groundStation = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(2)
                .forward(23)
                .addTemporalMarker(0.75, () -> {
                    telemetry.addLine("TEMPORAL MARKER");
                    telemetry.update();
                    rubberBandIntake.intake(-1);
                })
                .addTemporalMarker(2.25, () -> {
                    telemetry.addLine("TEMPORAL MARKER 2");
                    telemetry.update();
                    rubberBandIntake.intake(0);
                })
                .waitSeconds(1)
                .build();

        TrajectorySequence parkLeft = drive.trajectorySequenceBuilder(groundStation.end())
                //TODO: CREATE LEFT PARKING TRAJECTORY
                .back(44)
                .strafeLeft(35)
                .build();

        TrajectorySequence parkMiddle = drive.trajectorySequenceBuilder(groundStation.end())
                //TODO: CREATE MIDDLE PARKING TRAJECTORY
                .back(22)
                .strafeLeft(35)
                .build();


        TrajectorySequence parkRight = drive.trajectorySequenceBuilder(groundStation.end())
                //TODO: CREATE RIGHT PARKING TRAJECTORY
                .back(3)
                .strafeLeft(35)
                .build();

        drive.followTrajectorySequence(groundStation);
        if (detectedTag == 1) {
            drive.followTrajectorySequence(parkLeft);
        } else if (detectedTag == 2) {
            drive.followTrajectorySequence(parkMiddle);
        } else if (detectedTag == 3) {
            drive.followTrajectorySequence(parkRight);
        }
    }

}
