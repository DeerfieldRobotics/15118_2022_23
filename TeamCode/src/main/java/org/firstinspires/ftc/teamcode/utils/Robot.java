package org.firstinspires.ftc.teamcode.utils;


import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    
    public Drivetrain drivetrain;
    public Slide slide;
    
    public Robot(HardwareMap hwMap) {
        drivetrain = new Drivetrain(hwMap);
        slide = new Slide(hwMap);
        // -> INSERT INTAKE CLASS
    }
    
}
