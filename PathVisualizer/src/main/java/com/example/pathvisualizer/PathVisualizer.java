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
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 62, Math.toRadians(-90)))
                                // Path 1: Terminal
                                .forward(3)
                                .turn(Math.toRadians(-90))
                                .forward(25)
                                // outtake



                                // Path 2: pick up 2nd cone
                                .strafeLeft(26)
                                .splineToSplineHeading(new Pose2d(-58 ,12,Math.toRadians(180)),Math.toRadians(-100))
                                .back(21)
                                .turn(Math.toRadians(-45))
                                .forward(6)
                                .back(6)
                                .turn(Math.toRadians(45))
                                .forward(22)
                                .back(22)
                                .turn(Math.toRadians(135))
                                .forward(7)
                                .back(7)
                                .turn(Math.toRadians(-135))
                                .forward(22)

//                                .lineToSplineHeading(new Pose2d(-57,12,Math.toRadians(180)))
//                                .back(20)
//                                .lineToSplineHeading(new Pose2d(-35,12,Math.toRadians(-45)))
//                                .forward(6)
//                                .back(6)
//                                .turn(Math.toRadians(-135))
//
//                                .lineToSplineHeading(new Pose2d(-57,12,Math.toRadians(180)))
//                                .back(20)
//                                .lineToSplineHeading(new Pose2d(-35,12,Math.toRadians(-45)))
//                                .forward(6)
//                                .back(6)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}