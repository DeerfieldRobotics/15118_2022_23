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
                    drive.trajectorySequenceBuilder(new Pose2d(-36,-63, Math.toRadians(0)))
                            .splineTo(new Vector2d(-17, -48), Math.toRadians(90))
                            .splineTo(new Vector2d(-9, -29), Math.toRadians(50))

                            //RAISE SLIDE
                            .addTemporalMarker(0.5, () -> {

                            })
                            //OUTTAKE CONE
                            .addTemporalMarker(3, () -> {

                            })
                            //STOP OUTTAKE
                            .addTemporalMarker(4.5, () -> {

                            })
                            //.waitSeconds(.5)
                            //LOWER SLIDE
                            .addTemporalMarker(5.5, () -> {

                            })

                            .setReversed(true)
                            .splineTo(new Vector2d(-14, -35), Math.toRadians(270))
                            .setReversed(false)

                            .splineTo(new Vector2d(-23, -12), Math.toRadians(180))
                            //.waitSeconds(4)
                            .splineTo(new Vector2d(-47, -13), Math.toRadians(180))
                            .splineTo(new Vector2d(-62, -17), Math.toRadians(180))

                            .forward(4)

                            .addTemporalMarker(() -> {

                            })

                            .addTemporalMarker(11, () -> {

                            })

                            .build()


                            );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}