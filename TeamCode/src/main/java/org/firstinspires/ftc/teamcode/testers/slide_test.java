package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.configuration.ExpansionHubMotorControllerParamsState;
//import org.openftc.revextensions2.ExpansionHubEx;
//import org.openftc.revextensions2.ExpansionHubMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "slidetester", group = "slidetester")
public class slide_test extends LinearOpMode{

    private DcMotorEx s;

    public ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime.reset();
        while(opModeIsActive()) {
            telemetry.addLine("Current: "+ s.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }
    }

    public void initialize() {

        s = (DcMotorImplEx) hardwareMap.get(DcMotor.class, "slide");
        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}