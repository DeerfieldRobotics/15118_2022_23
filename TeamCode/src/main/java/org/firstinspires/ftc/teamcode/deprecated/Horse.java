package org.firstinspires.ftc.teamcode.deprecated;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@Autonomous(name = "Kevin Auto")
public class Horse extends LinearOpMode {
    public Drivetrain drivetrain;
    public AprilTags aprilTags;
    public ElapsedTime runtime;
    public SampleMecanumDrive drive;
    public RubberBandIntake intake;
    public Slide slide;
    public Pose2d startPose = new Pose2d(-35, 63, Math.toRadians(180));;


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        int detectedID = 0;

        while(opModeInInit()){
            detectedID = aprilTags.getID();
            telemetry.addLine("TAG: " + detectedID);
            telemetry.update();
        }

        waitForStart();

        runtime.reset();

        telemetry.addLine("Runtime:" + runtime.milliseconds());

        if (isStopRequested()) return;

        drive.setPoseEstimate(startPose);
        runtime.reset();

        TrajectorySequence terminal = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(-60, 63), Math.toRadians(180))
                .addDisplacementMarker(() -> {
                    slide.setSlideLevel(1);
                    while(runtime.milliseconds() < 1000)
                        intake.intake(-1);
                })
                .lineToConstantHeading(new Vector2d(-59 ,32))
                .build();

        TrajectorySequence lowPole = drive.trajectorySequenceBuilder(terminal.end())
                .splineToSplineHeading(new Pose2d(-58 ,12,Math.toRadians(180)),Math.toRadians(-100))
                .lineToConstantHeading(new Vector2d(-46 ,12))
                .lineToSplineHeading(new Pose2d(-36 ,12,Math.toRadians(135)))
                .forward(7)
                .lineToSplineHeading(new Pose2d(-36 ,12, Math.toRadians(180)))
                .lineToConstantHeading(new Vector2d(-58,12))

                .build();


        TrajectorySequence highPole= drive.trajectorySequenceBuilder(lowPole.end())
                .lineToConstantHeading(new Vector2d(-38,12))
                .lineToSplineHeading(new Pose2d(-36 ,12,Math.toRadians(-45)))
                .forward(7)
                .lineToSplineHeading(new Pose2d(-36 ,12,Math.toRadians(180)))
                .lineToConstantHeading(new Vector2d(-58,12))
                .build();

        drive.followTrajectorySequence(terminal);
        drive.followTrajectorySequence(lowPole);
        drive.followTrajectorySequence(highPole);
    }

    public void initialize(){
        drivetrain = new Drivetrain(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "leftCam");
        runtime = new ElapsedTime();
        drive = new SampleMecanumDrive(hardwareMap);
        intake = new RubberBandIntake(hardwareMap);
        slide = new Slide(hardwareMap);
    }

    public void park(int id){
        Trajectory parkingPath= drive.trajectoryBuilder(new Pose2d(-35,11, Math.toRadians(180)))
                .build();
        switch(id){
            case 1:
                //left
                parkingPath = drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(180)))
                        .forward(26)
                    .build();
                break;
            case 2:
                //middle

                //do nothing
                break;
            case 3:
                //right
                parkingPath = drive.trajectoryBuilder(new Pose2d(-35,11,Math.toRadians(180)))
                        .back(26)
                        .build();

                break;
            default:
                // do nothing
                break;
        }
        drive.followTrajectory(parkingPath);
    }

    public void capHigh(int intakeHeight){
        Trajectory highPole= drive.trajectoryBuilder(new Pose2d(-57,12,Math.toRadians(180)))
                .lineToConstantHeading(new Vector2d(-38,12))
                .lineToSplineHeading(new Pose2d(-36 ,12,Math.toRadians(-45)))
                .forward(7)
                .lineToSplineHeading(new Pose2d(-36 ,12,Math.toRadians(180)))
                .lineToConstantHeading(new Vector2d(-58,12))
                .build();

        drive.followTrajectory(highPole);
    }

}
