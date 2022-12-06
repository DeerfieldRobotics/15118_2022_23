package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "AUTO")

public class auto extends LinearOpMode {
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

    @Override
    public void runOpMode() throws InterruptedException {
        //APRILTAGS



//        robot.drivetrain.stop_reset_encoder();
//        robot.drivetrain.run_using_encoder();
//
//        // Send telemetry message to indicate successful Encoder reset
//        telemetry.addData("Starting at",  "%7d :%7d",
//                robot.drivetrain.getMotorPositions()[0], robot.drivetrain.getMotorPositions()[1]);
//        telemetry.addLine(">. START");
//        telemetry.update();
//        robot.drivetrain.stop();
//
//        ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
//
//        int detectedID = 0;
//
//        while(opModeInInit()){
//            detectedID = robot.getAprilTag();
//        }
//
//        waitForStart();
//
//        runtime.reset();
//
//        telemetry.setMsTransmissionInterval(50);
//
//        while (opModeIsActive()) {
//
//        }
    }


//
//    public void drive(int tag, ElapsedTime runtime){ //TODO change this name its not descriptive
//        switch(tag){
//            case 1:
//                //inside
//                telemetry.addLine("DRIVE 1");
//
//                while ((opModeIsActive()) && runtime.milliseconds() <= 6000 &&  runtime.milliseconds() >= 3000) {
//
//                    telemetry.addLine("STRAFE");
//                    telemetry.update();
//                    drivetrain.strafe(true, 26, 26);
//                }
//                drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//                while ((opModeIsActive()) && runtime.milliseconds() >= 6000  &&  runtime.milliseconds() <= 9000) {
//                    telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
//                    telemetry.update();
//                    drivetrain.forwards(false, 26, 26,1);
//                }
//                drivetrain.stop();
//
//                break;
//            case 2:
//                //middle
//                telemetry.addLine("DRIVE 2");
//                while ((opModeIsActive()) && runtime.milliseconds() <= 6000) {
//
//                    telemetry.addLine("STRAFE");
//                    telemetry.update();
//                    drivetrain.strafe(false, 26, 26);
//                }
//                drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                while ((opModeIsActive()) && runtime.milliseconds() >= 6000 && runtime.milliseconds() <= 9000) {
//                    telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
//                    telemetry.update();
//                    drivetrain.forwards(false, 26, 26,1);
//                }
//                drivetrain.reset();
//                while ((opModeIsActive()) && runtime.milliseconds() >= 9000 && runtime.milliseconds() <= 12000) {
//
//                    telemetry.addLine("STRAFE");
//                    telemetry.update();
//                    drivetrain.strafe(true, 26, 26);
//                }
//                drivetrain.stop();
//                break;
//            case 3:
//                //outside
//
//                while ((opModeIsActive()) && runtime.milliseconds() <= 6000) {
//
//                    telemetry.addLine("STRAFE");
//                    telemetry.update();
//                    drivetrain.strafe(false, 26, 26);
//                }
//                drivetrain.stop_reset_encoder();
//                drivetrain.run_using_encoder();
//
//                telemetry.addLine("DRIVE 3");
//                while ((opModeIsActive()) && runtime.milliseconds() >= 6000 && runtime.milliseconds() <= 9000) {
//                    telemetry.addLine(drivetrain.getMotorPositions()[0] + "\n" + drivetrain.getMotorPositions()[1] + "\n" + runtime.milliseconds());
//                    telemetry.update();
//                    drivetrain.forwards(false, 26, 26,1);
//                }
//                drivetrain.stop();
//                break;
//            default:
//                break;
//        }
//    }
}
