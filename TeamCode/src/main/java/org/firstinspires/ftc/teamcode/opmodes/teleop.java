package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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

            //Driver 1

            if(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0)
            {
                drivetrain.move(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_y);
            }
            else {
                drivetrain.stop();
            }
            
            if(gamepad1.left_trigger != 0 || gamepad1.right_trigger != 0) { //slide mvmt
                //(right_trigger)-(left_trigger) for slide movement + limits, maybe add separate thread for evaluating limits with limit switch
            }
            else {
                //static power
            }

            //Driver 2

            if(gamepad2.left_stick_x != 0 || gamepad2.right_stick_x != 0 || gamepad2.left_stick_y != 0 || gamepad2.right_stick_y != 0)
            {
                // left stick x controls rotation of robot, left stick y controls arm, right stick controls claw pitch and roll
            }
            else {

            }

            if(gamepad1.right_trigger != 0) { //slide mvmt
                //right trigger closes 
            }
            else {
                //claw open
            }

        }
    }

    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
    }
}