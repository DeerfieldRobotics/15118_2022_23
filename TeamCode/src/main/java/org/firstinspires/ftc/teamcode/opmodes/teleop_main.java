package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@TeleOp(name = "MAIN_TELEOP")
public class teleop_main extends LinearOpMode {
    private Drivetrain drivetrain;
    private ElapsedTime runtime;
    private Slide slide;
    private RubberBandIntake intake;

    private double turnMult = 0.65;
    private final double forwardMult = 0.7;
    private final double strafeMult = 0.9;
    private double speedMult;

    private boolean manual;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime = new ElapsedTime();
        //start timer

        while(opModeIsActive()) {
            speedMult = .8+0.3 * gamepad1.right_trigger-0.5*gamepad1.left_trigger;

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

            if(gamepad2.right_stick_y > 0) {
                manual = true;
            }

            telemetry.addData("Slide ticks", slide.getCurrentPosition());
            telemetry.addData("slide current draw", slide.getMotor().getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }

    }

    public void initialize() {
        slide = new Slide(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
        intake = new RubberBandIntake(hardwareMap);
        slide.s.setDirection(DcMotorSimple.Direction.REVERSE);
        manual = true;
    }

}
