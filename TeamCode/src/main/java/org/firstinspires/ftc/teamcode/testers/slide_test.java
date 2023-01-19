package org.firstinspires.ftc.teamcode.testers;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.configuration.ExpansionHubMotorControllerParamsState;
//import org.openftc.revextensions2.ExpansionHubEx;
//import org.openftc.revextensions2.ExpansionHubMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
@Disabled
@TeleOp(name = "slidetester", group = "slidetester")
public class slide_test extends LinearOpMode{

    private DcMotorEx s;
    private int cross;
    private int box;
    private int tri;
    private int circle;

    public boolean a;
    public boolean b;
    public boolean x;
    public boolean y;

    public ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        runtime.reset();
        while(opModeIsActive()) {
            if(gamepad1.left_trigger>0) {
                s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                telemetry.addLine("Braking");
            }
            else {
                s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                telemetry.addLine("Floating");
            }

            if(gamepad1.right_trigger>0) {
                if(gamepad1.cross)
                    cross = s.getCurrentPosition();
                if(gamepad1.square)
                    box = s.getCurrentPosition();
                if(gamepad1.triangle)
                    tri = s.getCurrentPosition();
                if(gamepad1.circle)
                    circle = s.getCurrentPosition();
            }
            else {
                if(gamepad1.cross) {
                    s.setTargetPosition(cross);
                    s.setPower(1);
                    s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                else if(gamepad1.square) {
                    s.setTargetPosition(box);
                    s.setPower(1);
                    s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                else if(gamepad1.triangle) {
                    s.setTargetPosition(tri);
                    s.setPower(1);
                    s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                else if(gamepad1.circle) {
                    s.setTargetPosition(circle);
                    s.setPower(1);
                    s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            }

            if(gamepad1.dpad_down) {
                s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            if(gamepad1.dpad_right) {
                s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }


            telemetry.addLine("Position: "+ s.getCurrentPosition());
            telemetry.addLine("Amperage: "+ s.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }
    }

    public void initialize() {

        s = (DcMotorImplEx) hardwareMap.get(DcMotor.class, "slide");
        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        s.setDirection(DcMotorSimple.Direction.REVERSE);
        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}