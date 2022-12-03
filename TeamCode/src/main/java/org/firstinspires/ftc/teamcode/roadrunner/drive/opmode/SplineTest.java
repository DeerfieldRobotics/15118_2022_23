package org.firstinspires.ftc.teamcode.roadrunner.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

/*
 * This is an example of a more complex path to really test the tuning.
 */
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

        Trajectory path = drive.trajectoryBuilder(startPose)
                .strafeRight(20)
<<<<<<< HEAD
                .splineTo(new Vector2d(-45,30), Math.toRadians(-45))
=======
                .splineToConstantHeading(new Vector2d(-45,30), Math.toRadians(-45))
>>>>>>> parent of cae19e6 (started RR for new bot)
                .build();

        //drive.followTrajectory(strafeRight);
        drive.followTrajectory(path);
        //sleep(2000);

        /*drive.followTrajectory(
                drive.trajectoryBuilder(traj.end(), true)
                        .splineTo(new Vector2d(0, 0), Math.toRadians(180))
                        .build()
        );*/
    }
}
