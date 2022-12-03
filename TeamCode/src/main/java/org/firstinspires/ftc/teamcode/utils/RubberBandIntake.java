package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


public class RubberBandIntake {
    private ElapsedTime runtime;
    private HardwareMap hw;
    private CRServoImpl frontServo, backServo;

    private double servoPow = 0.8;

    public RubberBandIntake (HardwareMap hardwaremap) {
        hw = hardwaremap;

        initialize();
    }

    public void initialize() {
        frontServo = (CRServoImpl) hw.get(CRServoImpl.class, "frontBand");
        backServo = (CRServoImpl) hw.get(CRServoImpl.class, "backBand");

        frontServo.setDirection(DcMotorSimple.Direction.REVERSE);

        runtime = new ElapsedTime();
    }

    public void intake(){
        frontServo.setPower(servoPow);
        backServo.setPower(servoPow);
    }

    public void outtake(){
        frontServo.setPower(-servoPow);
        backServo.setPower(-servoPow);

    }

    public void stop(){
        frontServo.setPower(0);
        backServo.setPower(0);
    }

}
