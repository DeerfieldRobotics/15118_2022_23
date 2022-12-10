package org.firstinspires.ftc.teamcode.roadrunner.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Disabled
@Autonomous(group = "drive")
public class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        Pose2d startPose = new Pose2d(-35, 60, Math.toRadians(-90));
        drive.setPoseEstimate(startPose);

        Trajectory strafeRight = drive.trajectoryBuilder(startPose)
                .strafeRight(25)
                .build();

        Trajectory path1 = drive.trajectoryBuilder(startPose)
                .lineToSplineHeading(new Pose2d(-55,60,Math.toRadians(-180)))
                .build();
        Trajectory path2 = drive.trajectoryBuilder(new Pose2d(-55, 60, Math.toRadians(-180)))
                .lineToSplineHeading(new Pose2d(-60,17, Math.toRadians(-135)))
                .build();
        Trajectory path3 = drive.trajectoryBuilder(new Pose2d(-60,17, Math.toRadians(-135)))
                .lineToSplineHeading(new Pose2d(-30,8, Math.toRadians(-45)))
                .build();

        //drive.followTrajectory(strafeRight);
        drive.followTrajectory(path1);
        drive.followTrajectory(path2);
        drive.followTrajectory(path3);

        sleep(2000);

//        drive.followTrajectory(
//                drive.trajectoryBuilder(path.end(), true)
//                        .splineTo(new Vector2d(0, 0), Math.toRadians(180))
//                        .build()
//        );
    }
}
