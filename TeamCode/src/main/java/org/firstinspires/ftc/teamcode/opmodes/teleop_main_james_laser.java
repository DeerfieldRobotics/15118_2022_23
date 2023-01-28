package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;


@TeleOp(name = "nidemama")
public class teleop_main_james_laser extends LinearOpMode {
    private Drivetrain drivetrain;
    private ElapsedTime runtime;
    private Slide slide;
    private RubberBandIntake intake;
    public static double SENSOROFFSET = 130.36/2; //distance between sensors
    public static double sensorAngle = 42.6; //angle of sensor away from midline of robot in degrees
    public static double targetRadius = 68.5/2; //41.1385; //radius of target circle
    public static double error = 4; //millimeters of acceptable error

    public static double TARGET_X = 0;
    public static double TARGET_Y = 100;

    private Rev2mDistanceSensor dist1; //left
    private Rev2mDistanceSensor dist2; //right

    private DcMotorEx left;
    private DcMotorEx right;

    private static double targetDistance = (SENSOROFFSET -targetRadius)/Math.cos(Math.toRadians(sensorAngle)); //target distance for each distance sensor to be in optimal position

    public static double sensingRange = 150;

    private TelemetryPacket packet;
    private FtcDashboard dash;

    private double turnMult = 0.65;
    private final double forwardMult = 0.7;
    private final double strafeMult = 0.9;
    private double speedMult;

    private boolean manual;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime = new ElapsedTime();
        //start timer


        while(opModeIsActive()) {
            speedMult = 1+0.3 * gamepad1.right_trigger-0.5*gamepad1.left_trigger;
            double d1 = dist1.getDistance(DistanceUnit.MM);
            double d2 = dist2.getDistance(DistanceUnit.MM);
            telemetry.addData("Distance 1", d1);
            telemetry.addData("Distance 2", d2);
            if(d1>sensingRange&&d2>sensingRange) {
                gamepad1.setLedColor(255, 0,0, 1);
            }else{
                gamepad1.setLedColor(0,255,0,1);
            }
            if(gamepad1.right_bumper !=false){
                while(true){
                    if(d1<=sensingRange&&d2>sensingRange) {
                        telemetry.addLine("rotate right, out of range");
                        left.setPower(0);
                        right.setPower(.5);
                        //u can also strafe i think
                    }
                    else if(d2<=sensingRange&&d1>sensingRange) {
                        telemetry.addLine("rotate left, out of range");
                        right.setPower(0);
                        left.setPower(.5);
                    }
                    else { //when both sensors are within sensing range, i.e. can see the cone in theory
                        left.setPower(0);
                        right.setPower(0);
                        // all the logic below can turn into some math to make a proportion of left turn
                        // and right turn in order to try to approach equal left and right distance values

                        // Also needs to slow down as we approach the target, we can find location of us relative
                        // to target based on the radius of the target and the location and orientation of the
                        // distance sensors

                        double x1 = -SENSOROFFSET + d1 * Math.sin(Math.toRadians(sensorAngle));
                        double x2 = SENSOROFFSET - d2 * Math.sin(Math.toRadians(sensorAngle));

                        double y1 = d1 * Math.cos(Math.toRadians(sensorAngle));
                        double y2 = d2 * Math.cos(Math.toRadians(sensorAngle));

                        double d = Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));

                        double theta1 = Math.atan((y2 - y1) / (x2 - x1));
                        double theta2 = Math.acos(d / (2 * targetRadius));

                        double targetX = x1 + targetRadius * Math.cos(theta1 + theta2);
                        double targetY = y1 + targetRadius * Math.sin(theta1 + theta2);

                        telemetry.addData("Cone X", targetX);
                        telemetry.addData("Cone Y", targetY);

                        packet.put("x", targetX);
                        packet.put("y", targetY);

                        double Px = .06;
                        double Py = 0.05;
                        double P = 0.008;

                        double propX = Px * (TARGET_X - targetX); //y error times coefficient
                        double propY = Py * (TARGET_Y - targetY); //x error times coefficient

                        double totalError = Math.sqrt(Math.pow(TARGET_X - targetX, 2) + Math.pow(TARGET_Y - targetY, 2)); //total distance

                        packet.put("x error", TARGET_X - targetX);
                        packet.put("y error", TARGET_Y - targetY);

                        if (Math.abs(TARGET_X - targetX) > error || Math.abs(TARGET_Y - targetY) > error) {
                            telemetry.addLine("adjusting");
                            right.setPower(P * totalError * (-propY - propX));
                            left.setPower(P * totalError * (-propY + propX));
                        } else {
                            telemetry.addLine("not adjusting");
                            gamepad2.rumble(100);
                            gamepad1.rumble(100);
                            break;
                        }

                        telemetry.update();
                        dash.sendTelemetryPacket(packet);
                    }
                }
            }

            //movement
            if(gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0||gamepad1.left_stick_x!=0) {
                double forward = gamepad1.left_stick_y * forwardMult * speedMult;
                double turn = gamepad1.right_stick_x * turnMult * speedMult;
                double strafe = gamepad1.left_stick_x * strafeMult * speedMult;

                drivetrain.setMotorPowers(forward - turn - strafe,forward + turn + strafe,forward - turn + strafe,forward + turn - strafe);

                telemetry.addLine("moving");
            }
            else {
                drivetrain.stop();
                telemetry.addLine("not moving");
            }

            intake.intake(gamepad2.right_trigger-gamepad2.left_trigger);

            if(Math.abs(-gamepad2.right_stick_y) > 0) {
                slide.setPower(-gamepad2.right_stick_y);
            } else {
                slide.setPower(0.001);
            }

            if(gamepad2.right_stick_y > 0) {
                manual = true;
            }

            telemetry.addData("Slide ticks", slide.getCurrentPosition());
            telemetry.addData("slide current draw", slide.getMotor().getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }

    }

    public void initialize() {
        slide = new Slide(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
        intake = new RubberBandIntake(hardwareMap);
        slide.s.setDirection(DcMotorSimple.Direction.REVERSE);
        manual = true;
        dist1 = hardwareMap.get(Rev2mDistanceSensor.class, "dist1");
        dist2 = hardwareMap.get(Rev2mDistanceSensor.class, "dist2");

        left = (DcMotorEx)hardwareMap.get(DcMotor.class, "l");
        right = (DcMotorEx)hardwareMap.get(DcMotor.class, "r");

        right.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        packet = new TelemetryPacket();
        dash = FtcDashboard.getInstance();
    }

}
