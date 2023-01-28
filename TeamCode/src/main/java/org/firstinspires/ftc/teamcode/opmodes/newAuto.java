package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;
@Autonomous(name = "你是猴子的屎")
public class newAuto extends OpMode {

    private SampleMecanumDrive drive;
    private AprilTags aprilTags;
    private Slide slide;
    private RubberBandIntake intake;
    private int detectedTag;

    TrajectorySequence autoSequence;
    TrajectorySequence cycle;

    Pose2d startPose = new Pose2d(36, -63, Math.toRadians(180));

    @Override
    public void init() {
        intake = new RubberBandIntake(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        aprilTags = new AprilTags(hardwareMap, "rightCam");
        slide = new Slide(hardwareMap);
        slide.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        drive.setPoseEstimate(startPose);
    }
    @Override
    public void init_loop() {
        detectedTag = aprilTags.getID();

        telemetry.addData("DETECTED TAG: ", detectedTag);
        telemetry.update();
    }

    @Override
    public void start() {
        autoSequence = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(2)
                .forward(4)

                //Going to first high
                .addTemporalMarker(1, ()-> {
                    slide.setPower(1);
                    slide.setTarget(Slide.HIGH);
                })

                .splineTo(new Vector2d(26,-8), Math.toRadians(55))

                //Drops first high
                .addTemporalMarker(3.4, () -> {
                    intake.updatePower(-1);
                })

                .addTemporalMarker(5,() -> {
                    intake.updatePower(0);
                })

                //Goes to cone stack
                .lineToLinearHeading(new Pose2d(14,-14.25, Math.toRadians(0)))
                .lineToSplineHeading(new Pose2d(67,-14.25, Math.toRadians(0)))


                .addTemporalMarker(5, () -> {
                    slide.setPower(1);
                    slide.setTarget(500);
                })
//
//               //cycle
                .addTemporalMarker(6.5,()->{
                    intake.updatePower(1);
                })

                .addTemporalMarker(7.75,()->{
                    slide.setPower(1);
                    slide.setTarget(Slide.HIGH);
                })

                .addTemporalMarker(8.5,()->{
                    intake.updatePower(0);
                })

                //CYCLE 1

                .lineToSplineHeading(new Pose2d(43,-14.25, Math.toRadians(0)))
                .splineToSplineHeading(new Pose2d(32,-7, Math.toRadians(125)), Math.toRadians(125))
                .waitSeconds(0.01)
                .addTemporalMarker(() -> {
                    intake.updatePower(-1);
                })
                .waitSeconds(1)
                .back(2)

//
                .splineToSplineHeading(new Pose2d(46,-15, Math.toRadians(0)), Math.toRadians(0))

                .addTemporalMarker(13,()->{
                    slide.setPower(1);
                    intake.updatePower(0);
                    slide.setTarget(420);
                })
               /*
                //PICK UP CONE 2
                .lineToSplineHeading(new Pose2d(67,-15, Math.toRadians(0)))
                .addTemporalMarker(13,()->{
                    intake.updatePower(1);
                })
                .addTemporalMarker(14.5,()->{
                    intake.updatePower(0);
                    slide.setTarget(Slide.HIGH);
                })

                //CAP 2

                .lineToSplineHeading(new Pose2d(43,-15,Math.toRadians(0)))
                .splineToSplineHeading(new Pose2d(33.5,-6, Math.toRadians(125)), Math.toRadians(125))
                .waitSeconds(0.01)
                .addTemporalMarker(() -> {
                    intake.updatePower(-1);
                })
                .waitSeconds(0.2)
                .back(2)



                //PICK UP 3
                .addTemporalMarker(17, ()->{
                    slide.setPower(1);
                    intake.updatePower(0);
                    slide.setTarget(350);
                })

                .splineToSplineHeading(new Pose2d(46,-15, Math.toRadians(0)), Math.toRadians(0))
                .lineToLinearHeading(new Pose2d(6,-15, Math.toRadians(0)))

                .addTemporalMarker(18, ()->{
                    intake.updatePower(1);
                })
                .addTemporalMarker(21.5, ()->{
                    slide.setPower(1);
                    slide.setTarget(Slide.HIGH);
                })

                .addTemporalMarker(22.75, ()->{
                    intake.updatePower(0);
                })

                .lineToLinearHeading(new Pose2d(43,-15,Math.toRadians(0)))
                .splineToSplineHeading(new Pose2d(33.5,-7, Math.toRadians(125)), Math.toRadians(125))
                .waitSeconds(0.01)
                .addTemporalMarker(()->{
                    intake.updatePower(-1);
                })
                .waitSeconds(0.2)
                .back(2)
                .addTemporalMarker(()->{
                    slide.setPower(1);
                    slide.setTarget(275);
                    intake.updatePower(0);

                })
                .splineToSplineHeading(new Pose2d(46,-15, Math.toRadians(0)), Math.toRadians(0))
                .addTemporalMarker(()->{
                    intake.updatePower(1);
                })
                .lineToLinearHeading(new Pose2d(66,-15, Math.toRadians(0)))

                .addTemporalMarker(27, ()->{
                    slide.setPower(1);
                    slide.setTarget(Slide.HIGH);
                    intake.updatePower(0);
                })
                .lineToLinearHeading(new Pose2d(43,-15,Math.toRadians(0)))
                .splineToSplineHeading(new Pose2d(33.5,-7, Math.toRadians(125)), Math.toRadians(125))
                .waitSeconds(0.01)
                .addTemporalMarker(() -> {
                    intake.updatePower(-1);
                })
                .addTemporalMarker(29, ()->{
                    intake.updatePower(0);
                })

                 */
                .build();

        cycle = drive.trajectorySequenceBuilder(autoSequence.end())
                .lineToConstantHeading(new Vector2d(43,-12))
//                                        .splineToSplineHeading(new Pose2d(32,-9, Math.toRadians(135)), Math.toRadians(135))
                .splineToSplineHeading(new Pose2d(28.5,-7, Math.toRadians(125)), Math.toRadians(125))

                .waitSeconds(0.1)
                .back(3)
//               .splineToConstantHeading(new Vector2d(32,-9), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(43,-12, Math.toRadians(0)), Math.toRadians(0))
                .lineToConstantHeading(new Vector2d(60,-12))

                .build();

        drive.followTrajectorySequenceAsync(autoSequence);
    }

    @Override
    public void loop() {
        drive.update();
        slide.update();
        intake.update();

        /*
        * You've been hit by
        * You've been struck by
        *
        * TRUCK
        * ~aah!~
        * */
    }
}
