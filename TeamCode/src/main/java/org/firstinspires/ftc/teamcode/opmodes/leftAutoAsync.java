package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@Autonomous(name = "LEFT_AUTO_ASYNC")
public class leftAutoAsync extends OpMode {
    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private int detectedTag;
    private RubberBandIntake rubberBandIntake;
    private Slide s;
    TrajectorySequence path;
    Pose2d startPose = new Pose2d(38, -60, Math.toRadians(0));

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "rightCam");
        rubberBandIntake = new RubberBandIntake(hardwareMap);
        s = new Slide(hardwareMap);
    }

    public void init_loop(){
        drive.setPoseEstimate(startPose);
        detectedTag = aprilTags.getID();
        telemetry.addData("DETECTED TAG: ", detectedTag);
        telemetry.update();
    }

    public void loop(){
        drive.update();
        s.update();
        rubberBandIntake.update();
    }


//    public void initialize() {
//        drive = new SampleMecanumDrive(hardwareMap);
//        aprilTags = new AprilTags(hardwareMap, "rightCam");
//        rubberBandIntake = new RubberBandIntake(hardwareMap);
//        s = new Slide(hardwareMap);
//
//        while(opModeInInit()) {
//            detectedTag = aprilTags.getID();
//            telemetry.addData("DETECTED TAG: ", detectedTag);
//            telemetry.update();
//        }
//    }

    public void start() {
        if (detectedTag == 1) {
            path = drive.trajectorySequenceBuilder(startPose)
                    .strafeRight(2)
                    .forward(28)
                    .addTemporalMarker(3, () -> {
                        telemetry.update();
                        rubberBandIntake.updatePower(-1);
                    })
                    .addTemporalMarker(5, () -> {
                        rubberBandIntake.updatePower(0);
                        telemetry.update();
                        s.updateTarget(-600);
                    })
                    //park
                    .waitSeconds(1)
                    .back(5)
                    .strafeRight(30)
                    .forward(5)
                    .build();
        } else if (detectedTag == 2) {
            path = drive.trajectorySequenceBuilder(startPose)
                    .strafeRight(2)
                    .forward(28)
                    .addTemporalMarker(3, () -> {
                        telemetry.update();
                        rubberBandIntake.updatePower(-1);
                    })

                    .addTemporalMarker(5, () -> {
                        rubberBandIntake.updatePower(0);
                        telemetry.update();
                        s.updateTarget(-600);
                    })
                    //park
                    .waitSeconds(2.5)
                    .back(27)
                    .strafeRight(35)
                    .build();
        } else{
            path = drive.trajectorySequenceBuilder(startPose)
                    .strafeRight(2)
                    .forward(28)
                    .addTemporalMarker(3, () -> {
                        telemetry.update();
                        rubberBandIntake.updatePower(-1);
                    })
                    .addTemporalMarker(5, () -> {
                        rubberBandIntake.updatePower(0);
                        telemetry.update();
                        s.updateTarget(-600);
                    })
                    //park
                    .waitSeconds(2.5)
                    .back(47)
                    .strafeRight(32)
                    .build();
        }

        drive.followTrajectorySequenceAsync(path);

    }



}
