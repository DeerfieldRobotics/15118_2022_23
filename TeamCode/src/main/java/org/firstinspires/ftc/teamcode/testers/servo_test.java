package org.firstinspires.ftc.teamcode.testers;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "servotester")
public class servo_test extends LinearOpMode {
    private Servo s;
    private boolean open, close;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        s = hardwareMap.get(Servo.class, "roll");

        waitForStart();
        while(opModeIsActive()) {
            s.setPosition(1);
            telemetry.addLine("1");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.9);
            telemetry.addLine(".9");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.8);
            telemetry.addLine(".8");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.7);
            telemetry.addLine(".7");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.6);
            telemetry.addLine(".6");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.5);
            telemetry.addLine(".5");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.4);
            telemetry.addLine(".4");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.3);
            telemetry.addLine(".3");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.2);
            telemetry.addLine(".2");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(.1);
            telemetry.addLine(".1");
            telemetry.update();
            Thread.sleep(1000);
            s.setPosition(0);
            telemetry.addLine("0");
            telemetry.update();
            Thread.sleep(1000);
        }
    }

    public void initialize() {

    }
}
