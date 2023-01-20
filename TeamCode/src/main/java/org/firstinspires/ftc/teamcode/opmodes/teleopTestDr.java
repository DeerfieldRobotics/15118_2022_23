package org.firstinspires.ftc.teamcode.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad.LedEffect;
import com.qualcomm.robotcore.hardware.Gamepad.RumbleEffect;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Test Teleop", group = "teleop")
public class teleopTestDr extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();

    private DcMotorEx fl;
    private DcMotorEx fr;
    private DcMotorEx bl;
    private DcMotorEx br;

    private final double turnMult = 0.6;
    private final double forwardMult = 0.8;
    private final double strafeMult = 0.85;

    public final static int low = 1140;
    public final static int medium = 1815;
    public final static int high = 2535;

    private boolean manual;

    private double speedMult = 0.7;

    private LedEffect.Builder ledB;
    private LedEffect red;

    private RumbleEffect.Builder rumbleB;
    private RumbleEffect warning;

    private final double leftXSens = 0.5;
    private final double leftYSens = 1;
    private final double rightXSens = 1;
    private final double rightYSens = 1;

    private final double leftTriggerSens = 0.2;

    public ElapsedTime loopTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            double loopStart = loopTime.milliseconds();

            speedMult = 0.7+0.3 * gamepad1.right_trigger-0.7*gamepad1.left_trigger;
            //movement
            if(gamepad1.right_stick_x != 0 || gamepad1.left_stick_y != 0||gamepad1.left_stick_x!=0) {


                double forward = gamepad1.left_stick_y*forwardMult*speedMult;
                double turn = gamepad1.right_stick_x*turnMult*speedMult;
                double strafe = gamepad1.left_stick_x*strafeMult*speedMult;

                fl.setPower(forward - turn - strafe);
                fr.setPower(forward + turn + strafe);
                bl.setPower(forward - turn + strafe);
                br.setPower(forward + turn - strafe);
                telemetry.addLine("moving");
            }
            else {
                fl.setPower(0);
                fr.setPower(0);
                bl.setPower(0);
                br.setPower(0);
                telemetry.addLine("not moving");
            }


//            if(gamepad2.right_trigger != 0) {
//                //(right_trigger)-(left_trigger) for slide movement + limits, maybe add separate thread for evaluating limits with limit switch\
//                c.moveClaw(1);
//            }
//            else {
//                c.moveClaw(0);
//            }
//            if(gamepad2.right_stick_y!=0) {
//                slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                slide.setPower(gamepad2.right_stick_y);
//                manual = true;
//            }
//            else if(manual) {
//                slide.setPower(0);
//            }
//
//            if(gamepad2.cross){
//                slide.setTargetPosition(low);
//                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                manual = false;
//            } else if(gamepad2.square){
//                slide.setTargetPosition(medium);
//                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                manual = false;
//            } else if(gamepad2.triangle){
//                slide.setTargetPosition(high);
//                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                manual = false;
//            } else if (gamepad2.circle){
//                manual = false;
//                slide.setTargetPosition(0);
//                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                slide.setPower(1);
//                resetSlide();
//            }
//
//
//
//            telemetry.addData("Slide ticks", slide.getCurrentPosition());
//
//            telemetry.update();
        }

    }

    public void initialize() {
//        c = new Claw(hardwareMap);
//
//        c.moveClaw(0);

        ledB = new LedEffect.Builder();
        ledB.addStep(1,0,0,100);
        red= ledB.build();

        rumbleB = new RumbleEffect.Builder();
        rumbleB.addStep(1,1, 100);
        warning = rumbleB.build();

//        slide = (DcMotorEx) hardwareMap.get(DcMotor.class, "slide");
//        slide.setDirection(DcMotorSimple.Direction.REVERSE);
        fl = (DcMotorEx) hardwareMap.get(DcMotor.class, "fl");
        fr = (DcMotorEx) hardwareMap.get(DcMotor.class, "fr");
        bl = (DcMotorEx) hardwareMap.get(DcMotor.class, "bl");
        br = (DcMotorEx) hardwareMap.get(DcMotor.class, "br");

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void resetSlide() {
//        slide.setDirection(DcMotorSimple.Direction.REVERSE);
//        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        while(slide.getCurrent(CurrentUnit.AMPS)<6) {
//            telemetry.addLine("resetting");
//            telemetry.update();
//            slide.setPower(-1);
//        }
//        slide.setPower(0);
//        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}