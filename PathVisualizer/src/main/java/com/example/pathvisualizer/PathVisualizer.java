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
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 14.55)
                .followTrajectorySequence(drive ->

                        drive.trajectorySequenceBuilder(new Pose2d(56,-7, Math.toRadians(0)))
                                .lineToConstantHeading(new Vector2d(46,-7))

                                .lineToSplineHeading(new Pose2d(36,-7,Math.toRadians(-45)))
                                .forward(7)
                                .lineToSplineHeading(new Pose2d(36,-7,Math.toRadians(0)))
                                .lineToConstantHeading(new Vector2d(56,-7))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}