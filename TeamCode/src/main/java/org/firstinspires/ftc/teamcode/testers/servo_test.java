package org.firstinspires.ftc.teamcode.testers;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "servotester")
public class servo_test extends LinearOpMode {
    private Servo s;
    private boolean open, close;
    private boolean inverted = false;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        s = hardwareMap.get(Servo.class, "roll");

        waitForStart();
        while(opModeIsActive()) {
            if(gamepad1.cross && inverted == false) {
                s.setPosition(0);
                inverted = true;
            }
            if(gamepad1.a && inverted == true) {
                s.setPosition(1);
                Thread.sleep(1000);
                inverted = false;
            }
        }
    }

    public void initialize() {

    }
}
