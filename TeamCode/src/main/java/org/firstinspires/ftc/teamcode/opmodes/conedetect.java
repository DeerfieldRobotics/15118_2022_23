package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opencvpipelines.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.opencvpipelines.BetterRedConeDetection;
import org.firstinspires.ftc.teamcode.utils.AprilTags;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.IMU;
import org.firstinspires.ftc.teamcode.utils.PID;
import org.openftc.easyopencv.OpenCvCamera;

@Autonomous(name = "pick up cone")

public class conedetect extends LinearOpMode {

    private Drivetrain drivetrain;
    private DcMotorEx slide;
    private IMU imu;
    private AprilTags aprilTags;
    Claw c;

    // APRILTAGS
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    BetterRedConeDetection redConeDetection;

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
    static final double proper_width = 0;

    ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(hardwareMap);
        imu = new IMU(hardwareMap);
        slide = (DcMotorEx) hardwareMap.get(DcMotor.class, "slide");
        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        c =new Claw(hardwareMap);

        aprilTags = new AprilTags(hardwareMap);
        while(opModeIsActive()){
            while(true){
                if(setOrientation()){
                    break;
                }
            }
            //drive forward some level
            if (redConeDetection.getRight()-redConeDetection.getLeft() == proper_width){
                //activate claw
            }

        }


//        //APRILTAGS
//

        drivetrain.stop();

        drivetrain.stop_reset_encoder();
        drivetrain.run_using_encoder();

    } 
    public boolean setOrientation(){
        if (redConeDetection.getLeft()-redConeDetection.width()+redConeDetection.getRight()>20){
            turn(-1);
        } else if (redConeDetection.getLeft()-redConeDetection.width()+redConeDetection.getRight()<-20){
            turn(1);
        }
        return redConeDetection.getLeft()-redConeDetection.width()+redConeDetection.getRight()<20 && redConeDetection.getLeft()-redConeDetection.width()+redConeDetection.getRight()>20;
    }
    //turn a specific amount of degrees, using the gyro to check.
    public void turn(double degrees){
        imu.resetAngle();

        double error = degrees;
        while(opModeIsActive() || Math.abs(error)>2){
            double motorPower = (error < 0? -0.3: 0.3);
            drivetrain.setMotorPowers(-motorPower, motorPower, -motorPower, motorPower);
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
            drivetrain.setMotorPowers(-motorPower, motorPower, -motorPower, motorPower);
        }

        drivetrain.stop();
    }


}
