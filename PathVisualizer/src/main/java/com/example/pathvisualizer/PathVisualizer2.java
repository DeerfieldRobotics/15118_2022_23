package com.example.pathvisualizer;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class PathVisualizer2 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        //Trajectory strafeRight = new Trajectory()
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setDimensions(15,14)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                //.setStartPose(new Pose2d(50, 50))
                .setConstraints(35, 30, Math.toRadians(180), 2, 14.55)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(36,-60, Math.toRadians(180)))
                                        .strafeRight(2)
                                        .forward(4)

                                        //Going to first high
                                        .splineTo(new Vector2d(26,-8), Math.toRadians(55))
                                        .lineToLinearHeading(new Pose2d(14,-15,Math.toRadians(-5)))
                                        .lineToLinearHeading(new Pose2d(67,-15,Math.toRadians(-5)))

                                        //CYCLE 1
                                        .lineToSplineHeading(new Pose2d(48,-15,Math.toRadians(0)))
                                        .splineToSplineHeading(new Pose2d(32,-7, Math.toRadians(125)), Math.toRadians(140))
//                //cycle
                                        //.splineToLinearHeading(new Pose2d(32,-7,Math.toRadians(125)),Math.toRadians(130))
                                        .addTemporalMarker(5.5,()->{

                                        })

                                        .addTemporalMarker(7.75,()->{

                                        })

                                        .addTemporalMarker(8,()->{

                                        })
                                        // CYCLE 1
                                        .splineToSplineHeading(new Pose2d(46,-15, Math.toRadians(0)), Math.toRadians(0))
                                        .lineToLinearHeading(new Pose2d(66,-15, Math.toRadians(0)))


                                        .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}