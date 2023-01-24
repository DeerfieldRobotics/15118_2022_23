package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "distance")
public class distanceTest extends LinearOpMode {

    private Rev2mDistanceSensor dist1; //left
    private Rev2mDistanceSensor dist2; //right

    private static final double sensorOffset = 130.36/2; //distance between sensors
    private static final double sensorAngle = 42.6; //angle of sensor away from midline of robot in degrees
    private static final double targetRadius = 102; //41.1385; //radius of target circle
    private static final double targetDistance = (sensorOffset-targetRadius)/Math.cos(Math.toRadians(sensorAngle)); //target distance for each distance sensor to be in optimal position

    private static final double powerMult = 0.3;

    private static final double error = 12; //millimeters of acceptable error
    public static final double sensingRange = 150;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();
        while(opModeIsActive()) {
            double d1 = dist1.getDistance(DistanceUnit.MM);
            double d2 = dist2.getDistance(DistanceUnit.MM);

            telemetry.addData("Distance 1", d1);
            telemetry.addData("Distance 2", d2);


            if(d1>sensingRange&&d2>sensingRange) {
                telemetry.addLine("move forward, out of range");
                //make drivetrain movement forward proportional to the average distance if approaching cone stack, basically just P in PID
            } //if farther back, move forward more, so just have the proportion of moving forward to turning to adjust decrease as we approach the target, P tuning again
            else if(d1<=sensingRange&&d2>sensingRange) {
                telemetry.addLine("rotate right, out of range");
                //u can also strafe i think
            }
            else if(d2<=sensingRange&&d1>sensingRange) {
                telemetry.addLine("rotate left, out of range");
            }
            else { //when both sensors are within sensing range, i.e. can see the cone in theory

                // all the logic below can turn into some math to make a proportion of left turn
                // and right turn in order to try to approach equal left and right distance values

                // Also needs to slow down as we approach the target, we can find location of us relative
                // to target based on the radius of the target and the location and orientation of the
                // distance sensors

                double x1 = -sensorOffset+d1*Math.sin(Math.toRadians(sensorAngle));
                double x2 = sensorOffset-d2*Math.sin(Math.toRadians(sensorAngle));

                double y1 = d1*Math.cos(Math.toRadians(sensorAngle));
                double y2 = d2*Math.cos(Math.toRadians(sensorAngle));

                double d = Math.sqrt(Math.pow((y2-y1),2)+Math.pow((x2-x1),2));

                double theta1 = Math.atan((y2 - y1) / (x2 - x1));
                double theta2 = Math.acos(d/(2*targetRadius));

                double targetX = x1+targetRadius*Math.cos(theta1 + theta2);
                double targetY = y1+targetRadius*Math.sin(theta1 + theta2);

                telemetry.addData("Cone X", targetX);
                telemetry.addData("Cone Y", targetY);

                /*

                if(d1>targetDistance+error&&d2>targetDistance+error) {
                    telemetry.addLine("move forward, in alignment range");
                }
                else if (d1<=targetDistance+error&&d2>targetDistance+error) {
                    telemetry.addLine("rotate/strafe left");
                }
                else if (d2<=targetDistance+error&&d1>targetDistance+error) {
                    telemetry.addLine("rotate/strafe right");
                }
                else if (d2<=targetDistance+error&&d1<=targetDistance+error) {
                    telemetry.addLine("ON TARGET");
                }
                else {
                    telemetry.addLine("bruh wtf boy");
                }
                */

            }

            telemetry.update();
        }
    }
    public void initialize() {
        dist1 = hardwareMap.get(Rev2mDistanceSensor.class, "dist1");
        dist2 = hardwareMap.get(Rev2mDistanceSensor.class, "dist2");
    }
}
