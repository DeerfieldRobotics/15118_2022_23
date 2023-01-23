package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Led;
@Autonomous(name = "LED test")
public class ledtest extends LinearOpMode {

    private Led led;
    private Servo s;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while (opModeIsActive()) {
            //led.setPattern(RevBlinkinLedDriver.BlinkinPattern.AQUA);
            s.setPosition(.77);
            Thread.sleep(1000);
            //led.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            s.setPosition(.61);
            Thread.sleep(1000);
            //telemetry.addData("bruh",led.led.getConnectionInfo());
            //telemetry.update();
        }
    }
    public void initialize(){
        //led = new Led(hardwareMap);
        s = hardwareMap.get(Servo.class, "led");
    }
}
