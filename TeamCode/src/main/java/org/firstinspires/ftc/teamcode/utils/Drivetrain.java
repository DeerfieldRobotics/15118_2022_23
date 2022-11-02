package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Drivetrain
{
    private DcMotorEx fl, fr, bl, br;
    private HardwareMap hw;


    static final double     COUNTS_PER_MOTOR_REV    = 384.5 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    public static final double     DRIVE_SPEED             = 0.6;
    public static final double     TURN_SPEED              = 0.5;

    private ElapsedTime runtime = new ElapsedTime();

    private double DRIVETRAIN_SPEED_MODIFIER = 1;
    public Drivetrain (HardwareMap hardwaremap) {
        hw = hardwaremap;


        initialize();
    }

    public void initialize() {
        fr = (DcMotorImplEx) hw.get(DcMotorEx.class, "fr");
        fl = (DcMotorImplEx) hw.get(DcMotorEx.class, "fl");
        br = (DcMotorImplEx) hw.get(DcMotorEx.class, "br");
        bl = (DcMotorImplEx) hw.get(DcMotorEx.class, "bl");

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    /**
     * Moves drivetrain
     *
     * @param strafe speed of strafe between 0 and 1
     * @param turn speed of turn between 0 and 1
     * @param forward speed of forward between 0 and 1
     */
    public void move(double strafe, double turn, double forward)
    {
        turn *= -DRIVETRAIN_SPEED_MODIFIER;
        forward *= DRIVETRAIN_SPEED_MODIFIER;
        strafe *= -DRIVETRAIN_SPEED_MODIFIER;
        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn - strafe);
        bl.setPower(forward + turn - strafe);
        br.setPower(forward - turn + strafe);
        stop();
    }

    public void move(double flpow, double frpow, double blpow, double brpow){
        fl.setPower(flpow);
        fr.setPower(frpow);
        bl.setPower(blpow);
        br.setPower(brpow);
    }
    public void stop() {
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

    }

    public void reset(){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setSpeed(double newSpeed)
    {
        DRIVETRAIN_SPEED_MODIFIER = newSpeed;
    }

    public void setEncoderMode(DcMotor.RunMode encoderMode)
    {
        fr.setMode(encoderMode);
        fl.setMode(encoderMode);
        br.setMode(encoderMode);
        bl.setMode(encoderMode);
    }

    public void setDrivetrainSpeed(double speed) {
        DRIVETRAIN_SPEED_MODIFIER=speed;
    }

    public int[] getEncoderTicks()
    {
        return new int[]
                {fl.getCurrentPosition(), fr.getCurrentPosition(), bl.getCurrentPosition(), br.getCurrentPosition()};
    }
    public void forwards(boolean forward, int LTarget, int RTarget)
    {
        int FLTarget = fl.getCurrentPosition()+ (int) (LTarget * COUNTS_PER_INCH);
        int FRTarget = fr.getCurrentPosition() + (int) (RTarget * COUNTS_PER_INCH);
        int BLTarget = bl.getCurrentPosition()+ (int) (LTarget * COUNTS_PER_INCH);
        int BRTarget = br.getCurrentPosition() + (int) (RTarget * COUNTS_PER_INCH);
        double pow = 0.0;
        if (!forward) {
            pow = DRIVETRAIN_SPEED_MODIFIER * -1;
            fr.setTargetPosition(FRTarget);
            fl.setTargetPosition(FLTarget);
            br.setTargetPosition(BRTarget);
            bl.setTargetPosition(BLTarget);
        } else {
            fr.setTargetPosition(-FRTarget);
            fl.setTargetPosition(-FLTarget);
            br.setTargetPosition(-BRTarget);
            bl.setTargetPosition(-BLTarget);
        }

        setEncoderMode(DcMotor.RunMode.RUN_TO_POSITION);

        move(0,0,pow);



    }
    public void strafe(boolean left, int LTarget, int RTarget)
    {
        int FLTarget = (int) (LTarget * COUNTS_PER_INCH);
        int FRTarget = (int) (RTarget * COUNTS_PER_INCH);
        int BLTarget = (int) (LTarget * COUNTS_PER_INCH);
        int BRTarget = (int) (RTarget * COUNTS_PER_INCH);
        reset();
        double pow= 0.0;
        if (!left) {
            fr.setTargetPosition(FRTarget);
            fl.setTargetPosition(-FLTarget);
            br.setTargetPosition(-BRTarget);
            bl.setTargetPosition(BLTarget);
            pow = DRIVETRAIN_SPEED_MODIFIER * -1;
        } else {
            fr.setTargetPosition(-FRTarget);
            fl.setTargetPosition(FLTarget);
            br.setTargetPosition(BRTarget);
            bl.setTargetPosition(-BLTarget);
        }

        move(pow, 0, 0);

    }

    public void turn(boolean left, int amount) {
        if (!left) {
            fr.setTargetPosition(-amount);
            fl.setTargetPosition(amount);
            br.setTargetPosition(-amount);
            bl.setTargetPosition(amount);
        } else {
            fr.setTargetPosition(amount);
            fl.setTargetPosition(-amount);
            br.setTargetPosition(amount);
            bl.setTargetPosition(-amount);
        }

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean isBusy(){
        return fl.isBusy() && fr.isBusy();
    }
    public double avgCurrent() {
        return (fl.getCurrent(CurrentUnit.AMPS)+fr.getCurrent(CurrentUnit.AMPS)+bl.getCurrent(CurrentUnit.AMPS)+br.getCurrent(CurrentUnit.AMPS))/4;
    }
    public DcMotorEx[] getMotors() {
        return new DcMotorEx[] {fl, fr, bl, br};
    }

}