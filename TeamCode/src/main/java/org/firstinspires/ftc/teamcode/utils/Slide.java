package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Slide {

    public DcMotorEx s;
    private HardwareMap hw;

    private final float speed = 1;
    //TODO: tune these values
    public final static int LOW = 1234;
    public final static int MEDIUM = 2252;
    public final static int HIGH = 3200;

    public final int[] heights= {0,1234,2252,3480};

    public int targetLevel = 0;


    public final static int cone1 = 0;
//    public final static int cone2 = ;
//    public final static int cone3 = ;
//    public final static int cone4 = ;
//    public final static int cone5 = ;


    int target = 0;

    public Slide (HardwareMap hardwaremap) {
        hw = hardwaremap;

        initialize();
    }

    public void initialize() {
        s = (DcMotorEx) hw.get(DcMotor.class, "s");
        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets current encoder position to zero
        s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        s.setZeroPowerBehavior(DcMotorImplEx.ZeroPowerBehavior.BRAKE); //static power
    }

    public int getCurrentPosition() {
        return s.getCurrentPosition();
    }

    public int getTargetPosition() {
        return s.getTargetPosition();
    }

    public void setTargetLevel(int target) {
        s.setTargetPosition(target);
        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        s.setPower(speed);
    }

    public void setTarget(int target){
        targetLevel = target;
    }

    public void setSlideLevel(int level) {
        if (level == 0) {
            //maybe make it run down until it hits limit switch and then reset
            while(s.getCurrentPosition() >=0){
                s.setPower(-1);
            }
        } else if (level == 1) {
            if(s.getCurrentPosition() < Slide.LOW) {
                while (s.getCurrentPosition() < Slide.LOW) {
                    s.setPower(1);

                }
            } else{
                while (s.getCurrentPosition() > Slide.LOW) {
                    s.setPower(-1);
                }
            }
        } else if (level == 2) {
            if(s.getCurrentPosition() < Slide.MEDIUM) {
                while (s.getCurrentPosition() < Slide.MEDIUM) {
                    s.setPower(1);
                }
            } else{
                while (s.getCurrentPosition() > Slide.MEDIUM) {
                    s.setPower(-1);
                }
            }
        } else if (level == 3) {
            if(s.getCurrentPosition() < Slide.HIGH) {
                while (s.getCurrentPosition() < Slide.HIGH) {
                    s.setPower(1);

                }
            } else{
                while (s.getCurrentPosition() > Slide.HIGH) {
                    s.setPower(-1);
                }
            }
        }
        //s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setConeLevel(int level){
//        if (level == 0) {
//            //maybe make it run down until it hits limit switch and then reset
//            setTargetLevel(0);
//        } else if (level == 1) {
//            setTargetLevel(Slide.cone1);
//        } else if (level == 2) {
//            setTargetLevel(Slide.cone2);
//        } else if (level == 3) {
//            setTargetLevel(Slide.cone3);
//        }else if (level == 4) {
//            setTargetLevel(Slide.cone4);
//        }else if (level == 5) {
//            setTargetLevel(Slide.cone5);
//        }
//
//        s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void move(double amount) {

        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //static power
        s.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        s.setPower(amount);
    }

    public void stop() {
        s.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //static power
        s.setPower(0);
    }
    public void setPower(double pow){
        s.setPower(pow);
    }
    public void resetEncoder() {
        s.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets current encoder position to zero
    }

    public DcMotorEx getMotor() {
        return s;
    }

    public double getAmperage() {
        return s.getCurrent(CurrentUnit.AMPS);
    }

    public void update() {
        if(Math.abs(targetLevel - s.getCurrentPosition()) > 10) {
            s.setTargetPosition(targetLevel);
            s.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            s.setPower(1);
        } else {
            s.setPower(0);
        }
    }
}
