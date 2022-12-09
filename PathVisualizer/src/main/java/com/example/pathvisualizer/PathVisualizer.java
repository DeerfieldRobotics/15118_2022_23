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
                .setConstraints(20, 30, Math.toRadians(180), Math.toRadians(180), 14.55)
                .followTrajectorySequence(drive ->

                        drive.trajectorySequenceBuilder(new Pose2d(35,-60, Math.toRadians(0)))
                                //.splineTo(new Vector2d(58,-60), Math.toRadians(0))
                                .forward(20)
                                .setReversed(true)
                                .splineTo(new Vector2d(44, -60), Math.toRadians(180))
                                .setReversed(false)
                                .splineTo(new Vector2d(58, -12), Math.toRadians(90))
                                .turn(Math.toRadians(-90))
                                //.splineToSplineHeading(new Vector2d(65, -12), Math.toRadians(0))
                                //.splineToSplineHeading(new Pose2d(58, -10), Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}