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

@Autonomous(name = "CONE_AUTO_LEFT")
public class coneAutoLeftSide extends OpMode {
    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private int detectedTag;
    private RubberBandIntake rubberBandIntake;
    private Slide s;
    TrajectorySequence path;
    Pose2d startPose = new Pose2d(-36, -63, Math.toRadians(180));

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");
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


        if (detectedTag == 1) {
            path = drive.trajectorySequenceBuilder(startPose)
                    .strafeLeft(3)
                    .forward(3)
                    .splineTo(new Vector2d(-14,-50), Math.toRadians(65))
                    .splineTo(new Vector2d(-4,-33), Math.toRadians(35))
                    .addTemporalMarker(3, () -> {
                        s.updateTarget(-3400);
                    })

                    .forward(2)
                    .addTemporalMarker(4.25,() ->{
                        telemetry.update();
                        rubberBandIntake.updatePower(-1);
                    })
                    .addTemporalMarker(4.75, ()-> {
                        telemetry.update();
                        rubberBandIntake.updatePower(0);
                    })
                    .waitSeconds(0.5)
                    .back(10)
                    .turn(Math.toRadians(55))
                    .addDisplacementMarker(() ->{
                        s.updateTarget(0);
                    })
                    .strafeLeft(27)
                    .build();
        } else if (detectedTag == 2) {
            path = drive.trajectorySequenceBuilder(startPose)
                    .strafeLeft(3)
                    .forward(3)
                    .splineTo(new Vector2d(-14,-50), Math.toRadians(65))
                    .splineTo(new Vector2d(-4,-33), Math.toRadians(35))
                    .addTemporalMarker(3, () -> {
                        s.updateTarget(-3400);
                    })

                    .forward(2)
                    .addTemporalMarker(4.25,() ->{
                        telemetry.update();
                        rubberBandIntake.updatePower(-1);
                    })
                    .addTemporalMarker(4.75, ()-> {
                        telemetry.update();
                        rubberBandIntake.updatePower(0);
                    })
                    .waitSeconds(0.5)
                    .back(10)
                    .turn(Math.toRadians(55))
                    .addDisplacementMarker(() ->{
                        s.updateTarget(0);
                    })
                    .strafeLeft(15)
                    .build();
        } else {
            path = drive.trajectorySequenceBuilder(startPose)
                    .strafeLeft(3)
                    .forward(3)
                    .splineTo(new Vector2d(-14,-50), Math.toRadians(65))
                    .splineTo(new Vector2d(-4,-33), Math.toRadians(35))
                    .addTemporalMarker(3, () -> {
                        s.updateTarget(-3400);
                    })

                    .forward(2)
                    .addTemporalMarker(4.25,() ->{
                        telemetry.update();
                        rubberBandIntake.updatePower(-1);
                    })
                    .addTemporalMarker(4.75, ()-> {
                        telemetry.update();
                        rubberBandIntake.updatePower(0);
                    })
                    .waitSeconds(0.5)
                    .back(10)
                    .turn(Math.toRadians(55))
                    .addDisplacementMarker(() ->{
                        s.updateTarget(0);
                    })
                    .strafeLeft(3)
                    .build();
        }

        drive.followTrajectorySequenceAsync(path);
    }
}
