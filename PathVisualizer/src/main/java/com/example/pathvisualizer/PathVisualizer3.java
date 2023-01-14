package com.example.pathvisualizer;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class PathVisualizer3 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        //Trajectory strafeRight = new Trajectory()
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setDimensions(15,16)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                //.setStartPose(new Pose2d(50, 50))
                .setConstraints(35, 30, Math.toRadians(180), Math.toRadians(180), 14.55)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36,-63, Math.toRadians(180)))
                                // RIGHT
                                .strafeRight(2)
                                .forward(6)
                                .splineTo(new Vector2d(19.5,-7.5),Math.toRadians(60))

                                .strafeRight(10)
                                .splineToSplineHeading(new Pose2d(40,-12,Math.toRadians(0)), Math.toRadians(0))
                                .forward(19)



                                .lineToConstantHeading(new Vector2d(43,-12))
                                .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(90)), Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(24,-9), Math.toRadians(180))

                                .strafeRight(0.1)
                                .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(43,-12, Math.toRadians(0)), Math.toRadians(0))
                                .lineToConstantHeading(new Vector2d(60,-12))




                                .lineToConstantHeading(new Vector2d(43,-12))
                                .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(90)), Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(24,-9), Math.toRadians(180))

                                .strafeRight(0.1)
                                .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(43,-12, Math.toRadians(0)), Math.toRadians(0))
                                .lineToConstantHeading(new Vector2d(60,-12))





                                .lineToConstantHeading(new Vector2d(43,-12))
                                .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(90)), Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(24,-9), Math.toRadians(180))

                                .strafeRight(0.1)
                                .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(43,-12, Math.toRadians(0)), Math.toRadians(0))
                                .lineToConstantHeading(new Vector2d(60,-12))





                                .lineToConstantHeading(new Vector2d(43,-12))
                                .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(90)), Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(24,-9), Math.toRadians(180))


                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}