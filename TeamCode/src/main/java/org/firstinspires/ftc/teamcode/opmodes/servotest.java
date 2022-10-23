package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.opencvpipelines.ConeDetectionPipeline;
import org.firstinspires.ftc.teamcode.opencvpipelines.TestPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name = "servotester")
public class servotest extends LinearOpMode {
    private Servo s;
    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        s = hardwareMap.get(Servo.class, "claw");

        waitForStart();
        while(opModeIsActive()) {
            s.setPosition(0);
            Thread.sleep(1000);
            s.setPosition(0.2);
            Thread.sleep(1000);
        }
    }

    public void initialize() {

    }
}
