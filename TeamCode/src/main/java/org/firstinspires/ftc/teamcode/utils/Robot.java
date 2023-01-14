package org.firstinspires.ftc.teamcode.utils;


import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public AprilTags aprilT;
    public Drivetrain drivetrain;
    public Slide slide;
    //Init intake class
    
    public Robot(HardwareMap hwMap) {
        initClasses(hwMap);

        drivetrain.stop();
        drivetrain.stop_reset_encoder();
    }

    public void initClasses(HardwareMap hwMap){
        drivetrain = new Drivetrain(hwMap);
        slide = new Slide(hwMap);
        // -> INSERT INTAKE CLASS
        aprilT = new AprilTags(hwMap, "leftCam");
    }

    public int getAprilTag(){
        return aprilT.getID();
    }


    
}
