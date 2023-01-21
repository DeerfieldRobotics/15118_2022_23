package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;
import org.firstinspires.ftc.teamcode.utils.Led;

@TeleOp(name = "你的妈妈你的妈妈你的妈妈你的妈妈你的妈妈")
public class teleop2 extends LinearOpMode {
    private Drivetrain drivetrain;
    private ElapsedTime runtime;
    private Slide slide;
    private RubberBandIntake intake;
    private Led led;

    private double turnMult = 0.65;
    private final double forwardMult = 0.8;
    private final double strafeMult = 0.85;
    private double speedMult;

    private boolean manual = true;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime = new ElapsedTime();
        //start timer

        while(opModeIsActive()) {
            speedMult = 1+0.3 * gamepad1.right_trigger-0.7*gamepad1.left_trigger;

            if (runtime.seconds()>110) {
                led.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_RED);
            }
            else if (runtime.seconds()>90) {
                led.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }

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

            intake.intake(gamepad2.right_trigger-gamepad2.left_trigger);

            if(Math.abs(-gamepad2.right_stick_y) > 0) {
                slide.setPower(-gamepad2.right_stick_y);
            } else {
                slide.setPower(0.001);
            }


            if(gamepad2.right_stick_y > 0) manual = true;

            if(slide.getAmperage() > 7) {
                slide.stop();
                telemetry.addLine("CURRENT LIMIT");
            }

            if(manual) {
                slide.s.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            } else{
                slide.s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }


            if(gamepad2.cross){
                manual = false;
                slide.s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                slide.s.setTargetPosition(Slide.LOW);
                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            } else if(gamepad2.square){
                manual = false;
                slide.s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                slide.s.setTargetPosition(Slide.MEDIUM);
                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            } else if(gamepad2.triangle){
                manual = false;
                slide.s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                slide.s.setTargetPosition(Slide.HIGH);
                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            } else if (gamepad2.circle){
                manual = false;
                slide.s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                slide.s.setTargetPosition(0);
                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            }

            manual = true;

            telemetry.addData("Slide ticks", slide.getCurrentPosition());
            telemetry.addData("slide current draw", slide.getMotor().getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }

    }

    public void initialize() {
        slide = new Slide(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
        intake = new RubberBandIntake(hardwareMap);
        led = new Led(hardwareMap);

        slide.s.setDirection(DcMotorSimple.Direction.REVERSE);
        manual = true;
    }

}
