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
                .setConstraints(35, 40, Math.toRadians(180), Math.toRadians(180), 14.55)
                .followTrajectorySequence(drive ->

//                        drive.trajectorySequenceBuilder(new Pose2d(-34,63, Math.toRadians(0)))
//                                // BLUE RIGHT
//
//                                .strafeRight(3)
//                                .lineToSplineHeading(new Pose2d(-28,60, Math.toRadians(0)))
//                                .splineTo(new Vector2d(-16,14), Math.toRadians(-117))
//                                .forward(7)
//
//
//                                .strafeRight(3)
//                                .splineToSplineHeading(new Pose2d(-44,11.5,Math.toRadians(180)), Math.toRadians(180))
//                                .forward(16)
//
//
//                                .lineToSplineHeading(new Pose2d(-40,11.5, Math.toRadians(45)))
//                                .splineToSplineHeading(new Pose2d(-29,6, Math.toRadians(-45)), Math.toRadians(-45))
//                                .lineToSplineHeading(new Pose2d(-47,11,Math.toRadians(90)))
//                                .splineToSplineHeading(new Pose2d(-60,11.5, Math.toRadians(180)), Math.toRadians(180))
//
//                                .lineToSplineHeading(new Pose2d(-40,11.5, Math.toRadians(45)))
//                                .splineToSplineHeading(new Pose2d(-29,6, Math.toRadians(-45)), Math.toRadians(-45))
//
//                                .build()
//                );


//                                drive.trajectorySequenceBuilder(new Pose2d(34,63, Math.toRadians(180)))
//                                        //  BLUE LEFT
//                                        .strafeLeft(3)
//                                        .lineToSplineHeading(new Pose2d(28,60, Math.toRadians(180)))
//                                        .splineTo(new Vector2d(16,14), Math.toRadians(-63))
//                                        .forward(7)
//
//
//                                        .strafeLeft(3)
//                                        .splineToSplineHeading(new Pose2d(44,11.5,Math.toRadians(0)), Math.toRadians(0))
//                                        .forward(16)
//
//                                        .waitSeconds(0.2)
//
//                                        .lineToSplineHeading(new Pose2d(40,11.5, Math.toRadians(135)))
//                                        .splineToSplineHeading(new Pose2d(29,6, Math.toRadians(-135)), Math.toRadians(-135))
//                                        .lineToSplineHeading(new Pose2d(47,11,Math.toRadians(90)))
//                                        .splineToSplineHeading(new Pose2d(60,11.5, Math.toRadians(0)), Math.toRadians(0))
//
//                                        .waitSeconds(0.2)
//
//                                        .lineToSplineHeading(new Pose2d(40,11.5, Math.toRadians(135)))
//                                        .splineToSplineHeading(new Pose2d(29,6, Math.toRadians(-135)), Math.toRadians(-135))
//                                        .lineToSplineHeading(new Pose2d(47,11,Math.toRadians(90)))
//                                        .splineToSplineHeading(new Pose2d(60,11.5, Math.toRadians(0)), Math.toRadians(0))
//                                        .waitSeconds(0.2)
//
//                                        .lineToSplineHeading(new Pose2d(40,11.5, Math.toRadians(135)))
//                                        .splineToSplineHeading(new Pose2d(29,6, Math.toRadians(-135)), Math.toRadians(-135))
//
//                                        .build()
//                );

//                                drive.trajectorySequenceBuilder(new Pose2d(-34,-63, Math.toRadians(0)))
        //                                 RED LEFT
//                                        .strafeLeft(3)
//                                        .lineToSplineHeading(new Pose2d(-31,-60, Math.toRadians(0)))
//                                        .splineTo(new Vector2d(-16,-14), Math.toRadians(117))
//                                        .forward(7)
//
//
//                                        .strafeLeft(3)
//                                        .splineToSplineHeading(new Pose2d(-44,-11.5,Math.toRadians(180)), Math.toRadians(180))
//                                        .forward(16)
//
//                                        .waitSeconds(0.2)
//
//                                        .lineToSplineHeading(new Pose2d(-40,-11.5, Math.toRadians(-45)))
//                                        .splineToSplineHeading(new Pose2d(-29,-6, Math.toRadians(45)), Math.toRadians(45))
//                                        .lineToSplineHeading(new Pose2d(-40,-11.5,Math.toRadians(180)))
//                                        .splineToSplineHeading(new Pose2d(-60,-13, Math.toRadians(170)), Math.toRadians(170))

                                            //.waitSeconds(0.2)
//
//                                        .lineToSplineHeading(new Pose2d(-40,-11.5, Math.toRadians(-45)))
//                                        .splineToSplineHeading(new Pose2d(-29,-6, Math.toRadians(45)), Math.toRadians(45))
//
//                                        .build()
//
//                );

                    drive.trajectorySequenceBuilder(new Pose2d(36,-63, Math.toRadians(180)))
                            // RIGHT
//                            .splineToSplineHeading(new Pose2d(36,-17,Math.toRadians(90)), Math.toRadians(90))
//                            .splineTo(new Vector2d(29, -6), Math.toRadians(135))


                            .strafeRight(2)
                            .forward(6)
                            .splineTo(new Vector2d(17,-7.5),Math.toRadians(20))

                            .strafeRight(10)
                            .splineToSplineHeading(new Pose2d(40,-12,Math.toRadians(0)), Math.toRadians(0))
                            .forward(19)
                            .build()
                            );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}