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

                            .setReversed(true)
                            .splineTo(new Vector2d(-14, -35), Math.toRadians(270))
                            .setReversed(false)

                            .splineTo(new Vector2d(-23, -12), Math.toRadians(180))
                            .splineTo(new Vector2d(-62,-12), Math.toRadians(180))


                            /*
                            .addTemporalMarker(10, () -> {
                                rubberBandIntake.updatePower(0);
                            })
                            .addTemporalMarker(11, () -> {
                                slide.setPower(1);
                                slide.setTarget(1000);
                            })
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