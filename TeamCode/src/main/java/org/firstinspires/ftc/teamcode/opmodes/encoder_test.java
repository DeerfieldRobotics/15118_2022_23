package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.opencvpipelines.RedConeDetection;
import org.firstinspires.ftc.teamcode.utils.Claw;
import org.firstinspires.ftc.teamcode.utils.IMU;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.ClawMechanism;
import org.firstinspires.ftc.teamcode.utils.PID;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name = "encoderTest")

public class encoder_test extends LinearOpMode {

    private Drivetrain drivetrain;
    //private ClawMechanism claw;
    private IMU imu;

    // APRILTAGS
    OpenCvCamera backCamera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    RedConeDetection redConeDetection;

    //OTHER CAMERA
    //OpenCvCamera frontCamera;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.173;

    int numFramesWithoutDetection = 0;

    final float DECIMATION_HIGH = 3;
    final float DECIMATION_LOW = 2;
    final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
    final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

    // ENCODERS & GYRO
    static final double     COUNTS_PER_MOTOR_REV    = 384.5 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(hardwareMap);
        DcMotorEx slide = (DcMotorEx) hardwareMap.get(DcMotor.class, "slide");
        Claw c = new Claw(hardwareMap);

        slide.setDirection(DcMotorSimple.Direction.REVERSE);

        drivetrain.stop();
        //c.moveClaw(0);
        waitForStart();
        ElapsedTime runtime = new ElapsedTime();

        drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);


        while(opModeIsActive()){
            telemetry.addLine(""+runtime.milliseconds());
            telemetry.update();
            while (opModeIsActive() && runtime.milliseconds() <= 500) {
                slide.setTargetPosition(300);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(0.7);

            }

            while (opModeIsActive() &&  runtime.milliseconds() >= 500 && runtime.milliseconds() <= 2500) {
                telemetry.addLine(drivetrain.getPower());
                telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
                telemetry.update();

                drivetrain.strafe(true, 40, 40);
            }
//
//            drivetrain.stop();
//
            drivetrain.reset();

            while (opModeIsActive() && runtime.milliseconds() >= 2500 && runtime.milliseconds() <= 6500) {
                telemetry.addLine("Forward");
                telemetry.update();
                drivetrain.forwards(false, 20, 20, 1);
            }

            while(opModeIsActive() && runtime.milliseconds() >= 6500 && runtime.milliseconds() <= 8500){

                slide.setTargetPosition(2536);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            }

            drivetrain.reset();

            while (opModeIsActive() && runtime.milliseconds() >= 8500 && runtime.milliseconds() <= 10500) {
                telemetry.addLine("Forward");
                telemetry.update();
                drivetrain.forwards(false, 13, 13, 0.5);
            }

//            while (opModeIsActive() && runtime.milliseconds() >= 8500 && runtime.milliseconds() <= 9500) {
//                telemetry.addLine("Open claw");
//                telemetry.update();
//                c.moveClaw(1);
//            }

//
            drivetrain.reset();

            while (opModeIsActive() && runtime.milliseconds() >= 10500 && runtime.milliseconds() <= 12000) {
                telemetry.addLine("move back");
                telemetry.update();
                drivetrain.forwards(true, 5,5, 0.5);
            }

//            while (opModeIsActive() && runtime.milliseconds() >= 11500 && runtime.milliseconds() <= 12500) {
//                telemetry.addLine("Finish");
//                telemetry.update();
//                c.moveClaw(0);
//            }

            while(opModeIsActive() && runtime.milliseconds() >= 12500 && runtime.milliseconds() <= 16000){
                slide.setTargetPosition(0);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(0.7);
            }

            while (opModeIsActive() &&  runtime.milliseconds() >= 16000 && runtime.milliseconds() <= 20000) {
                drivetrain.strafe(true, 40, 40);
            }
        }

        }
}
