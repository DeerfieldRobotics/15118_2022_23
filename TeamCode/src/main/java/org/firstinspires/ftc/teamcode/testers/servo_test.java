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
        s = hardwareMap.get(Servo.class, "pitch");

        waitForStart();
        while(opModeIsActive()) {
            s.setPosition(1.1);
            Thread.sleep(1000);
            s.setPosition(0.2);
            Thread.sleep(1000);
        }
    }

    public void initialize() {

    }
}
