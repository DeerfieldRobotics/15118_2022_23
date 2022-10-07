package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;

@TeleOp(name = "teleop", group = "teleop")
public class teleop extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();

    private Drivetrain drivetrain;

    private DcMotor getMotor(String motorName) {
        return hardwareMap.get(DcMotor.class, motorName);
    }
    public ElapsedTime loopTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            double loopStart = loopTime.milliseconds();
            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                drivetrain.move(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_y);
            }
            else {
                drivetrain.stop();
            }
        }
    }

    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
    }
}