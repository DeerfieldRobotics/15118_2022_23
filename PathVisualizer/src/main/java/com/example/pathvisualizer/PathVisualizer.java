package com.example.pathvisualizer;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class PathVisualizer {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        //Trajectory strafeRight = new Trajectory()
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setDimensions(15,17)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                //.setStartPose(new Pose2d(50, 50))
                .setConstraints(15, 30, Math.toRadians(180), Math.toRadians(180), 14.55)
                .followTrajectorySequence(drive ->

                        drive.trajectorySequenceBuilder(new Pose2d(36,-60.4, Math.toRadians(90)))
                                //.splineTo(new Pose2d(60,-60.4, Math.toRadians(90)))
                                .splineToConstantHeading(new Vector2d(60,-60.4), Math.toRadians(90))
                        /*
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 60, Math.toRadians(-90)))

                                .strafeRight(15)
                                .splineToSplineHeading(new Pose2d(-52,28,Math.toRadians(-45)), Math.toRadians(-45))
                                //.back(5)
                                .splineToSplineHeading(new Pose2d(-52-(5/Math.sqrt(2)),27.5+(5/Math.sqrt(2)),Math.toRadians(-125)), Math.toRadians(-135))
                                .splineToConstantHeading(new Vector2d(-65,20), Math.toRadians(-125))
                                .strafeRight(20)
                                .splineToConstantHeading(new Vector2d(-45,30), Math.toRadians(-45))

                                .strafeRight(20)
                                .splineToConstantHeading(new Vector2d(-45,30), Math.toRadians(-45))
*/
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}