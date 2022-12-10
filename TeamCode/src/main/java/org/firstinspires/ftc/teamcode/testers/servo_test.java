package org.firstinspires.ftc.teamcode.testers;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Autonomous(name = "servotester")
public class servo_test extends LinearOpMode {
    private Servo s;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        s = hardwareMap.get(Servo.class, "arm");

        waitForStart();
        while(opModeIsActive()) {
            s.setPosition(.25);
            Thread.sleep(1000);
            s.setPosition(.45);
            Thread.sleep(1000);
        }
    }

    public void initialize() {

    }
}
