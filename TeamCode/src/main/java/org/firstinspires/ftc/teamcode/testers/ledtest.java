package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Led;

@Autonomous(name = "LEDTest")
public class ledtest extends LinearOpMode {

    private Led led;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while (opModeIsActive()) {

        }
    }
    public void initialize(){
        led = new Led(hardwareMap);
    }
}
