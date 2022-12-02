package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.Claw;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@TeleOp(name = "horse")
public class teleop2 extends LinearOpMode {
    private Drivetrain drivetrain;
    private ElapsedTime runtime;
    private Slide slide;
    private RubberBandIntake intake;

    private double turnMult = 0.6;
    private final double forwardMult = 0.8;
    private final double strafeMult = 0.85;
    private double speedMult;

    private boolean manual;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime = new ElapsedTime();
        //start timer

        while(opModeIsActive()) {
            speedMult = 1+0.3 * gamepad1.right_trigger-0.7*gamepad1.left_trigger;

            //movement
            if(gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0||gamepad1.left_stick_x!=0) {
                double forward = gamepad1.left_stick_y * forwardMult * speedMult;
                double turn = gamepad1.right_stick_x * turnMult * speedMult;
                double strafe = gamepad1.left_stick_x * strafeMult * speedMult;

                drivetrain.setMotorPowers(forward - turn - strafe,forward + turn + strafe,forward - turn + strafe,forward + turn - strafe);

                telemetry.addLine("moving");
            }
            else {
                drivetrain.stop();
                telemetry.addLine("not moving");
            }

            //intake



//            if(gamepad2.left_bumper) {
//                //(right_trigger)-(left_trigger) for slide movement + limits, maybe add separate thread for evaluating limits with limit switch\
//                run = false;
//            }

            if(gamepad2.right_trigger != 0) {
                intake.intake();
            } else if(gamepad2.left_trigger !=0) {
                intake.outtake();
            } else {
                intake.stop();
            }

            if(gamepad2.right_stick_y != 0) {
                slide.setPower(-gamepad2.right_stick_y);
            } else {
                slide.stop();
            }
            /*
            if(gamepad2.cross){
                slide.moveSlide(1);
                slide.setPower(1);
            } else if(gamepad2.square) {
                slide.moveSlide(2);
                slide.setPower(1);
            } else if(gamepad2.triangle) {
                slide.setPosition(3);
                slide.setPower(1);
            } else if (gamepad2.circle) {
                slide.setPosition(0);
                slide.setPower(1);
            } else {

            }*/

            if(gamepad2.cross){
                slide.setTargetLevel(1);
                slide.getMotor().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
                manual = false;
            } else if(gamepad2.square) {
                slide.setTargetLevel(2);
                slide.getMotor().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
                manual = false;
            } else if(gamepad2.triangle) {
                slide.setTargetLevel(3);
                slide.getMotor().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
                manual = false;
            } else if (gamepad2.circle) {
                slide.setTargetLevel(0);
                slide.getMotor().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
                manual = false;
            }

            telemetry.addData("Slide ticks", slide.getCurrentPosition());
            telemetry.update();
        }

    }

    public void initialize() {
        slide = new Slide(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
        intake = new RubberBandIntake(hardwareMap);

        slide.s.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.no_encoder();
    }

}
