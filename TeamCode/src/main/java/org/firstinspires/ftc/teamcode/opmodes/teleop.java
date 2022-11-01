package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Gamepad.LedEffect;
import com.qualcomm.robotcore.hardware.Gamepad.RumbleEffect;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.Claw;
import org.firstinspires.ftc.teamcode.utils.ClawMechanism;
import org.firstinspires.ftc.teamcode.utils.Drivetrain;

@TeleOp(name = "teleop", group = "teleop")
public class teleop extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();

    private Drivetrain drivetrain;
    private ClawMechanism claw;

    private Claw c;

    private LedEffect.Builder ledB;
    private LedEffect red;

    private RumbleEffect.Builder rumbleB;
    private RumbleEffect warning;

    private final double armNeutral = 0.1;
    private final double pitchNeutral = 0.1;
    private final double rollNeutral = 0.1;

    private final double armMin = 0.6;

    private final double leftXSens = 0.5;
    private final double leftYSens = 1;
    private final double rightXSens = 1;
    private final double rightYSens = 1;

    private final double pitchMult = 2; //this should be arm rom/pitch rom

    private final double leftTriggerSens = 0.2;

    private boolean lb;
    private boolean s;
    private boolean w;
    private boolean f;

    private boolean pitch = false;
    private boolean roll = false;
    private boolean flip = false;

    public ElapsedTime loopTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            double loopStart = loopTime.milliseconds();

            //drivetrain movement
            if(gamepad2.left_trigger < leftTriggerSens&&(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0 || gamepad2.left_stick_x != 0))
            {
                drivetrain.move(gamepad1.left_stick_x, gamepad1.right_stick_x+gamepad2.left_stick_x*leftXSens, gamepad1.left_stick_y);
            }
            else if(gamepad2.left_trigger >= leftTriggerSens&&(gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0 || gamepad2.left_stick_x != 0)) {
                drivetrain.move(gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_y);
            }
            else {
                drivetrain.stop();
            }
            //manual slide movement
            if(gamepad1.left_trigger != 0 || gamepad1.right_trigger != 0) {
                //(right_trigger)-(left_trigger) for slide movement + limits, maybe add separate thread for evaluating limits with limit switch
                claw.setSlidePower(gamepad1.right_trigger - gamepad1.left_trigger);
            }
            else {
                claw.stopSlide();
            }
            //claw.setArm(1);
            claw.closeClaw(gamepad2.right_trigger);
            //claw.setRoll(Math.max(0,-gamepad2.right_stick_x));

            if(gamepad2.dpad_down && !s) {
                if(pitch) {
                    claw.setPitch(0);
                    pitch = false;
                }
                else {
                    claw.setPitch(0.9);
                    pitch = true;
                }
                s = true;
            }
            else if(!gamepad2.dpad_down) {
                s = false;
            }

            if(gamepad2.dpad_left && !w) {
                if(roll) {
                    claw.setRoll(0);
                    roll = false;
                }
                else {
                    claw.setRoll(1);
                    roll = true;
                }
                w = true;
            }
            else if(!gamepad2.dpad_left) {
                w = false;
            }

            if(gamepad2.left_bumper && !f) {
                if(flip) {
                    claw.setArm(0);
                    flip = false;
                }
                else {
                    claw.setArm(0.8);
                    flip = true;
                }
                f = true;
            }
            else if(!gamepad2.left_bumper) {
                f = false;
            }

            //Driver 2

            /*if(!claw.flip&&(gamepad2.left_stick_y != 0 || gamepad2.right_stick_y != 0 || gamepad2.right_stick_x != 0))
            {
                if(armNeutral+gamepad2.left_stick_y*leftYSens<armMin) { //add limits with slide height
                    claw.setArm(armMin);

                    gamepad2.ledQueue.clear();
                    gamepad2.rumbleQueue.clear();

                    gamepad2.runLedEffect(red);
                    gamepad2.runRumbleEffect(warning);
                }
                else if(armNeutral+gamepad2.left_stick_y*leftYSens>1) {
                    claw.setArm(1);

                    gamepad2.ledQueue.clear();
                    gamepad2.rumbleQueue.clear();

                    gamepad2.runLedEffect(red);
                    gamepad2.runRumbleEffect(warning);
                }
                else
                    claw.setArm(armNeutral+gamepad2.left_stick_y*leftYSens);
                if(pitchNeutral-gamepad2.left_stick_y*leftYSens*pitchMult+gamepad2.right_stick_y*rightYSens<0) {
                    claw.setPitch(0);

                    gamepad2.ledQueue.clear();
                    gamepad2.rumbleQueue.clear();

                    gamepad2.runLedEffect(red);
                    gamepad2.runRumbleEffect(warning);
                }
                else if(pitchNeutral-gamepad2.left_stick_y*leftYSens*pitchMult+gamepad2.right_stick_y*rightYSens>1) {
                    claw.setPitch(1);

                    gamepad2.ledQueue.clear();
                    gamepad2.rumbleQueue.clear();

                    gamepad2.runLedEffect(red);
                    gamepad2.runRumbleEffect(warning);
                }
                else
                    claw.setPitch(pitchNeutral-gamepad2.left_stick_y*leftYSens*pitchMult+gamepad2.right_stick_y*rightYSens);
                claw.setRoll(rollNeutral+gamepad2.right_stick_x*rightXSens);
            }

            claw.closeClaw(gamepad2.right_trigger);

            if(gamepad2.left_bumper&&!lb&&claw.getSlideCheck()!=null&&!claw.getSlideCheck().isAlive()) {
                claw.flip();
                lb = true;
            }
            else if (!gamepad2.left_bumper)
                lb = false;

            if(gamepad2.cross) {
                claw.setSlideLevel(0);
            }
            else if (gamepad2.square) {
                claw.setSlideLevel(1);
            }
            else if (gamepad2.triangle) {
                claw.setSlideLevel(2);
            }
            else if (gamepad2.circle) {
                claw.setSlideLevel(3);
            }*/

            telemetry.addData("Pos", gamepad2.right_trigger);
            telemetry.addData("AMPS: ", claw.getSlide().getMotor().getCurrent(CurrentUnit.AMPS));
            telemetry.addData("", gamepad2.left_bumper);
            telemetry.update();
        }

    }

    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
        claw = new ClawMechanism(hardwareMap);
        c = new Claw(hardwareMap);

        claw.closeClaw(0);
        claw.setRoll(0);
        claw.setPitch(0);
        claw.setArm(1);

        ledB = new LedEffect.Builder();
        ledB.addStep(1,0,0,100);
        red = ledB.build();

        rumbleB = new RumbleEffect.Builder();
        rumbleB.addStep(1,1, 100);
        warning = rumbleB.build();
    }
}