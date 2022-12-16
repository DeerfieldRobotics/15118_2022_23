package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.RubberBandIntake;
import org.firstinspires.ftc.teamcode.utils.Slide;

@TeleOp(name = "你的妈妈你的妈妈你的妈妈你的妈妈你的妈妈")
public class teleop2 extends LinearOpMode {
    private Drivetrain drivetrain;
    private ElapsedTime runtime;
    private Slide slide;
    private RubberBandIntake intake;

    private double turnMult = 0.65;
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

            intake.intake(gamepad2.right_trigger-gamepad2.left_trigger);

            slide.setPower(-gamepad2.right_stick_y);

            if(gamepad2.right_stick_y > 0) manual = true;

            if(slide.getAmperage() > 7) {
                slide.stop();
                telemetry.addLine("CURRENT LIMIT");
            }

            if(gamepad2.cross){
                slide.targetLevel = 1;
                manual = false;
            } else if(gamepad2.square) {
                slide.targetLevel = 2;
                manual = false;
            } else if(gamepad2.triangle) {
                slide.targetLevel =3;
                manual = false;
            } else if (gamepad2.circle) {
                slide.targetLevel = 0;
                manual = false;
            }

            if(!manual) slide.update();

//            if(gamepad2.cross){
//                slide.s.setTargetPosition(low);
//                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                manual = false;
//            } else if(gamepad2.square){
//                slide.s.setTargetPosition(medium);
//                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                manual = false;
//            } else if(gamepad2.triangle){
//                slide.s.setTargetPosition(high);
//                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                manual = false;
//            } else if (gamepad2.circle){
//                manual = false;
//                slide.s.setTargetPosition(0);
//                slide.s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                slide.s.resetSlide();
//            }

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
