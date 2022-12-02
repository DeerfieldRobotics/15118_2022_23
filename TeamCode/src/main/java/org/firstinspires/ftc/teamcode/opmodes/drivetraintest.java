package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utils.Drivetrain;

@TeleOp(name = "drivetrain test ", group = "teleop")
public class drivetraintest extends LinearOpMode {
    //private Drivetrain drivetrain;
    private DcMotorEx fl;
    private DcMotorEx fr;
    private DcMotorEx bl;
    private DcMotorEx br;
    public void initialize() {
        //drivetrain = new Drivetrain(hardwareMap);
        //drivetrain.setEncoderMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
    }

    public void runOpMode() {
        initialize();
        waitForStart();
        while(opModeIsActive()) {

            double forward = gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            fl.setPower(forward + turn + strafe);
            fr.setPower(forward - turn - strafe);
            bl.setPower(forward + turn - strafe);
            br.setPower(forward - turn + strafe);
        }
    }
}
