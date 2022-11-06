package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.opencvpipelines.RedConeDetection;
import org.firstinspires.ftc.teamcode.utils.IMU;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.ClawMechanism;
import org.firstinspires.ftc.teamcode.utils.PID;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name = "LEFT AUTO")

public class autoL extends LinearOpMode {

    private Drivetrain drivetrain;
    private ClawMechanism claw;
    private DcMotorEx slide;
    private IMU imu;

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
        slide = (DcMotorEx) hardwareMap.get(DcMotor.class, "slide");

//        //APRILTAGS
//
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        frontCamera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "frontWeb"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        redConeDetection = new RedConeDetection();

        frontCamera.setPipeline(aprilTagDetectionPipeline);
        frontCamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                frontCamera.startStreaming(800,600, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        drivetrain.stop();

        drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at",  "%7d :%7d",
                drivetrain.getEncoderTicks()[0], drivetrain.getEncoderTicks()[1]);

        waitForStart();

        ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        telemetry.setMsTransmissionInterval(50);

        int detectedID = 0;

        while (opModeIsActive())
        {
            while(runtime.milliseconds()< 3000 && opModeIsActive()) {
                // Calling getDetectionsUpdate() will only return an object if there was a new frame
                // processed since the last time we called it. Otherwise, it will return null. This
                // enables us to only run logic when there has been a new frame, as opposed to the
                // getLatestDetections() method which will always return an object.
                ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();

                // If there's been a new frame...
                if (detections != null) {
                    telemetry.addData("FPS", frontCamera.getFps());
                    telemetry.addData("Overhead ms", frontCamera.getOverheadTimeMs());
                    telemetry.addData("Pipeline ms", frontCamera.getPipelineTimeMs());

                    // If we don't see any tags
                    if (detections.size() == 0) {
                        numFramesWithoutDetection++;

                        // If we haven't seen a tag for a few frames, lower the decimation
                        // so we can hopefully pick one up if we're e.g. far back
                        if (numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
                            aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                        }
                    }
                    // We do see tags!
                    else {
                        numFramesWithoutDetection = 0;

                        // If the target is within 1 meter, turn on high decimation to
                        // increase the frame rate
                        if (detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS) {
                            aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                        }

                        for (AprilTagDetection detection : detections) {
                            detectedID = detection.id;
                            telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
                            telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x * FEET_PER_METER));
                            telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y * FEET_PER_METER));
                            telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z * FEET_PER_METER));
                            telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
                            telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
                            telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
                        }
                    }

                    telemetry.update();
                }
            }

            telemetry.addData("FINAL ID", detectedID);

            telemetry.update();

            while(opModeIsActive()){
                drive(detectedID);
            }

            drivetrain.stop();

            drivetrain.reset();

            while(opModeIsActive()){

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
                    drivetrain.strafe(true, 26, 26);
                }
                drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);

                while ((opModeIsActive() && drivetrain.isBusy()) || runtime.milliseconds() <= 9000) {
                    telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
                    telemetry.update();
                    drivetrain.forwards(false, 26, 26);
                }


                break;
            case 2:
                //middle
                telemetry.addLine("DRIVE 2");
                while ((opModeIsActive() && drivetrain.isBusy()) || runtime.milliseconds() <= 9000) {
                    telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
                    telemetry.update();
                    drivetrain.forwards(false, 26, 26);
                }
                break;
            case 3:
                //outside
                telemetry.addLine("DRIVE 3");
                while ((opModeIsActive() && drivetrain.isBusy()) || runtime.milliseconds() <= 6000) {

                    telemetry.addLine("STRAFE");
                    telemetry.update();
                    drivetrain.strafe(false, 26, 26);
                }
                drivetrain.setEncoderMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                drivetrain.setEncoderMode(DcMotor.RunMode.RUN_USING_ENCODER);

                while ((opModeIsActive() && drivetrain.isBusy()) || runtime.milliseconds() <= 9000) {
                    telemetry.addLine(drivetrain.getEncoderTicks()[0] + "\n" + drivetrain.getEncoderTicks()[1] + "\n" + runtime.milliseconds());
                    telemetry.update();
                    drivetrain.forwards(false, 26, 26);
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
