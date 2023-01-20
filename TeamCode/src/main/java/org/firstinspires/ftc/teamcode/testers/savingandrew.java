package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.Drivetrain;

@Autonomous(name = "savingandrew")
public class savingandrew extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {


        DcMotorEx fr = (DcMotorEx)hardwareMap.get(DcMotor.class, "fr");
        DcMotorEx fl = (DcMotorEx)hardwareMap.get(DcMotor.class, "fl");
        DcMotorEx br = (DcMotorEx)hardwareMap.get(DcMotor.class, "br");
        DcMotorEx bl = (DcMotorEx)hardwareMap.get(DcMotor.class, "bl");

        DcMotor e0 = hardwareMap.get(DcMotor.class, "leftEncoder");
        DcMotor e1 = hardwareMap.get(DcMotor.class, "rightEncoder");
        DcMotor e2 = hardwareMap.get(DcMotor.class, "middleEncoder");

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("fl", fl.getCurrentPosition());
            telemetry.addData("fr", fr.getCurrentPosition());
            telemetry.addData("bl", bl.getCurrentPosition());
            telemetry.addData("br", br.getCurrentPosition());
            telemetry.addData("left", e0.getCurrentPosition());
            telemetry.addData("right", e1.getCurrentPosition());
            telemetry.addData("middle", e2.getCurrentPosition());
            telemetry.update();

        }
    }
}
