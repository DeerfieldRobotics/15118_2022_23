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
                .setDimensions(15,14)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                //.setStartPose(new Pose2d(50, 50))
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 14.55)
                .followTrajectorySequence(drive ->

//                        drive.trajectorySequenceBuilder(new Pose2d(-34,63, Math.toRadians(180)))
//                                // RIGHT
//
//                                .strafeLeft(2)
//                                .forward(27)
//                                .back(2.5)
//                                .strafeLeft(49.5)
////                                .back(2)
//
//
//
//                                .lineToLinearHeading(new Pose2d(-47, 11, Math.toRadians(90)))
//                                .forward(4)
//                                .back(4)
//                                .lineToLinearHeading(new Pose2d(-60, 11, Math.toRadians(180)))
////                                .forward(2)
//
//
//                                .back(17)
//                                .lineToSplineHeading(new Pose2d(-34, 11, Math.toRadians(-45)))
//                                .forward(7)
//
//                                .lineToSplineHeading(new Pose2d(-34, 11, Math.toRadians(180)))
//                                .build()


                                drive.trajectorySequenceBuilder(new Pose2d(34,63, Math.toRadians(180)))
                                        // LEFT BLUE
                                        .strafeLeft(3)
                                        .forward(5)
//                                        .splineTo(new Vector2d(14,45) ,Math.toRadians(-90))
                                        .splineTo(new Vector2d(14,20) ,Math.toRadians(-75))
                                        .splineTo(new Vector2d(16,14), Math.toRadians(-63))
                                        .forward(7)
//                                        .splineToSplineHeading(new Pose2d(17,15,Math.toRadians(-30)),Math.toRadians(-30))
                                        .strafeLeft(3)
                                        .splineToSplineHeading(new Pose2d(44,11.5,Math.toRadians(0)), Math.toRadians(0))
                                        .forward(16)

                                        .lineToSplineHeading(new Pose2d(40,11.5, Math.toRadians(-30)))
                                        .lineToLinearHeading(new Pose2d(35,11.5,Math.toRadians(-135)))
                                        .forward(8)
                                        .back(5)
                                        .lineToLinearHeading(new Pose2d(35,11.5,Math.toRadians(0)))
                                        .forward(24)

//                                        .back(20)
//                                        .lineToLinearHeading(new Pose2d(35,11.5,Math.toRadians(-135)))
//                                        .forward(8)
//                                        .back(5)
//                                        .lineToLinearHeading(new Pose2d(35,11.5,Math.toRadians(0)))
//                                        .forward(24)

                                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}