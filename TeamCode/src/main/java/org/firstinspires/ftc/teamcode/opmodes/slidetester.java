package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;

@TeleOp(name = "teleop", group = "teleop")
public class slidetester extends LinearOpMode{

    private DcMotor s;


    public ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime.reset();
        while(opModeIsActive()) {
            telemetry.addLine("Encoder Ticks: "+s.getCurrentPosition());
        }
    }

    public void initialize() {
        s = hardwareMap.get(DcMotor.class, "s");
        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}