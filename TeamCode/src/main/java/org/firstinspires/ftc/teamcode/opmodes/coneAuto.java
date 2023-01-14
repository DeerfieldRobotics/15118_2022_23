package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@Autonomous(name = "CONE_AUTO")
public class coneAuto extends OpMode {
    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private int detectedTag;
    private RubberBandIntake rubberBandIntake;
    private Slide s;
    TrajectorySequence path;
    Pose2d startPose = new Pose2d(36, -63, Math.toRadians(180));

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "rightCam");
        rubberBandIntake = new RubberBandIntake(hardwareMap);
        s = new Slide(hardwareMap);
    }

    public void init_loop() {
        drive.setPoseEstimate(startPose);
        detectedTag = aprilTags.getID();
        telemetry.addData("DETECTED TAG: ", detectedTag);
        telemetry.update();
    }

    public void loop() {
        drive.update();
        s.update();
        rubberBandIntake.update();
    }

    public void start() {
        TrajectorySequence highPole = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(3)
                .forward(3)
                .splineTo(new Vector2d(14,-50), Math.toRadians(115))
                .splineTo(new Vector2d(4,-30), Math.toRadians(140))
                .build();
        /*
        if (detectedTag == 3) {
            path = drive.trajectorySequenceBuilder(startPose)
                    .build();
        } else if (detectedTag == 2) {
            path = drive.trajectorySequenceBuilder(startPose)
                    .build();
        } else {
            path = drive.trajectorySequenceBuilder(startPose)
                    .build();
        }
            */
        drive.followTrajectorySequenceAsync(highPole);
    }
}
