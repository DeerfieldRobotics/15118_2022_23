package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.utils.Slide;

@TeleOp(name = "SLIDE_POWER_TEST")
public class slidePower extends LinearOpMode {
    private Slide slide;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while(opModeIsActive()) {
            for(double i = 0.0; i < 1.0; i += 0.01) {
                slide.setPower(i);

                telemetry.addData("POWER: ", i);
                telemetry.update();

                Thread.sleep(2500);
            }
        }
    }

    public void initialize() {
        slide = new Slide(hardwareMap);
        slide.s.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}
