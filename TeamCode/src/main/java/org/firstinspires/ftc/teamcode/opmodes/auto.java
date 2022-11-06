package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.opencvpipelines.RedConeDetection;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.Claw;
import org.firstinspires.ftc.teamcode.utils.IMU;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.ClawMechanism;
import org.firstinspires.ftc.teamcode.utils.PID;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "AUTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")

public class auto extends LinearOpMode {

    private Drivetrain drivetrain;
    private ClawMechanism claw;
    private DcMotorEx slide;
    private IMU imu;
    private AprilTags aprilTags;

    // APRILTAGS
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    RedConeDetection redConeDetection;

    OpenCvCamera frontCamera;

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
        imu = new IMU(hardwareMap);
        aprilTags = new AprilTags(hardwareMap);
        Claw c = new Claw(hardwareMap);

        slide = (DcMotorEx) hardwareMap.get(DcMotor.class, "slide");

        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at",  "%7d :%7d",
                drivetrain.getEncoderTicks()[0], drivetrain.getEncoderTicks()[1]);
        telemetry.addLine(">. START");
        telemetry.update();

        waitForStart();

        ElapsedTime totalTime = new ElapsedTime();

        ElapsedTime runtime = new ElapsedTime();

        telemetry.setMsTransmissionInterval(50);

        int detectedID = 0;

        while (opModeIsActive() && totalTime.milliseconds() <= 29000) {
            while (runtime.milliseconds() < 3000) {
                detectedID = aprilTags.getID();
                telemetry.addData("FINAL ID", detectedID);
                telemetry.update();
            }

            runtime.reset();

            while (opModeIsActive()) {
                telemetry.addLine("" + runtime.milliseconds());
                telemetry.update();
                while (opModeIsActive() && runtime.milliseconds() <= 500) {
                    slide.setTargetPosition(300);
                    slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slide.setPower(0.7);

                }

                while (opModeIsActive() && runtime.milliseconds() >= 500 && runtime.milliseconds() <= 2500) {
                    telemetry.addLine(drivetrain.getPower());
                    telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
                    telemetry.update();

                    drivetrain.strafe(true, 42, 42);
                }
                drivetrain.reset();

                while (opModeIsActive() && runtime.milliseconds() >= 2500 && runtime.milliseconds() <= 4500) {
                    telemetry.addLine("Forward");
                    telemetry.update();
                    drivetrain.forwards(false, 20, 20, 1);
                }

                while (opModeIsActive() && runtime.milliseconds() >= 4500 && runtime.milliseconds() <= 6500) {

                    slide.setTargetPosition(2536);
                    slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slide.setPower(1);
                }

                drivetrain.reset();

                while (opModeIsActive() && runtime.milliseconds() >= 6500 && runtime.milliseconds() <= 8500) {
                    telemetry.addLine("Forward");
                    telemetry.update();
                    drivetrain.forwards(false, 13, 13, 0.5);
                }

            while (opModeIsActive() && runtime.milliseconds() >= 8500 && runtime.milliseconds() <= 9500) {
                telemetry.addLine("Open claw");
                telemetry.update();
                c.moveClaw(1);
            }

                drivetrain.reset();

                while (opModeIsActive() && runtime.milliseconds() >= 9500 && runtime.milliseconds() <= 11500) {
                    telemetry.addLine("move back");
                    telemetry.update();
                    drivetrain.forwards(true, 5, 5, 0.5);
                }

            while (opModeIsActive() && runtime.milliseconds() >= 11500 && runtime.milliseconds() <= 12500) {
                telemetry.addLine("Finish");
                telemetry.update();
                c.moveClaw(0);
            }

                while (opModeIsActive() && runtime.milliseconds() >= 12500 && runtime.milliseconds() <= 15500) {
                    slide.setTargetPosition(0);
                    slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slide.setPower(0.7);
                }

                runtime.reset();
                drivetrain.reset();
                while (opModeIsActive() && runtime.milliseconds() <= 9000) {
                    drive(detectedID);
                }

                drivetrain.stop();

            }
        }
    }



    public void drive(int tag){ //TODO change this name its not descriptive
        switch(tag){
            case 1:
                //inside
                telemetry.addLine("DRIVE 1");

                while ((opModeIsActive() && drivetrain.isBusy()) || runtime.milliseconds() <= 6000) {

                    telemetry.addLine("STRAFE");
                    telemetry.update();
                    drivetrain.strafe(false, 65, 65);
                }
                break;
            case 2:
                //middle
                telemetry.addLine("DRIVE 2");
                while ((opModeIsActive() && drivetrain.isBusy()) || runtime.milliseconds() <= 9000) {
                    telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
                    telemetry.update();
                    drivetrain.strafe(false, 39, 39);
                }
                break;
            case 3:
                //outside
                telemetry.addLine("DRIVE 3");
                while ((opModeIsActive() && drivetrain.isBusy()) || runtime.milliseconds() <= 6000) {

                    telemetry.addLine("STRAFE");
                    telemetry.update();
                    drivetrain.strafe(false, 13, 13);
                }
                break;
            default:
                break;
        }
    }

    //turn a specific amount of degrees, using the gyro to check.
    public void turn(double degrees){
        imu.resetAngle();

        double error = degrees;
        while(opModeIsActive() || Math.abs(error)>2){
            double motorPower = (error < 0? -0.3: 0.3);
            drivetrain.move(-motorPower, motorPower, -motorPower, motorPower);
            error = degrees - imu.getAngle();
            telemetry.addData("error", error);
            telemetry.update();
        }
        //stop the robot
        drivetrain.stop();
    }

    public void turnTo(double degrees){
        //update the current angle
        imu.getTemp();

        double error = degrees - imu.temp().firstAngle;

        error = imu.normalize(error);

        turn(error);
    }

    public void turnPID(double degrees){
        turnToPID(degrees + imu.getAbsoluteAngle());
    }

    void turnToPID(double targetAngle){
        PID pid = new PID(targetAngle, 0,0,0); //TUNE PID VALUES
        while(opModeIsActive() && Math.abs(targetAngle - imu.getAbsoluteAngle()) > 2){
            double motorPower = pid.update(imu.getAbsoluteAngle());
            drivetrain.move(-motorPower, motorPower, -motorPower, motorPower);
        }

        drivetrain.stop();
    }


}
