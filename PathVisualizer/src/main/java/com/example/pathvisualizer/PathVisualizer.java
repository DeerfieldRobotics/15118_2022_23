package com.example.pathvisualizer;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class PathVisualizer {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        //Trajectory strafeRight = new Trajectory()
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setDimensions(15,14)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                //.setStartPose(new Pose2d(50, 50))
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 13.5)
                .followTrajectorySequence(drive ->
                    drive.trajectorySequenceBuilder(new Pose2d(59,-12, Math.toRadians(0)))
                            .back(20)
                            .lineToSplineHeading(new Pose2d(30, -12, Math.toRadians(90)))
                            .setTangent(150)
                            .splineToSplineHeading(new Pose2d(24, -11, Math.toRadians(90)), Math.toRadians(150))


//                            .splineTo(new Vector2d(17, -48), Math.toRadians(90))
//                            .splineTo(new Vector2d(9, -29), Math.toRadians(130))
//
//                            //RAISE SLIDE
//
//                            //OUTTAKE CONE
//
//
//                            .setReversed(true)
//                            .splineTo(new Vector2d(17, -35), Math.toRadians(270))
//                            .setReversed(false)
//
//                            .splineTo(new Vector2d(23, -12), Math.toRadians(0))
//                            //.waitSeconds(4)
//                            //.splineTo(new Vector2d(-47, -13), Math.toRadians(180))
//                            .lineToSplineHeading(new Pose2d(61.75,-17,Math.toRadians(2)))
//                            // .splineTo(new Vector2d(-63, -17.5), Math.toRadians(180))
//
//                            .forward(4)
//
//                            //START OF CONE CYCEL
//                            //DROP INTAKE ONTO CONE STACK
//
//                            .waitSeconds(2)
//                            //PICKUP INTAKE
//
//                            .back(5)
//                            //SPLINE TO POLE
//                            .splineToSplineHeading(new Pose2d(28.5,-7.5,Math.toRadians(100)),Math.toRadians(150))
//
//
//
//
//                            .back(3)
//                            .lineToSplineHeading(new Pose2d(60,-13,Math.toRadians(90)))
                            .build()


                            );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}